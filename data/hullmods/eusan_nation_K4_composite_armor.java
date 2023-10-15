package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.listeners.*;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class eusan_nation_K4_composite_armor extends BaseHullMod {

    private float ENERGY_damage_reduction = 0.1f;
    private float FRAG_damage_reduction = 0.3f;
    private float HE_damage_reduction = 0.6f;
    private float KINETIC_damage_reduction = 0.6f;


    public void applyEffectsBeforeShipCreation (HullSize hullSize, MutableShipStatsAPI stats, String id){
        stats.getEnergyDamageTakenMult().modifyMult(id,  1.0f - ENERGY_damage_reduction);
        stats.getHighExplosiveDamageTakenMult().modifyMult(id, 1.0f - HE_damage_reduction);
        stats.getFragmentationDamageTakenMult().modifyMult(id, 1.0f - FRAG_damage_reduction);
        stats.getKineticDamageTakenMult().modifyMult(id, 1.0f - KINETIC_damage_reduction);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if(index == 0){
            return "" + (int) ENERGY_damage_reduction + "%";
        }
        if(index == 1){
            return "" + (int) HE_damage_reduction + "%";
        }
        if(index == 2){
            return "" + (int) FRAG_damage_reduction + "%";
        }
        if(index == 3){
            return "" + (int) KINETIC_damage_reduction + "%";
        }
		return null;
	}

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec){
        final Color green = new Color(55,245,65,255);
		final Color red = new Color(245,55,65,255);
		final Color flavor = new Color(110,110,110,255);
    }
}