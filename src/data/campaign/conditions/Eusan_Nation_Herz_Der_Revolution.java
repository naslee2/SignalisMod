package data.campaign.conditions;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class Eusan_Nation_Herz_Der_Revolution extends BaseMarketConditionPlugin{

    protected float heimat_stability_bonus = 2.0f;
	protected float heimat_defense_bonus = 2.0f;

    public void apply(String id) {
		if(isNationControlled()){
			market.getStability().modifyFlat(id,  heimat_stability_bonus, "Herz der Revolution");
			market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(id,heimat_defense_bonus, "Herz der Revolution");
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
		Color h = Misc.getHighlightColor();
		tooltip.addPara("Heimat is home to many government agencies, design bureaus, manufacturing and research facilities. The people who work here contribute greatly to the Nation and their best interests are served ensuring the Revolution endures.", opad);
		tooltip.addPara("%s stability", opad, Misc.getHighlightColor(), "+" + (int) heimat_stability_bonus);
		tooltip.addPara("%s Ground defense strength", opad, h, Strings.X + heimat_defense_bonus);
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
