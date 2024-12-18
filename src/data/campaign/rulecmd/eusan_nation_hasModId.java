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
        if(params.isEmpty()){
            return false;
        }
        if(dialog == null){
            return false;
        }
        return Global.getSettings().getModManager().isModEnabled(params.get(0).getString(memoryMap));
    }
    
}
