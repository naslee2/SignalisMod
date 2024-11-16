package data.scripts.plugins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;

public class Eusan_Nation_NeuralRepo_MessageIntel extends MessageIntel {
    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height){
        Color h = Misc.getHighlightColor();
        Color tc = Misc.getTextColor();
        Color c = Global.getSector().getFaction("eusan_nation").getColor();
        float pad = 3.0F;
        float opad = 10.0F;
        info.addImage("graphics/portraits/eusan_nation_officer_yeong.png", width, 128.0F, opad);


    }
}
