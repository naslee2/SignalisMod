package data.scripts.plugins;


import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class Eusan_Nation_SetFactionRelations {
    
    public void SetFactionRelations(SectorAPI sector){
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
        FactionAPI lionsguard = sector.getFaction(Factions.LIONS_GUARD);
        FactionAPI remnants = sector.getFaction(Factions.REMNANTS);
        FactionAPI derelicts = sector.getFaction(Factions.DERELICT);
        FactionAPI knightsofLudd = sector.getFaction(Factions.KOL);

        //vanilla relations
        eusan_nation.setRelationship(player.getId(), 0.0f);
        eusan_nation.setRelationship(diktat.getId(), 0.6f);
        eusan_nation.setRelationship(hegemony.getId(), -0.4f);
        eusan_nation.setRelationship(tritachyon.getId(), -0.1f);
        eusan_nation.setRelationship(pirates.getId(), -0.5f);
        eusan_nation.setRelationship(independent.getId(), 0.1f);
        eusan_nation.setRelationship(church.getId(), -0.5f);
        eusan_nation.setRelationship(path.getId(), -0.5f);
        eusan_nation.setRelationship(persean.getId(), 0.3f);
        eusan_nation.setRelationship(lionsguard.getId(), 0.6f);
        eusan_nation.setRelationship(remnants.getId(), -0.1f);
        eusan_nation.setRelationship(derelicts.getId(), -0.5f);
        eusan_nation.setRelationship(knightsofLudd.getId(), -0.25f);

        //mod faction relations
        eusan_nation.setRelationship("batavia", -0.1f);
        eusan_nation.setRelationship("star_federation", 0.1f);
    }
}
