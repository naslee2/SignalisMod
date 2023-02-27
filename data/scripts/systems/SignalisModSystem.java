package data.scripts.systems;

import java.awt.Color;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
//import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;


public class SignalisModSystem {

    public void generate(SectorAPI sector){
        StarSystemAPI system = sector.createStarSystem("Alatyr");
        system.getLocation().set(-34000, 35000);
        system.setBackgroundTextureFilename("graphics/backgrounds/background5.jpg");
    
        PlanetAPI signalisStar = system.initStar("Die Sonne", "star_yellow", 650f, 450); //initeStar( Unique starId, planettype from planets.json, radius, corona radius from star edge)
        
        PlanetAPI buyan = system.addPlanet("signalis_buyan", signalisStar , "Buyan", "toxic", 30f, 170f, 2000f, 225f);
        ProcgenUsedNames.notifyUsed("Buyan");
        buyan.setFaction("signalis_eusan_nation");
    
        MarketAPI buyan_market = Global.getFactory().createMarket("buyan_market", buyan.getName(), 5);
        buyan_market.setPrimaryEntity(buyan);
        buyan.setMarket(buyan_market);
        buyan_market.setFactionId("signalis_eusan_nation");
        buyan_market.addCondition(Conditions.POPULATION_5);
        buyan_market.addCondition(Conditions.TOXIC_ATMOSPHERE);
        buyan_market.addCondition(Conditions.RARE_ORE_MODERATE);
        buyan_market.addCondition(Conditions.ORE_MODERATE);
        buyan_market.addCondition(Conditions.HOT);
        buyan_market.addCondition(Conditions.ORGANICS_COMMON);
        buyan_market.addCondition(Conditions.VOLATILES_DIFFUSE);
        buyan_market.addCondition(Conditions.RUINS_EXTENSIVE);
        buyan_market.addIndustry(Industries.POPULATION);
        buyan_market.addIndustry(Industries.WAYSTATION);
        buyan_market.addIndustry(Industries.MINING);
        buyan_market.addIndustry(Industries.REFINING);
        buyan_market.addIndustry(Industries.MEGAPORT);
        buyan_market.addIndustry(Industries.HEAVYBATTERIES);
        buyan_market.addIndustry(Industries.MILITARYBASE);
        buyan_market.addIndustry(Industries.BATTLESTATION);
        buyan_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        buyan_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        buyan_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        buyan_market.addSubmarket(Submarkets.GENERIC_MILITARY);
        buyan_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(buyan_market, false);

        cleanup(system);
    }
    
    //Jump point generator
    void cleanup(StarSystemAPI system) {
        system.autogenerateHyperspaceJumpPoints(true, true);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }


}
