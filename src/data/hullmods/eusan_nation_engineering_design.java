package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.Color;

import org.magiclib.util.MagicIncompatibleHullmods;

public class eusan_nation_engineering_design extends BaseLogisticsHullMod{
    public static float CR_RECOVERY_BONUS = 50f;
    public static float REPAIR_RATE_BONUS = 50f;
    public static float OVERLOAD_BONUS = 20f;

    static String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    static String eusan_nation_engineering_design1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText1");
    static String eusan_nation_engineering_design2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText2");
	static String eusan_nation_engineering_design3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText3");
    static String eusan_nation_engineering_design4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText4");
    static String eusan_nation_engineering_design5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText5");

    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
        stats.getBaseCRRecoveryRatePercentPerDay().modifyPercent(id, CR_RECOVERY_BONUS);
		stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_RATE_BONUS);
        stats.getOverloadTimeMod().modifyMult(id, 1f - (OVERLOAD_BONUS * 0.01f));
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
        //if someone tries to install incompatible hullmods, remove it.
        if(ship.getVariant().getHullMods().contains(HullMods.AUTOREPAIR)){
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.AUTOREPAIR, "Eusan Engineering");
        }
	}

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
		if (index == 1) return "" + (int) Math.round(CR_RECOVERY_BONUS) + "%";
        return null;
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color flavor = new Color(110,110,110,255);

        tooltip.addSectionHeading(detailText, Alignment.MID, 10.0F);

        tooltip.addPara(eusan_nation_engineering_design1, 10.0f, Misc.getHighlightColor(), (int) Math.round(REPAIR_RATE_BONUS) + "%");
        tooltip.addPara(eusan_nation_engineering_design2, 10.0f, Misc.getHighlightColor(), (int) Math.round(CR_RECOVERY_BONUS) + "%");
        tooltip.addPara(eusan_nation_engineering_design3, 10.0f, Misc.getHighlightColor(), (int) Math.round(OVERLOAD_BONUS) + "%");
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_engineering_design4).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_engineering_design5);
    }
}
