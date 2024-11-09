package data.scripts.plugins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;

import org.apache.log4j.Logger;

public class Eusan_Nation_MessageIntel {
    static Logger logger = Global.getLogger(Eusan_Nation_MessageIntel.class);
    public static void arianeMessage_neuroRepo(){
        if (Global.getSector().getMemoryWithoutUpdate().getBoolean("$officeryeong_neuralrepositorystart"))
            logger.info("Logger Active: ref memory is active and fired!");

    }
}
