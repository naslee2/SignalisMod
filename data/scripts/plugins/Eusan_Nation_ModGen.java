package data.scripts.plugins;

import data.scripts.systems.Eusan_Nation_System;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.util.Misc;

// import com.fs.starfarer.api.campaign.FactionAPI;
// import com.fs.starfarer.api.impl.campaign.ids.Factions;
// import com.fs.starfarer.api.impl.campaign.CoreScript;
// import com.fs.starfarer.api.campaign.StarSystemAPI;
//import com.fs.starfarer.api.util.Misc;

import exerelin.campaign.SectorManager;
import exerelin.campaign.intel.missions.remnant.RemnantM1;

public class Eusan_Nation_ModGen  {
    
    public void generateNew(SectorAPI sector){
        new Eusan_Nation_System().generate(sector);
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("eusan_nation");
        new Eusan_Nation_SetFactionRelations().SetFactionRelations(sector);

    }

}
