package data.campaign.conditions;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.econ.ConditionData;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_herz_der_revolution extends BaseMarketConditionPlugin{

    float heimat_stability_bonus = 2.0f;

    public void apply(String id) {
		market.getStability().modifyFlat(id, heimat_stability_bonus, "Herz der Revolution");
	}

	public void unapply(String id) {
		market.getStability().unmodify(id);
	}

	@Override
	protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
		float opad = 10f;
		tooltip.addPara("Heimat is home to many goverment agencies, design bureaus, manufacturing and research. These ", opad);
		tooltip.addPara("%s stability", opad, Misc.getHighlightColor(), "+" + String.valueOf(heimat_stability_bonus));
	}

}
