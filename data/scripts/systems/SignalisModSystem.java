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
        system.addCorona(signalisStar, 250f, 5f, 2f, 2f); //addCorona(SectorEntityToken star, float extraRadius, float windBurnLevel, float flareProbability, float crLossMult)

        PlanetAPI buyan = system.addPlanet("signalis_buyan", signalisStar , "Buyan", "toxic", 30f, 170f, 2000f, 225f);
        ProcgenUsedNames.notifyUsed("Buyan");
        buyan.setFaction("signalis_eusan_nation");
        //buyan.setCustomDescriptionId("signalis_buyan_planet");
    
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


        //-VINETA AND THE SHATTERED MOON AND DEBRIS RING- Terran world? Water World?
        PlanetAPI vineta = system.addPlanet("signalis_vineta", signalisStar, "Vineta", "water", 88f, 190f, 6000f, 360f);
        ProcgenUsedNames.notifyUsed("Vineta");
        vineta.setFaction("signalis_eusan_nation");
        //vineta.setCustomDescriptionId("signalis_vineta_planet");
        //vineta.setInteractionImage("illustrations", "");

        MarketAPI vineta_market = Global.getFactory().createMarket("vineta_market", vineta.getName(), 7);
        vineta_market.setPrimaryEntity(vineta);
        vineta.setMarket(vineta_market);
        vineta_market.setFactionId("signalis_eusan_nation");
        vineta_market.addCondition(Conditions.POPULATION_7);
        vineta_market.addCondition(Conditions.HABITABLE);
        vineta_market.addCondition(Conditions.MILD_CLIMATE);
        vineta_market.addCondition(Conditions.RUINS_EXTENSIVE);
        vineta_market.addCondition(Conditions.ORE_MODERATE);
        vineta_market.addCondition(Conditions.ORGANICS_ABUNDANT);
        vineta_market.addCondition(Conditions.WATER_SURFACE);
        vineta_market.addIndustry(Industries.POPULATION);
        vineta_market.addIndustry(Industries.MEGAPORT);
        vineta_market.addIndustry(Industries.AQUACULTURE);
        vineta_market.addIndustry(Industries.MINING);
        vineta_market.addIndustry(Industries.LIGHTINDUSTRY);
        vineta_market.addIndustry(Industries.HEAVYBATTERIES);
        vineta_market.addIndustry(Industries.WAYSTATION);
        vineta_market.addIndustry(Industries.PATROLHQ);
        vineta_market.addIndustry(Industries.BATTLESTATION);
        vineta_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        vineta_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        vineta_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        vineta_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(vineta_market, false);

        PlanetAPI shattered_moon = system.addPlanet("signalis_shattered_moon", vineta, "Shattered Moon", "barren", 270f, 90f, 750f, 51f);
        shattered_moon.getMarket().addCondition(Conditions.LOW_GRAVITY);
        shattered_moon.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        shattered_moon.getMarket().addCondition(Conditions.METEOR_IMPACTS);
        shattered_moon.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        shattered_moon.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        //shattered_moon.setCustomDescriptionId("signalis_shattered_moon");

        system.addRingBand(vineta, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 740f, 305f, null, null);

        //Inner Jump Point related to Vineta
        JumpPointAPI innerJumpPoint = Global.getFactory().createJumpPoint("alatyr_jump_point", "Inner Jump P");
        innerJumpPoint.setCircularOrbit(signalisStar, 266, 4200f, 296f);
        innerJumpPoint.setRelatedPlanet(vineta);
        system.addEntity(innerJumpPoint);


        PlanetAPI kitezh = system.addPlanet("signalis_kitezh", signalisStar, "Kitezh", "arid", 310f, 120f, 8000f, 687f);
        ProcgenUsedNames.notifyUsed("Kitezh");
        kitezh.setFaction("signalis_eusan_nation");

        MarketAPI kitezh_market = Global.getFactory().createMarket("kitezh_market", kitezh.getName(), 6);
        kitezh_market.setPrimaryEntity(kitezh);
        kitezh.setMarket(kitezh_market);
        kitezh_market.setFactionId("signalis_eusan_nation");
        kitezh_market.addCondition(Conditions.POPULATION_6);
        kitezh_market.addCondition(Conditions.HABITABLE);
        kitezh_market.addCondition(Conditions.FARMLAND_POOR);
        kitezh_market.addCondition(Conditions.ORE_MODERATE);
        kitezh_market.addCondition(Conditions.RARE_ORE_SPARSE);
        kitezh_market.addCondition(Conditions.LOW_GRAVITY);
        kitezh_market.addIndustry(Industries.FARMING);
        kitezh_market.addIndustry(Industries.MINING);
        kitezh_market.addIndustry(Industries.REFINING);
        kitezh_market.addIndustry(Industries.LIGHTINDUSTRY);
        kitezh_market.addIndustry(Industries.POPULATION);
        kitezh_market.addIndustry(Industries.WAYSTATION);
        kitezh_market.addIndustry(Industries.SPACEPORT);
        kitezh_market.addIndustry(Industries.PATROLHQ);
        kitezh_market.addIndustry(Industries.GROUNDDEFENSES);
        kitezh_market.addIndustry(Industries.BATTLESTATION);
        kitezh_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        kitezh_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        kitezh_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        kitezh_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(kitezh_market, false);

        //-AsteroidBelt- //starsector-core\graphics\planets
        system.addAsteroidBelt(signalisStar, 1000, 10000f, 256f, 300f, 350f, Terrain.ASTEROID_BELT, "Asteroid Ring");
        system.addRingBand(signalisStar, "misc", "rings_dust0", 256f, 0, Color.gray, 256f, 10200f, 400f);
        system.addRingBand(signalisStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, 10000f, 400f);
        system.addRingBand(signalisStar, "misc", "rings_dust0", 256f, 2, Color.gray, 256f, 9800f, 400f);

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
