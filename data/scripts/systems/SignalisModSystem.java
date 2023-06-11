package data.scripts.systems;

import java.awt.Color;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.JumpPointAPI;
import com.fs.starfarer.api.campaign.OrbitAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
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

        //Stable Location 1, 2 and 3
        SectorEntityToken relay_eusan = system.addCustomEntity("signalisStar_loc1", "Comm Relay 3NO", "comm_relay", "eusan_nation"); //3NO means 3 Note Oddity Number Stations
        SectorEntityToken loc2 = system.addCustomEntity("signalisStar_loc2", "Sensor Array Six", "sensor_array_makeshift", "eusan_nation"); //6 is the magic number
        SectorEntityToken loc3 = system.addCustomEntity("signalisStar_loc3", "Nav Buoy 84-21-A", "nav_buoy", "eusan_nation"); //Date when Falke became ill

        //Setting stable locations setCircularOrbitPointingDown(location/star/planet, angle, orbit raidus/range, orbit period)
        relay_eusan.setCircularOrbitPointingDown(signalisStar, 40f, 5200f, 220f);
        loc2.setCircularOrbitPointingDown(signalisStar, 310f, 8000f, 235f);
        loc3.setCircularOrbitPointingDown(signalisStar, 178f, 16600f, 921f); 
        
        //-BUYAN - Toxic World-
        PlanetAPI buyan = system.addPlanet("eusan_nation_buyan", signalisStar , "Buyan", "toxic", 30f, 170f, 2000f, 225f);
        ProcgenUsedNames.notifyUsed("Buyan");
        buyan.setFaction("eusan_nation");
        buyan.setCustomDescriptionId("eusan_nation_buyan_planet");
    
        MarketAPI buyan_market = Global.getFactory().createMarket("buyan_market", buyan.getName(), 5);
        buyan_market.setPrimaryEntity(buyan);
        buyan.setMarket(buyan_market);
        buyan_market.setFactionId("eusan_nation");
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
        PlanetAPI vineta = system.addPlanet("eusan_nation_vineta", signalisStar, "Vineta", "water", 88f, 190f, 6000f, 360f);
        ProcgenUsedNames.notifyUsed("Vineta");
        vineta.setFaction("eusan_nation");
        vineta.getSpec().setTexture(Global.getSettings().getSpriteName("planets","vineta_planet"));
        vineta.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "vineta_habglows"));
        vineta.getSpec().setCloudTexture(Global.getSettings().getSpriteName("cloud_layer", "vineta_clouds"));
        vineta.setCustomDescriptionId("eusan_nation_vineta_planet");
        //vineta.setInteractionImage("illustrations", "");
        vineta.applySpecChanges();

        MarketAPI vineta_market = Global.getFactory().createMarket("vineta_market", vineta.getName(), 7);
        vineta_market.setPrimaryEntity(vineta);
        vineta.setMarket(vineta_market);
        vineta_market.setFactionId("eusan_nation");
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

        PlanetAPI shattered_moon = system.addPlanet("eusan_nation_shattered_moon", vineta, "Shattered Moon", "barren", 270f, 90f, 750f, 51f);
        shattered_moon.getMarket().addCondition(Conditions.LOW_GRAVITY);
        shattered_moon.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        shattered_moon.getMarket().addCondition(Conditions.METEOR_IMPACTS);
        shattered_moon.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        shattered_moon.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        shattered_moon.setCustomDescriptionId("eusan_nation_shattered_moon");

        system.addRingBand(vineta, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 740f, 305f, null, null);

        //Inner Jump Point related to Vineta
        JumpPointAPI innerJumpPoint = Global.getFactory().createJumpPoint("alatyr_jump_point", "Inner Jump P");
        innerJumpPoint.setCircularOrbit(signalisStar, 266, 4200f, 296f);
        innerJumpPoint.setRelatedPlanet(vineta);
        system.addEntity(innerJumpPoint);

        //-KITZEH - Arid World-
        PlanetAPI kitezh = system.addPlanet("eusan_nation_kitezh", signalisStar, "Kitezh", "arid", 310f, 120f, 8000f, 687f);
        ProcgenUsedNames.notifyUsed("Kitezh");
        kitezh.setFaction("eusan_nation");
        kitezh.setCustomDescriptionId("eusan_nation_kitezh_planet");
        //kitezh.setInteractionImage("illustrations", "");

        MarketAPI kitezh_market = Global.getFactory().createMarket("kitezh_market", kitezh.getName(), 6);
        kitezh_market.setPrimaryEntity(kitezh);
        kitezh.setMarket(kitezh_market);
        kitezh_market.setFactionId("eusan_nation");
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

        //-GAS GIANT 1, DUST RINGS AND ROTFRONT-
        PlanetAPI gas_giant1 = system.addPlanet("eusan_nation_gas_giant1", signalisStar, "Jove", "gas_giant", 234f, 400f, 12500f, 4330f);
        gas_giant1.getSpec().setTexture(Global.getSettings().getSpriteName("planets","gas_giant1"));
        gas_giant1.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        gas_giant1.getMarket().addCondition(Conditions.VOLATILES_TRACE);
        gas_giant1.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        gas_giant1.applySpecChanges();
        gas_giant1.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        gas_giant1.setCustomDescriptionId("eusan_nation_gas_giant1_planet");

        PlanetAPI rotfront = system.addPlanet("eusan_nation_rotfront", gas_giant1, "Rotfront", "tundra", 0f, 90f, 800f, 22f);
        rotfront.setFaction("eusan_nation");
        rotfront.setCustomDescriptionId("eusan_nation_rotfront_planet");
        //rotfront.setInteractionImage("illustrations", "");

        MarketAPI rotfront_market = Global.getFactory().createMarket("rotfront_market", rotfront.getName(), 6);
        rotfront_market.setPrimaryEntity(rotfront);
        rotfront.setMarket(rotfront_market);
        rotfront_market.setFactionId("eusan_nation");
        rotfront_market.addCondition(Conditions.POPULATION_6);
        rotfront_market.addCondition(Conditions.HABITABLE);
        rotfront_market.addCondition(Conditions.COLD);
        rotfront_market.addCondition(Conditions.FARMLAND_POOR);
        rotfront_market.addCondition(Conditions.ORGANICS_COMMON);
        rotfront_market.addCondition(Conditions.ORE_SPARSE);
        rotfront_market.addCondition(Conditions.LOW_GRAVITY);
        rotfront_market.addIndustry(Industries.POPULATION);
        rotfront_market.addIndustry(Industries.MEGAPORT);
        rotfront_market.addIndustry(Industries.WAYSTATION);
        rotfront_market.addIndustry(Industries.FARMING);
        rotfront_market.addIndustry(Industries.MINING);
        rotfront_market.addIndustry(Industries.LIGHTINDUSTRY);
        rotfront_market.addIndustry(Industries.REFINING);
        rotfront_market.addIndustry(Industries.PATROLHQ);
        rotfront_market.addIndustry(Industries.HEAVYBATTERIES);
        rotfront_market.addIndustry(Industries.BATTLESTATION);
        //rotfront_market.addIndustry("eusan_nation_aeon_facility");
        rotfront_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        rotfront_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        rotfront_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        rotfront_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(rotfront_market, true);

        system.addRingBand(gas_giant1, "misc", "rings_dust0", 256f, 2, Color.white, 256f, 1000f, 300f);
        
        //-GAS GIANT 2, PRETTY RINGS AND HEIMAT-
        PlanetAPI gas_giant2 = system.addPlanet("eusan_nation_gas_giant2", signalisStar, "Saturnus", "gas_giant", 90f, 370f, 14500f, 1076f);
        gas_giant2.getSpec().setTexture(Global.getSettings().getSpriteName("planets","gas_giant2"));
        gas_giant2.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        gas_giant2.getMarket().addCondition(Conditions.VOLATILES_TRACE);
        gas_giant2.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        gas_giant2.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        gas_giant2.applySpecChanges();
        gas_giant2.setCustomDescriptionId("eusan_nation_gas_giant2_planet");

        PlanetAPI heimat = system.addPlanet("eusan_nation_heimat", gas_giant2, "Heimat", "toxic", 50f, 90f, 1100f, 56f);
        heimat.setFaction("eusan_nation");
        heimat.setCustomDescriptionId("eusan_nation_heimat_planet");
        //heimat.setInteractionImage("illustrations", "");

        MarketAPI heimat_market = Global.getFactory().createMarket("heimat_market", heimat.getName(), 6);
        heimat_market.setPrimaryEntity(heimat);
        heimat.setMarket(heimat_market);
        heimat_market.setFactionId("eusan_nation");
        heimat_market.addCondition(Conditions.POPULATION_6);
        heimat_market.addCondition(Conditions.ORGANICS_PLENTIFUL);
        heimat_market.addCondition(Conditions.VOLATILES_DIFFUSE);
        heimat_market.addCondition(Conditions.ORE_SPARSE);
        heimat_market.addCondition(Conditions.RARE_ORE_SPARSE);
        heimat_market.addCondition(Conditions.LOW_GRAVITY);
        heimat_market.addCondition(Conditions.TOXIC_ATMOSPHERE);
        heimat_market.addCondition(Conditions.REGIONAL_CAPITAL); //need to change to Headquaters condition when implemented in future Starsector versions
        heimat_market.addIndustry(Industries.MINING);
        heimat_market.addIndustry(Industries.FUELPROD);
        heimat_market.addIndustry(Industries.ORBITALWORKS);
        heimat_market.addIndustry(Industries.HIGHCOMMAND);
        heimat_market.addIndustry(Industries.MEGAPORT);
        heimat_market.addIndustry(Industries.WAYSTATION);
        heimat_market.addIndustry(Industries.HEAVYBATTERIES);
        heimat_market.addIndustry(Industries.POPULATION);
        heimat_market.addIndustry("eusan_nation_aeon_hq");
        heimat_market.addIndustry(Industries.STARFORTRESS);
        heimat_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        heimat_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        heimat_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        heimat_market.addSubmarket(Submarkets.GENERIC_MILITARY);
        heimat_market.addSubmarket("eusan_nation_volksmarine_market");
        heimat_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(heimat_market, true);

        system.addRingBand(gas_giant2, "misc", "rings_special0", 256f, 0, Color.white, 256f, 500f, 60f);
        system.addRingBand(gas_giant2, "misc", "rings_special0", 256f, 1, Color.white, 256f, 700f, 60f);
        
        //-LENG-
        PlanetAPI leng = system.addPlanet("eusan_nation_leng", signalisStar, "Leng", "barren", 180, 80f, 16400f, 3050f);
        ProcgenUsedNames.notifyUsed("Leng");
        leng.setFaction("eusan_nation");
        leng.setCustomDescriptionId("eusan_nation_leng_planet");
        //leng.setInteractionImage("illustrations", "");

        MarketAPI leng_market = Global.getFactory().createMarket("leng_market", leng.getName(), 5);
        leng_market.setPrimaryEntity(leng);
        leng.setMarket(leng_market);
        leng_market.setFactionId("eusan_nation");
        leng_market.addCondition(Conditions.POPULATION_5);
        leng_market.addCondition(Conditions.COLD);
        leng_market.addCondition(Conditions.LOW_GRAVITY);
        leng_market.addCondition(Conditions.ORE_ABUNDANT);
        leng_market.addCondition(Conditions.NO_ATMOSPHERE);
        leng_market.addCondition(Conditions.RARE_ORE_MODERATE);
        leng_market.addIndustry(Industries.SPACEPORT);
        leng_market.addIndustry(Industries.WAYSTATION);
        leng_market.addIndustry(Industries.GROUNDDEFENSES);
        leng_market.addIndustry(Industries.POPULATION);
        leng_market.addIndustry(Industries.MINING);
        leng_market.addIndustry(Industries.REFINING);
        leng_market.addIndustry(Industries.HEAVYINDUSTRY);
        leng_market.addIndustry(Industries.PATROLHQ);
        leng_market.addIndustry(Industries.ORBITALSTATION);
        leng_market.addIndustry("eusan_nation_aeon_facility");
        leng_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        leng_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        leng_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        leng_market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        sector.getEconomy().addMarket(leng_market, true);

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
