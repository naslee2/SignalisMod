package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatFleetManagerAPI;
import com.fs.starfarer.api.combat.DeployedFleetMemberAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import org.magiclib.util.MagicIncompatibleHullmods;

public class eusan_nation_advanced_flag_facilities extends BaseHullMod{

    public static final float RECOVERY_BONUS = 300f;
	public static final String MOD_ID = "eusan_nation_advanced_flag_facilities";

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	}

	public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		if(ship.getVariant().getHullMods().contains(HullMods.OPERATIONS_CENTER)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.OPERATIONS_CENTER, "Eusan Advanced Flag Facilities");
		}
	}

	//flag facilities 
    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
		CombatEngineAPI engine = Global.getCombatEngine();
		if (engine == null) return;
		
		CombatFleetManagerAPI manager = engine.getFleetManager(ship.getOriginalOwner());
		if (manager == null) return;
		
		DeployedFleetMemberAPI member = manager.getDeployedFleetMember(ship);
		if (member == null) return; // happens in refit screen etc
		
		boolean apply = ship == engine.getPlayerShip();
		PersonAPI commander = null;
		if (member.getMember() != null) {
			commander = member.getMember().getFleetCommander();
			if (member.getMember().getFleetCommanderForStats() != null) {
				commander = member.getMember().getFleetCommanderForStats();
			}
		}
		apply |= commander != null && ship.getCaptain() == commander;
		
		if (apply) {
			ship.getMutableStats().getDynamic().getMod(Stats.COMMAND_POINT_RATE_FLAT).modifyFlat(MOD_ID, RECOVERY_BONUS * 0.01f);
		} else {
			ship.getMutableStats().getDynamic().getMod(Stats.COMMAND_POINT_RATE_FLAT).unmodify(MOD_ID);
		}
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) RECOVERY_BONUS + "%";
		return null;
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getVariant().getHullMods().contains(HullMods.OPERATIONS_CENTER)){
			return false;
		}
		if (ship != null && ship.getHullSpec().getHullId().contains("eusan_nation_admiral")){
			return true;
		}
		return false;
	}

	public String getUnapplicableReason(ShipAPI ship){
		if (ship != null && ship.getVariant().getHullMods().contains(HullMods.OPERATIONS_CENTER)){
			return "Incompatable with Operations Center";
		}
		return null;
	}


}
