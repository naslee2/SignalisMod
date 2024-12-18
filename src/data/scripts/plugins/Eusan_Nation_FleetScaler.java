package data.scripts.plugins;

import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.fleets.FleetParamsV3;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;

public class Eusan_Nation_FleetScaler{

    static int playerFleetDP = 0;
    static String enemyFaction;
    static float strengthMult;

    
    public Eusan_Nation_FleetScaler(int data, String faction, float mult) {
        playerFleetDP = data;
        enemyFaction = faction;
        strengthMult = mult;
    }

    public CampaignFleetAPI fleetScaler(){
        
        FleetParamsV3 enemyFleetParams;
        CampaignFleetAPI enemyFleetData;
        float modifierDP = (float) (playerFleetDP - (playerFleetDP * strengthMult));

        if(playerFleetDP < 100){
            enemyFleetParams = new FleetParamsV3(null, null, enemyFaction, null, FleetTypes.PATROL_LARGE, modifierDP, 15f, 10f, 0f, 0f, 0f, 10f);

            enemyFleetData = FleetFactoryV3.createFleet(enemyFleetParams);

            return enemyFleetData;
        }
        enemyFleetParams = new FleetParamsV3(null, null, enemyFaction, null, FleetTypes.TASK_FORCE, modifierDP, 20f, 15f, 0f, 0f, 0f, 15f);

        enemyFleetData = FleetFactoryV3.createFleet(enemyFleetParams);

        return enemyFleetData;

    }

}