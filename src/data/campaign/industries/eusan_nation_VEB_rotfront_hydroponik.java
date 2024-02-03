package data.campaign.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;


public class eusan_nation_VEB_rotfront_hydroponik extends BaseIndustry {

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

        demand(Commodities.SUPPLIES, 3);
        demand(Commodities.HEAVY_MACHINERY, 3);
        demand(Commodities.FOOD, 3);

        supply("eusan_nation_nationsmokes", 4);

        Pair<String, Integer> deficitSmokes = getMaxDeficit(Commodities.FOOD);
        applyDeficitToProduction(1, deficitSmokes, "eusan_nation_nationsmokes");
        modifyStabilityWithBaseMod();

		if (!isFunctional()) {
			supply.clear();
			unapply();
		}
    }

    @Override
    public void unapply(){
        super.unapply();
        unmodifyStabilityWithBaseMod();

    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
		return mode != IndustryTooltipMode.NORMAL || isFunctional();
	}

	// @Override
	// protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
	// 	if (mode != IndustryTooltipMode.NORMAL || isFunctional()) {
	// 		addStabilityPostDemandSection(tooltip, hasDemand, mode);
	// 	}
	// }
    
    public String getNameForModifier() {
		if (getSpec().getName().contains("VEB")) {
			return getSpec().getName();
		}
		return Misc.ucFirst(getSpec().getName());
	}

    // @Override
	// protected Pair<String, Integer> getStabilityAffectingDeficit() {
	// 	return getMaxDeficit(Commodities.SUPPLIES, Commodities.HEAVY_MACHINERY, Commodities.FOOD);
	// }

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
