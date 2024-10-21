package data.campaign.rulecmd;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;

public class eusan_nation_penrose512Quest_rulecmd extends BaseCommandPlugin{
     Color eusanRed = new Color(106,21,28,255);
     String headerLine = "----------------------------------------------------------------------------------------";

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
                text.setFontVictor();
                text.addPara(headerLine, eusanRed);
                text.addPara("                     -PENROSE-512-", eusanRed);
                text.addPara("            SCOUT SYSTEMS VEHICLE STATUS MONITOR", eusanRed);
                text.addPara("STATUS: CRITICAL SYSTEMS FAILURE", eusanRed);
                text.addPara("LOCATION: UNKNOWN PLANETARY SURFACE", eusanRed);
                text.addPara("");
                text.addPara("                       ---REPORT---", eusanRed);
                text.addPara("CREW STATUS:", eusanRed);
                text.addPara("  A. YEONG, GESTALT PILOT - NO SIGNAL", eusanRed);
                text.addPara("  LSTR-512, REPLIKA UNIT - NO SIGNAL", eusanRed);
                text.addPara("");
                text.addPara("         HARD LANDING HAS DAMAGED CRITICAL SYSTEMS!            ", eusanRed);
                text.addPara("");
                text.addPara("                 /////////////WARNING////////////             ", eusanRed);
                text.addPara("          SURFACE TEMPERATURES CRITICALLY LOW!                 ", eusanRed);
                text.addPara("         WEAR PROTECTIVE CLOTHING DURING A.V.A.                ", eusanRed);
                text.addPara("                 //////////////////////////////////////", eusanRed);
                text.addPara(headerLine, eusanRed);
                text.setFontInsignia();
                break;
            case "addSystemStatusLockdown":
                TextPanelAPI text2b = dialog.getTextPanel();
                TextPanelAPI text2c = dialog.getTextPanel();

                text2b.setFontVictor();
                text2b.addPara(headerLine, eusanRed);
                text2b.addPara("                      -PENROSE-512-", eusanRed);
                text2b.addPara("           SCOUT SYSTEMS VEHICLE STATUS MONITOR", eusanRed);
                text2b.addPara("   COMPARTMENT LOCKDOWN ACTIVE. SECURITY OVERRIDE NEEDED", eusanRed);
                text2b.addPara(headerLine, eusanRed);
                text2b.setFontInsignia();

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
