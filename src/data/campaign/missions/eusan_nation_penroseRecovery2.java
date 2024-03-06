package data.campaign.missions;

import static com.fs.starfarer.api.impl.campaign.ids.FleetTypes.PATROL_MEDIUM;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BattleAPI;
import com.fs.starfarer.api.campaign.CampaignEventListener.FleetDespawnReason;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.FleetEventListener;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.fleets.FleetParamsV3;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_penroseRecovery2 extends HubMissionWithSearch implements FleetEventListener {
    public static enum Stage {
        DEFEAT_HOSTILES,
        LOCATE_PENROSE388,
        RETURN_BLACKBOX,
        COMPLETED
    }

    protected PersonAPI officer_yeong;
    protected PlanetAPI vineta;
    protected PlanetAPI target_planet;
    protected CampaignFleetAPI target_fleet;
    protected StarSystemAPI target_starsystem;
    protected PersonAPI tricach_fleetcommander;

    @Override
    protected boolean create(MarketAPI arg0, boolean arg1) {
        if(!setGlobalReference("$eusan_nation_penroseRecovery2_ref")){
            return false;
        }

        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");

        resetSearch();
        requirePlanetUnpopulated();
		preferPlanetNotFullySurveyed();
		preferPlanetInDirectionOfOtherMissions();
        preferPlanetWithoutRuins();
        preferPlanetNonGasGiant();
        preferSystemUnexplored();
        preferSystemNotPulsar();
        preferSystemNotBlackHole();

        target_planet = pickPlanet();
        target_starsystem = target_planet.getStarSystem();

        if(officer_yeong == null){
            return false;
        }
        if (target_planet == null) {
			return false;
		}
        if(target_planet.isStar()){
            return false;
        }

        //enemy fleet
        FleetParamsV3 hostile_fleetParams = new FleetParamsV3(null, null, Factions.TRITACHYON, null, PATROL_MEDIUM, 50f, 10f, 10f, 10f, 0f, 0f, -10f); 
        hostile_fleetParams.averageSMods = 1;

        target_fleet = FleetFactoryV3.createFleet(hostile_fleetParams);
        target_fleet.setName("Deep Space Patrol Group 41");
        target_fleet.setNoFactionInName(false);
        Misc.makeHostile(target_fleet);
        Misc.makeNoRepImpact(target_fleet, "$eusan_nation");
        //Misc.makeImportant(target_fleet, "$eusan_nation");

        target_fleet.getMemoryWithoutUpdate().set(MemFlags.MEMORY_KEY_MAKE_AGGRESSIVE, "$eusan_nation");
        target_fleet.getMemoryWithoutUpdate().set(MemFlags.MEMORY_KEY_MAKE_AGGRESSIVE_ONE_BATTLE_ONLY, "$eusan_nation");
        target_fleet.getMemoryWithoutUpdate().set(MemFlags.FLEET_IGNORED_BY_OTHER_FLEETS, "$eusan_nation");
        target_fleet.getMemoryWithoutUpdate().set(MemFlags.FLEET_IGNORES_OTHER_FLEETS, "$eusan_nation");

        target_fleet.getMemoryWithoutUpdate().set("$eusan_nation_hostilefleet", true);
        target_fleet.getAI().addAssignment(FleetAssignment.PATROL_SYSTEM, target_starsystem.getCenter(), 200f, null);
        target_fleet.addEventListener(this);
        target_starsystem.addEntity(target_fleet);

        setStartingStage(Stage.DEFEAT_HOSTILES);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        setStageOnGlobalFlag(Stage.LOCATE_PENROSE388, "$eusan_nation_penroseRecovery2_locatePenrose");
        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "$eusan_nation_penroseRecovery2_returnBlackbox");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_penroseRecovery2_completed");

        makeImportant(target_fleet, "$eusan_nation_penroseRecovery2_killFleet", Stage.DEFEAT_HOSTILES);

        beginStageTrigger(Stage.LOCATE_PENROSE388);
        makeImportant(target_planet, "$eusan_nation_penroseRecovery2_targetPlanet", Stage.LOCATE_PENROSE388);
        endTrigger();

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "$eusan_nation_penroseRecovery2_returnBlackbox", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_penroseRecovery2_completed", true);
        endTrigger();

        setCreditReward(100000);
        setRepRewardPerson(RepRewards.HIGH);
		setRepRewardFaction(RepRewards.HIGH);

        

        return true;
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.DEFEAT_HOSTILES){
            info.addPara("Destroy hostile forces in the star system before recovering the Penrose.", opad);
        }
        else if(currentStage == Stage.LOCATE_PENROSE388){
            info.addPara("Locate and recover the blackbox of the PENROSE-488.", opad);
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Blackbox retrieved. Return the data to " + officer_yeong.getNameString() + ".", opad);
        }
    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.DEFEAT_HOSTILES){
            info.addPara("Destroy hostile forces in the star system before recovering the Penrose", tc, pad);
            return true;
        }
        else if(currentStage == Stage.LOCATE_PENROSE388){
            info.addPara("Locate and recover the Penrose-388's blackbox and any remains of the crew if possible.", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Return to " + officer_yeong.getNameString() + ".", tc, pad);
            
        }
        return false;
    }
    
    @Override
    public String getBaseName() {
        return "Recover the mission data recorder from the PENROSE-388";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }

    protected void updateInteractionDataImpl(){
        set("$eusan_nation_penroseRecovery2_planetId", target_planet.getId());
        set("$eusan_nation_penroseRecovery2_planetName", target_planet.getName());
        set("$eusan_nation_penroseRecovery2_systemName", target_planet.getStarSystem().getNameWithNoType());
        set("$eusan_nation_penroseRecovery2_distanceLy", getDistanceLY(target_planet));
    }

    @Override //based off the Internal Affairs example mod
    public void reportBattleOccurred(CampaignFleetAPI fleet, CampaignFleetAPI winner, BattleAPI battle) {
        float distToPlayer = Misc.getDistance(fleet, Global.getSector().getPlayerFleet());
        boolean playerInvolved = battle.isPlayerInvolved() || (fleet.isInCurrentLocation() && distToPlayer < 2000f);

        if (battle.isInvolved(fleet) && !playerInvolved) {
            if (fleet.getFlagship() == null || fleet.getFlagship().getCaptain() != target_fleet) {
                fleet.setCommander(fleet.getFaction().createRandomPerson());
                getPerson().getMemoryWithoutUpdate().set("$eusan_nation_penroseRecovery2_killFleet", true);
                return;
            }
        }

        if (!playerInvolved || !battle.isInvolved(fleet) || battle.onPlayerSide(fleet)) {
            return;
        }

        getPerson().getMemoryWithoutUpdate().set("$eusan_nation_penroseRecovery2_killFleet", true);
    }

    @Override
    public void reportFleetDespawnedToListener(CampaignFleetAPI fleet, FleetDespawnReason winner, Object battle) {
        if (isDone() || result != null) return;

        if(fleet.getMemoryWithoutUpdate().contains("$eusan_nation_hostilefleet")){
            getPerson().getMemoryWithoutUpdate().set("$eusan_nation_penroseRecovery2_killFleet", true);
        }
    }
}
