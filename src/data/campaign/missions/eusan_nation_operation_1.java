package data.campaign.missions;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;

public class eusan_nation_operation_1 extends HubMissionWithSearch {

    public static enum Stage{
        TALK_TO_AGENT,
        LOCATE_HVT,
        RETURN_TO_HEIMAT,
        COMPLETED
    }

    @Override
    protected boolean create(MarketAPI createdAt, boolean barEvent) {
        return false;
    }
}
