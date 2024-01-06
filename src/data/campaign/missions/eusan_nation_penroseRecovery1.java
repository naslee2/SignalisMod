package data.campaign.missions;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin.RepRewards;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class eusan_nation_penroseRecovery1 extends HubMissionWithSearch {

    public static enum Stage {
        LOCATE_PENROSE,
        RETURN_BLACKBOX,
        COMPLETED
    }
    
    protected PersonAPI officer_yeong;
    protected PlanetAPI vineta;
    protected PlanetAPI target_planet;
    protected StarSystemAPI target_starsystem;

    @Override
    protected boolean create(MarketAPI arg0, boolean arg1) {
        officer_yeong = getImportantPerson("eusan_nation_officer_yeong");
        vineta = (PlanetAPI) Global.getSector().getEntityById("eusan_nation_vineta");

        requirePlanetUnpopulated();
		preferPlanetNotFullySurveyed();
		preferPlanetInDirectionOfOtherMissions();
        target_planet = pickPlanet();

        if (target_planet == null) {
			return false;
		}

        //return false;
        return true;
    }
    
}
