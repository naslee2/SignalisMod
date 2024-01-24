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

public class eusan_nation_penroseRecovery2 extends HubMissionWithSearch {
    public static enum Stage {
        DEFEAT_HOSTILES,
        LOCATE_PENROSE488,
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

        setStartingStage(Stage.DEFEAT_HOSTILES);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        return true;
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.DEFEAT_HOSTILES){
            info.addPara("Destroy hostile forces in the star system before recovering the Penrose.", opad);
        }
        else if(currentStage == Stage.LOCATE_PENROSE488){
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
        else if(currentStage == Stage.LOCATE_PENROSE488){
            info.addPara("Locate and recover the Penrose-488's blackbox and any remains of the crew if possible.", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Return to " + officer_yeong.getNameString() + ".", tc, pad);
            
        }
        return false;
    }
    
    @Override
    public String getBaseName() {
        return "Recover the mission data recorder from the PENROSE-488";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }
}
