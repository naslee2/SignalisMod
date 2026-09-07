package data.hullmods;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicIncompatibleHullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import org.magiclib.util.MagicRender;

import java.awt.*;

public class eusan_nation_missile_autoforge extends BaseHullMod{
    
    public static String MR_DATA_KEY = "eusan_reload_data_key";
    public static float AMMO_MALUS = 0.5f;
    public static float SMOD_ROF_MULT = 1.1f;

	public static float MIN_INTERVAL = 20f;
	public static float MAX_INTERVAL = 80f;

    String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
	String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");
	String eusan_nation_missile_assembly_compartment1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText1");
	String eusan_nation_missile_assembly_compartment2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText2");
	String eusan_nation_missile_assembly_compartment3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText3");
	String eusan_nation_missile_assembly_compartment4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText4");
	String eusan_nation_missile_assembly_compartment5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText5");
	String eusan_nation_missile_assembly_compartment6 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText6");
	String eusan_nation_missile_assembly_compartment7 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_missile_assembly_compartmentText7");

    public static class EusanMissileReloadData {
		IntervalUtil interval = new IntervalUtil(MIN_INTERVAL, MIN_INTERVAL);
	}

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileAmmoBonus().modifyMult(id, AMMO_MALUS);
		
		boolean sMod = isSMod(stats);
		if (sMod) {
			stats.getMissileRoFMult().modifyMult(id, SMOD_ROF_MULT);
		}
	}

	public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
        magicIncompatibleHullmodsChecker(ship);
	}

    @Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		super.advanceInCombat(ship, amount);

		if (!ship.isAlive()) return;
		if (ship.getCurrentCR() / ship.getCRAtDeployment() < 0.25f) return; //Stop reloading under 25% CR
		
		CombatEngineAPI engine = Global.getCombatEngine();
		
		String key = MR_DATA_KEY + "_" + ship.getId();
		EusanMissileReloadData data = (EusanMissileReloadData) engine.getCustomData().get(key);
		if (data == null) {
			data = new EusanMissileReloadData();
			engine.getCustomData().put(key, data);
		}
		
		data.interval.advance(amount);
		if (data.interval.intervalElapsed()) {
			
            boolean forge_used = false;
			for (WeaponAPI w : ship.getAllWeapons()) {
				if (w.getType() != WeaponType.MISSILE) continue;
				
				if (w.usesAmmo() && w.getAmmo() < w.getMaxAmmo()) {
					w.setAmmo(w.getMaxAmmo());
                    forge_used = true;
				}
			}

            if (forge_used){
				renderGlow(ship);

				float crRatio = ship.getCurrentCR() / ship.getCRAtDeployment();
				float exponent = 2f;

				float curve = 1f - (float)Math.pow(crRatio, exponent);

				float new_interval = MIN_INTERVAL + curve * MAX_INTERVAL;
				data.interval.setInterval(new_interval, new_interval);
			}
		}

		if(ship == Global.getCombatEngine().getPlayerShip()){
            Global.getCombatEngine().maintainStatusForPlayerShip("Autoforge", "graphics/hullmods/Eusan_missiles_assembly_compartments_BPv2.png", "MISSILE RELOAD IN",
            Math.round(data.interval.getIntervalDuration() - data.interval.getElapsed()) + "", false);
        }
	}

    private void renderGlow(ShipAPI ship){
        MagicRender.objectspace(Global.getSettings().getSprite("fx","eusan_nation_revolution_glow_1"), ship, new Vector2f(4,0), new Vector2f(), new Vector2f(84,40), new Vector2f(0,0),
                 180f, 0f, true, new Color(255,255,255,255), true, 1, 2, 6, true); 
        MagicRender.objectspace(Global.getSettings().getSprite("fx","eusan_nation_revolution_glow_2"), ship, new Vector2f(-156,0), new Vector2f(), new Vector2f(84,38), new Vector2f(0,0),
                 180f, 0f, true, new Color(255,255,255,255), true, 1, 2, 6, true); 
    }

	public void magicIncompatibleHullmodsChecker(ShipAPI ship){
		if(ship.getVariant().getHullMods().contains(HullMods.MISSILE_AUTOLOADER)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.MISSILE_AUTOLOADER, "eusan_nation_missile_autoforge");
		}
		if(ship.getVariant().getHullMods().contains(HullMods.MISSLERACKS)){
			//if someone tries to install incompatible hullmods, remove it.
			MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.MISSLERACKS, "eusan_nation_missile_autoforge");
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
		// tooltip.addPara("- " + eusan_nation_missile_assembly_compartment1, pad10, green, (int) missile_ammo_bonus_small + Strings.X);
		// tooltip.addPara("- " + eusan_nation_missile_assembly_compartment2, pad5, green, (int) missile_ammo_bonus_medium + Strings.X);
		// tooltip.addPara("- " + eusan_nation_missile_assembly_compartment3, pad5, green, (int) missile_ammo_bonus_large + Strings.X);
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
