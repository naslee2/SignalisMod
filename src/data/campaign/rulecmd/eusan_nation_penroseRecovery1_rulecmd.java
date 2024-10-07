package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;

public class eusan_nation_penroseRecovery1_rulecmd extends BaseCommandPlugin{

    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        if(dialog == null){
            return false;
        }
        if(params.isEmpty()){
            return false;
        }
        String ruleCall1 = params.get(0).getString(memoryMap);

        switch(ruleCall1){
            case "playSignal_penroseRecovery1":
                Global.getSoundPlayer().playCustomMusic(1, 1, "penrose_419_SOS", true);
                break;
            case "endMusic_penroseRecovery1":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(true);
                //Global.getSoundPlayer().pauseMusic();
                break;
            case "resumeMusic_penroseRecovery1":
                //Global.getSoundPlayer().setSuspendDefaultMusicPlayback(false);
                Global.getSoundPlayer().restartCurrentMusic();
                break;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("No intel for code: " + ruleCall1);
                break;
        }
           
    return true;
    }   
}
