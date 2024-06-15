package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Planets;
import com.fs.starfarer.api.impl.campaign.missions.hub.ReqMode;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch.*;

//import org.apache.log4j.Logger;

public class Eusan_Nation_PlanetTerrainRequirement implements PlanetRequirement{
    //Logger logger = Global.getLogger(Eusan_Nation_PlanetTerrainRequirement.class);
    
    //checks if planets are cold, very cold, cryovolcanic, or frozen.
    @Override
    public boolean planetMatchesRequirement(PlanetAPI planet) {
        String planetType = planet.getTypeId();
        if(planetType == null){
            return false;
        }
        //logger.info("Logger Active: Planet Type ID is: " + planetType);
        switch(planetType){
            case "frozen":
                return true;
            case "frozen1":
                return true;
            case "frozen2":
                return true;
            case "frozen3":
                return true;
            case "cryovolcanic":
                return true;
            case "rocky_ice":
                return true;
            case "toxic_cold":
                return false;
            default:
                return false;
        }
    }


 }