package data.campaign.submarkets;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI.CoreUITradeMode;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.util.Highlights;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class Eusan_Nation_VolksmarineSubMarket extends BaseSubmarketPlugin{

    @Override
    public void init(SubmarketAPI submarket) {
		super.init(submarket);
	}
    
    @Override
    public void updateCargoPrePlayerInteraction(){
        sinceLastCargoUpdate = 0f;
        float seconds = Global.getSector().getClock().convertToSeconds(sinceLastCargoUpdate);
        addAndRemoveStockpiledResources(seconds, false, true, true);
        
        if (okToUpdateShipsAndWeapons()){
            sinceSWUpdate = 0f;
            pruneWeapons(0f);
            int weapons = 12 + Math.max(0, this.market.getSize() - 1) * 2;
            int fighters = 2 + Math.max(0, this.market.getSize() - 3);
            addWeapons(weapons, weapons + 2, 3, submarket.getFaction().getId());
            addFighters(fighters, fighters + 2, 3, submarket.getFaction().getId());
            
            float stability = market.getStabilityValue();
            float sMult = Math.max(0.1f, stability/10f);
            getCargo().getMothballedShips().clear();
            addShips(submarket.getFaction().getId(),
                200f * sMult,
                15f,
                10f,
                20f,
                0f,
                10f,
                null,
                0f,
                null,
                null);

            addHullMods(4, 2 + itemGenRandom.nextInt(4));
        }
        getCargo().sort();
    }


    protected boolean requiresCommission(RepLevel req){
        if(!submarket.getFaction().getCustomBoolean(Factions.CUSTOM_OFFERS_COMMISSIONS)){
            return false;
        }

        if(req.isAtWorst(RepLevel.COOPERATIVE)){
            return true;
        }
        return false;
    }

    protected boolean hasCommission(){
        return submarket.getFaction().getId().equals(Misc.getCommissionFactionId());
    }

    @Override
    public boolean shouldHaveCommodity(CommodityOnMarketAPI com) {
		if (Commodities.CREW.equals(com.getId())) return true;
		return com.getCommodity().hasTag(Commodities.TAG_MILITARY);
	}

    @Override
    public boolean isEnabled(CoreUIAPI ui){
        if (ui.getTradeMode() == CoreUITradeMode.SNEAK){
            return false;
        } 
        RepLevel level = submarket.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
        return level.isAtWorst(RepLevel.COOPERATIVE);
    }

    @Override
    public String getTooltipAppendix(CoreUIAPI ui) {
		if (!isEnabled(ui)) {
			return "Requires: " + submarket.getFaction().getDisplayName() + " - " + RepLevel.COOPERATIVE.getDisplayName().toLowerCase();
		}
		if (ui.getTradeMode() == CoreUITradeMode.SNEAK) {
			return "Requires: proper docking authorization";
		}
		return null;
	}

    @Override
    public Highlights getTooltipAppendixHighlights(CoreUIAPI ui) {
		String appendix = getTooltipAppendix(ui);
		if (appendix == null) return null;
		
		Highlights h = new Highlights();
		h.setText(appendix);
		h.setColors(Misc.getNegativeHighlightColor());
		return h;
	}


    @Override
    public boolean isHidden(){
        if(market.getFaction() != Global.getSector().getFaction("eusan_nation")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isMilitaryMarket() {
		return true;
	}

    @Override
    public boolean isParticipatesInEconomy() {
		return false;
	}
}
