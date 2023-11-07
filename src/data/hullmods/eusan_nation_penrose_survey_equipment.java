package data.hullmods;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.SurveyPluginImpl;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import org.magiclib.util.MagicIncompatibleHullmods;

public class eusan_nation_penrose_survey_equipment extends BaseHullMod{

    float BONUS = 200f;

    static String detailText = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_hullmodDetails");
    static String eusan_nation_penrose_survey_equipment1 = Global.getSettings().getString("eusan_nation_strings","eusan_nation_penrose_survey_equipmentText1");
    static String eusan_nation_penrose_survey_equipment2 = Global.getSettings().getString("eusan_nation_strings","eusan_nation_penrose_survey_equipmentText2");
    static String eusan_nation_penrose_survey_equipment3 = Global.getSettings().getString("eusan_nation_strings","eusan_nation_penrose_survey_equipmentText3");
    static String eusan_nation_penrose_survey_equipment4 = Global.getSettings().getString("eusan_nation_strings","eusan_nation_penrose_survey_equipmentText4");
    static String eusan_nation_penrose_survey_equipment5 = Global.getSettings().getString("eusan_nation_strings","eusan_nation_penrose_survey_equipmentText5");

    
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id){
        Float mod = 1f + (BONUS/100f);

        stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.HEAVY_MACHINERY)).modifyFlat(id, mod);
		stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.SUPPLIES)).modifyFlat(id, mod);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) BONUS + "%";
		return null;
	}

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec){
        final Color flavor = new Color(110,110,110,255);
        final Color green = new Color(55,245,65,255);

        if (isForModSpec || ship == null) return;
		if (Global.getSettings().getCurrentState() == GameState.TITLE) return;

        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
		int machinery = (int) Misc.getFleetwideTotalMod(fleet, Stats.getSurveyCostReductionId(Commodities.HEAVY_MACHINERY), 0, ship);
		int supplies = (int) Misc.getFleetwideTotalMod(fleet, Stats.getSurveyCostReductionId(Commodities.SUPPLIES), 0, ship);

        tooltip.addSectionHeading(detailText, Alignment.MID, 10.0F);
        tooltip.addPara(eusan_nation_penrose_survey_equipment1, 10f, Misc.getHighlightColor(), "" + BONUS);
        tooltip.addPara(eusan_nation_penrose_survey_equipment2, 10f, Misc.getHighlightColor(), "" + supplies);
        tooltip.addPara(eusan_nation_penrose_survey_equipment3, 10f, Misc.getHighlightColor(), "" + machinery);
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_penrose_survey_equipment4).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_penrose_survey_equipment5);

    }

}
