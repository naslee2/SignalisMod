package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

public class eusan_nation_replika_cadre extends BaseLogisticsHullMod {

    public static float MAINTENANCE_MULT = 0.8f;
	
	public static float REPAIR_RATE_BONUS = 50f;
	public static float CR_RECOVERY_BONUS = 50f;
	public static float REPAIR_BONUS = 50f;
    
    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
        
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
        return null;
    }

}
