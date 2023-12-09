package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.Color;

import org.magiclib.util.MagicIncompatibleHullmods;

public class eusan_nation_replika_cadre extends BaseHullMod {

    public static float MAINTENANCE_MULT = 0.8f;
	
	public static float REPAIR_RATE_BONUS = 50f;
	public static float CR_RECOVERY_BONUS = 50f;
	public static float REPAIR_BONUS = 50f;
    public static float OVERLOAD_BONUS = 20f;

    static String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    static String eusan_nation_replika_cadre1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText1");
    static String eusan_nation_replika_cadre2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText2");
    static String eusan_nation_replika_cadre3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText3");
    static String eusan_nation_replika_cadre4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText4");
    static String eusan_nation_replika_cadre5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText5");
    static String eusan_nation_replika_cadre6 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText6");
    static String eusan_nation_replika_cadre7 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_replika_cadreText7");
    
    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
        stats.getMinCrewMod().modifyMult(id, MAINTENANCE_MULT);
        stats.getSuppliesPerMonth().modifyMult(id, MAINTENANCE_MULT);
        stats.getFuelUseMod().modifyMult(id, MAINTENANCE_MULT);

        stats.getCombatEngineRepairTimeMult().modifyMult(id, 1f - REPAIR_BONUS * 0.01f);
		stats.getCombatWeaponRepairTimeMult().modifyMult(id, 1f - REPAIR_BONUS * 0.01f);

        stats.getBaseCRRecoveryRatePercentPerDay().modifyPercent(id, CR_RECOVERY_BONUS);
		stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_RATE_BONUS);
        stats.getOverloadTimeMod().modifyMult(id, 1f - OVERLOAD_BONUS * 0.01f);
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		if(ship.getVariant().getHullMods().contains(HullMods.EFFICIENCY_OVERHAUL)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.EFFICIENCY_OVERHAUL, "Replika Cadre");
		}
        if(ship.getVariant().getHullMods().contains(HullMods.AUTOREPAIR)){
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.AUTOREPAIR, "Replika Cadre");
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
        tooltip.addPara(eusan_nation_replika_cadre1, 10.0f, Misc.getHighlightColor(), (int) Math.round((1f - MAINTENANCE_MULT) * 100f) + "%");
        tooltip.addPara(eusan_nation_replika_cadre2, 10.0f, Misc.getHighlightColor(), (int) Math.round(REPAIR_RATE_BONUS) + "%");
        tooltip.addPara(eusan_nation_replika_cadre3, 10.0f, Misc.getHighlightColor(), (int) Math.round(CR_RECOVERY_BONUS) + "%");
        tooltip.addPara(eusan_nation_replika_cadre4, 10.0f, Misc.getHighlightColor(), (int) Math.round(REPAIR_BONUS) + "%");
        tooltip.addPara(eusan_nation_replika_cadre5, 10.0f, Misc.getHighlightColor(), (int) Math.round(OVERLOAD_BONUS) + "%");
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_replika_cadre6).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_replika_cadre7);
    }

}
