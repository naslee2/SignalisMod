// package com.fs.starfarer.api.impl.campaign.submarkets;

// import com.fs.starfarer.api.campaign.SubmarketPlugin;
// import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
// import com.fs.starfarer.api.Global;
// import com.fs.starfarer.api.campaign.CargoStackAPI;
// import com.fs.starfarer.api.campaign.CoreUIAPI;
// import com.fs.starfarer.api.campaign.RepLevel;
// import com.fs.starfarer.api.fleet.FleetMemberAPI;

// public class signalis_eusan_nation_volksmarineSubMarket extends BaseSubmarketPlugin{
    
//     @Override
//     public void init(SubmarketAPI submarket) {
// 		super.init(submarket);
// 	}
    
//     @Override
//     public void updateCargoPrePlayerInteraction(){
//         sinceLastCargoUpdate = 0f;
//         float seconds = Global.getSector().getClock().convertToSeconds(sinceLastCargoUpdate);
//         addAndRemoveStockpiledResources(seconds, false, true, true);
        
//         if (okToUpdateShipsAndWeapons()){
//             sinceSWUpdate = 0f;
//             pruneWeapons(0f);

//             int weapons = 10 + Math.max(0, market.getSize() - 1 * 2);
//             int fighters = 5 + Math.max(0, market.getSize() - 3);

//             addWeapons(weapons, weapons + 2, 3, market.getFaction().getId());
//             addFighters(fighters, fighters, 3, market.getFactionId());
            
//             float stability = market.getStabilityValue();
//             float sMult = Math.max(0.1f, stability/10f);
//             getCargo().getMothballedShips().clear();
//             addShips(submarket.getFaction().getId(),
//                 200f * sMult,
//                 15f,
//                 10f,
//                 20f,
//                 0f,
//                 10f,
//                 null,
//                 0f,
//                 null,
//                 null);

//             addHullMods(4, 2 + itemGenRandom.nextInt(4));
//         }
//         getCargo().sort();
//     }

//     @Override
//     public String getName(){
//         return "Volksmarine";
//     }

//     @Override
//     public boolean isMilitaryMarket() {
// 		return true;
// 	}

//     @Override
//     public boolean isParticipatesInEconomy() {
// 		return false;
// 	}
// }
