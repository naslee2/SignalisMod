package data.campaign.intel.events;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseFactorTooltip;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseHostileActivityCause2;
import com.fs.starfarer.api.impl.campaign.intel.events.HostileActivityEventIntel;
import com.fs.starfarer.api.impl.campaign.intel.events.LuddicChurchHostileActivityFactor;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI.*;
import com.fs.starfarer.api.util.Misc;

public class Eusan_Nation_HostileActivityCause extends BaseHostileActivityCause2 {

    public Eusan_Nation_HostileActivityCause(HostileActivityEventIntel intel) {
        super(intel);
    }

    //@Override
    public TooltipCreator getTooltip() {
        return new BaseFactorTooltip() {
            public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
                float opad = 10f;
                tooltip.addPara("Negotiating with the Luddic Church about controlling the "
                                + "immigration of the faithful to your colonies "
                                + "is likely to get this harassment to stop. If "
                                + "left unchecked, this low-grade conflict will eventually come to a head and is likely to "
                                + "be resolved one way or another.", 0f, Misc.getHighlightColor(),
                        "Negotiating");

                FactionAPI f = Global.getSector().getFaction("eusan_nation");
                tooltip.addPara("Event progress value is based on the number and size of colonies "
                                + "with the \"Luddic Path Cells\" condition.", opad, f.getBaseUIColor(),
                        "Luddic Path Cells");
            }
        };
    }
}
