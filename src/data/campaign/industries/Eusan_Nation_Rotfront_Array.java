package data.campaign.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.econ.CommRelayCondition;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.SensorArrayEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

//import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.List;

public class Eusan_Nation_Rotfront_Array extends BaseIndustry{

    //Logger logger = Global.getLogger(eusan_nation_Rotfront_Array.class);

    protected static float array_stability_bonus = 1.0f;
    protected static float rotfront_array_sensor_bonus = 250f;
    StarSystemAPI system = Global.getSector().getStarSystem("Alatyr");

    //List<MarketAPI> data = Misc.getMarketsInLocation(system);
    
    @Override
    public boolean isHidden(){
        return !market.getFactionId().equals("eusan_nation");
    }

    @Override
    public boolean isFunctional(){
        return super.isFunctional() && market.getFactionId().equals("eusan_nation");
    }

    @Override
    public void apply() {
        super.apply(true);

        demand(Commodities.SUPPLIES, 2);
        demand(Commodities.HEAVY_MACHINERY, 2);

        for(MarketAPI market : Misc.getMarketsInLocation(system, "eusan_nation")){
            CommRelayCondition relays = CommRelayCondition.get(market);
            //logger.info("Logger Active: Market is: " + market);
            if(relays != null){
                //logger.info("Logger Active: Relay: " + relays);
                market.getStability().modifyFlat("eusan_nation_Rotfront_Array", array_stability_bonus, "Rotfront Array");
            }
        }
            
        if (!isFunctional()) {
			supply.clear();
			unapply();
		}
        
    }

    //@Override
    //public void advance(float amount) {
        //super.advance(amount);
    //}

    @Override
    public void unapply(){
        super.unapply();
        for(MarketAPI market : Misc.getMarketsInLocation(system, "eusan_nation")){
            CommRelayCondition relays = CommRelayCondition.get(market);
            if(relays != null){
                market.getStability().unmodifyFlat("eusan_nation_Rotfront_Array");
            }
        }
    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
		return mode != IndustryTooltipMode.NORMAL || isFunctional();
	}

    @Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode){
		float opad = 10f;
		Color h = Misc.getHighlightColor();
		tooltip.addPara("Enhances Stability to all markets", h, opad);
		tooltip.addPara("Stability Bonus: %s", opad, h, "+" + (int) array_stability_bonus);
	}

    public boolean isDemandLegal(CommodityOnMarketAPI com) {
		return true;
	}

	public boolean isSupplyLegal(CommodityOnMarketAPI com) {
		return true;
	}
    
    @Override
	public boolean isAvailableToBuild() {
		return false;
	}
	
	public boolean showWhenUnavailable() {
		return false;
	}

	@Override
	public boolean canImprove() {
		return false;
	}
}
