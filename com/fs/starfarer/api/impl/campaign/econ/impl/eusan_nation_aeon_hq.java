package com.fs.starfarer.api.impl.campaign.econ.impl;

import java.util.Random;

//import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;


public class eusan_nation_aeon_hq extends BaseIndustry{
    
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
        demand(Commodities.HAND_WEAPONS, size - 1);
		//demand(Commodities.HEAVY_MACHINERY, size -1);

		supply(Commodities.CREW, size);
		supply(Commodities.MARINES, size);

		Pair<String, Integer> deficitHandWeapons = getMaxDeficit(Commodities.HAND_WEAPONS);
		applyDeficitToProduction(1, deficitHandWeapons, Commodities.MARINES);
		
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
		return 2;
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
