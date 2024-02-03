package data.hullmods;

import org.magiclib.util.MagicIncompatibleHullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class eusan_nation_missile_assembly_compartment extends BaseHullMod{
    
    protected static float missile_ammo_bonus = 150f;
    protected static float missile_regen_bonus = 100f;

	protected static float small_missile_cost_reduction = 5;
	protected static float medium_missile_cost_reduction = 10;
	protected static float large_missile_cost_reduction = 15;

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMissileAmmoBonus().modifyMult(id, missile_ammo_bonus);
        //stats.getMissileAmmoRegenMult().modifyMult(id, missile_regen_bonus);
	}

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		if(ship.getVariant().getHullMods().contains(HullMods.MISSILE_AUTOLOADER)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.MISSILE_AUTOLOADER, "eusan_nation_missile_assembly_compartment");
		}
	}



    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
    
    }

    @Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
			return true;
		}
		return false;
	}
}
