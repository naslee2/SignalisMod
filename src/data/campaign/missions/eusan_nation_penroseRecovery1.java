package data.campaign.missions;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_penroseRecovery1 extends HubMissionWithSearch {

    public static enum Stage {
        LOCATE_PENROSE,
        RETURN_BLACKBOX,
        COMPLETED
    }
    
    protected PersonAPI officer_yeong;
    protected PlanetAPI vineta;
    protected PlanetAPI target_planet;
    protected StarSystemAPI target_starsystem;

    @Override
    protected boolean create(MarketAPI arg0, boolean arg1) {
        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");

        requirePlanetUnpopulated();
		preferPlanetNotFullySurveyed();
		preferPlanetInDirectionOfOtherMissions();
        target_planet = pickPlanet();

        if(officer_yeong == null){
            return false;
        }
        if (target_planet == null) {
			return false;
		}
        if(!setGlobalReference("$eusan_nation_penroseRecovery1_ref")){
            return false;
        }

        setPersonOverride(officer_yeong);

        setStartingStage(Stage.LOCATE_PENROSE);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "");
        setStageOnGlobalFlag(Stage.COMPLETED, "");

        makeImportant(target_planet, "", Stage.LOCATE_PENROSE);

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("", true);
        endTrigger();

        setCreditReward(50000);

        //return false;
        return true;
    }
    
}
