package data.campaign.intel.events;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseEventIntel;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseHostileActivityFactor;
import com.fs.starfarer.api.impl.campaign.intel.events.HostileActivityEventIntel;
import com.fs.starfarer.api.impl.campaign.intel.group.FleetGroupIntel;

public class Eusan_Nation_HostileActivityFactor extends BaseHostileActivityFactor implements FleetGroupIntel.FGIEventListener {

    public final String eusanNationIncursionDefeated= "$eusanNationIncursionDefeated";




    public Eusan_Nation_HostileActivityFactor(HostileActivityEventIntel intel) {
        super(intel);
        Global.getSector().getListenerManager().addListener(this);
    }

    @Override
    public void reportFGIAborted(FleetGroupIntel intel) {

    }

    @Override
    public int getProgress(BaseEventIntel intel) {
        if (!checkFactionExists("eusan_nation", true)) {
            return 0;
        }
        return super.getProgress(intel);
    }
}
