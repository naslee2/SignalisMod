package data.campaign.missions;

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
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.fleets.FleetParamsV3;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import data.scripts.plugins.Eusan_Nation_SystemNotHiddenReq;
//import org.apache.log4j.Logger;

import data.scripts.plugins.Eusan_Nation_FleetScaler;

public class eusan_nation_penroseRecovery2 extends HubMissionWithSearch implements FleetEventListener {
    //Logger logger = Global.getLogger(eusan_nation_penroseRecovery2.class);

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
    protected PersonAPI tritach_fleetcommander;
    
    public int playerFleetDP = Global.getSector().getPlayerFleet().getFleetPoints();
    public String enemyFaction = Factions.TRITACHYON;
    protected int enemyCaptials;
    protected int enemyCrusiers;
    protected int enemyDestroyers;
    protected int enemyFrigates;
    
    @Override
    protected boolean create(MarketAPI arg0, boolean arg1) {
        if(!setGlobalReference("$eusan_nation_penroseRecovery2_ref")){
            return false;
        }

        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");

        resetSearch();
        requirePlanetUnpopulated();
        requirePlanetNotStar();
		preferPlanetNotFullySurveyed();
		preferPlanetInDirectionOfOtherMissions();
        preferPlanetWithoutRuins();
        preferPlanetNonGasGiant();
        preferSystemUnexplored();
        preferSystemNotPulsar();
        preferSystemNotBlackHole();
        search.systemReqs.add(new Eusan_Nation_SystemNotHiddenReq());

        target_planet = pickPlanet();
        target_starsystem = target_planet.getStarSystem();

        if(officer_yeong == null){
            return false;
        }

        tritach_fleetcommander = Global.getSector().getFaction(Factions.TRITACHYON).createRandomPerson();
        tritach_fleetcommander.setRankId(Ranks.SPACE_COMMANDER);
        tritach_fleetcommander.setPostId(Ranks.POST_PATROL_COMMANDER);
        tritach_fleetcommander.getMemoryWithoutUpdate().set("$tritach_fleetcommander", true);

        //data sent to fleetScaler
        Eusan_Nation_FleetScaler fleetData = new Eusan_Nation_FleetScaler(playerFleetDP, enemyFaction);
        //logger.info("Logger Active: Fleet DP points is equal to: " + scalerData.fleetScaler());

        //enemy fleet
        CampaignFleetAPI target_fleet = fleetData.fleetScaler();
        //FleetParamsV3 hostile_fleetParams = new FleetParamsV3(null, null, Factions.TRITACHYON, null, fleetType, 100f, 10f, 10f, 0f, 0f, 0f, 10f);
        
        //hostile_fleetParams.averageSMods = 1;

        //target_fleet = FleetFactoryV3.createFleet(hostile_fleetParams);

        enemyCaptials = target_fleet.getNumCapitals();
        enemyCrusiers = target_fleet.getNumCruisers();
        enemyDestroyers = target_fleet.getNumDestroyers();
        enemyFrigates = target_fleet.getNumFighters();
        
        target_fleet.setName("Deep Space Patrol Group 41");
        target_fleet.setNoFactionInName(false);
        target_fleet.setCommander(tritach_fleetcommander);
        target_fleet.getFlagship().setCaptain(tritach_fleetcommander);
        target_fleet.getCommander().getStats().setLevel(5);
        target_fleet.getCommander().getStats().setSkillLevel(Skills.HELMSMANSHIP, 1);
        target_fleet.getCommander().getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
        target_fleet.getCommander().getStats().setSkillLevel(Skills.POINT_DEFENSE, 1);
        target_fleet.getCommander().getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 1);
        target_fleet.getCommander().getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 1);

        Misc.makeHostile(target_fleet);
        Misc.makeNoRepImpact(target_fleet, "$eusan_nation");
        Misc.makeImportant(target_fleet, "$eusan_nation");

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

        setStageOnMemoryFlag(Stage.LOCATE_PENROSE388, officer_yeong, "$eusan_nation_penroseRecovery2_killFleet");
        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "$eusan_nation_penroseRecovery2_returnBlackbox");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_penroseRecovery2_completed");

        makeImportant(target_fleet, "$eusan_nation_penroseRecovery2_targetSystem", Stage.DEFEAT_HOSTILES);

        beginStageTrigger(Stage.LOCATE_PENROSE388);
        makeImportant(target_planet, "$eusan_nation_penroseRecovery2_targetPlanet", Stage.LOCATE_PENROSE388);
        endTrigger();

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "$eusan_nation_penroseRecovery2_returnBlackbox", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_penroseRecovery2_completed", true);
        endTrigger();

        
        int totalReward = rewardScaleer(enemyCaptials, enemyCrusiers, enemyDestroyers, enemyFrigates);
        setCreditReward(totalReward);
        setRepRewardPerson(RepRewards.VERY_HIGH);
		setRepRewardFaction(RepRewards.VERY_HIGH);

        return true;
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.DEFEAT_HOSTILES){
            info.addPara("Destroy hostile forces in the " + target_starsystem.getNameWithNoType() + " star system before recovering the Penrose.", opad);
        }
        else if(currentStage == Stage.LOCATE_PENROSE388){
            info.addPara("Locate and recover the blackbox of the PENROSE-388.", opad);
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Blackbox retrieved. Return the data to " + officer_yeong.getNameString() + ".", opad);
        }
    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.DEFEAT_HOSTILES){
            info.addPara("Destroy hostile forces in the " + target_starsystem.getNameWithLowercaseTypeShort() + " star system before recovering the Penrose-388.", tc, pad);
            return true;
        }
        else if(currentStage == Stage.LOCATE_PENROSE388){
            info.addPara("Locate and recover the Penrose-388's blackbox and any remains of the crew if possible. The location has been determined to be at " + target_planet.getName() + " in the " + target_planet.getStarSystem().getNameWithNoType() + " system.", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Blackbox retrieved. Return the data to " + officer_yeong.getNameString() + ".", tc, pad);
            
        }
        return false;
    }
    
    @Override
    public String getBaseName() {
        return "Recover the PENROSE-388's mission data recorder.";
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
        set("$eusan_nation_penroseRecovery2_tritach_fleetcommanderName", tritach_fleetcommander.getNameString());
    }

    @Override //based off the Internal Affairs example mod
    public void reportBattleOccurred(CampaignFleetAPI fleet, CampaignFleetAPI winner, BattleAPI battle) {
        if (isDone() || result != null) return;

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

        if (fleet.getFlagship() != null && fleet.getFlagship().getCaptain() == target_fleet) return;

        getPerson().getMemoryWithoutUpdate().set("$eusan_nation_penroseRecovery2_killFleet", true);
    }

    @Override
    public void reportFleetDespawnedToListener(CampaignFleetAPI fleet, FleetDespawnReason winner, Object battle) {
        if (isDone() || result != null) return;

        if(fleet.getMemoryWithoutUpdate().contains("$eusan_nation_hostilefleet")){
            getPerson().getMemoryWithoutUpdate().set("$eusan_nation_penroseRecovery2_killFleet", true);
        }
    }

    public int rewardScaleer(int numCaptials, int numCruisers, int numDestroyers, int numFrigates){
        int reward = 100000;

        for(int i = 0; i < numCaptials; i++){
            reward = reward + 750000; 
        }
        for(int i = 0; i < numCruisers; i++){
            reward = reward + 65000; 
        }
        for(int i = 0; i < numDestroyers; i++){
            reward = reward + 55000; 
        }
        for(int i = 0; i < numFrigates; i++){
            reward = reward + 45000; 
        }

        return reward;
    }

}
