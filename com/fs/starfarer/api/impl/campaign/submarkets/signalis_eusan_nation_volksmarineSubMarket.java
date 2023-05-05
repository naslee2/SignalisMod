package com.fs.starfarer.api.impl.campaign.submarkets;

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

            addWeapons(4, 7, 3, submarket.getFaction().getId());
            addFighters(2, 3, 3, submarket.getFaction().getId());
            
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
    public String getName() {
		if (submarket.getFaction().getId().equals("signalis_eusan_nation")) {
			return "Volksmarine";
		}
		return Misc.ucFirst(submarket.getFaction().getPersonNamePrefix()) + "\n" + "Military";
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
