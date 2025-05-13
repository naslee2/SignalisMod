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
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import org.magiclib.util.MagicIncompatibleHullmods;

import java.awt.*;

public class eusan_nation_advanced_flag_facilities extends BaseHullMod{

    public final float RECOVERY_BONUS = 225f;
	public final String MOD_ID = "eusan_nation_advanced_flag_facilities";

	String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
	String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	}

	public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		magicIncompatibleHullmodsChecker(ship);
	}

	public void magicIncompatibleHullmodsChecker(ShipAPI ship) {
		if(ship.getVariant().getHullMods().contains(HullMods.OPERATIONS_CENTER)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.OPERATIONS_CENTER, MOD_ID);
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
	public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
		final Color green = new Color(55,245,65,255);
		final Color red = new Color(245,55,65,255);
		final Color flavor = new Color(110,110,110,255);
		final Color negative = Misc.getNegativeHighlightColor();
		final Color negativeBG = new Color(128,38,0,175);
		final float pad5 = 5.0f;
		final float pad10 = 10.0f;

		//Details section
		tooltip.addSectionHeading(detailText, Alignment.MID, pad10);

		//Incompatibilities
		tooltip.addSectionHeading(incompatibilitiesText, negative,negativeBG, Alignment.MID, pad10);

		//Quotes
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getVariant().getHullMods().contains(HullMods.OPERATIONS_CENTER)){
			return false;
		}
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
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
