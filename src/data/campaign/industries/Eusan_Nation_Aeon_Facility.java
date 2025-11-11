package data.campaign.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import java.awt.Color;

public class Eusan_Nation_Aeon_Facility extends BaseIndustry{


	protected String[] miningOreConditionsList = new String[] {Conditions.ORE_ABUNDANT, Conditions.ORE_MODERATE, Conditions.ORE_RICH, Conditions.ORE_SPARSE, Conditions.ORE_ULTRARICH};
	protected String[] miningRareOreConditionsList = new String[] {Conditions.RARE_ORE_ABUNDANT, Conditions.RARE_ORE_MODERATE, Conditions.RARE_ORE_RICH, Conditions.RARE_ORE_SPARSE, Conditions.RARE_ORE_ULTRARICH};
	protected String[] miningOrganicsConditionsList = new String[] {Conditions.ORGANICS_ABUNDANT, Conditions.ORGANICS_COMMON, Conditions.ORGANICS_PLENTIFUL, Conditions.ORGANICS_TRACE};
	protected String[] miningvolatilesConditionsList = new String[] {Conditions.VOLATILES_ABUNDANT, Conditions.VOLATILES_DIFFUSE, Conditions.VOLATILES_PLENTIFUL, Conditions.VOLATILES_TRACE};

	protected float mining_ore_bonus = 1.0f;
	protected float mining_rareOre_bonus = 1.0f;
	protected float mining_organics_bonus = 1.0f;
	protected float mining_volatiles_bonus = 1.0f;
	protected float refining_metal_bonus = 1.0f;
	protected float refining_rare_metal_bonus = 1.0f;

    protected boolean aeonFacility_improve = false;
    protected float aeonFacility_bonus = 0.0f;

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

        demand(Commodities.SUPPLIES, size-1);
		demand(Commodities.CREW, size-1);
		demand(Commodities.HEAVY_MACHINERY, size-1);

		supply(Commodities.ORGANS, size-2);
        if(aeonFacility_improve){
            aeonFacility_bonus = 1.0f;
        }
        applyMiningModifications();

		//Pair<String, Integer> deficitSupplies = getMaxDeficit(Commodities.SUPPLIES);
		Pair<String, Integer> deficitCrew = getMaxDeficit(Commodities.CREW);

		//applyDeficitToProduction(1, deficitCrew, Commodities.ORGANS);
		//applyDeficitToProduction(1, deficitSupplies, Commodities.ORE, Commodities.ORGANICS);
		applyDeficitToProduction(1, deficitCrew, Commodities.ORGANS);
		
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
        unapplyMiningModifications();
//        if(!aeonFacility_improve){
//            unapplyMiningModifications();
//        }
    }

    public void applyMiningModifications(){
        for (Industry industry : market.getIndustries()) {
            //logger.info("Logger Active: Industry is " + industry);
            if(industry.getSpec().hasTag(Industries.MINING)){
                //logger.info("Logger Active: Mining tag is " + industry.getSpec().hasTag(Industries.MINING));
                if(matchesConditionReq(miningOreConditionsList)){
                    //logger.info("Logger Active: Ore Condition found for " + industry);
                    industry.getSupply(Commodities.ORE).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", mining_ore_bonus + aeonFacility_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningRareOreConditionsList)){
                    //logger.info("Logger Active: Rare Ore Condition found for " + industry);
                    industry.getSupply(Commodities.RARE_ORE).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", mining_rareOre_bonus+ aeonFacility_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningOrganicsConditionsList)){
                    //logger.info("Logger Active: Organics Condition found for " + industry);
                    industry.getSupply(Commodities.ORGANICS).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", mining_organics_bonus+ aeonFacility_bonus, "AEON Facility");
                }
                if(matchesConditionReq(miningvolatilesConditionsList)){
                    //logger.info("Logger Active: Volitailes Condition found for " + industry);
                    industry.getSupply(Commodities.VOLATILES).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", mining_volatiles_bonus+ aeonFacility_bonus, "AEON Facility");
                }
            }
            if(industry.getSpec().hasTag(Industries.REFINING)){
                industry.getSupply(Commodities.METALS).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", refining_metal_bonus+ aeonFacility_bonus, "AEON Facility");
                industry.getSupply(Commodities.RARE_METALS).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility", refining_rare_metal_bonus+ aeonFacility_bonus, "AEON Facility");
            }
        }
    }

    public void unapplyMiningModifications(){
        for (Industry industry : market.getIndustries()) {
            if(industry.getSpec().hasTag(Industries.MINING)){
                market.getIndustry(Industries.MINING).getSupply(Commodities.ORE).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
                market.getIndustry(Industries.MINING).getSupply(Commodities.RARE_ORE).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
                market.getIndustry(Industries.MINING).getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
                market.getIndustry(Industries.MINING).getSupply(Commodities.ORGANICS).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            }
            if(industry.getSpec().hasTag(Industries.REFINING)){
                market.getIndustry(Industries.MINING).getSupply(Commodities.METALS).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
                market.getIndustry(Industries.MINING).getSupply(Commodities.RARE_METALS).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");

            }
        }
    }

	public boolean matchesConditionReq(String ... conditions){
        for (String condition : conditions) {
            if(market.hasCondition(condition)) {
				return true;
        	}
		}
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
        float mining_ore_total = mining_ore_bonus + aeonFacility_bonus;
        float mining_rareOre_total = mining_rareOre_bonus + aeonFacility_bonus;
        float mining_organics_total = mining_organics_bonus + aeonFacility_bonus;
        float mining_volatiles_total = mining_volatiles_bonus + aeonFacility_bonus;
        float refining_metal_total = refining_metal_bonus + aeonFacility_bonus;
        float refining_rare_metal_total = refining_rare_metal_bonus + aeonFacility_bonus;

		float opad = 10f;
		Color h = Misc.getHighlightColor();

		for (Industry industry : market.getIndustries()){
			if(industry.getSpec().hasTag(Industries.MINING)){
				tooltip.addPara("Enhances Mining Industry production", h, opad);
				if(industry.getSupply(Commodities.ORE).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Metal Ore Bonus: %s", opad, h, "+" + (int) mining_ore_total);
				}
				if(industry.getSupply(Commodities.RARE_ORE).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Transplutonic Ore Bonus: %s", opad, h, "+" + (int) mining_rareOre_total);
				}
				if(industry.getSupply(Commodities.ORGANICS).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Organics Bonus: %s", opad, h, "+" + (int) mining_organics_total);
				}
				if(industry.getSupply(Commodities.VOLATILES).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Volatiles Bonus: %s", opad, h, "+" + (int) mining_volatiles_total);
				}
			}
			if(industry.getSpec().hasTag(Industries.REFINING)){
				tooltip.addPara("Enhances Refining Industry production", h, opad);
				if(industry.getSupply(Commodities.METALS).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Metals Bonus: %s", opad, h, "+" + (int) refining_metal_total);
				}
				if(industry.getSupply(Commodities.RARE_METALS).getQuantity().getModifiedValue() > 0){
					tooltip.addPara("Transplutonics Bonus: %s", opad, h, "+" + (int) refining_rare_metal_total);
				}
			}
		}
		
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
		String marketName = market.getId();
		if(marketName.equals("leng_market")){
			return Global.getSettings().getSpriteName("industry","leng_aeon_facility");
		}
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

//	@Override
//	public boolean canImprove() { return false; }

    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }

//    @Override //override this code so it automatically plugs back into vanilla function addImprovedSection()
//    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
//        float initPad = 0f;
//        float opad = 10f;
//        Color h = Misc.getHighlightColor();
//        boolean addedSomething = false;
//        if (canImproveToIncreaseProduction()) {
//            String unit = "unit";
//            if (getImproveProductionBonus() > 1) { //alex had this at != 1 which is hmmm
//                unit = "units";
//            }
//            if (mode == ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
//                info.addPara("Production increased by %s " + unit + ".", initPad, Misc.getHighlightColor(),
//                        "" + getImproveProductionBonus());
//                for (Industry industry : market.getIndustries()){
//                    if(industry.getSpec().hasTag(Industries.MINING)){
//                        if(industry.getSupply(Commodities.ORE).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Metal Ore Bonus: %s" + unit, opad, h, "+" + (int) mining_ore_bonus);
//                        }
//                        if(industry.getSupply(Commodities.RARE_ORE).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Transplutonic Ore Bonus: %s" + unit + ".", opad, h, "+" + (int) mining_rareOre_bonus);
//                        }
//                        if(industry.getSupply(Commodities.ORGANICS).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Organics Bonus: %s" + unit + ".", opad, h, "+" + (int) mining_organics_bonus);
//                        }
//                        if(industry.getSupply(Commodities.VOLATILES).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Volatiles Bonus: %s" + unit + ".", opad, h, "+" + (int) mining_volatiles_bonus);
//                        }
//                    }
//                    if(industry.getSpec().hasTag(Industries.REFINING)){
//                        if(industry.getSupply(Commodities.METALS).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Metals Refining Bonus: %s" + unit + ".", opad, h, "+" + (int) refining_metal_bonus);
//                        }
//                        if(industry.getSupply(Commodities.RARE_METALS).getQuantity().getModifiedValue() > 0){
//                            info.addPara("Transplutonics Refining Bonus: %s" + unit  + ".", opad, h, "+" + (int) refining_rare_metal_bonus );
//                        }
//                    }
//                }
//            } else {
//                info.addPara("Increases production by %s " + unit + ".", initPad, Misc.getHighlightColor(),
//                        "" + getImproveProductionBonus());
//            }
//            initPad = opad;
//            addedSomething = true;
//        }
//
//        if (mode != ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
//            info.addPara("Each improvement made at a colony doubles the number of " +
//                            "" + Misc.STORY + " points required to make an additional improvement.", initPad,
//                    Misc.getStoryOptionColor(), Misc.STORY + " points");
//            addedSomething = true;
//        }
//        if (!addedSomething) {
//            info.addSpacer(-opad);
//        }
//    }

    @Override
    protected void updateImprovementSupplyAndDemandModifiers() {
        if (!canImproveToIncreaseProduction()) return;
        if (!isImproved()) return;

        int bonus = getImproveProductionBonus();
        if (bonus <= 0) return;

        supplyBonus.modifyFlat(getModId(3), bonus, getImprovementsDescForModifiers());
        aeonFacility_improve = true;
    }

}