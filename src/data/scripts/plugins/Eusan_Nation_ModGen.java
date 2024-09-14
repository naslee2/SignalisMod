package data.scripts.plugins;

import data.scripts.systems.Eusan_Nation_System;

import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

public class Eusan_Nation_ModGen  {
    
    public void generateNew(SectorAPI sector){
        new Eusan_Nation_System().generate(sector);
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("eusan_nation");
        new Eusan_Nation_SetFactionRelations().SetFactionRelations(sector);
        //new Eusan_Nation_People().create();
    }


}
