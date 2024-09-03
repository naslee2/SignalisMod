package data.campaign.industries;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.Color;

public class eusan_nation_volksarmee_defense_coordination extends BaseIndustry {

    int size = market.getSize();
    float ground_defense_bonus = 2f;

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
        demand(Commodities.SUPPLIES, size-2);
        
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult("Volksarmee Defense Coordination Center", ground_defense_bonus, "Volksarmee Defense Coordination Center");

        if (!isFunctional()) {
			supply.clear();
			unapply();
		}
    }

    @Override
    public void unapply(){
        super.unapply();
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyFlat("Volksarmee Defense Coordination");
    }

    protected boolean hasPostDemandSection(boolean hasDemand, Industry.IndustryTooltipMode mode) {
        return mode != Industry.IndustryTooltipMode.NORMAL || isFunctional();
    }

    @Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode){
        if (mode != Industry.IndustryTooltipMode.NORMAL || isFunctional()) {
            addStabilityPostDemandSection(tooltip, hasDemand, mode);
        }
    }

    protected void addStabilityPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, Industry.IndustryTooltipMode mode) {
        Color h = Misc.getHighlightColor();
        float opad = 10f;
        tooltip.addPara("Enhances Ground defenses", h, opad);
        tooltip.addPara("Ground defense strength: %s", opad, h, "+" + String.valueOf(ground_defense_bonus) + "%");

    }

    @Override
	public String getCurrentImage() {
		return super.getCurrentImage();
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
