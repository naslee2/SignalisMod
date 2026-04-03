package data.campaign.industries;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.ResourceDepositsCondition;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Eusan_Nation_Aeon_Facility extends BaseIndustry{
    //Kaysaar rewrote this entire thing bc I am dum. Thank you Kaysaar *sob*

    public static LinkedHashMap<String,Integer>miningBonuses = new LinkedHashMap<>();
    public static LinkedHashMap<String,Integer>refiningBonuses = new LinkedHashMap<>();
    static {
        miningBonuses.put(Commodities.ORE,1);
        miningBonuses.put(Commodities.RARE_ORE,1);
        miningBonuses.put(Commodities.ORGANICS,1);
        miningBonuses.put(Commodities.VOLATILES,1);

        refiningBonuses.put(Commodities.METALS,1);
        refiningBonuses.put(Commodities.RARE_METALS,1);
    }

    protected boolean aeonFacility_improve = false;
    protected float aeonFacility_bonus = 0.0f;
    protected float aeonFacility_stability_penalty = -1.0f;
    protected int aeonFacility_demand_increase = 0;

    protected String playerFaction = Misc.getCommissionFactionId();

//    @Override
//    public boolean isHidden(){
//        return !playerFaction.equals("eusan_nation");
//    }

//    @Override
//    public boolean isFunctional(){
//        return super.isFunctional() && market.getFactionId().equals("eusan_nation");
//    }

    public void apply(){
        super.apply(true);
        int size = market.getSize();
        int total_demand = size - 1;
        if(aeonFacility_improve){
            aeonFacility_bonus = 1.0f;
            //aeonFacility_stability_penalty = -1.0f;
            aeonFacility_demand_increase = 1;
        }

        demand(Commodities.SUPPLIES, total_demand + aeonFacility_demand_increase);
        demand(Commodities.CREW, total_demand + aeonFacility_demand_increase);
        demand(Commodities.HEAVY_MACHINERY, total_demand + aeonFacility_demand_increase);

        supply(Commodities.ORGANS, size-2);

        applyMiningModifications();

        //Pair<String, Integer> deficitSupplies = getMaxDeficit(Commodities.SUPPLIES);
        Pair<String, Integer> deficitCrew = getMaxDeficit(Commodities.CREW);
        applyDeficitToProduction(2, deficitCrew, Commodities.ORGANS);
        modifyStabilityAeonFac();
    }

    @Override
    public void unapply(){
        super.unapply();
        unapplyBonuses();
        unmodifyStabilityAeonFac();
    }

    public LinkedHashSet<String>getResourceConditionRelatedToCommodity(String commodityId){
        LinkedHashSet<String>conditions = new LinkedHashSet<>();
        for (Map.Entry<String, String> entry : ResourceDepositsCondition.COMMODITY.entrySet()) {
            if(entry.getValue().equals(commodityId)){
                conditions.add(entry.getKey());
            }
        }
        return conditions;
    }
    public void applyMiningBonusToIndustry(Industry ind, String commodityId){
        if(matchesConditionReq(getResourceConditionRelatedToCommodity(commodityId).toArray(new String[0]))){
            ind.getSupply(commodityId).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility",aeonFacility_bonus+miningBonuses.getOrDefault(commodityId,0),"AEON facility");
        }
    }
    public void applyRefiningBonusToIndustry(Industry ind, String commodityId){
        if(matchesConditionReq(getResourceConditionRelatedToCommodity(commodityId).toArray(new String[0]))){
            ind.getSupply(commodityId).getQuantity().modifyFlat("Eusan_Nation_Aeon_Facility",aeonFacility_bonus+refiningBonuses.getOrDefault(commodityId,0),"AEON facility");
        }
    }

    public void applyMiningModifications(){
        if(!market.isInEconomy())return;
        for (Industry industry : market.getIndustries()) {
            if(industry.getSpec().hasTag(Industries.MINING)){
                for (String s : miningBonuses.keySet()) {
                    applyMiningBonusToIndustry(industry, s);
                }
            }
            if(industry.getSpec().hasTag(Industries.REFINING)){
                for (String s : refiningBonuses.keySet()) {
                    applyRefiningBonusToIndustry(industry, s);
                }
            }
        }
    }

    public void unapplyBonuses(){
        for (Industry industry : market.getIndustries()) {
            industry.getSupply(Commodities.ORE).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            industry.getSupply(Commodities.RARE_ORE).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            industry.getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            industry.getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            industry.getSupply(Commodities.METALS).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
            industry.getSupply(Commodities.RARE_METALS).getQuantity().unmodifyFlat("Eusan_Nation_Aeon_Facility");
        }
    }

    public void modifyStabilityAeonFac(){
        if(aeonFacility_improve){
            market.getStability().modifyFlat("Eusan_Nation_Aeon_Facility",aeonFacility_stability_penalty,"Aeon Facility Improvements");
        }
    }

    public void unmodifyStabilityAeonFac(){
        market.getStability().unmodifyFlat("Eusan_Nation_Aeon_Facility");
    }

    public boolean matchesConditionReq(String ... conditions){
        for (String condition : conditions) {
            if(market.hasCondition(condition)) {
                return true;
            }
        }
        return false;
    }

//    public boolean producedThisCommodity(Industry ind,String commodity){
//        return ind.getSupply(commodity).getQuantity().getModifiedInt()>0;
//    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
        return mode != IndustryTooltipMode.NORMAL || isFunctional();
    }

    @Override
    protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
        float mining_ore_total = miningBonuses.getOrDefault(Commodities.ORE,0) + aeonFacility_bonus;
        float mining_rareOre_total = miningBonuses.getOrDefault(Commodities.RARE_ORE,0) + aeonFacility_bonus;
        float mining_organics_total = miningBonuses.getOrDefault(Commodities.ORGANICS,0) + aeonFacility_bonus;
        float mining_volatiles_total = miningBonuses.getOrDefault(Commodities.VOLATILES,0) + aeonFacility_bonus;
        float refining_metal_total = refiningBonuses.getOrDefault(Commodities.METALS,0) + aeonFacility_bonus;
        float refining_rare_metal_total = refiningBonuses.getOrDefault(Commodities.RARE_METALS,0) + aeonFacility_bonus;

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

//	@Override
//	public String getCurrentImage() {
//		String marketName = market.getId();
//		if(marketName.equals("leng_market")){
//			return Global.getSettings().getSpriteName("industry","leng_aeon_facility");
//		}
//		return super.getCurrentImage();
//	}

    public boolean isDemandLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean isSupplyLegal(CommodityOnMarketAPI com) {
        return true;
    }

    @Override
    public boolean isAvailableToBuild() {
        if(market.hasTag(Tags.MARKET_NO_INDUSTRIES_ALLOWED)) return false;

        //logger.info("Logger active: player playerFaction is: " + playerFaction);
        if(playerFaction == null) return false;
        if(!playerFaction.equals("eusan_nation")) return false;

        if(!market.hasIndustry(Industries.REFINING) && !market.hasIndustry(Industries.MINING)) return false;
        //if(market.hasIndustry(Industries.REFINING) || market.hasIndustry(Industries.MINING)) return true;
        //logger.info("Logger active: Industry check is: " + (!market.hasIndustry(Industries.REFINING) || !market.hasIndustry(Industries.MINING)));
        return market.hasIndustry(Industries.REFINING) || market.hasIndustry(Industries.MINING);

    }

    @Override
    public String getUnavailableReason() {
        return "Requires Mining or Refining industry and Eusan Nation commission!";
    }

//    @Override
//    public boolean canInstallAICores() {
//        return false;
//    }

    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }

    @Override //override this code so it automatically plugs back into vanilla function addImprovedSection()
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float initPad = 0f;
        float opad = 10f;
        //Color h = Misc.getHighlightColor();
        boolean addedSomething = false;
        if (canImproveToIncreaseProduction()) {
            String unit = "unit";
            if (getImproveProductionBonus() > 1) { //alex had this at != 1 which is hmmm
                unit = "units";
            }
            if (mode == ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
                info.addPara("Production increased by %s " + unit + ".", initPad, Misc.getHighlightColor(),
                        "" + getImproveProductionBonus());
                info.addPara("Stability decreased by %s ", opad, Misc.getNegativeHighlightColor(),
                        "" + aeonFacility_stability_penalty);
                info.addPara("Increases demand for all inputs by %s ", opad, Misc.getNegativeHighlightColor(),
                        "" + 1);
            } else {
                info.addPara("Increases production by %s " + unit + ".", initPad, Misc.getHighlightColor(),
                        "" + getImproveProductionBonus());
                info.addPara("Stability decreased by %s ", opad, Misc.getNegativeHighlightColor(),
                        "" + aeonFacility_stability_penalty);
                info.addPara("Increases demand for all inputs by %s ", opad, Misc.getNegativeHighlightColor(),
                        "" + 1);
            }
            initPad = opad;
            addedSomething = true;
        }

        if (mode != ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            info.addPara("Each improvement made at a colony doubles the number of " + Misc.STORY + " points required to make an additional improvement.", initPad,
                    Misc.getStoryOptionColor(), Misc.STORY + " points");
            addedSomething = true;
        }
        if (!addedSomething) {
            info.addSpacer(-opad);
        }
    }

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