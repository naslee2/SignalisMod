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

public class eusan_nation_chaingun_script implements OnFireEffectPlugin{

    private int shot_counter = 0;

    private final String WEAPON_ID = "eusan_nation_pernach_rocket";

	public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
		shot_counter++;
        if(shot_counter >= 30){
            shot_counter = 0;

             // Define the 8 offsets: 4 on the left, 4 on the right
            Vector2f[] rocketOffsets = new Vector2f[] {
                new Vector2f(-4, -10),
                new Vector2f(-4, -14),
                new Vector2f(-6, -10),
                new Vector2f(-6, -14),
                new Vector2f(-4, 10),
                new Vector2f(-4, 14),
                new Vector2f(-6, 10),
                new Vector2f(-6, 14)
            };
            WeaponSpecAPI weapon_spec = Global.getSettings().getWeaponSpec(WEAPON_ID);

            // Iterate over each offset to spawn a rocket
            for (Vector2f offset : rocketOffsets) {
                Vector2f rotatedOffset = offset; // copy for rotation
                VectorUtils.rotate(rotatedOffset, projectile.getFacing());

                Vector2f spawn_point = new Vector2f(projectile.getLocation());

                Vector2f.add(spawn_point, rotatedOffset, spawn_point);

                float random_offset = 3 - MathUtils.getRandomNumberInRange(0, 6); // -3 to 3

                engine.spawnProjectile(
                    weapon.getShip(),
                    weapon,
                    WEAPON_ID,
                    spawn_point,
                    projectile.getFacing() + random_offset,
                    weapon.getShip().getVelocity()
                );

                Global.getSoundPlayer().playSound(
                    "annihilator_fire", //annihilator for now
                    1f,   // pitch
                    1f,   // volume
                    spawn_point,
                    weapon.getShip().getVelocity()
                );
                
                engine.spawnMuzzleFlashOrSmoke(weapon.getShip(), spawn_point, weapon_spec, 1);
            }
        }
	}
}