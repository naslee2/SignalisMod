package data.campaign.conditions;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_protektor_cadres extends BaseMarketConditionPlugin{

    protected static float protektor_stability_bonus = 1.0f;

    public void apply(String id){
		if(isNationControlled()){
			market.getStability().modifyFlat(id,  protektor_stability_bonus, "Protektor Cadres");
		}
    }

    public void unapply(String id) {
		if(isNationControlled()){
			market.getStability().unmodify(id);
		}
    }

    @Override
	protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
		float opad = 10f;
		tooltip.addPara("Specialized models of Replikas operating in Protektor units guard against threats to the Nation. Some serve as basic security at work facilities, other serve as supervisors of apartment blocks. Wherever a citizen of the Nation goes, there is guaranteed to be a Replika Protektor not too far behind.", opad);
		tooltip.addPara("%s stability", opad, Misc.getHighlightColor(), "+" + (int) protektor_stability_bonus);
	}

    @Override
	public boolean showIcon() {
		return isNationControlled();
	}

    public boolean isNationControlled(){
		if(market.getFactionId() == null){
			return false;
		}
		if(market.getFactionId().equals("eusan_nation")){
			return true;
		}
		return false;
	}
    
}
