package data.campaign.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import java.awt.Color;

public class eusan_nation_VEB_rotfront_hydroponik extends BaseIndustry {

	float farming_bonus = 2.0f;
	//MarketAPI rotfront_market = Global.getSector().getEconomy().getMarket("rotfront_market");


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
        //demand(Commodities.FOOD, 3);
		if(market.hasIndustry(Industries.FARMING)){
			market.getIndustry(Industries.FARMING).getSupply(Commodities.FOOD).getQuantity().modifyFlat("VEB Hydroponik", farming_bonus, "VEB");
			//rotfront_market.getIndustry(Industries.FARMING).getSupplyBonus().modifyFlat("VEB Hydroponik", farming_bonus);
		}

        //supply("eusan_nation_nationsmokes", 4);

        //Pair<String, Integer> deficitSmokes = getMaxDeficit(Commodities.FOOD);
        //applyDeficitToProduction(1, deficitSmokes, "eusan_nation_nationsmokes");

		if (!isFunctional()) {
			supply.clear();
			unapply();
		}
    }

    @Override
    public void unapply(){
        super.unapply();
		if (market.hasIndustry(Industries.FARMING)) {
            market.getIndustry(Industries.FARMING).getSupply(Commodities.FOOD).getQuantity().unmodifyFlat("VEB_Hydroponik");
   }
    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
		return mode != IndustryTooltipMode.NORMAL || isFunctional();
	}

	@Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode){
		float opad = 10f;
		Color h = Misc.getHighlightColor();
		tooltip.addPara("Food Production Bonus: %s", opad, h, "+" + String.valueOf(farming_bonus));
	}
    
    public String getNameForModifier() {
		if (getSpec().getName().contains("VEB")) {
			return getSpec().getName();
		}
		return Misc.ucFirst(getSpec().getName());
	}

    @Override
	public String getCurrentImage() {
		return super.getCurrentImage();
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
