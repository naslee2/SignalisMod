package data.hullmods;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;
import org.magiclib.util.MagicIncompatibleHullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;

public class eusan_nation_missile_assembly_compartment extends BaseHullMod{
    
    protected float missile_ammo_bonus_large = 2f;
	protected float missile_ammo_bonus_medium = 4f;
	protected float missile_ammo_bonus_small = 6f;
    protected float missile_regen_bonus = 4f;
	protected float missile_flux_increase = 3f;

	String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
	String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");
	String eusan_nation_missile_assembly_compartment1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText1");
	String eusan_nation_missile_assembly_compartment2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText2");
	String eusan_nation_missile_assembly_compartment3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText3");
	String eusan_nation_missile_assembly_compartment4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText4");
	String eusan_nation_missile_assembly_compartment5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText5");
	String eusan_nation_missile_assembly_compartment6 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText6");
	String eusan_nation_missile_assembly_compartment7 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText7");

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        //stats.getMissileAmmoBonus().modifyMult(id, missile_ammo_bonus_large);
        //stats.getMissileAmmoRegenMult().modifyMult(id, missile_regen_bonus);
		//stats.getMissileWeaponFluxCostMod().modifyMult(id, missile_flux_increase);
	}

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
		magicIncompatibleHullmodsChecker(ship);
		for(WeaponAPI weapon : ship.getAllWeapons()){
			weapon.ensureClonedSpec();
			//MutableShipStatsAPI stats = ship.getMutableStats();
			//WeaponSpecAPI spec = weapon.getSpec();
			//FluxTrackerAPI flux = ship.getFluxTracker();

			if(weapon.getSlot().getWeaponType().equals(WeaponAPI.WeaponType.MISSILE) && weapon.getSlot().getSlotSize().equals(WeaponAPI.WeaponSize.LARGE)){
				int data = weapon.getMaxAmmo() * (int) missile_ammo_bonus_large;
				weapon.setMaxAmmo(data);
				weapon.setAmmo(data);
			}
			if(weapon.getSlot().getWeaponType().equals(WeaponAPI.WeaponType.MISSILE) && weapon.getSlot().getSlotSize().equals(WeaponAPI.WeaponSize.MEDIUM)){
				int data = weapon.getMaxAmmo() * (int) missile_ammo_bonus_medium;
				weapon.setMaxAmmo(data);
				weapon.setAmmo(data);
			}
			if(weapon.getSlot().getWeaponType().equals(WeaponAPI.WeaponType.MISSILE) && weapon.getSlot().getSlotSize().equals(WeaponAPI.WeaponSize.SMALL)){
				int data = weapon.getMaxAmmo() * (int) missile_ammo_bonus_small;
				weapon.setMaxAmmo(data);
				weapon.setAmmo(data);

			}
		}
	}

	public void magicIncompatibleHullmodsChecker(ShipAPI ship){
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
		final Color negative = Misc.getNegativeHighlightColor();
		final Color negativeBG = new Color(128,38,0,175);
		final Color flavor = new Color(110,110,110,255);
		final float pad5 = 5.0f;
		final float pad10 = 10.0f;

		//Details section
		tooltip.addSectionHeading(detailText, Alignment.MID, pad10);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment1, pad5, green, (int) missile_ammo_bonus_small + Strings.X);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment2, pad5, green, (int) missile_ammo_bonus_medium + Strings.X);
		tooltip.addPara("- " + eusan_nation_missile_assembly_compartment3, pad5, green, (int) missile_ammo_bonus_large + Strings.X);
		//tooltip.addPara("- " + eusan_nation_missile_assembly_compartment4, pad5, green, (int) missile_regen_bonus + Strings.X);
		//tooltip.addPara("- " + eusan_nation_missile_assembly_compartment5, pad5, red, (int) missile_flux_increase + Strings.X);

		//Incompatibilities
		tooltip.addSectionHeading(incompatibilitiesText, negative,negativeBG, Alignment.MID, pad10);
		final TooltipMakerAPI warning_section = tooltip.beginImageWithText(Global.getSettings().getSpriteName("tooltips", "warningSymbol"), 40);
		warning_section.addPara("Incompatible with %s and %s", 0f, negative, new String[] { "Missile Autoloader", "Expanded Missile Racks"});
		tooltip.addImageWithText(pad10);

		//Quotes
		tooltip.addPara("%s", 6.0f, flavor, eusan_nation_missile_assembly_compartment6).italicize();
		tooltip.addPara("%s", 1.0f, flavor, eusan_nation_missile_assembly_compartment7).setAlignment(Alignment.RMID);

    }

    @Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
			return true;
		}
		return false;
	}
}
