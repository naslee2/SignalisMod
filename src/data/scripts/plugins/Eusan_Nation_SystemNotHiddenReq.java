package data.scripts.plugins;

import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch.*;

public class Eusan_Nation_SystemNotHiddenReq implements StarSystemRequirement {

    @Override
    public boolean systemMatchesRequirement(StarSystemAPI system) {
        if (system == null) {
            return false;
        }
        return !system.hasTag(Tags.THEME_HIDDEN);
    }

}
