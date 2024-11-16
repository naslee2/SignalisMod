package data.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import data.scripts.plugins.Eusan_Nation_MessageIntel;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class eusan_nation_neuroRepo_rulecmd extends BaseCommandPlugin {
    static Logger logger = Global.getLogger(eusan_nation_neuroRepo_rulecmd.class);
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if(dialog == null){
            return false;
        }
        if(params.isEmpty()){
            return false;
        }
        String message = params.get(0).getString(memoryMap);
        switch(message){
            case "sendArianeMessage":
                Eusan_Nation_MessageIntel.arianeMessage_neuroRepo();
                logger.info("Logger Active: rulecmd fired1dfs!~!TOOOT");
                break;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occurred!");
                dialog.getTextPanel().addPara("No intel for code: " + message);
                break;
        }

        return true;
    }
}
