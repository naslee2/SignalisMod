package data.scripts.weapons;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;

import org.lwjgl.util.vector.Vector2f;

import org.magiclib.util.MagicRender;

public class eusan_nation_station_missile_glow implements EveryFrameWeaponEffectPlugin{    

    private boolean runOnce=false;
    private ShipAPI ship;
    private SpriteAPI sprite;
    private boolean activated=false;
    private Vector2f offset;
    
    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        
        if(!runOnce){
            runOnce=true;
            ship=weapon.getShip();
            if(weapon.getId().endsWith("left")){
                sprite = Global.getSettings().getSprite("fx","eusan_nation_station_glow_left");
                offset = new Vector2f(-8,64);
            }else{
                sprite = Global.getSettings().getSprite("fx","eusan_nation_station_glow_right");
                offset = new Vector2f(-8,-64);
            }
        }

        if (engine.isPaused() || !ship.isAlive()) return;

        if(!activated && ship.getSystem().isChargeup()){  
            activated = true;
            MagicRender.objectspace(sprite, ship, offset, new Vector2f(), new Vector2f(74,36), new Vector2f(0,0),
                 180f, 0f, true, new Color(255,255,255,255), true, 1, 2, 4, true);   
        } else if (activated && !ship.getSystem().isActive()){
            activated = false;
        }
    }
}