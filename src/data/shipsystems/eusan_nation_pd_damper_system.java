package data.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.AIHints;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

public class eusan_nation_pd_damper_system extends BaseShipSystemScript {

	protected Object STATUSKEY1 = new Object();
    
    private static final float DMG_REDUCTION = 0.4f;

	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		effectLevel = 1f;
		
		stats.getHullDamageTakenMult().modifyMult(id, 1f - (1f - DMG_REDUCTION) * effectLevel);
		stats.getArmorDamageTakenMult().modifyMult(id, 1f - (1f - DMG_REDUCTION) * effectLevel);
		stats.getEmpDamageTakenMult().modifyMult(id, 1f - (1f - DMG_REDUCTION) * effectLevel);
		
		ShipAPI ship = null;
		boolean player = false;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
			player = ship == Global.getCombatEngine().getPlayerShip();
		}

		for (WeaponAPI weapon : ship.getAllWeapons()) {
			if(!(weapon.hasAIHint(AIHints.PD) || weapon.hasAIHint(AIHints.PD_ONLY))){
				weapon.setForceNoFireOneFrame(true);
			}
		}

		if (player) {
			ShipSystemAPI system = getDamper(ship);
			if (system != null) {
				float percent = (1f - DMG_REDUCTION) * effectLevel * 100;
				Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY1,
					system.getSpecAPI().getIconSpriteName(), system.getDisplayName(),
					(int) Math.round(percent) + "% less damage taken, non-pd weapons disabled", false);
			}
		}
	}
	
	public static ShipSystemAPI getDamper(ShipAPI ship) {
		ShipSystemAPI system = ship.getPhaseCloak();
		if (system != null && system.getId().equals("damper")) return system;
		if (system != null && system.getId().equals("damper_omega")) return system;
		if (system != null && system.getSpecAPI() != null && system.getSpecAPI().hasTag(Tags.SYSTEM_USES_DAMPER_FIELD_AI)) return system;
		return ship.getSystem();
	}
	
	public void unapply(MutableShipStatsAPI stats, String id) {
		stats.getHullDamageTakenMult().unmodify(id);
		stats.getArmorDamageTakenMult().unmodify(id);
		stats.getEmpDamageTakenMult().unmodify(id);
	}

}
