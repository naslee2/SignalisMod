package data.campaign.conditions;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.econ.ConditionData;

public class eusan_nation_hertz_der_revolution extends BaseMarketConditionPlugin{

    float heimat_stability_bonus = 2.0f;

    public void apply(String id) {
		market.getStability().modifyFlat(id, heimat_stability_bonus, "Regional capital");
	}

	public void unapply(String id) {
		market.getStability().unmodify(id);
	}

}
