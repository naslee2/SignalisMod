package data.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Eusan_Nation_OfficerSkillListing extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if(params.isEmpty()){
            return false;
        }

        String personId = params.get(0).getString(memoryMap);
        PersonAPI personData = Global.getSector().getImportantPeople().getPerson(personId);

        if(personData == null){
            return false;
        }

        //MutableCharacterStatsAPI skillData = personData.getStats();
        TextPanelAPI text = dialog.getTextPanel();

        //Color hl = Misc.getHighlightColor();
        //Color red = Misc.getNegativeHighlightColor();

        text.addSkillPanel(personData, false);

        text.setFontInsignia();

        return false;
    }
}
