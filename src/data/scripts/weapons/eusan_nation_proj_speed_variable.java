package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnFireEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class eusan_nation_proj_speed_variable implements OnFireEffectPlugin{
    
    @Override
    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {

        float speedMult = 0.90f + 0.10f * (float) Math.random();
		projectile.getVelocity().scale(speedMult);
        
    }
}
