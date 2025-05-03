package data.scripts.plugins;

import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch.*;

public class Eusan_Nation_PlanetColdTypeRequirement implements PlanetRequirement{
    
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
            case "frozen1":
            case "frozen2":
            case "frozen3":
            case "cryovolcanic":
            case "rocky_ice":
                return true;
            default:
                return false;
        }
    }
 }