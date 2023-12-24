package data.campaign.missions;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_neuralRepoQuest extends HubMissionWithSearch{

    // mission stages. 
    public static enum Stage {
        MEET_AGENT,
        RETURN_INFO,
        COMPLETED
    }
    // important people and planets
    protected PersonAPI officer_yeong;
    protected PersonAPI specialAgent;
    protected PlanetAPI vineta;

    @Override
    protected boolean create(MarketAPI createdAt, boolean event) {
        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        specialAgent =  getImportantPerson("eusan_nation_special_agent");

        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");

        MarketAPI vineta_market = Global.getSector().getEconomy().getMarket("vineta_market");
        vineta_market.getCommDirectory().addPerson(specialAgent);
        //vineta_market.getCommDirectory().getEntryForPerson(specialAgent).isHidden();
        if(officer_yeong == null){
            return false;
        }

        if (!setPersonMissionRef(specialAgent, "$intaff_ref")) {
            return false;
        }

        if (!setGlobalReference("$eusan_nation_neuralrepo_ref")) {
            return false;
        }

        setPersonOverride(officer_yeong);
       
        setStartingStage(Stage.MEET_AGENT);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();
        
        setStageOnGlobalFlag(Stage.RETURN_INFO, "$eusan_nation_neuralrepo_returnInfo");
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_neuralrepo_completed");

        //beginStageTrigger(Stage.MEET_AGENT);
        makeImportant(specialAgent, "$eusan_nation_neuralrepo_started", Stage.MEET_AGENT);
        //endTrigger();
        
        beginStageTrigger(Stage.RETURN_INFO);
        triggerHideCommListing(specialAgent);
        makeImportant(officer_yeong, "$eusan_nation_neuralrepo_returnInfo", Stage.RETURN_INFO);
        endTrigger();

        
        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_neuralrepo_completed", true);
        endTrigger();

        //setRepFactionChangesNone();
        //setRepPersonChangesNone();
        setCreditReward(50000);
        //setRepRewardPerson(RepRewards.HIGH);
		//setRepRewardFaction(RepRewards.HIGH);

        setPersonIsPotentialContactOnSuccess(officer_yeong, 1f);
        
        return true;
        //return false;
    }
    
    protected void updateInteractionDataImpl() {
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.MEET_AGENT){
            info.addPara("Meet with the " + specialAgent.getNameString() + " on Vineta.", opad);
        }
        else if(currentStage == Stage.RETURN_INFO){
            info.addPara("Successfully retrieved data. Now to return the data to " + officer_yeong.getNameString() + ".",opad);
        }

    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.MEET_AGENT){
            info.addPara("Meet with the agent on Vineta", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_INFO){
            info.addPara("Return to " + officer_yeong.getNameString() + ".", tc, pad);
        }
        return false;
    }

    @Override
    public String getBaseName() {
        return "Neural Repository Quest";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }
}
