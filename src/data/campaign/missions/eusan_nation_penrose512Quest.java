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


import data.scripts.plugins.Eusan_Nation_PlanetColdTypeRequirement;
import data.scripts.plugins.Eusan_Nation_SystemNotHiddenReq;

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
    //protected String[] conditionsList;
    //protected String[] planetTypeList;

	@Override
	protected boolean create(MarketAPI arg0, boolean arg1) {
        if(!setGlobalReference("$eusan_nation_penrose512_ref")){
            return false;
        }
        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");
        //conditionsList = new String[] {Conditions.VERY_COLD, Conditions.COLD};
        //planetTypeList = new String[] {Planets.FROZEN, Planets.ROCKY_ICE, Planets.CRYOVOLCANIC};

        
        resetSearch();
        requirePlanetUnpopulated();
        requirePlanetNotStar();
		requirePlanetNotFullySurveyed();
        requirePlanetUnexploredRuins();
        requirePlanetNotGasGiant();
        requireSystemUnexplored();
        requireSystemNotHasPulsar();
        requireSystemNotBlackHole();
        requireSystemOnFringeOfSector();
        search.systemReqs.add(new Eusan_Nation_SystemNotHiddenReq());
        search.planetReqs.add(new Eusan_Nation_PlanetColdTypeRequirement());
        

        target_planet = pickPlanet();
        target_starsystem = target_planet.getStarSystem();

        if(officer_yeong == null){
            return false;
        }

        setStartingStage(Stage.LOCATE_PENROSE512);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        setStageOnGlobalFlag(Stage.RETURN_BLACKBOX, "$eusan_nation_penrose512_returnBlackbox");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_penrose512_completed");

        makeImportant(target_planet, "$eusan_nation_penrose512_targetPlanet", Stage.LOCATE_PENROSE512);

        beginStageTrigger(Stage.RETURN_BLACKBOX);
        makeImportant(officer_yeong, "$eusan_nation_penrose512_returnBlackbox", Stage.RETURN_BLACKBOX);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_penrose512_completed", true);
        endTrigger();

        setCreditReward(100000);
        setRepRewardPerson(RepRewards.HIGH);
		setRepRewardFaction(RepRewards.HIGH);

        return true;
	}

    protected void updateInteractionDataImpl(){
        set("$eusan_nation_penrose512_planetId", target_planet.getId());
        set("$eusan_nation_penrose512_planetName", target_planet.getName());
        set("$eusan_nation_penrose512_systemName", target_planet.getStarSystem().getNameWithNoType());
        set("$eusan_nation_penrose512_distanceLy", getDistanceLY(target_planet));
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.LOCATE_PENROSE512){
            info.addPara("Locate the Penrose 512 in the  " + target_starsystem.getNameWithNoType() + " and investigate the crashed spacecraft.", opad);
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Investigation complete. Return the data to " + officer_yeong.getNameString() + ".", opad);
        }
    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.LOCATE_PENROSE512){
            info.addPara("Locate the Penrose 512 at the " + target_starsystem.getNameWithLowercaseTypeShort() + " ", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_BLACKBOX){
            info.addPara("Return to " + officer_yeong.getNameString() +  " with your findings.", tc, pad);
            return true;
        }
        return false;
    }
    
    @Override
    public String getBaseName() {
        return "Investigate the Penrose-512";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }
}