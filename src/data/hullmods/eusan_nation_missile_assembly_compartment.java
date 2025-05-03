package data.hullmods;

import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.ui.Alignment;
import org.magiclib.util.MagicIncompatibleHullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;

public class eusan_nation_missile_assembly_compartment extends BaseHullMod{
    
    protected float missile_ammo_bonus = 5f;
    protected float missile_regen_bonus = 4f;
	protected float missile_flux_increase = 3f;

	String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
	String eusan_nation_missile_assembly_compartment1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText1");
	String eusan_nation_missile_assembly_compartment2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText2");
	String eusan_nation_missile_assembly_compartment3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText3");
	String eusan_nation_missile_assembly_compartment4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText4");
	String eusan_nation_missile_assembly_compartment5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText5");

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMissileAmmoBonus().modifyMult(id, missile_ammo_bonus);
        stats.getMissileAmmoRegenMult().modifyMult(id, missile_regen_bonus);
		stats.getMissileWeaponFluxCostMod().modifyMult(id, missile_flux_increase);
	}

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		if(ship.getVariant().getHullMods().contains(HullMods.MISSILE_AUTOLOADER)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.MISSILE_AUTOLOADER, "eusan_nation_missile_assembly_compartment");
		}
		if(ship.getVariant().getHullMods().contains(HullMods.MISSLERACKS)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.MISSLERACKS, "eusan_nation_missile_assembly_compartment");
		}
	}

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
		final Color green = new Color(55,245,65,255);
		final Color red = new Color(245,55,65,255);
		final Color flavor = new Color(110,110,110,255);
		final float pad10 = 5.0f;
		final float pad5 = 5.0f;

		tooltip.addSectionHeading(detailText, Alignment.MID, pad10);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment1, pad10, green, (int) missile_ammo_bonus + Strings.X);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment2, pad5, green, (int) missile_regen_bonus + Strings.X);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment3, pad5, red, (int) missile_flux_increase + Strings.X);
		tooltip.addPara("%s", 6.0f, flavor, eusan_nation_missile_assembly_compartment4).italicize();
		tooltip.addPara("%s", 1.0f, flavor, eusan_nation_missile_assembly_compartment5);

    }

    @Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
			return true;
		}
		return false;
	}
}
