package data.campaign.fleets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
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
import org.lwjgl.util.vector.Vector2f;

public class Eusan_Nation_personalFleetFalke extends PersonalFleetScript {

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
        m.triggerFleetSetName("Eusan Nation Revolutionary Guard");
        m.triggerPatrolAllowTransponderOff();
        m.triggerOrderFleetPatrol(heimat.getStarSystem());

        CampaignFleetAPI fleet = m.createFleet();
        FleetMemberAPI oldFlagship = fleet.getFlagship();
        FleetMemberAPI newFlagship = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "eusan_nation_admiral");
        fleet.getFleetData().addFleetMember(newFlagship);
        if (newFlagship != null && oldFlagship != null) {
            newFlagship.setCaptain(oldFlagship.getCaptain());
            oldFlagship.setFlagship(false);
            newFlagship.setFlagship(true);
            fleet.getFleetData().setFlagship(newFlagship);
            fleet.getFleetData().removeFleetMember(oldFlagship);}
        fleet.removeScriptsOfClass(MissionFleetAutoDespawn.class);
        heimat.getContainingLocation().addEntity(fleet);
        fleet.setLocation(heimat.getPlanetEntity().getLocation().x, heimat.getPlanetEntity().getLocation().y);
        fleet.setFacing((float) random.nextFloat() * 360f);
        fleet.getFlagship().setShipName("VM Guardian of the Revolution");
        fleet.getFleetData().sort();
        return fleet;
    }

    @Override
    public boolean canSpawnFleetNow() {
        MarketAPI heimat = Global.getSector().getEconomy().getMarket("heimat_market");
        if (heimat == null || heimat.hasCondition(Conditions.DECIVILIZED)) return false;
        if (!heimat.getFactionId().equals(Factions.DIKTAT)) return false;
        return true;
    }

    @Override
    public boolean shouldScriptBeRemoved() {
        return false;
    }

}
