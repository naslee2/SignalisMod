package data.campaign.industries;

import java.util.Random;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

public class eusan_nation_aeon_facility extends BaseIndustry{

    @Override
    public boolean isHidden(){
        return !market.getFactionId().equals("eusan_nation");
    }

    @Override
    public boolean isFunctional(){
        return super.isFunctional() && market.getFactionId().equals("eusan_nation");
    }

    public void apply(){
        super.apply(true);
        int size = market.getSize();

        demand(Commodities.SUPPLIES, size - 1);
		demand(Commodities.CREW, size - 1);

		//supply(Commodities.ORE, size - 1);
		//supply(Commodities.ORGANICS, size - 2);
		supply(Commodities.ORGANS, size);

		//Pair<String, Integer> deficitSupplies = getMaxDeficit(Commodities.SUPPLIES);
		Pair<String, Integer> deficitCrew = getMaxDeficit(Commodities.CREW);

		//applyDeficitToProduction(1, deficitCrew, Commodities.ORGANS);
		//applyDeficitToProduction(1, deficitSupplies, Commodities.ORE, Commodities.ORGANICS);
		applyDeficitToProduction(1, deficitCrew, Commodities.ORGANS);
		
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

	@Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
		if (mode != IndustryTooltipMode.NORMAL || isFunctional()) {
			addStabilityPostDemandSection(tooltip, hasDemand, mode);
		}
	}

    @Override
	protected int getBaseStabilityMod() {
		return 1;
	}

    public String getNameForModifier() {
		if (getSpec().getName().contains("HQ")) {
			return getSpec().getName();
		}
		return Misc.ucFirst(getSpec().getName());
	}

    @Override
	protected Pair<String, Integer> getStabilityAffectingDeficit() {
		return getMaxDeficit(Commodities.SUPPLIES, Commodities.HEAVY_MACHINERY, Commodities.HAND_WEAPONS);
	}
	
	@Override
	public String getCurrentImage() {
		String marketName = market.getId();
		if(marketName.equals("leng_market")){
			return Global.getSettings().getSpriteName("industry","leng_aeon_facility");
		}
		return super.getCurrentImage();

		// switch(marketName){
		// 	case "buyan_market":
		// 		return Global.getSettings().getSpriteName("industry","generic_aeon_facility");
		//	case "vineta_market":
		//		return Global.getSettings().getSpriteName("industry","generic_aeon_facility");
		// 	case "kitzeh_market":
		// 		return Global.getSettings().getSpriteName("industry","generic_aeon_facility");
		//	case "kitezh_market":
		//		return Global.getSettings().getSpriteName("industry","generic_aeon_facility");
		// 	case "leng_market":
		// 		return Global.getSettings().getSpriteName("industry","leng_aeon_facility");
		// 	default: 
		// 		return super.getCurrentImage();
		// }
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