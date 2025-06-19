package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class eusan_nation_onboard_supercomputer extends BaseHullMod {

	public static float MISSILE_SPEED_BONUS = 25f;
	public static float MISSILE_RANGE_MULT = 1.2f;
	public static float MISSILE_ACCEL_BONUS = 150f;
	public static float MISSILE_RATE_BONUS = 50f;
	public static float MISSILE_TURN_ACCEL_BONUS = 150f;

    public static float ECCM_CHANCE = 0.5f;
	public static float GUIDANCE_IMPROVEMENT = 1f;
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (ECCM_CHANCE * 100f) + "%";
		if (index == 1) return "" + (int) (MISSILE_SPEED_BONUS) + "%";
		if (index == 2) return "" + (int) (MISSILE_RATE_BONUS) + "%";
		return null;
	}
	
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getEccmChance().modifyFlat(id, ECCM_CHANCE);
		stats.getMissileGuidance().modifyFlat(id, GUIDANCE_IMPROVEMENT);
		
		stats.getMissileMaxSpeedBonus().modifyPercent(id, MISSILE_SPEED_BONUS);
		stats.getMissileWeaponRangeBonus().modifyMult(id, MISSILE_RANGE_MULT);
		stats.getMissileAccelerationBonus().modifyPercent(id, MISSILE_ACCEL_BONUS);
		stats.getMissileMaxTurnRateBonus().modifyPercent(id, MISSILE_RATE_BONUS);
		stats.getMissileTurnAccelerationBonus().modifyPercent(id, MISSILE_TURN_ACCEL_BONUS);
	}


	
}