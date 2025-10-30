package data.missions.showcase;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin{

    final static String[] shipSelector(){
        String[] array = new String[2];
        final String[] choicesA ={
                "eusan_nation_admiral_standard",
                "eusan_nation_revolution_standard"
        };
        final String [] choicesB = {
                "conquest_DEM",
                "pegasus_Strike",
                "pegasus_Balanced",
                "executor_Standard",
                "conquest_Standard",
                "onslaught_Standard",
                "paragon_Escort",
                "paragon_Raider",
                "odyssey_Balanced",
                "retribution_Standard",
                "invictus_Standard",
                "invictus_Support",
                "atlas2_Standard",
                "prometheus2_Standard",
                "astral_Strike",
                "astral_Attack",
                "legion_Assault",
                "legion_Escort",
                "legion_FS",
                "legion_Strike"
        };
        String flagship = choicesA[(int) (Math.random() * (float) choicesA.length)];
        String enemySelect = choicesB[(int) (Math.random() * (float) choicesB.length)];
        array[0] = flagship;
        array[1] = enemySelect;
        return array;
    }

    public void defineMission(MissionDefinitionAPI api){
        api.initFleet(FleetSide.PLAYER, "VM", FleetGoal.ATTACK, false, 5);
        api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, true);

        api.setFleetTagline(FleetSide.PLAYER, "Volksmarine Virtual Fleet");
        api.setFleetTagline(FleetSide.ENEMY, "Virtual Enemy Forces (Various)");

        api.addBriefingItem("Defeat the enemy warship");
        api.addBriefingItem("Experiment with various tactics and equipment to win the engagement.");

        String[] flagshipSelection = shipSelector();
        api.addToFleet(FleetSide.PLAYER, flagshipSelection[0], FleetMemberType.SHIP, true);

        //other player ships
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_copthorne_standard", FleetMemberType.SHIP, "VM Copthorne", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_iltis_standard", FleetMemberType.SHIP, "VM Iltis", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_lerche_standard", FleetMemberType.SHIP, "VM Lerche", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_tyrann_standard", FleetMemberType.SHIP, "VM Tyrann", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_raubtier_standard", FleetMemberType.SHIP, "VM Raubtier", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_cantor_standard", FleetMemberType.SHIP, "VM Cantor", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_tremore_standard", FleetMemberType.SHIP, "VM Tremore", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_trevivian_standard", FleetMemberType.SHIP, "VM Trevivian", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_triglav_standard", FleetMemberType.SHIP, "VM Triglav", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_jagd_standard", FleetMemberType.SHIP, "VM Jadg", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_comoros_standard", FleetMemberType.SHIP, "VM Comoros", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_engollan_standard", FleetMemberType.SHIP, "VM engollan", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_isolde_standard", FleetMemberType.SHIP, "VM Isold", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_penrose_standard", FleetMemberType.SHIP, "VM Penrose-512", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_pelikan_standard", FleetMemberType.SHIP, "Transport 541", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_pisagua_standard", FleetMemberType.SHIP, "Heavy 809", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_wachtel_standard", FleetMemberType.SHIP, "K41", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_kanal_standard", FleetMemberType.SHIP, "VM Kanal", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_vilm_standard", FleetMemberType.SHIP, "VM Vilm", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_riems_freighter_standard", FleetMemberType.SHIP, "VM Riems", false);
        api.addToFleet(FleetSide.PLAYER, "eusan_nation_riems_tanker_standard", FleetMemberType.SHIP, "VM Reims", false);

        //enemy fleet
        String[] enemyShip = shipSelector();
        api.addToFleet(FleetSide.ENEMY, enemyShip[1], FleetMemberType.SHIP, false).getCaptain().setPersonality(Personalities.AGGRESSIVE);

        // Set up the map.
        float width = 24000f;
        float height = 14000f;
        api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);

        float minX = -width/2;
        float minY = -height/2;

        // And a few random ones to spice up the playing field.
        // A similar approach can be used to randomize everything
        // else, including fleet composition.
        for (int i = 0; i < 5; i++) {
            float x = (float) Math.random() * width - width/2;
            float y = (float) Math.random() * height - height/2;
            float radius = 100f + (float) Math.random() * 400f;
            api.addNebula(x, y, radius);
        }

        // Add objectives. These can be captured by each side
        // and provide stat bonuses and extra command points to
        // bring in reinforcements.
        // Reinforcements only matter for large fleets - in this
        // case, assuming a 100 command point battle size,
        // both fleets will be able to deploy fully right away.
        api.addObjective(minX + width * 0.25f + 3000, minY + height * 0.5f,
                "nav_buoy");
        api.addObjective(minX + width * 0.75f - 3000, minY + height * 0.5f,
                "sensor_array");

        // Add an asteroid field going diagonally across the
        // battlefield, 2000 pixels wide, with a maximum of
        // 100 asteroids in it.
        // 20-70 is the range of asteroid speeds.
        api.addAsteroidField(minY, minY, 45, 2000f,
                20f, 70f, 100);
    }
}