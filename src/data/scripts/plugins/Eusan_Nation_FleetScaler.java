package data.scripts.plugins;

import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.fleets.FleetParamsV3;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;

//import java.util.Random;
//import org.apache.log4j.Logger;

public class Eusan_Nation_FleetScaler{
    //static Logger logger = Global.getLogger(Eusan_Nation_FleetScaler.class);

    static int playerFleetDP = 0;
    static String enemyFaction;
    
    public Eusan_Nation_FleetScaler(int data, String faction) {
        playerFleetDP = data;
        enemyFaction = faction;
    }

    public CampaignFleetAPI fleetScaler(){
        //logger.info("Logger Active: playerFleet DP is: " + playerFleetDP);
        //FleetParamsV3 enemyFleetData;
        
        FleetParamsV3 enemyFleetParams;
        CampaignFleetAPI enemyFleetData;
        float modiferDP = (float) (playerFleetDP - (playerFleetDP * 0.10));

        if(playerFleetDP < 100){
            enemyFleetParams = new FleetParamsV3(null, null, enemyFaction, null, FleetTypes.PATROL_LARGE, modiferDP, 15f, 10f, 0f, 0f, 0f, 10f);

            enemyFleetData = FleetFactoryV3.createFleet(enemyFleetParams);

            return enemyFleetData;
        }
        if(playerFleetDP >= 100 || playerFleetDP < 150){
            enemyFleetParams = new FleetParamsV3(null, null, enemyFaction, null, FleetTypes.TASK_FORCE, modiferDP, 20f, 15f, 0f, 0f, 0f, 15f);
            
            enemyFleetData = FleetFactoryV3.createFleet(enemyFleetParams);

            return enemyFleetData;
        }
        else{
            enemyFleetParams = new FleetParamsV3(null, null, enemyFaction, null, FleetTypes.TASK_FORCE, modiferDP, 25f, 20f, 0f, 0f, 0f, 20f);

            enemyFleetData = FleetFactoryV3.createFleet(enemyFleetParams);

            return enemyFleetData; 
        }

	}

}