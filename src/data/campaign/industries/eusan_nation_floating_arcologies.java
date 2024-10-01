package data.campaign.industries;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import java.awt.Color;

public class eusan_nation_floating_arcologies extends BaseIndustry {

    public static float accessibility_bonus = 0.25f;

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

        market.getAccessibilityMod().modifyFlat("eusan_nation_floating_arcologies", accessibility_bonus, "Imperial-era Floating Arcologies");
        if (!isFunctional()) {
			supply.clear();
			unapply();
		}
    }

    @Override
    public void unapply(){
        super.unapply();
        market.getAccessibilityMod().unmodifyFlat("eusan_nation_floating_arcologies");
    }

    @Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
		float opad = 10f;
		Color h = Misc.getHighlightColor();
        tooltip.addPara("Enhances Market Accessibilty", h, opad);
		tooltip.addPara("Market Accessibilty Bonus: %s", opad, h, "");
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
