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
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.fleets.FleetParamsV3;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.impl.campaign.missions.hub.ReqMode;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_penrose512Quest extends HubMissionWithSearch{

    public static enum Stage {
        LOCATE_PENROSE512,
        RETURN_BLACKBOX,
        COMPLETED
    }

    protected PersonAPI officer_yeong;
    protected PlanetAPI vineta;
    protected PlanetAPI target_planet;
    protected StarSystemAPI target_starsystem;
    protected String[] conditionsList;

	@Override
	protected boolean create(MarketAPI arg0, boolean arg1) {
        if(!setGlobalReference("$eusan_nation_penrose512_ref")){
            return false;
        }
        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");
        conditionsList = new String[] {Conditions.VERY_COLD};

        resetSearch();
        requirePlanetUnpopulated();
        requirePlanetNotStar();
		preferPlanetNotFullySurveyed();
		preferPlanetInDirectionOfOtherMissions();
        preferPlanetUnexploredRuins();
        preferPlanetNonGasGiant();
        preferSystemUnexplored();
        preferSystemNotPulsar();
        preferSystemNotBlackHole();
        requirePlanetConditions(ReqMode.ANY, conditionsList);

        target_planet = pickPlanet();
        target_starsystem = target_planet.getStarSystem();

        if(officer_yeong == null){
            return false;
        }
        if (target_planet == null) {
			return false;
		}

        setStartingStage(Stage.LOCATE_PENROSE512);
        addSuccessStages(Stage.RETURN_BLACKBOX);

        setStoryMission();

        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "$eusan_nation_penrose512_returnBlackbox");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_penrose512_completed");

        beginStageTrigger(Stage.LOCATE_PENROSE512);
        makeImportant(target_planet, "$eusan_nation_penrose512_targetPlanet", Stage.LOCATE_PENROSE512);
        endTrigger();

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "$eusan_nation_penrose512_returnBlackbox", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_penrose512_completed", true);
        endTrigger();

        setCreditReward(100000);
        setRepRewardPerson(RepRewards.HIGH);
		setRepRewardFaction(RepRewards.HIGH);

        return false;
	}
    
}
