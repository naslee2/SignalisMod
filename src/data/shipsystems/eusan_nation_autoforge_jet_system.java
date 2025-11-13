package data.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;

import java.awt.Color;

import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicRender;

public class eusan_nation_autoforge_jet_system extends BaseShipSystemScript {

    private boolean ammoReloaded = false;
    private ShipAPI ship = null;
    private SpriteAPI sprite;

	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        if(ship == null){
            if (stats.getEntity() instanceof ShipAPI) {
                ship = (ShipAPI) stats.getEntity();
                sprite = Global.getSettings().getSprite("fx","eusan_nation_lerche_heat");
            } else {
                return;
            }
        }

		if (state == ShipSystemStatsScript.State.OUT) {
			stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
			stats.getMaxTurnRate().unmodify(id);
            ammoReloaded = false;
		} else {

            if(!ammoReloaded){
                for(WeaponAPI w : ship.getAllWeapons()){
                    if(w.getSlot().getWeaponType() == WeaponType.MISSILE){
                        if(w.getAmmo() < w.getMaxAmmo()){
                            //Try to reload a "clip" of the weapon
                            int toReload = 1;
                            if(w.getMaxAmmo() <= 4){
                                toReload = w.getMaxAmmo();
                            }else if(w.getSpec().getBurstSize() <= 4){
                                toReload = w.getSpec().getBurstSize();
                            }

                            if(w.getAmmo() + toReload > w.getMaxAmmo()){
                                w.setAmmo(w.getMaxAmmo());
                            }
                            w.setAmmo(w.getAmmo() + toReload);
                        }
                    }
                }
                ammoReloaded = true;
                renderGlow();
            }

			stats.getMaxSpeed().modifyFlat(id, 50f);
			stats.getAcceleration().modifyPercent(id, 180f * effectLevel);
			stats.getDeceleration().modifyPercent(id, 180f * effectLevel);
			stats.getTurnAcceleration().modifyFlat(id, 30f * effectLevel);
			stats.getTurnAcceleration().modifyPercent(id, 180f * effectLevel);
			stats.getMaxTurnRate().modifyFlat(id, 15f);
			stats.getMaxTurnRate().modifyPercent(id, 100f);
		}
	}
	public void unapply(MutableShipStatsAPI stats, String id) {
		stats.getMaxSpeed().unmodify(id);
		stats.getMaxTurnRate().unmodify(id);
		stats.getTurnAcceleration().unmodify(id);
		stats.getAcceleration().unmodify(id);
		stats.getDeceleration().unmodify(id);
	}
	
	public StatusData getStatusData(int index, State state, float effectLevel) {
		if (index == 0) {
			return new StatusData("improved maneuverability", false);
		} else if (index == 1) {
			return new StatusData("+50 top speed", false);
		}
		return null;
	}

    private void renderGlow(){
        MagicRender.objectspace(sprite, ship, new Vector2f(-16,0), new Vector2f(), new Vector2f(54,36), new Vector2f(0,0),
                 180f, 0f, true, new Color(255,255,255,255), true, 1, 1, 5, true); 
    }
}
