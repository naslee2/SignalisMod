package data.scripts.weapons;

import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnFireEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;

public class eusan_nation_pernach_script implements OnFireEffectPlugin{

    private int shot_counter = 0;

    private final String WEAPON_ID = "eusan_nation_pernach_rocket";

	public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
		shot_counter++;
        if(shot_counter >= 5){
            shot_counter = 0;

            Vector2f rocket_offset = new Vector2f(-8,-8);
            VectorUtils.rotate(rocket_offset, projectile.getFacing());

            Vector2f spawn_point = projectile.getLocation();
            Vector2f.add(spawn_point, rocket_offset, spawn_point);

            float random_offset = 3 - MathUtils.getRandomNumberInRange(0, 6); //-3 to 3

            engine.spawnProjectile(
                weapon.getShip(),
                weapon,
                WEAPON_ID,
                spawn_point,
                projectile.getFacing() + random_offset,
                weapon.getShip().getVelocity()
            );

            WeaponSpecAPI weapon_spec = Global.getSettings().getWeaponSpec(WEAPON_ID);

            engine.spawnMuzzleFlashOrSmoke(weapon.getShip(), spawn_point, weapon_spec, 1);

            
        }
	}
    
}