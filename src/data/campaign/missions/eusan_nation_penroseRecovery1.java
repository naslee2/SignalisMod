package data.campaign.missions;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class eusan_nation_penroseRecovery1 extends HubMissionWithSearch {

    public static enum Stage {
        LOCATE_PENROSE419,
        RETURN_BLACKBOX,
        COMPLETED
    }
    
    protected PersonAPI officer_yeong;
    protected PlanetAPI vineta;
    protected PlanetAPI target_planet;
    protected StarSystemAPI target_starsystem;

    @Override
    protected boolean create(MarketAPI arg0, boolean arg1) {
        if(!setGlobalReference("$eusan_nation_penroseRecovery1_ref")){
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

        if(officer_yeong == null){
            return false;
        }
        if (target_planet == null) {
			return false;
		}
        if(target_planet.isStar()){
            return false;
        }

        setStartingStage(Stage.LOCATE_PENROSE419);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "$eusan_nation_penroseRecovery1_returnBlackbox");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_penroseRecovery1_completed");

        makeImportant(target_planet, "$eusan_nation_penroseRecovery1_targetPlanet", Stage.LOCATE_PENROSE419);

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "$eusan_nation_penroseRecovery1_returnBlackbox", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_penroseRecovery1_completed", true);
        endTrigger();

        setCreditReward(100000);
        setRepRewardPerson(RepRewards.HIGH);
		setRepRewardFaction(RepRewards.HIGH);

        //return false;
        return true;
    }

    protected void updateInteractionDataImpl(){
        set("$eusan_nation_penroseRecovery1_planetId",target_planet.getId());
        set("$eusan_nation_penroseRecovery1_planetName",target_planet.getName());
        set("$eusan_nation_penroseRecovery1_systemName",target_planet.getStarSystem().getNameWithNoType());
        set("$eusan_nation_penroseRecovery1_distanceLy",getDistanceLY(target_planet));
    }
    
    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.LOCATE_PENROSE419){
            info.addPara("A transponder signal has been detected by the Deep Space Antenna Array on Rotfront. It has been identified as the PENROSE-419. Recover the blackbox and any remains of the crew.", opad);
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Blackbox retrieved. Return the data to " + officer_yeong.getNameString() + ".", opad);
        }
    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.LOCATE_PENROSE419){
            info.addPara("A transponder signal has been detected. Locate and recover the Penrose-419's blackbox and any remains of the crew if possible.", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Return to " + officer_yeong.getNameString() + ".", tc, pad);
        }
        return false;
    }

    @Override
    public String getBaseName() {
        return "Recover the mission data recorder from the PENROSE-419";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }
}
