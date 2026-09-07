package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.input.InputEventAPI;

import java.util.ArrayList;
import java.util.List;

public class eusan_nation_rotmilan_wings extends BaseEveryFrameCombatPlugin {

    private final ShipAPI ship;
    private boolean runOnce = true;

    private List<WeaponAPI> wings = new ArrayList<>();

    public eusan_nation_rotmilan_wings(ShipAPI ship){
        this.ship = ship;
    }

    @Override
    public void advance(float amount, List<InputEventAPI> events) {
        if (Global.getCombatEngine().isPaused()) return;

        if( ship == null || !ship.isAlive() || ship.isHulk() || !Global.getCombatEngine().isEntityInPlay(ship)){
            Global.getCombatEngine().removePlugin(this);
        }

        if(runOnce){
            runOnce = false;

            for(WeaponAPI w : ship.getAllWeapons()){
                if(w.getSlot().isDecorative()){
                    if(w.getSlot().getId().endsWith("WING0")){
                        wings.add(w);
                        w.getAnimation().setFrame(0);
                    } else
                    if(w.getSlot().getId().endsWith("WING1")){
                        wings.add(w);
                        w.getAnimation().setFrame(1);
                    } else
                    if(w.getSlot().getId().endsWith("WING2")){
                        wings.add(w);
                        w.getAnimation().setFrame(2);
                    } else
                    if(w.getSlot().getId().endsWith("WING3")){
                        wings.add(w);
                        w.getAnimation().setFrame(3);
                    } else
                    if(w.getSlot().getId().endsWith("WING4")){
                        wings.add(w);
                        w.getAnimation().setFrame(4);
                    } else
                    if(w.getSlot().getId().endsWith("WING5")){
                        wings.add(w);
                        w.getAnimation().setFrame(5);
                    } else
                    if(w.getSlot().getId().endsWith("WING6")){
                        wings.add(w);
                        w.getAnimation().setFrame(6);
                    } else
                    if(w.getSlot().getId().endsWith("WING7")){
                        wings.add(w);
                        w.getAnimation().setFrame(7);
                    }
                }
            }
        }
    }
}
