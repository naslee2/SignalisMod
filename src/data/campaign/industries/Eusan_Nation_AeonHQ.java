package data.campaign.industries;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import java.awt.Color;

public class Eusan_Nation_AeonHQ extends BaseIndustry{

	protected static float ship_quality_bonus = 0.1f;
    
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
        //demand(Commodities.HAND_WEAPONS, size - 1);
		demand(Commodities.HEAVY_MACHINERY, size -1);

		//supply(Commodities.CREW, size);
		//supply(Commodities.MARINES, size);

		//Pair<String, Integer> deficitHandWeapons = getMaxDeficit(Commodities.HAND_WEAPONS);
		//applyDeficitToProduction(1, deficitHandWeapons, Commodities.MARINES);

		market.getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD).modifyFlat("eusan_nation_aeon_hq", ship_quality_bonus, "AEON HQ");

		//modifyStabilityWithBaseMod();

		if (!isFunctional()) {
			supply.clear();
			unapply();
		}
    }

    @Override
    public void unapply(){
        super.unapply();

        //unmodifyStabilityWithBaseMod();
		market.getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD).unmodifyFlat("eusan_nation_aeon_hq");

    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
		return mode != IndustryTooltipMode.NORMAL || isFunctional();
	}

	@Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
		float opad = 10f;
		Color h = Misc.getHighlightColor();
		//if (mode != IndustryTooltipMode.NORMAL || isFunctional()) {
		//	addStabilityPostDemandSection(tooltip, hasDemand, mode);
		//}
		tooltip.addPara("Ship Quality Bonus: %s", opad, h, "+" + (int) (ship_quality_bonus * 100) + "%");
	}

    // @Override
	// protected int getBaseStabilityMod() {
	// 	return 1;
	// }

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
