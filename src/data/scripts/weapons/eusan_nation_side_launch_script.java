package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnFireEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class eusan_nation_side_launch_script implements OnFireEffectPlugin{

    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {

        float proj_new_facing = 0;

        if (weapon.getSlot().isTurret()){

            proj_new_facing = weapon.getCurrAngle();

        } else {

            float weapon_side_modifier = weapon.getArcFacing() < 0 ? 90 : -90;
            proj_new_facing = weapon.getShip().getFacing() + weapon.getArcFacing() + weapon_side_modifier;
        }

        projectile.setFacing(proj_new_facing);

        }
    
}