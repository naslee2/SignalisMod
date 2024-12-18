package data.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.ui.KeypadPanel;

import java.util.List;
import java.util.Map;

public class KaysaarKeypad extends BaseCommandPlugin {

    protected InteractionDialogAPI dialog;
    protected Map<String, MemoryAPI> memoryMap;
    // initCode : mode where you just showcase already generated code (showcase password)
    // initUI : mode where you initialize proper keypad panel, where player enters password.
    // Example usage in rulescmd in scirpt row: KaysaarKeypad initCode
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        this.dialog = dialog;
        this.memoryMap = memoryMap;

        String command = params.get(0).getString(memoryMap);
        if (command == null) return false;
        if(command.equals("initCode")){
            initializeUI(true);
        }
        if(command.equals("initUI")){
            initializeUI(false);
        }

        return true;
    }
    public void initializeUI(boolean codeMode){
        TooltipMakerAPI tooltip = dialog.getTextPanel().beginTooltip();
        tooltip.addCustom(new KeypadPanel(dialog,200,340,codeMode).getPanel(),5f);
        dialog.getTextPanel().addTooltip();
    }
}
