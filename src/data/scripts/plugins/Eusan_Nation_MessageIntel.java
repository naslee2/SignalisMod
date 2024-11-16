package data.scripts.plugins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;

import com.fs.starfarer.coreui.U;
import org.apache.log4j.Logger;

public class Eusan_Nation_MessageIntel {
    static Logger logger = Global.getLogger(Eusan_Nation_MessageIntel.class);
    public static void arianeMessage_neuroRepo(){
        if (Global.getSector().getMemoryWithoutUpdate().getBoolean("$officeryeong_neuralrepositorystart")){
            MessageIntel arianeMessage_neuroRepo_a = new MessageIntel("Message from Ariane",Misc.getBasePlayerColor());
            arianeMessage_neuroRepo_a.setIcon("graphics/portraits/eusan_nation_officer_yeong.png");
            arianeMessage_neuroRepo_a.addLine("\"I sent word to our agent on Vineta. He is expecting you. If you succeed in this very simple task, the Nation will be grateful of course.\"");
            Global.getSector().getIntelManager().addIntel(arianeMessage_neuroRepo_a);

            logger.info("Logger Active: Message Fired!");
        }


    }
}

