package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddRemoveCommodity;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.util.Misc.Token;

public class eusan_nation_penrose512Quest_rulecmd extends BaseCommandPlugin{

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        if(dialog == null){
            return false;
        }
        if(params.isEmpty()){
            return false;
        }
        String ruleCall512 = params.get(0).getString(memoryMap);

        switch(ruleCall512){
            case "playSignal_512":
                Global.getSoundPlayer().playCustomMusic(1, 1, "penrose_512_SOS", true);
                break;
            case "endMusic_512":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(true);
                //Global.getSoundPlayer().pauseMusic();
                break;
            case "resumeMusic_512":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(false);
                Global.getSoundPlayer().restartCurrentMusic();
                break;
            case "addSystemStatusActive":
                TextPanelAPI text = dialog.getTextPanel();
                break;
            case "addSystemStatusLockdown":
                TextPanelAPI text2 = dialog.getTextPanel();
                text2.addPara("Your briefing packet mentioned about a security keycard for Penrose vessels. It would take too much time to decrypt the security system. Recovering the keycard would also unlock many of the Penrose-512's compartments.%n");
                text2.addPara("Lets hope it wasn't destroyed in the landing...");
                break;
            case "":
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("No intel for code: " + ruleCall512);
                break;
        }

        return true; 
    }
    
}
