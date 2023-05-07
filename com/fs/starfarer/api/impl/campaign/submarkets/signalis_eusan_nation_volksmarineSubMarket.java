package com.fs.starfarer.api.impl.campaign.submarkets;

import org.apache.log4j.Logger;

import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class signalis_eusan_nation_volksmarineSubMarket extends BaseSubmarketPlugin{

    public static Logger log = Global.getLogger(signalis_eusan_nation_volksmarineSubMarket.class);
    
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

    @Override
    public boolean isHidden(){
        if(market.getFaction() != Global.getSector().getFaction("signalis_eusan_nation")){
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
