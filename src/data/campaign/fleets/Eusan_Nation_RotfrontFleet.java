package data.campaign.fleets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.fleets.PersonalFleetScript;
import data.campaign.ids.Eusan_Nation_PeopleStrings;

public class Eusan_Nation_RotfrontFleet extends PersonalFleetScript {
    protected String EUSAN_NATION = "eusan_nation";

    public Eusan_Nation_RotfrontFleet(String personId) {
        super(Eusan_Nation_PeopleStrings.ROTFRONT_FLEETADMIRAL);
        setMinRespawnDelayDays(10f);
        setMaxRespawnDelayDays(20f);
    }

    protected MarketAPI getSourceMarket(){
        return Global.getSector().getEconomy().getMarket("rotfront_market");
    }

    @Override
    public CampaignFleetAPI spawnFleet() {
        MarketAPI rotfront_market = getSourceMarket();

        return null;
    }

    @Override
    public boolean canSpawnFleetNow() {
        return false;
    }

    @Override
    public boolean shouldScriptBeRemoved() {
        return false;
    }
}
