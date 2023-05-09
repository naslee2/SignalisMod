package data.scripts.systems;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.CoreScript;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.util.Misc;

import exerelin.campaign.SectorManager;

public class SignalisModGen  {
    
    public void generateNew(SectorAPI sector){
        new SignalisModSystem().generate(sector);
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("eusan_nation");
        SetFactionRelations(sector);
        
    }

    // public static void generateNew(){
    //     boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    //     if (!haveNexerelin || SectorManager.getManager().isCorvusMode()){
    //         SectorAPI sector = Global.getSector();
    //         new SignalisGen().generate(sector);
    //         SetFactionRelations(sector);
    //     }
    // }

    public static void SetFactionRelations(SectorAPI sector){
        FactionAPI eusan_nation = sector.getFaction("eusan_nation");
        FactionAPI player = sector.getFaction(Factions.PLAYER);
        FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
        FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
        FactionAPI pirates = sector.getFaction(Factions.PIRATES);
        FactionAPI independent = sector.getFaction(Factions.INDEPENDENT);
        FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
        FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);
        FactionAPI diktat = sector.getFaction(Factions.DIKTAT);
        FactionAPI persean = sector.getFaction(Factions.PERSEAN);

        eusan_nation.setRelationship(player.getId(), 0.0f);
        eusan_nation.setRelationship(diktat.getId(), 0.4f);
        eusan_nation.setRelationship(hegemony.getId(), -0.5f);
        eusan_nation.setRelationship(tritachyon.getId(), -0.5f);
        eusan_nation.setRelationship(pirates.getId(), -0.2f);
        eusan_nation.setRelationship(independent.getId(), -0.2f);
        eusan_nation.setRelationship(church.getId(), -0.5f);
        eusan_nation.setRelationship(path.getId(), -0.5f);
        eusan_nation.setRelationship(persean.getId(), 0.3f);
    }

}
