package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class eusan_nation_electronic_warfare_suite extends BaseHullMod {
    
	public static float MISSILE_RANGE_MULT = 0.7f;
	public static float MISSILE_RATE_BONUS = 60f;
	public static float MISSILE_TURN_ACCEL_BONUS = 150f;

    public static float EW_PENALTY_MULT = 0.7f;

    public static float ECCM_CHANCE = 1f;
	public static float GUIDANCE_IMPROVEMENT = 1f;

    private static Map mag = new HashMap<>();
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
		if (index == 0){
            return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue() + "%";
        } 
		if (index == 1){
            return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue() + "%";
        }
		if (index == 2){
            return "" + ((Float) mag.get(HullSize.CRUISER)).intValue() + "%";
        }
		if (index == 3){
            return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue() + "%";
        }
        if(index == 4){
            return "" + Float.valueOf(ECCM_CHANCE * 100f) + "%";
        }
        if(index == 5){
            return "" + Float.valueOf(GUIDANCE_IMPROVEMENT * 100f) + "%";
        }
        if(index == 6){
            return "" + Float.valueOf(MISSILE_RANGE_MULT * 100f) + "%";
        }
        if(index == 7){
            return "" + Float.valueOf(MISSILE_RATE_BONUS) + "%";
        }
        if(index == 8){
            return "" + Float.valueOf(MISSILE_TURN_ACCEL_BONUS * 100f) + "%";
        }
        if(index == 9){
            return "" + Float.valueOf((1f - EW_PENALTY_MULT) * 100f) + "%";
        }
		return null;
	}

}
