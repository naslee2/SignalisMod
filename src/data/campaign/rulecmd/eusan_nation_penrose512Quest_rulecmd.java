package data.campaign.rulecmd;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc.Token;

import javax.tools.Tool;

public class eusan_nation_penrose512Quest_rulecmd extends BaseCommandPlugin{
     Color eusanRed = new Color(106,21,28,255);
     String headerLine = "---------------------------------------------------------------------";

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
                TooltipMakerAPI text = dialog.getTextPanel().beginTooltip();
                text.setParaFont("graphics/fonts/silver18.fnt");
                text.setParaFontColor(eusanRed);

                text.addPara(headerLine, 0f).setAlignment(Alignment.MID);
                text.setParaFont("graphics/fonts/silver28.fnt");
                text.addPara("-PENROSE-512-", 5f).setAlignment(Alignment.MID);
                text.setParaFont("graphics/fonts/silver18.fnt");
                text.addPara("SCOUT SYSTEMS VEHICLE STATUS MONITOR", 5f).setAlignment(Alignment.MID);
                text.addPara("STATUS: CRITICAL SYSTEMS FAILURE", 5f);
                text.addPara("LOCATION: UNKNOWN PLANETARY SURFACE", 5f);
                text.addPara("---REPORT---", 10f).setAlignment(Alignment.MID);
                text.addPara("CREW STATUS:", 5f);
                text.addPara("A. YEONG, GESTALT PILOT - NO SIGNAL", 5f).setAlignment(Alignment.LMID);
                text.addPara("LSTR-512, REPLIKA UNIT - NO SIGNAL", 5f).setAlignment(Alignment.LMID);
                text.addPara("HARD LANDING HAS DAMAGED CRITICAL SYSTEMS!", 10f).setAlignment(Alignment.MID);
                text.addPara("/////////////// WARNING //////////////", 15f).setAlignment(Alignment.MID);
                text.addPara("SURFACE TEMPERATURES CRITICALLY LOW!", 5f).setAlignment(Alignment.MID);
                text.addPara("WEAR PROTECTIVE CLOTHING DURING A.V.A.", 5f).setAlignment(Alignment.MID);
                text.addPara("//////////////////////////////////////", 5f).setAlignment(Alignment.MID);
                text.addPara(headerLine, 5f).setAlignment(Alignment.MID);
                dialog.getTextPanel().addTooltip();
                break;
            case "addSystemStatusLockdown":
                TooltipMakerAPI text2b = dialog.getTextPanel().beginTooltip();
                TextPanelAPI text2c = dialog.getTextPanel();
                text2b.setParaFont("graphics/fonts/silver18.fnt");
                text2b.setParaFontColor(eusanRed);

                text2b.addPara(headerLine, 0f).setAlignment(Alignment.MID);
                text2b.setParaFont("graphics/fonts/silver28.fnt");
                text2b.addPara("-PENROSE-512-", 5f).setAlignment(Alignment.MID);
                text2b.setParaFont("graphics/fonts/silver18.fnt");
                text2b.addPara("SCOUT SYSTEMS VEHICLE STATUS MONITOR", 5f).setAlignment(Alignment.MID);
                text2b.addPara("COMPARTMENT LOCKDOWN ACTIVE. SECURITY OVERRIDE NEEDED", 5f).setAlignment(Alignment.MID);
                text2b.addPara(headerLine, 5f).setAlignment(Alignment.MID);
                dialog.getTextPanel().addTooltip();

                text2c.addPara("Your briefing packet mentioned about a security keycard for Penrose vessels. It would take too much time to decrypt the security system. Recovering the keycard would also unlock many of the Penrose-512's compartments.");
                text2c.addPara("Lets hope it wasn't destroyed in the landing...");
                break;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("No intel for this code!");
                break;
        }
        return true; 
    }
    
}
