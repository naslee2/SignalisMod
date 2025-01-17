package data.campaign.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableStat.StatMod;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.econ.CommRelayCondition;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

//import org.apache.log4j.Logger;

import java.awt.Color;
//import java.util.List;

public class Eusan_Nation_Rotfront_Array extends BaseIndustry{

    //Logger logger = Global.getLogger(Eusan_Nation_Rotfront_Array.class);

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
            if(relays != null){
                market.getStability().modifyFlat("eusan_nation_Rotfront_Array", array_stability_bonus, "Rotfront Array");
            }
        }
        //logger.info("Logger active: entity is: " + entity);;
            
        if (!isFunctional()) {
			supply.clear();
			unapply();
		}
        
    }

    @Override
    public void advance(float amount) {
        super.advance(amount);

        for(CampaignFleetAPI fleet : system.getFleets()){
//            if(fleet.isInHyperspaceTransition()){
//                continue;
//            }
            if(fleet.getFaction() == Global.getSector().getFaction("eusan_nation") || fleet.getFaction().isPlayerFaction()){
                StatMod current = fleet.getStats().getSensorRangeMod().getFlatBonus(id);

                if (current == null || current.value <= rotfront_array_sensor_bonus){
                    fleet.getStats().addTemporaryModFlat(0.1f, id, "Rotfront Array", rotfront_array_sensor_bonus, fleet.getStats().getSensorRangeMod());
                }
            }
        }

    }

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
		tooltip.addPara("Enhances Stability to all markets and provides sensor boost to allied fleets.", h, opad);
		tooltip.addPara("Stability Bonus: %s", opad, h, "+" + (int) array_stability_bonus);
        tooltip.addPara("Sensor Boost: %s", opad, h, "+" + (int) rotfront_array_sensor_bonus);
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
