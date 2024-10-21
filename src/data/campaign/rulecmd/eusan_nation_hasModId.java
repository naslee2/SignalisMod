package data.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;

import java.util.List;
import java.util.Map;

public class eusan_nation_hasModId extends BaseCommandPlugin{

    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        
        String modId = params.get(0).getString(memoryMap);
        Boolean modChecker = Global.getSettings().getModManager().isModEnabled(modId);
        if(dialog == null){
            return false;
        }
        switch(modId){
            case "sbr":
                //checks if San Bativa Republic is active.
                return modChecker;
            case "PAGSM":
                //checks if Phillip Andrada Gas Station Manager is active.
                return modChecker;
            case "uaf":
                //checks if United Aurora Federation is active
                return modChecker;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("The mod id is invalid or does not exist for: " + modId);
                break;
            }
        return true;
    }
    
}
