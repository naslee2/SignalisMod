package data.campaign.fleets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.fleets.PersonalFleetScript;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.missions.FleetCreatorMission;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetSize;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerNum;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.MissionFleetAutoDespawn;

//import org.apache.log4j.Logger;
//import org.lwjgl.util.vector.Vector2f;

public class Eusan_Nation_personalFleetFalke_backup extends PersonalFleetScript {
    //Logger logger = Global.getLogger(Eusan_Nation_personalFleetFalke.class);

    public static String ADMIRAL_FALKE = "eusan_nation_admiral_falke";
    public static String EUSAN_NATION = "eusan_nation";

    public Eusan_Nation_personalFleetFalke_backup() {
        super(ADMIRAL_FALKE);
        setMinRespawnDelayDays(10f);
        setMaxRespawnDelayDays(20f);
    }

    @Override
    public CampaignFleetAPI spawnFleet() {

        MarketAPI heimat_market = Global.getSector().getEconomy().getMarket("heimat_market");
        //logger.info("Logger active: .getMarket is: " + heimat.getStarSystem());
        SectorEntityToken heimat_entity = Global.getSector().getEntityById("eusan_nation_heimat");

        FleetCreatorMission m = new FleetCreatorMission(random);
        m.beginFleet();

        Vector2f loc = heimat_market.getLocationInHyperspace();

        m.triggerCreateFleet(FleetSize.MAXIMUM, FleetQuality.SMOD_2, EUSAN_NATION, FleetTypes.TASK_FORCE, loc);
        m.triggerSetFleetOfficers( OfficerNum.MORE, OfficerQuality.UNUSUALLY_HIGH);
        m.triggerSetFleetCommander(getPerson());
        m.triggerSetFleetFaction(EUSAN_NATION);
        //m.triggerSetPatrol();
        m.triggerSetFleetMemoryValue(MemFlags.MEMORY_KEY_SOURCE_MARKET, heimat_market);
        m.triggerFleetSetNoFactionInName();
        m.triggerFleetSetName("Ever Victorious Fleet");
        m.triggerSetFleetQuality(FleetQuality.SMOD_1);
        //m.triggerFleetSetPatrolActionText("Patrolling");
        m.triggerPatrolAllowTransponderOff();
        //m.triggerOrderFleetPatrol(heimat.getStarSystem());

        CampaignFleetAPI falkeFleet = m.createFleet();
        FleetMemberAPI oldFlagship = falkeFleet.getFlagship();
        FleetMemberAPI newFlagship = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "eusan_nation_admiral_standard");
        falkeFleet.getFleetData().addFleetMember(newFlagship);
        //logger.info("Logger active: flagship is: " + newFlagship.getCaptain().getId());
        if (newFlagship != null && oldFlagship != null) {
            newFlagship.setCaptain(oldFlagship.getCaptain());
            oldFlagship.setFlagship(false);
            newFlagship.setFlagship(true);
            falkeFleet.getFleetData().setFlagship(newFlagship);
            falkeFleet.getFleetData().removeFleetMember(oldFlagship);
        }

        falkeFleet.removeScriptsOfClass(MissionFleetAutoDespawn.class);
        heimat_market.getContainingLocation().addEntity(falkeFleet);
        falkeFleet.setLocation(heimat_market.getPlanetEntity().getLocation().x, heimat_market.getPlanetEntity().getLocation().y);
        falkeFleet.addAssignment(FleetAssignment.ORBIT_PASSIVE, (SectorEntityToken) heimat_entity, 365);
        falkeFleet.setFacing((float) random.nextFloat() * 360f);


        //flagship
        falkeFleet.getFlagship().setShipName("VM Guardian of the Revolution");
        falkeFleet.getFlagship().getVariant().addMod("eusan_nation_K4_composite_armor");
        falkeFleet.getFlagship().getVariant().addPermaMod("eusan_nation_K4_composite_armor", false);
        falkeFleet.getFlagship().getVariant().addMod(HullMods.DEDICATED_TARGETING_CORE);
        falkeFleet.getFlagship().getVariant().addMod(HullMods.TURRETGYROS);

        //battleship division
        falkeFleet.getFleetData().addFleetMember("eusan_nation_admiral_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_admiral_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_admiral_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard");
        
        //cruisers
        falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard");

        //destroyers
        falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard");

        //frigates
        falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard");

        //support ships
        falkeFleet.getFleetData().addFleetMember("eusan_nation_pelikan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_pelikan_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_pisagua_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_pisagua_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard");

        falkeFleet.getFleetData().sort();

        //logger.info("Logger active: fleet is: " + falkeFleet.getFleetData());

        return falkeFleet;
    }

    @Override
    public boolean canSpawnFleetNow() {
        MarketAPI heimat = Global.getSector().getEconomy().getMarket("heimat_market");
        //logger.info("Logger active: fleet is spawning at: " + heimat.getId());
        if (heimat == null || heimat.hasCondition(Conditions.DECIVILIZED)) return false;
        if (!heimat.getFactionId().equals(EUSAN_NATION)) return false;
        return true;
    }

    @Override
    public boolean shouldScriptBeRemoved() {
        return false;
    }

}