package data.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.AIHints;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.combat.listeners.WeaponBaseRangeModifier;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

public class eusan_nation_lr_targeting_system extends BaseShipSystemScript {

    private static final float RANGE_CAP = 1200;

	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        
        ShipAPI ship = null;
		//boolean player = false;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
			//player = ship == Global.getCombatEngine().getPlayerShip();
		}

        if (ship == null) return;

        if (effectLevel > 0f && !ship.hasListenerOfClass(EusanNationRangeModifier.class)) {
            ship.addListener(new EusanNationRangeModifier(RANGE_CAP));
        }
	}
	
	
	public void unapply(MutableShipStatsAPI stats, String id) {
		ShipAPI ship = (ShipAPI) stats.getEntity();
        if (ship == null) return;

        ship.removeListenerOfClass(EusanNationRangeModifier.class);
	}

    public static class EusanNationRangeModifier implements WeaponBaseRangeModifier {
		public float max;
		public EusanNationRangeModifier(float max) {
			this.max = max;
		}
		
		public float getWeaponBaseRangePercentMod(ShipAPI ship, WeaponAPI weapon) {
			return 0;
		}
		public float getWeaponBaseRangeMultMod(ShipAPI ship, WeaponAPI weapon) {
			return 1f;
		}
		public float getWeaponBaseRangeFlatMod(ShipAPI ship, WeaponAPI weapon) {
            float effectLevel = ship.getSystem().getEffectLevel();
            if(!(effectLevel > 0)) {
                return 0f;
            }

			if (weapon.getSpec() == null) {
				return 0f;
			}
			if (weapon.getSpec().getMountType() != WeaponType.BALLISTIC && 
					weapon.getSpec().getMountType() != WeaponType.HYBRID) {
				return 0f;
			}
			if (weapon.hasAIHint(AIHints.PD)) {
				return 0f;
			}

            float smoothLevel = effectLevel * effectLevel; // quadratic ramp
			
			float bonus = 0;
			float base = weapon.getSpec().getMaxRange();
			if (base < max) {
				bonus = max - base;
			}
			if (bonus < 0) bonus = 0;
			return bonus * smoothLevel;
		}
	}
}