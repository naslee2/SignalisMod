package data.campaign.industries;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import org.apache.log4j.Logger;

import java.awt.Color;

public class eusan_nation_aeon_facility extends BaseIndustry{

	Logger logger = Global.getLogger(eusan_nation_aeon_facility.class);

	protected String[] miningOreConditionsList = new String[] {Conditions.ORE_ABUNDANT, Conditions.ORE_MODERATE, Conditions.ORE_RICH, Conditions.ORE_SPARSE, Conditions.ORE_ULTRARICH};
	protected String[] miningRareOreConditionsList = new String[] {Conditions.RARE_ORE_ABUNDANT, Conditions.RARE_ORE_MODERATE, Conditions.RARE_ORE_RICH, Conditions.RARE_ORE_SPARSE, Conditions.RARE_ORE_ULTRARICH};
	protected String[] miningOrganicsConditionsList = new String[] {Conditions.ORGANICS_ABUNDANT, Conditions.ORGANICS_COMMON, Conditions.ORGANICS_PLENTIFUL, Conditions.ORGANICS_TRACE};
	protected String[] miningvolatilesConditionsList = new String[] {Conditions.VOLATILES_ABUNDANT, Conditions.VOLATILES_DIFFUSE, Conditions.VOLATILES_PLENTIFUL, Conditions.VOLATILES_TRACE};

	protected float mining_ore_bonus = 2.0f;
	protected float mining_rareOre_bonus = 1.0f;
	protected float mining_organics_bonus = 2.0f;
	protected float mining_volatiles_bonus = 2.0f;

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

        demand(Commodities.SUPPLIES, size);
		demand(Commodities.CREW, size);

		//supply(Commodities.ORE, size - 1);
		//supply(Commodities.ORGANICS, size - 2);
		supply(Commodities.ORGANS, size);

		for (Industry industry : market.getIndustries()) {
			logger.info("Logger Active: Industry is " + industry);
            if(industry.getSpec().hasTag(Industries.MINING)){
				logger.info("Logger Active: Mining tag is " + industry.getSpec().hasTag(Industries.MINING));
                if(matchesConditionReq(miningOreConditionsList)){
					logger.info("Logger Active: Ore Condition found for " + industry);
                    industry.getSupply(Commodities.ORE).getQuantity().modifyFlat("AEON Facility", mining_ore_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningRareOreConditionsList)){
					logger.info("Logger Active: Rare Ore Condition found for " + industry);
                    industry.getSupply(Commodities.RARE_ORE).getQuantity().modifyFlat("AEON Facility", mining_rareOre_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningOrganicsConditionsList)){
					logger.info("Logger Active: Organics Condition found for " + industry);
                    industry.getSupply(Commodities.ORGANICS).getQuantity().modifyFlat("AEON Facility", mining_organics_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningvolatilesConditionsList)){
					logger.info("Logger Active: Volitailes Condition found for " + industry);
                    industry.getSupply(Commodities.VOLATILES).getQuantity().modifyFlat("AEON Facility", mining_volatiles_bonus, "AEON Facility");
                }
            }
        }

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
		for (Industry industry : market.getIndustries()) {
			if(industry.getSpec().hasTag(Industries.MINING)){
				market.getIndustry(Industries.MINING).getSupply(Commodities.ORE).getQuantity().unmodifyFlat("AEON Facility");
				market.getIndustry(Industries.MINING).getSupply(Commodities.RARE_ORE).getQuantity().unmodifyFlat("AEON Facility");
				market.getIndustry(Industries.MINING).getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat("AEON Facility");
				market.getIndustry(Industries.MINING).getSupply(Commodities.ORGANICS).getQuantity().unmodifyFlat("AEON Facility");
			}
		}
    }

	public boolean matchesConditionReq(String ... conditions){
        for (String condition : conditions) {
			//logger.info("Logger Active: Condition is: " + condition);
            if(market.hasCondition(condition)) {
				//logger.info("Logger Active: market has condition listed. TRUE");
				return true;
        	}
		}
		//logger.info("Logger Active: market does not have condition listed: ");
        return false;
    }

    public boolean producedThisCommodity(Industry ind,String commodity){
        return ind.getSupply(commodity).getQuantity().getModifiedInt()>0;
    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
		return mode != IndustryTooltipMode.NORMAL || isFunctional();
	}

	@Override
	protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
		if (mode != IndustryTooltipMode.NORMAL || isFunctional()) {
			addStabilityPostDemandSection(tooltip, hasDemand, mode);
			
		}
		float opad = 10f;
		Color h = Misc.getHighlightColor();
		tooltip.addPara("Enhances Mining Industry production", h, opad);
		if(market.hasCondition(miningOreConditionsList[0]) || market.hasCondition(miningOreConditionsList[1]) || market.hasCondition(miningOreConditionsList[2]) || market.hasCondition(miningOreConditionsList[3]) || market.hasCondition(miningOreConditionsList[4])){
			tooltip.addPara("Metal Ore Bonus: %s", opad, h, "+" + String.valueOf(mining_ore_bonus));
		}
		if(market.hasCondition(miningRareOreConditionsList[0]) || market.hasCondition(miningRareOreConditionsList[1]) || market.hasCondition(miningRareOreConditionsList[2]) || market.hasCondition(miningRareOreConditionsList[3]) || market.hasCondition(miningRareOreConditionsList[4])){
			tooltip.addPara("Transplutonic Ore Bonus: %s", opad, h, "+" + String.valueOf(mining_rareOre_bonus));
		}
		if(market.hasCondition(miningOrganicsConditionsList[0]) || market.hasCondition(miningOrganicsConditionsList[1]) || market.hasCondition(miningOrganicsConditionsList[2]) || market.hasCondition(miningOrganicsConditionsList[3])){
			tooltip.addPara("Organics Bonus: %s", opad, h, "+" + String.valueOf(mining_organics_bonus));
		}
		if(market.hasCondition(miningvolatilesConditionsList[0]) || market.hasCondition(miningvolatilesConditionsList[1]) || market.hasCondition(miningvolatilesConditionsList[2]) || market.hasCondition(miningvolatilesConditionsList[3])){
			tooltip.addPara("Volatiles Bonus: %s", opad, h, "+" + String.valueOf(mining_volatiles_bonus));
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