package data.hullmods;

import org.apache.log4j.Logger;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.Global;

public class eusan_nation_operation_penrose extends BaseLogisticsHullMod{
    Logger logger = Global.getLogger(eusan_nation_operation_penrose.class);
    CampaignClockAPI clock = Global.getSector().getClock();
    public long timestamp = clock.getTimestamp();
    
    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
        return null;
    }
    
}
