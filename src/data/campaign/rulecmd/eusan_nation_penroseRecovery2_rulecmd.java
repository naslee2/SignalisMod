package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddRemoveCommodity;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.util.Misc.Token;
import org.apache.log4j.Logger;

public class eusan_nation_penroseRecovery2_rulecmd extends BaseCommandPlugin{
    //Logger logger = Global.getLogger(eusan_nation_penroseRecovery2_rulecmd.class);

    protected PersonAPI mary_cheng;
    protected CampaignFleetAPI playerFleet;

    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        if(dialog == null){
            return false;
        }
        if(params.isEmpty()){
            return false;
        }
        String ruleCall2 = params.get(0).getString(memoryMap);
        playerFleet = Global.getSector().getPlayerFleet();
        mary_cheng = Global.getSector().getImportantPeople().getPerson("eusan_nation_mary_cheng");
        //logger.info("Logger Active: mary cheng info " + mary_cheng);
        switch(ruleCall2){
            case "playSignal_penroseRecovery2":
                Global.getSoundPlayer().playCustomMusic(1, 1, "penrose_388_SOS", true);
                break;
            case "endMusic_penroseRecovery2":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(true);
                //Global.getSoundPlayer().pauseMusic();
                break;
            case "resumeMusic_penroseRecovery2":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(false);
                Global.getSoundPlayer().restartCurrentMusic();
                break;
            case "add_officer_penroseRecovery2":
                //logger.info("Logger Active: mary cheng's rankid is " + mary_cheng.getRankId());
                //logger.info("Logger Active: mary cheng's rank is " + mary_cheng.getRank());
                //logger.info("Logger Active: mary cheng's postid is " + mary_cheng.getPostId());
                //logger.info("Logger Active: mary cheng's post is " + mary_cheng.getPost());
                playerFleet.getFleetData().addOfficer(mary_cheng);
                AddRemoveCommodity.addOfficerGainText(mary_cheng, dialog.getTextPanel());
                break;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("No intel for code: " + ruleCall2);
                break;
        }
        
    return true;
    }
    
}
