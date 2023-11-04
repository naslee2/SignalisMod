package data.hullmods;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_electronic_warfare_suite extends BaseHullMod {
    
	public static float MISSILE_RANGE_MULT = 0.7f;
	public static float MISSILE_RATE_BONUS = 60f;
	public static float MISSILE_TURN_ACCEL_BONUS = 150f;

    public static float EW_PENALTY_MULT = 0.7f;

    public static float ECCM_CHANCE = 1f;
	public static float GUIDANCE_IMPROVEMENT = 1f;

    static String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    static String eusan_nation_electronic_warfare_suite1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText1");
    static String eusan_nation_electronic_warfare_suite2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText2");
    static String eusan_nation_electronic_warfare_suite3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText3");
    static String eusan_nation_electronic_warfare_suite4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText4");
    static String eusan_nation_electronic_warfare_suite5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_electronic_warfare_suiteText5");

    private static Map mag = new HashMap();
	static {
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

    public String getDescriptionParam(int index, HullSize hullSize) {  
        if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue() + "%";
        if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue() + "%";
		if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue() + "%";
		if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue() + "%";
		return null;
	}

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color green = new Color(55,245,65,255);
		final Color red = new Color(245,55,65,255);
		final Color flavor = new Color(110,110,110,255);

        tooltip.addSectionHeading(detailText, Alignment.MID, 10.0F);
        tooltip.addPara(eusan_nation_electronic_warfare_suite1, 10.0f, Misc.getHighlightColor(), (int) (ECCM_CHANCE * 100f) + "%");
        tooltip.addPara(eusan_nation_electronic_warfare_suite2, 10.0f, Misc.getHighlightColor(), (int) MISSILE_RATE_BONUS + "%");
        tooltip.addPara(eusan_nation_electronic_warfare_suite3, 10.0f, Misc.getHighlightColor(), (int) ((1f - EW_PENALTY_MULT) * 100f) + "%");
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_electronic_warfare_suite4).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_electronic_warfare_suite5);
    }

}
