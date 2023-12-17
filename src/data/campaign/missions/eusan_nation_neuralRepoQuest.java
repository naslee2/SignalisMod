package data.campaign.missions;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_neuralRepoQuest extends HubMissionWithSearch{

    public static enum Stage {
        MEET_AGENT,
        COMPLETED
    }

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

        if(officer_yeong == null){
            return false;
        }

        if (!setGlobalReference("$eusan_nation_neuralrepo_ref")) {
            return false;
        }
       
        setStartingStage(Stage.MEET_AGENT);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        makeImportant(specialAgent, "$eusan_nation_neuralrepo_started", Stage.MEET_AGENT);
        setStageOnGlobalFlag(Stage.COMPLETED, "$eusan_nation_neuralrepo_completed");
        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_neuralrepo_completed", true);

        endTrigger();

        setRepFactionChangesNone();
        setRepPersonChangesNone();
        return true;
        //return false;
    }
    
    protected void updateInteractionDataImpl() {
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.MEET_AGENT){
            info.addPara("Meet with the agent on Vineta and bring back the data to " + officer_yeong.getNameString() + ".", opad);
        }

    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.MEET_AGENT){
            info.addPara("Meet with the agent on Vineta", tc, pad);
            return true;
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
