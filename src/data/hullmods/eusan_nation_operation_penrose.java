package data.hullmods;

import org.apache.log4j.Logger;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
//import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import com.fs.starfarer.api.Global;

public class eusan_nation_operation_penrose extends BaseLogisticsHullMod {
    Logger logger = Global.getLogger(eusan_nation_operation_penrose.class);
    CampaignClockAPI clock = Global.getSector().getClock();

    protected float daysElasped = 0f;
    
    @Override
    public void advanceInCampaign(FleetMemberAPI member, float amount) {
        if (!Global.getSector().getListenerManager().hasListener(this)) {
            Global.getSector().getListenerManager().addListener(this, true);
        }
        float days = Global.getSector().getClock().convertToDays(amount);
        //float daysActive = Global.getSector().getClock().getElapsedDaysSince(startTime);
        daysElasped += days;
        logger.info("Logger active. Report Economy tick fired, StartTime is: " + daysElasped);

    }
    
    public void applyEffectsBeforeShipCreation(HullSize  hullSize, MutableShipStatsAPI stats, String id){
        
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship){
        float daysActive = daysElasped;
        if(index == 0){
            logger.info("Logger active. daysActive is: " + (int) daysActive);
            return "" + (int) daysActive;
        }
        return null;
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        float daysActive2 = daysElasped;
        if(daysActive2 > 1f){
            tooltip.addPara("Welcome to the Phase 1 of your glorious exporation comrade! ", 10.0f, Misc.getHighlightColor(), (int) daysActive2 + " days elasped!");
        }
        if(daysActive2 > 1499f){
            tooltip.addPara("Welcome to Phase 2 comrade! ", 10.0f, Misc.getHighlightColor(), (int) daysActive2 + " days elasped!");
        }
        if(daysActive2 > 2999f){
            tooltip.addPara("Welcome to Phase 3 comrade! ", 10.0f, Misc.getHighlightColor(), (int) daysActive2 + " days elasped!");
        }
    }
    
}
