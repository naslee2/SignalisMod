package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.Color;

import com.fs.starfarer.api.util.Misc;
import org.magiclib.util.MagicIncompatibleHullmods;

public class eusan_nation_engineering_design extends BaseLogisticsHullMod{
    public float CR_RECOVERY_BONUS = 35f;
    public float REPAIR_RATE_BONUS = 35f;
    public float OVERLOAD_BONUS = 20f;

    String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");
    String eusan_nation_engineering_design1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText1");
    String eusan_nation_engineering_design2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText2");
	String eusan_nation_engineering_design3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText3");
    String eusan_nation_engineering_design4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText4");
    String eusan_nation_engineering_design5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_engineering_designText5");

    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
        stats.getBaseCRRecoveryRatePercentPerDay().modifyPercent(id, CR_RECOVERY_BONUS);
		stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_RATE_BONUS);
        stats.getOverloadTimeMod().modifyMult(id, 1f - (OVERLOAD_BONUS * 0.01f));
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){

        magicIncompatibleHullmodsChecker(ship);
	}

    public void magicIncompatibleHullmodsChecker(ShipAPI ship){
        //if someone tries to install incompatible hullmods, remove it.
        if(ship.getVariant().getHullMods().contains(HullMods.AUTOREPAIR)){
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.AUTOREPAIR, "eusan_nation_engineering_design");
        }
        if(ship.getVariant().getHullMods().contains(HullMods.EFFICIENCY_OVERHAUL)){
            //if someone tries to install incompatible hullmods, remove it.
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.EFFICIENCY_OVERHAUL, "eusan_nation_engineering_design");
        }
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
		if (index == 1) return "" + (int) Math.round(CR_RECOVERY_BONUS) + "%";
            return null;
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color flavor = new Color(110,110,110,255);
        final Color green = new Color(55,245,65,255);
        //final Color red = new Color(245,55,65,255);
        final Color negative = Misc.getNegativeHighlightColor();
        final Color negativeBG = new Color(128,38,0,175);
        final float pad5 = 5.0f;
        final float pad10 = 10.0f;

        //Details section
        tooltip.addSectionHeading(detailText, Alignment.MID, pad10);
        tooltip.addPara("- " + eusan_nation_engineering_design1, pad10, green, (int) Math.round(REPAIR_RATE_BONUS) + "%");
        tooltip.addPara("- " + eusan_nation_engineering_design2, pad5, green, (int) Math.round(CR_RECOVERY_BONUS) + "%");
        tooltip.addPara("- " + eusan_nation_engineering_design3, pad5, green, (int) Math.round(OVERLOAD_BONUS) + "%");

        //Incompatibilities
        tooltip.addSectionHeading(incompatibilitiesText, negative,negativeBG, Alignment.MID, pad10);
        final TooltipMakerAPI warning_section = tooltip.beginImageWithText(Global.getSettings().getSpriteName("tooltips", "warningSymbol"), 40);
        warning_section.addPara("Incompatible with %s and %s", 0f, negative, new String[] { "Auto Repair", "Efficiency Overhaul"});
        tooltip.addImageWithText(pad10);

        //Quotes
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_engineering_design4).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_engineering_design5).setAlignment(Alignment.RMID);
    }
}
