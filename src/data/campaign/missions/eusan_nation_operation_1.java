package data.campaign.missions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.campaign.ids.Eusan_Nation_PeopleStrings;

import java.awt.*;

public class eusan_nation_operation_1 extends HubMissionWithSearch {

    public enum Stage {
        TALK_TO_AGENT,
        LOCATE_HVT,
        RETURN_TO_HEIMAT,
        COMPLETED
    }

    protected PersonAPI admiral_falke;
    protected PersonAPI specialAgent;
    protected PersonAPI hvt;
    protected MarketAPI kazeron;
    protected MarketAPI eldfell;

    @Override
    protected boolean create(MarketAPI createdAt, boolean barEvent) {

        kazeron = Global.getSector().getEconomy().getMarket("kazeron");
        eldfell = Global.getSector().getEconomy().getMarket("eldfell");
        admiral_falke = getImportantPerson(Eusan_Nation_PeopleStrings.ADMIRAL_FALKE);
        specialAgent = getImportantPerson(Eusan_Nation_PeopleStrings.SPECIAL_AGENT);
        hvt = getImportantPerson(Eusan_Nation_PeopleStrings.HVT);

        eldfell.getCommDirectory().addPerson(specialAgent);

        if(admiral_falke == null){
            return false;
        }

        if (!setGlobalReference("$eusan_nation_operation1_ref")) {
            return false;
        }

        if (!setPersonMissionRef(specialAgent, "$eusan_nation_operation1_ref")) {
            return false;
        }

        setStartingStage(Stage.TALK_TO_AGENT);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        setStageOnMemoryFlag(Stage.TALK_TO_AGENT,admiral_falke,"$eusan_nation_operation1_talk_to_agent");
        setStageOnGlobalFlag(Stage.LOCATE_HVT,"$eusan_nation_operation1_locate_hvt");
        setStageOnGlobalFlag(Stage.RETURN_TO_HEIMAT,"$eusan_nation_operation1_return_to_heimat");
        setStageOnGlobalFlag(Stage.COMPLETED,"$eusan_nation_operation1_competed");


        makeImportant(specialAgent,"$eusan_nation_operation1_eldfell",Stage.TALK_TO_AGENT);

        beginStageTrigger(Stage.LOCATE_HVT);
        kazeron.getCommDirectory().addPerson(hvt);
        triggerHideCommListing(specialAgent);
        makeImportant(hvt, "$eusan_nation_operation1_locate_hvt", Stage.LOCATE_HVT);
        endTrigger();

        beginStageTrigger(Stage.RETURN_TO_HEIMAT);
        triggerHideCommListing(hvt);
        makeImportant(admiral_falke,"$eusan_nation_operation1_return_to_heimat",Stage.RETURN_TO_HEIMAT);
        endTrigger();

        beginStageTrigger(Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$eusan_nation_operation1_completed", true);
        endTrigger();

        setCreditReward(50000);
        setRepRewardPerson(0.15f);
        setRepRewardFaction(CoreReputationPlugin.RepRewards.EXTREME);

        return true;
    }
    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad){
        if(currentStage == Stage.TALK_TO_AGENT){
            info.addPara("Meet with the agent on Eldfell", tc, pad);
            return true;
        }
        if(currentStage == Stage.LOCATE_HVT){
            info.addPara("Locate the HVT at Kazeron", tc, pad);
            return true;
        }
        else if(currentStage == Stage.RETURN_TO_HEIMAT){
            info.addPara("Return to " + admiral_falke.getNameString() + ".", tc, pad);
        }
        return false;
    }

    protected void updateInteractionDataImpl() {
        set("$eusan_nation_operation1_stage", getCurrentStage());
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height){
        float opad = 10f;
        if(currentStage == Stage.TALK_TO_AGENT) {
            info.addPara("Meet with the " +specialAgent.getNameString(), opad);
        }
        if(currentStage == Stage.LOCATE_HVT) {
            info.addPara("Take the HVT into custody", opad);
        }
        else if(currentStage == Stage.RETURN_TO_HEIMAT){
            info.addPara("The HVT is secured. Return him to Heimat.", opad);
        }
    }

    @Override
    public String getBaseName() {
        return "Extract the HVT";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }

}
