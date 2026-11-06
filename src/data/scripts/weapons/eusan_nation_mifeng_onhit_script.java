package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import java.awt.Color;
import org.lwjgl.util.vector.Vector2f;
/**
 *
 * @author Mayu
 */
public class eusan_nation_mifeng_onhit_script implements OnHitEffectPlugin {

    public static float explosion_damage;
    public static float explosion_size;
    private static final Color BOOM_COLOR = new Color(210, 73, 27, 155);
    private static final Color SMOKE_COLOR = new Color(100, 100, 100, 200);
    private static final float NEBULA_SIZE_MULT = 13.0f;
    private static final float NEBULA_RAMPUP = 0.3f;
    private static final float FX_DUR = 1.2f;
    private static final String SFX = "eusan_nation_mifeng_impact";
    
    @Override
    public void onHit(final DamagingProjectileAPI projectile, final CombatEntityAPI target, final Vector2f point, final boolean shieldHit, final ApplyDamageResultAPI damageResult, final CombatEngineAPI engine) {
        final Vector2f loc_target = new Vector2f(target.getLocation());
        final Vector2f v_target = new Vector2f(target.getVelocity());
        final Vector2f v_proj = new Vector2f(projectile.getVelocity());
        final Vector2f v_comp = (Vector2f)Vector2f.sub(v_proj, v_target, new Vector2f()).scale(0.03f);

        if (!projectile.isFading() && target instanceof ShipAPI) {
            //ShipAPI theTarget = (ShipAPI)projectile.getDamageTarget();
            final float selector = (float) Math.random();

            if (selector > 0.3f) {
                eusan_nation_mifeng_onhit_script.explosion_damage = projectile.getDamageAmount() / 3.0f;
                eusan_nation_mifeng_onhit_script.explosion_size = 15.0f * (0.75f + (float)Math.random() * 0.5f);
            }

            if (((ShipAPI) target).getHullSize() == ShipAPI.HullSize.FIGHTER) {
                Global.getCombatEngine().applyDamage(
                        target, point,
                        200f,
                        DamageType.FRAGMENTATION,
                        0.0f,
                        false,
                        false,
                        projectile.getSource(),
                        true
                );
            }

            engine.addNebulaSmokeParticle(point, v_comp, eusan_nation_mifeng_onhit_script.explosion_size, NEBULA_SIZE_MULT, NEBULA_RAMPUP, 0.3f, FX_DUR, eusan_nation_mifeng_onhit_script.SMOKE_COLOR);
            engine.addNebulaSmokeParticle(point, v_comp, eusan_nation_mifeng_onhit_script.explosion_size, NEBULA_SIZE_MULT, NEBULA_RAMPUP, 0.6f, FX_DUR, eusan_nation_mifeng_onhit_script.SMOKE_COLOR);
            engine.spawnExplosion(point, v_comp, eusan_nation_mifeng_onhit_script.BOOM_COLOR, eusan_nation_mifeng_onhit_script.explosion_size * 8.0f, 0.3f);

            Global.getSoundPlayer().playSound(SFX, 0.5f, 1.0f, loc_target, v_comp);
        }
        else if (!projectile.isFading() && target instanceof MissileAPI) {
            //ShipAPI theTarget = (ShipAPI)projectile.getDamageTarget();
            final float selector = (float) Math.random();

            if (selector > 0.3f) {
                eusan_nation_mifeng_onhit_script.explosion_damage = projectile.getDamageAmount() / 3.0f;
                eusan_nation_mifeng_onhit_script.explosion_size = 15.0f * (0.75f + (float)Math.random() * 0.5f);
            }

            Global.getCombatEngine().applyDamage(
                    target, point,
                    200f,
                    DamageType.FRAGMENTATION,
                    0.0f,
                    false,
                    false,
                    projectile.getSource(),
                    true
            );

            engine.addNebulaSmokeParticle(point, v_comp, eusan_nation_mifeng_onhit_script.explosion_size, NEBULA_SIZE_MULT, NEBULA_RAMPUP, 0.3f, FX_DUR, eusan_nation_mifeng_onhit_script.SMOKE_COLOR);
            engine.addNebulaSmokeParticle(point, v_comp, eusan_nation_mifeng_onhit_script.explosion_size, NEBULA_SIZE_MULT, NEBULA_RAMPUP, 0.6f, FX_DUR, eusan_nation_mifeng_onhit_script.SMOKE_COLOR);
            engine.spawnExplosion(point, v_comp, eusan_nation_mifeng_onhit_script.BOOM_COLOR, eusan_nation_mifeng_onhit_script.explosion_size * 8.0f, 0.3f);

            Global.getSoundPlayer().playSound(SFX, 0.5f, 1.0f, loc_target, v_comp);
        }

    }

}