package data.hullmods;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.magiclib.util.MagicRender;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lwjgl.util.vector.Vector2f;
/**
 *
 * @author Mayu
 */
public class eusan_nation_bioresonance extends BaseHullMod {

    protected Object STATUSKEY1;
    private final String ID;
    ShipAPI ship;
    private final List<ShipAPI> buffed;

    private static final float MAX_LINKED_RANGE = 3000f; // Buff script range
    private static final float AURA_RADIUS = 4000f;      // Visuals range

    public static final Color JITTER_COLOR_TIMID = new Color(105, 255, 255, 175);
    public static final Color JITTER_COLOR_CAUTIOUS = new Color(110, 255, 105, 175);
    public static final Color JITTER_COLOR_STEADY = new Color(74, 92, 255, 175);
    public static final Color JITTER_COLOR_AGGRESSIVE = new Color(208, 7, 231, 175);
    public static final Color JITTER_COLOR_RECKLESS = new Color(241, 72, 50, 175);

    public eusan_nation_bioresonance() {
        this.ID = "eusan_nation_bioresonance";
        this.STATUSKEY1 = new Object();
        this.buffed = new ArrayList<>();
    }

    String eusan_nation_bioresoance1 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance1");
    String eusan_nation_bioresoance2 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance2");
    String eusan_nation_bioresoance3 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance3");
    String eusan_nation_bioresoance4 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance4");
    String eusan_nation_bioresoance5 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance5");
    String eusan_nation_bioresoance6 = Global.getSettings().getString("eusan_nation_strings", "eusan_nation_bioresoance6");

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        final MutableShipStatsAPI stats = ship.getMutableStats();
        final CombatEngineAPI engine = Global.getCombatEngine();
        this.ship = (ShipAPI)stats.getEntity();
        final boolean visible = MagicRender.screenCheck(0.1f, this.ship.getLocation());
        final List<ShipAPI> nearby = AIUtils.getNearbyAllies(this.ship, AURA_RADIUS);
        final List<ShipAPI> previous = new ArrayList<>(this.buffed);

        // Stopgap - Null check
        if (engine == null) {
            return;
        }

        if (Global.getCurrentState() != GameState.COMBAT || !engine.isEntityInPlay(ship) || !ship.isAlive() || ship.isHulk()) {
            return;
        }

        // Apply the buffs

        // Aura thing
        MagicRender.objectspace(
                Global.getSettings().getSprite("fx", "eusan_nation_bioresonance_aura"),
                //Global.getSettings().getSprite("fx", "bbplus_sigma_field_ring"),
                this.ship, new Vector2f(), new Vector2f(),
                new Vector2f(MAX_LINKED_RANGE, MAX_LINKED_RANGE),
                new Vector2f(0, 0),
                0.0f,
                0.0f, // jitter fuck
                false,
                new Color(105, 220, 255,5),
                true,
                0.1f,
                0.0f,
                0.1f,
                true
        );

        if (!nearby.isEmpty()) {
            for (final ShipAPI affected : nearby) {
                // Apply it
                if (!previous.contains(affected) && !affected.isFighter()) {
                    this.applyBioresonanceBuffEffect(
                            affected,
                            this.ship,
                            amount,
                            visible
                    );
                    this.buffed.add(affected);
                }
                if (previous.contains(affected)) {
                    previous.remove(affected);
                    this.applyBioresonanceBuffEffect(
                            affected,
                            this.ship,
                            amount,
                            visible
                    );
                }

                // Status visuals on combat
                // Let's try a specific buff text later
                if (affected == Global.getCombatEngine().getPlayerShip()) {
                    Global.getCombatEngine().maintainStatusForPlayerShip(
                            this.ID + "_buffed_eusan_bioresonance_tooltip",
                            "graphics/icons/tactical/neural_link.png",
                            "Bioresonance",
                            "Improved Combat Parameters",
                            false
                    );
                }
            }
        }
        else if (!this.buffed.isEmpty()) {
            // Unapplies the buff
            for (final ShipAPI affected : this.buffed) {
                this.unapplyBioresonanceBuffEffect(affected);
            }
            this.buffed.clear();
        }

    }

    @Override
    public String getDescriptionParam(final int index, final HullSize hullSize) {
        if (index == 0) {
            return "Test";
        }
        return null;
    }

    @Override
    public void addPostDescriptionSection(final TooltipMakerAPI tooltip, final ShipAPI.HullSize hullSize, final ShipAPI ship, final float width, final boolean isForModSpec) {
        final Color green = new Color(55,245,65,255);
        final Color flavor = new Color(110,110,110,255);
        final float pad5 = 5.0f;
        final float pad10 = 10.0f;

        tooltip.addSectionHeading("Technical System Details", Alignment.MID, pad10);
        final TooltipMakerAPI text = tooltip.beginImageWithText("graphics/icons/tactical/cr_tactical3.png", 40f);
        text.addPara("Bio-Resonance", 0f, Global.getSettings().getColor("tooltipTitleAndLightHighlightColor"), "Neuro Resonance");
        text.addPara("A Bio-Resonator system that activates during combat, it provides varying buffs to nearby friendly units within 3000su, the type of buff depends on the ship's captain personality.",
                0f, Misc.getHighlightColor(),
                new String[] {"3000su",
                        "personality"});
        tooltip.addImageWithText(pad10);

        //Details section
        tooltip.addPara("- Increases PD weapon damage by %s for Timid officers.", pad5, Misc.getTextColor(), Misc.getTextColor(),
                Misc.getRoundedValue(10.0f) + "%", "Timid").setHighlightColors(green, Misc.getHighlightColor());
        tooltip.addPara("- Increases weapon range by %s for Cautious officers.", pad5, Misc.getTextColor(), Misc.getTextColor(),
                Misc.getRoundedValue(100f)+"su", "Cautious").setHighlightColors(green, Misc.getHighlightColor());
        tooltip.addPara("- Decreases damage taken by %s for Steady officers.", pad5, Misc.getTextColor(), Misc.getTextColor(),
                Misc.getRoundedValue(10.0f) + "%", "Steady").setHighlightColors(green, Misc.getHighlightColor());
        tooltip.addPara("- Increases max top speed by %s for Aggressive officers.", pad5, Misc.getTextColor(), Misc.getTextColor(),
                Misc.getRoundedValue(10.0f) + "%", "Aggressive").setHighlightColors(green, Misc.getHighlightColor());
        tooltip.addPara("- Increases damage dealt by %s for Reckless officers.", pad5, Misc.getTextColor(), Misc.getTextColor(),
                Misc.getRoundedValue(5.0f) + "%", "Reckless").setHighlightColors(green, Misc.getHighlightColor());

        //Incompatibilities

        //Quotes
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_bioresoance1 ).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_bioresoance2 ).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_bioresoance3 ).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_bioresoance4 ).italicize();
        tooltip.addPara("%s", 6.0f, flavor, eusan_nation_bioresoance5 ).italicize();
        tooltip.addPara("%s", 1.0f, flavor, eusan_nation_bioresoance6).setAlignment(Alignment.RMID);
    }

    // Buff handler
    private void applyBioresonanceBuffEffect(final ShipAPI ship, final ShipAPI source, final float level, final boolean visible) {
        float angle = level * -3f;
        // Create smooth pulsing effect
        float time = Global.getCombatEngine().getTotalElapsedTime(false);
        float pulse = 1f + 0.1f * (float) Math.sin(time * 2f); // size pulse
        float alphaMult = 0.4f + 0.6f * (float) Math.sin(time * 2f);
        if (alphaMult < 0f) alphaMult = -alphaMult; // keep positive fade
        int baseAlpha = (int) (15 * alphaMult);
        Color auraColor = new Color(105, 220, 255, baseAlpha);

//        MagicRender.objectspace(
//                Global.getSettings().getSprite("fx", "placeholder"),
//                ship, new Vector2f(), new Vector2f(),
//                new Vector2f(400f * pulse, 400f * pulse),
//                new Vector2f(-100f * pulse, -100f * pulse),
//                angle,
//                0f, // jitter fuck
//                false,
//                auraColor,
//                true,
//                0.1f,
//                0.0f,
//                0.1f,
//                true
//        );

        // Check if the allied ship has existing captain
        if (ship.getCaptain() != null && !ship.getCaptain().isDefault()) {

            String personality = Misc.lcFirst(ship.getCaptain().getPersonalityAPI().getId());

            if (personality != null) {
                switch (personality) {
                    // Timid goes for point defense | 15%
                    case Personalities.TIMID:
                        ship.setJitter(ship, JITTER_COLOR_TIMID, 0.6f, 3, 5.0f);
                        ship.getMutableStats().getDamageToFighters().modifyPercent(this.ID, 15f);
                        ship.getMutableStats().getDamageToMissiles().modifyPercent(this.ID, 15f);
                        break;
                    // Cautious focuses on range | +10% weapon range
                    case Personalities.CAUTIOUS:
                        ship.setJitter(ship, JITTER_COLOR_CAUTIOUS, 0.5f, 3, 5.0f);
                        ship.getMutableStats().getBallisticWeaponRangeBonus().modifyPercent(this.ID, 10f);
                        ship.getMutableStats().getEnergyWeaponRangeBonus().modifyPercent(this.ID, 10f);
                        ship.getMutableStats().getMissileWeaponRangeBonus().modifyPercent(this.ID, 10f);
                        break;
                    // Steady takes less damage taken | 10%
                    case Personalities.STEADY:
                        ship.setJitter(ship, JITTER_COLOR_STEADY, 0.8f, 3, 5.0f);
                        ship.getMutableStats().getArmorDamageTakenMult().modifyMult(this.ID, 0.90f);
                        ship.getMutableStats().getHullDamageTakenMult().modifyMult(this.ID, 0.90f);
                        ship.getMutableStats().getShieldDamageTakenMult().modifyMult(this.ID, 0.90f);
                        break;
                    // Increased speed - 10%
                    case Personalities.AGGRESSIVE:
                        ship.setJitter(ship, JITTER_COLOR_AGGRESSIVE, 0.5f, 3, 5.0f);
                        ship.getMutableStats().getMaxSpeed().modifyFlat(this.ID, 10f);
                        ship.getMutableStats().getAcceleration().modifyPercent(this.ID, 10f);
                        ship.getMutableStats().getTurnAcceleration().modifyPercent(this.ID, 10f);
                        ship.getMutableStats().getMaxTurnRate().modifyPercent(this.ID, 10f);
                        break;
                    // Damage dealt - 5%
                    case Personalities.RECKLESS:
                        ship.setJitter(ship, JITTER_COLOR_RECKLESS, 0.6f, 3, 5.0f);
                        ship.getMutableStats().getBallisticWeaponDamageMult().modifyMult(this.ID, 1.05f);
                        ship.getMutableStats().getEnergyWeaponDamageMult().modifyMult(this.ID, 1.05f);
                        ship.getMutableStats().getMissileWeaponDamageMult().modifyMult(this.ID, 1.05f);
                        break;
                    // Do we even need this?
                    default:
                        break;
                }
            }
        }
        else {
            // Without captain
            ship.setJitter(ship, JITTER_COLOR_STEADY, 0.8f, 3, 5.0f);
            ship.getMutableStats().getArmorDamageTakenMult().modifyMult(this.ID, 0.90f);
            ship.getMutableStats().getHullDamageTakenMult().modifyMult(this.ID, 0.90f);
            ship.getMutableStats().getShieldDamageTakenMult().modifyMult(this.ID, 0.90f);
        }
        ///
    }

    // Unapply the buffs when it is not within range
    private void unapplyBioresonanceBuffEffect(final ShipAPI ship) {
        ship.getMutableStats().getDamageToFighters().unmodify(this.ID);
        ship.getMutableStats().getDamageToMissiles().unmodify(this.ID);

        ship.getMutableStats().getBallisticWeaponRangeBonus().unmodify(this.ID);
        ship.getMutableStats().getEnergyWeaponRangeBonus().unmodify(this.ID);
        ship.getMutableStats().getMissileWeaponRangeBonus().unmodify(this.ID);

        ship.getMutableStats().getArmorDamageTakenMult().unmodify(this.ID);
        ship.getMutableStats().getHullDamageTakenMult().unmodify(this.ID);
        ship.getMutableStats().getShieldDamageTakenMult().unmodify(this.ID);

        ship.getMutableStats().getMaxSpeed().unmodify(this.ID);
        ship.getMutableStats().getAcceleration().unmodify(this.ID);
        ship.getMutableStats().getTurnAcceleration().unmodify(this.ID);
        ship.getMutableStats().getMaxTurnRate().unmodify(this.ID);

        ship.getMutableStats().getBallisticWeaponDamageMult().unmodify(this.ID);
        ship.getMutableStats().getEnergyWeaponDamageMult().unmodify(this.ID);
        ship.getMutableStats().getMissileWeaponDamageMult().unmodify(this.ID);
    }

}