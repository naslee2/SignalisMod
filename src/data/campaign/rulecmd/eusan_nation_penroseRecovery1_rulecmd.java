package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;

import data.campaign.missions.eusan_nation_penroseRecovery1;

public class eusan_nation_penroseRecovery1_rulecmd extends BaseCommandPlugin{

    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
    if(dialog == null){
        return false;
    }
    if(params.isEmpty()){
        return false;
    }
    String ruleCall = params.get(0).getString(memoryMap);

    if(ruleCall.equals("playSignal_penroseRecovery1")){
        Global.getSoundPlayer().playCustomMusic(1, 1, "", true);
    }
    else if(ruleCall.equals( "endMusic_penroseRecovery1")){
        Global.getSoundPlayer().setSuspendDefaultMusicPlayback(true);
        Global.getSoundPlayer().pauseMusic();
    }
    else if(ruleCall.equals( "resumeMusic_penroseRecovery1")){
        Global.getSoundPlayer().setSuspendDefaultMusicPlayback(false);
		Global.getSoundPlayer().restartCurrentMusic();
    }
    else{
        dialog.getTextPanel().addPara("Uh oh an error has occured!");
        dialog.getTextPanel().addPara("No intel for code: " + ruleCall);
    }
           
    return true;
    }   
}
