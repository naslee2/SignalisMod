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

import org.apache.log4j.Logger;
import org.lwjgl.util.vector.Vector2f;

public class Eusan_Nation_personalFleetFalke extends PersonalFleetScript {
    Logger logger = Global.getLogger(Eusan_Nation_personalFleetFalke.class);

    public static String ADMIRAL_FALKE = "eusan_nation_admiral_falke";
    public static String EUSAN_NATION = "eusan_nation";

    public Eusan_Nation_personalFleetFalke() {
        super(ADMIRAL_FALKE);
        setMinRespawnDelayDays(10f);
        setMaxRespawnDelayDays(20f);
    }

    @Override
    public CampaignFleetAPI spawnFleet() {

        MarketAPI heimat = Global.getSector().getEconomy().getMarket("heimat_market");
        logger.info("Logger active: .getMarket is: " + heimat.getStarSystem());

        FleetCreatorMission m = new FleetCreatorMission(random);
        m.beginFleet();

        Vector2f loc = heimat.getLocationInHyperspace();

        m.triggerCreateFleet(FleetSize.MAXIMUM, FleetQuality.SMOD_2, EUSAN_NATION, FleetTypes.TASK_FORCE, loc);
        m.triggerSetFleetOfficers( OfficerNum.MORE, OfficerQuality.UNUSUALLY_HIGH);
        m.triggerSetFleetCommander(getPerson());
        m.triggerSetFleetFaction(EUSAN_NATION);
        m.triggerSetPatrol();
        m.triggerSetFleetMemoryValue(MemFlags.MEMORY_KEY_SOURCE_MARKET, heimat);
        m.triggerFleetSetNoFactionInName();
        m.triggerFleetSetName("Ever Victorious Fleet");
        m.triggerFleetSetPatrolActionText("Patrolling");
        m.triggerPatrolAllowTransponderOff();
        m.triggerOrderFleetPatrol(heimat.getStarSystem());

        CampaignFleetAPI falkeFleet = m.createFleet();
        FleetMemberAPI oldFlagship = falkeFleet.getFlagship();
        FleetMemberAPI newFlagship = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "eusan_nation_admiral_standard");
        falkeFleet.getFleetData().addFleetMember(newFlagship);
        logger.info("Logger active: flagship is: " + newFlagship);
        if (newFlagship != null && oldFlagship != null) {
            newFlagship.setCaptain(oldFlagship.getCaptain());
            oldFlagship.setFlagship(false);
            newFlagship.setFlagship(true);
            falkeFleet.getFleetData().setFlagship(newFlagship);
            falkeFleet.getFleetData().removeFleetMember(oldFlagship);
        }

        falkeFleet.removeScriptsOfClass(MissionFleetAutoDespawn.class);
        heimat.getContainingLocation().addEntity(falkeFleet);
        falkeFleet.setLocation(heimat.getPlanetEntity().getLocation().x, heimat.getPlanetEntity().getLocation().y);
        //falkeFleet.addAssignment(FleetAssignment.DEFEND_LOCATION, (SectorEntityToken) heimat, 365);
        falkeFleet.setFacing((float) random.nextFloat() * 360f);

        //flagship
        falkeFleet.getFlagship().setShipName("VM Guardian of the Revolution");
        falkeFleet.getFlagship().setId("eusan_nation_admiral_standard");

        //battleship division
        falkeFleet.getFleetData().addFleetMember("eusan_nation_admiral_standard").setShipName("VM Yun In-cheom");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_admiral_standard").setShipName("VM Sejong");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard").setShipName("VM Rotfront Revolution");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard").setShipName("VM Iriy Revolution");
        falkeFleet.getFleetData().addFleetMember("eusan_nation_revolution_standard").setShipName("VM Lukomorye Revolution");
        
        // //cruisers
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_comoros_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard").setShipName("");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_engollan_standard").setShipName("");

        // //destroyers
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard").setShipName("Augsburg");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard").setShipName("Yeongcheon");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard").setShipName("Gunsan");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_cantor_standard").setShipName("Jeonju");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard").setShipName("Goyang");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard").setShipName("Gimje");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard").setShipName("Sanyi");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tremore_standard").setShipName("Suao");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard").setShipName("Luodong");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard").setShipName("Xinyuan");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard").setShipName("Wujie");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_trevivian_standard").setShipName("Murino");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Vyborg");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Daecheon");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Anyang");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Gangneung");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Suwon");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_triglav_standard").setShipName("Donghae");

        // //frigates
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard").setShipName("Geier");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard").setShipName("Widah");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard").setShipName("Zebrafink");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard").setShipName("Quetzal");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_iltis_standard").setShipName("Iltis");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard").setShipName("Specht");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard").setShipName("Waldrapp");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard").setShipName("Sturmvogel");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard").setShipName("Eistaucher");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_tyrann_standard").setShipName("Möwe");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard").setShipName("Waldkauz");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard").setShipName("Schleiereule");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard").setShipName("Drossel");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard").setShipName("Rotkehlchen");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_lerche_standard").setShipName("Amsel");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-414");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-223");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-516");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-510");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-395");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_copthorne_standard").setShipName("SKR-427");

        // //support ships
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_pelikan_standard").setShipName("N-201456");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_pelikan_standard").setShipName("N-201401");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_pelikan_standard").setShipName("N-202687");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_pisagua_standard").setShipName("N-199101");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_pisagua_standard").setShipName("N-199634");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard").setShipName("SSV-445");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard").setShipName("SSV-450");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard").setShipName("SSV-413");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard").setShipName("SSV-429");
        // falkeFleet.getFleetData().addFleetMember("eusan_nation_wachtel_standard").setShipName("SSV-406");

        falkeFleet.getFleetData().sort();

        logger.info("Logger active: fleet is: " + falkeFleet.getFleetData());

        return falkeFleet;
    }

    @Override
    public boolean canSpawnFleetNow() {
        MarketAPI heimat = Global.getSector().getEconomy().getMarket("heimat_market");
        logger.info("Logger active: fleet is spawning at: " + heimat.getId());
        if (heimat == null || heimat.hasCondition(Conditions.DECIVILIZED)) return false;
        if (!heimat.getFactionId().equals(EUSAN_NATION)) return false;
        return true;
    }

    @Override
    public boolean shouldScriptBeRemoved() {
        return false;
    }

}
