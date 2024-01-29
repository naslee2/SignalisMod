package data.hullmods;

import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.HullModEffect;
import com.fs.starfarer.api.combat.HullModFleetEffect;

public class eusan_nation_medical_facilities extends BaseHullMod implements HullModEffect, HullModFleetEffect{

	@Override
	public void advanceInCampaign(CampaignFleetAPI arg0) {
		
	}

	@Override
	public void onFleetSync(CampaignFleetAPI arg0) {
		
	}

	@Override
	public boolean withAdvanceInCampaign() {
        return false;
	}

	@Override
	public boolean withOnFleetSync() {
		return false;
		// TODO Auto-generated method stub
	}
    
}
