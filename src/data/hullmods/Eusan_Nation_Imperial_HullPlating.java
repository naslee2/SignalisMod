package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class Eusan_Nation_Imperial_HullPlating extends BaseHullMod {
    private float SENSOR_PROFILE_MULT = 0.60f;
    private float ENERGY_DAMAGE_DECREASE = 0.15f;
    private float HULLCOMBAT_REPAIR_RATE_DECREASE = 0.20f;
    private float MAINTENANCE_MULT = 0.1f;

    String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");
    String eusan_Nation_Imperial_HullPlatingText1a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating1a");
    String eusan_Nation_Imperial_HullPlatingText2a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating2a");
    String eusan_Nation_Imperial_HullPlatingText3a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating3a");
    String eusan_Nation_Imperial_HullPlatingText4a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating4a");
    String eusan_Nation_Imperial_HullPlatingText5a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating5a");
    String eusan_Nation_Imperial_HullPlatingText6a = Global.getSettings().getString("eusan_nation_strings", "eusan_Nation_Imperial_HullPlating6a");

    public void applyEffectsBeforeShipCreation (HullSize hullSize, MutableShipStatsAPI stats, String id){
        stats.getEnergyDamageTakenMult().modifyMult(id, 1f - ENERGY_DAMAGE_DECREASE);
        stats.getSensorProfile().modifyMult(id, SENSOR_PROFILE_MULT);
        stats.getHullCombatRepairRatePercentPerSecond().modifyMult(id, 1f - HULLCOMBAT_REPAIR_RATE_DECREASE);
        stats.getSuppliesPerMonth().modifyMult(id, 1f + MAINTENANCE_MULT);
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){}

    public void magicIncompatibleHullmodsChecker(ShipAPI ship){}

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
        return null;
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color flavor = new Color(110,110,110,255);
        final Color green = new Color(55,245,65,255);
        final Color red = new Color(245,55,65,255);
        final Color negative = Misc.getNegativeHighlightColor();
        final Color negativeBG = new Color(128,38,0,175);
        final float pad5 = 5.0f;
        final float pad10 = 10.0f;

        //Details section
        tooltip.addSectionHeading(detailText, Alignment.MID, pad10);
        tooltip.addPara("- " + eusan_Nation_Imperial_HullPlatingText1a,pad10, green, (int) Math.round(SENSOR_PROFILE_MULT * 100)+ "%");
        tooltip.addPara("- " + eusan_Nation_Imperial_HullPlatingText2a,pad5, green, (int) Math.round(ENERGY_DAMAGE_DECREASE * 100)+ "%");
        tooltip.addPara("- " + eusan_Nation_Imperial_HullPlatingText3a,pad5, red, (int) Math.round(HULLCOMBAT_REPAIR_RATE_DECREASE * 100)+ "%");
        tooltip.addPara("- " + eusan_Nation_Imperial_HullPlatingText4a,pad5, red, (int) Math.round(MAINTENANCE_MULT * 100)+ "%");

        //Incompatibilities

        //Quotes
        tooltip.addPara("%s", 6.0f, flavor, eusan_Nation_Imperial_HullPlatingText5a).italicize();
        tooltip.addPara("%s", 1.0f, flavor, eusan_Nation_Imperial_HullPlatingText6a).setAlignment(Alignment.RMID);

    }


}


