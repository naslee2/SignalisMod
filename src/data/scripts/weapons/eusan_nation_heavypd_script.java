package data.scripts.weapons;

import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;

public class eusan_nation_heavypd_script implements OnHitEffectPlugin{

    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldhit,
           ApplyDamageResultAPI damage, CombatEngineAPI engine) {
               if(target instanceof ShipAPI){
                    ShipAPI ship = (ShipAPI) target;
                    if(ship.isFighter() && !shieldhit){
                       engine.applyDamage(target, point, 200f, DamageType.FRAGMENTATION, 0, false, false, projectile.getSource());
                    }  
               } else if(target instanceof MissileAPI){
                    engine.applyDamage(target, point, 200f, DamageType.FRAGMENTATION, 0, false, false, projectile.getSource());
               }
           } 
}
