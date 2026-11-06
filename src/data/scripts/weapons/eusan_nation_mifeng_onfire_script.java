package data.scripts.weapons;

import com.fs.starfarer.api.combat.*;
import java.awt.Color;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;
/**
 *
 * @author Mayu
 */
public class eusan_nation_mifeng_onfire_script implements OnFireEffectPlugin, EveryFrameWeaponEffectPlugin {

    private static final Color FLASH_COLOR = new Color(225, 225, 200, 255);
    private static final float FLASH_SIZE = 7.2f;
    private static final float FLASH_DUR = 0.1f;
    
    @Override
    public void onFire(final DamagingProjectileAPI proj, final WeaponAPI weapon, final CombatEngineAPI engine) {
        final ShipAPI ship = weapon.getShip();

        engine.spawnExplosion(
                proj.getLocation(),
                ship.getVelocity(),
                eusan_nation_mifeng_onfire_script.FLASH_COLOR,
                FLASH_SIZE,
                FLASH_DUR
        );

        engine.addHitParticle(
                proj.getLocation(),
                weapon.getShip().getVelocity(),
                100f,
                1f,
                0.5f,
                new Color(252, 129, 75, 190)
        );

        Vector2f projVel, point;

        for (int i = 0; i < 3; i++) {
            projVel = new Vector2f(1f, 0f);
            projVel.normalise();
            VectorUtils.rotate(projVel, weapon.getCurrAngle(), projVel);

            point = new Vector2f(projVel);
            point.scale(MathUtils.getRandomNumberInRange(20f,60f));
            Vector2f.add(proj.getLocation(),point,point);

            projVel.scale(MathUtils.getRandomNumberInRange(0f,100f));
            Vector2f.add(weapon.getShip().getVelocity(), projVel, projVel);

            // Smoky effect going outward
            engine.addNebulaParticle(point,
                    projVel,
                    MathUtils.getRandomNumberInRange(6f, 12f),
                    2f,
                    0.1f,
                    0.3f,
                    MathUtils.getRandomNumberInRange(1f, 2f),
                    new Color(40,40,40,150),
                    true
            );

            for (int j = 0; j < 7; j++) {
                engine.addHitParticle(
                        MathUtils.getRandomPointInCircle(point,10f),
                        MathUtils.getRandomPointInCone(projVel,20f,proj.getFacing()-45f,proj.getFacing()+45f),
                        MathUtils.getRandomNumberInRange(1f,3f),
                        1f,
                        MathUtils.getRandomNumberInRange(1f,2f),
                        new Color(253, 89, 89)
                );
            }
        }
    }

    @Override
    public void advance(final float amount, final CombatEngineAPI engine, final WeaponAPI weapon) {
        // Can add something here later on!
    }

}