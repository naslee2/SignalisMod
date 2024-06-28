package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import org.apache.log4j.Logger;

import org.apache.log4j.Logger;

public class Eusan_Nation_FleetScaler{
    //Logger logger = Global.getLogger(Eusan_Nation_FleetScaler.class);

    int playerFleetDP = 0;
    
    public Eusan_Nation_FleetScaler(int data) {
        playerFleetDP = data;
    }

    public String fleetScaler(){
        //logger.info("Logger Active: playerFleet DP is: " + playerFleetDP);
        if(playerFleetDP < 100){
            return FleetTypes.PATROL_LARGE;
        }
        if(playerFleetDP >= 100 || playerFleetDP < 150){
            return FleetTypes.TASK_FORCE;
        }
        return FleetTypes.TASK_FORCE;

    }

}