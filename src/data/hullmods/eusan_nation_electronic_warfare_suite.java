package data.hullmods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.magiclib.util.MagicIncompatibleHullmods;

import java.awt.Color;

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

public class eusan_nation_electronic_warfare_suite extends BaseHullMod {
    
	public float MISSILE_RANGE_MULT = 0.6f;
	public float MISSILE_RATE_BONUS = 35f;
	public float MISSILE_TURN_ACCEL_BONUS = 100f;
    public float EW_PENALTY_MULT = 0.7f;
    public float ECCM_CHANCE = 0.3f;
	public float GUIDANCE_IMPROVEMENT = 0.8f;

	String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
	String eusan_nation_electronic_warfare_suite1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText1");
	String eusan_nation_electronic_warfare_suite2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText2");
    String eusan_nation_electronic_warfare_suite3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText3");
    String eusan_nation_electronic_warfare_suite4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText4");
    String eusan_nation_electronic_warfare_suite5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText5");
	String eusan_nation_electronic_warfare_suite6 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText6");

    private Map mag = new HashMap();
	{
		mag.put(HullSize.FRIGATE, 1f);
		mag.put(HullSize.DESTROYER, 2f);
		mag.put(HullSize.CRUISER, 3f);
		mag.put(HullSize.CAPITAL_SHIP, 4f);
	}

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_FLAT).modifyFlat(id, (Float) mag.get(hullSize));

        stats.getEccmChance().modifyFlat(id, ECCM_CHANCE);
        stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_PENALTY_MOD).modifyMult(id, EW_PENALTY_MULT);

        stats.getMissileGuidance().modifyFlat(id, GUIDANCE_IMPROVEMENT);
        stats.getMissileWeaponRangeBonus().modifyMult(id, MISSILE_RANGE_MULT);
		stats.getMissileMaxTurnRateBonus().modifyPercent(id, MISSILE_RATE_BONUS);
        stats.getMissileTurnAccelerationBonus().modifyPercent(id, MISSILE_TURN_ACCEL_BONUS);
	}

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		if(ship.getVariant().getHullMods().contains(HullMods.ECCM)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.ECM, "eusan_nation_electronic_warfare_suite");
		}
		if(ship.getVariant().getHullMods().contains(HullMods.ECM)){
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.ECCM, "eusan_nation_electronic_warfare_suite");

		}
	}

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
		final Color flavor = new Color(110,110,110,255);
		//String[] data = new String[]{"Frigate ", "Destroyer ", "Cruiser ", "Captial "};

        tooltip.addSectionHeading(detailText, Alignment.MID, 10.0F);
		tooltip.addPara(eusan_nation_electronic_warfare_suite1, 10f, Misc.getHighlightColor(), new String[]{((Float) mag.get(HullSize.FRIGATE)).intValue() + "%", ((Float) mag.get(HullSize.DESTROYER)).intValue() + "%", ((Float) mag.get(HullSize.CRUISER)).intValue() + "%", ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue() + "%"});
        tooltip.addPara("- " + eusan_nation_electronic_warfare_suite2, 10.0f, Color.green, (int) (ECCM_CHANCE * 100f) + "%");
        tooltip.addPara("- " + eusan_nation_electronic_warfare_suite3, 5f, Color.green, (int) MISSILE_RATE_BONUS + "%");
        tooltip.addPara("- " + eusan_nation_electronic_warfare_suite4, 5f, Color.green, (int) ((1f - EW_PENALTY_MULT) * 100f) + "%");
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_electronic_warfare_suite5).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_electronic_warfare_suite6);
    }

    @Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getVariant().getHullMods().contains(HullMods.ECCM)){
			return false;
		}
        if (ship != null && ship.getVariant().getHullMods().contains(HullMods.ECM)){
            return false;
        }
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
			return true;
		}
		return false;
	}

    public String getUnapplicableReason(ShipAPI ship){
		if (ship != null && ship.getVariant().getHullMods().contains(HullMods.ECCM)){
			return "Incompatible with the ECCM Package";
		}
        if (ship != null && ship.getVariant().getHullMods().contains(HullMods.ECM)){
            return "Incompatible with the ECM Package";
        }
		return null;
	}

}
