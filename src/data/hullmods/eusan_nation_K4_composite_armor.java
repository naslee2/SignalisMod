package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import org.magiclib.util.MagicIncompatibleHullmods;

import javax.tools.Tool;
import java.awt.Color;

public class eusan_nation_K4_composite_armor extends BaseHullMod {

    private float ENERGY_damage_increase = 0.1f;
    private float HE_damage_reduction = 0.20f;
    private float KINETIC_damage_reduction = 0.20f;

    String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    String incompatibilitiesText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodIncompatibilities");
    String eusan_nation_k4_composite_armor1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_k4_composite_armorText1");
    String eusan_nation_k4_composite_armor2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_k4_composite_armorText2");
    String eusan_nation_k4_composite_armor3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_k4_composite_armorText3");
    String eusan_nation_k4_composite_armor4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_k4_composite_armorText4");
    String eusan_nation_k4_composite_armor5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_k4_composite_armorText5");


    public void applyEffectsBeforeShipCreation (HullSize hullSize, MutableShipStatsAPI stats, String id){
        stats.getEnergyDamageTakenMult().modifyMult(id,  1.0f + ENERGY_damage_increase);
        stats.getHighExplosiveDamageTakenMult().modifyMult(id, 1.0f - HE_damage_reduction);
        stats.getKineticDamageTakenMult().modifyMult(id, 1.0f - KINETIC_damage_reduction);
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){
        magicIncompatibleHullmodsChecker(ship);

    }

    public void magicIncompatibleHullmodsChecker(ShipAPI ship){
        if(ship.getVariant().getHullMods().contains(HullMods.SHIELD_SHUNT)){
            //if someone tries to install incompatible hullmods, remove it.
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.SHIELD_SHUNT, "eusan_nation_K4_composite_armor");
        }
        if(ship.getVariant().getHullMods().contains(HullMods.HEAVYARMOR)){
            //if someone tries to install incompatible hullmods, remove it.
            MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), HullMods.HEAVYARMOR, "eusan_nation_K4_composite_armor");
        }
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color green = new Color(55,245,65,255);
		final Color red = new Color(245,55,65,255);
        final Color negative = Misc.getNegativeHighlightColor();
        final Color negativeBG = new Color(128,38,0,175);
		final Color flavor = new Color(110,110,110,255);
        final float pad10 = 10.0f;
        final float pad5 = 5.0f;

        //Details section
        tooltip.addSectionHeading(detailText, Alignment.MID, pad10  );
        tooltip.addPara("- " + eusan_nation_k4_composite_armor1, pad5, red, (int) (100f * ENERGY_damage_increase) + "%");
        tooltip.addPara("- " + eusan_nation_k4_composite_armor2, pad5, green, (int) (100f * HE_damage_reduction) + "%");
        tooltip.addPara("- " + eusan_nation_k4_composite_armor3, pad5, green, (int) (100f * KINETIC_damage_reduction) + "%");

        //Incompatibilities
        tooltip.addSectionHeading(incompatibilitiesText, negative, negativeBG, Alignment.MID, pad10);
        final TooltipMakerAPI warning_section = tooltip.beginImageWithText(Global.getSettings().getSpriteName("tooltips", "warningSymbol"), 40);
        warning_section.addPara("Incompatible with %s and %s", 0f, negative, new String[] { "Heavy Armor", "Shield Shunt"});
        tooltip.addImageWithText(pad10);

        //Quotes
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_k4_composite_armor4).italicize();
        tooltip.addPara("%s", 1.0f, flavor, eusan_nation_k4_composite_armor5).setAlignment(Alignment.RMID);
    }

    @Override
	public boolean isApplicableToShip(ShipAPI ship){
		if (ship != null && ship.getHullSpec().getHullId().startsWith("eusan_nation_")){
			return true;
		}
		return false;
	}
}