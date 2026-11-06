package data.scripts.ai.projectiles;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lwjgl.util.vector.Vector2f;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author Mayu
 */
public class eusan_nation_MifengAI implements MissileAIPlugin, GuidedMissileAI {

    private static final String WEAPON_ID = "eusan_nation_mifeng";
    private static final String KEY = "eusan_mifeng_assignments";

    private static final float DEFENSE_RADIUS = 2400f;

    // MagicLib-style guidance settings etc. whatever
    private static final float OVERSHOT_ANGLE = 60f;
    private static final float DAMPING = 0.1f;
    private static final boolean TARGET_SWITCH = true;
    private static final float PRECISION_RANGE = 500f;
    private static final float ECCM_DEFAULT = 3f;
    //private static final float MAX_SEARCH_RANGE = 1600f;

    private final MissileAPI MISSILE;
    private final ShipAPI LAUNCHING_SHIP;
    private final float MAX_SPEED;
    private final float OFFSET;

    private CombatEngineAPI engine;
    private CombatEntityAPI target;
    private Vector2f lead = new Vector2f();

    private float ECCM = ECCM_DEFAULT;
    private float timer = 0f;
    private float check = 0f;
    private boolean launch = true;

    public eusan_nation_MifengAI(MissileAPI missile, ShipAPI launchingShip) {
        this.MISSILE = missile;
        this.LAUNCHING_SHIP = launchingShip;
        this.MAX_SPEED = missile.getMaxSpeed();
        this.OFFSET = (float) (Math.random() * MathUtils.FPI * 2f);

        if (launchingShip != null && launchingShip.getVariant() != null && launchingShip.getVariant().getHullMods().contains("eccm")) {
            ECCM = 1f;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<MissileAPI, CombatEntityAPI> registry() {
        Map<MissileAPI, CombatEntityAPI> map = (Map<MissileAPI, CombatEntityAPI>) engine.getCustomData().get(KEY);

        if (map == null) {
            map = new HashMap<>();
            engine.getCustomData().put(KEY, map);
        }

        return map;
    }

    private Map<CombatEntityAPI, Integer> countAssignments() {
        Map<CombatEntityAPI, Integer> counts = new HashMap<>();
        Iterator<Map.Entry<MissileAPI, CombatEntityAPI>> it = registry().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<MissileAPI, CombatEntityAPI> e = it.next();
            MissileAPI m = e.getKey();

            if (!engine.isEntityInPlay(m) || m.isFading() || m.isFizzling()) {
                it.remove(); // prune dead missiles

                continue;
            }

            if (m == MISSILE) {
                continue;
            }

            Integer c = counts.get(e.getValue());

            counts.put(e.getValue(), c == null ? 1 : c + 1);
        }

        return counts;
    }


    @Override
    public void advance(float amount) {
        CombatEngineAPI currentEngine = Global.getCombatEngine();

        if (engine != currentEngine) {
            engine = currentEngine;
        }

        if (engine == null || engine.isPaused() || MISSILE.isFading() || MISSILE.isFizzling()) {
            return;
        }

        ShipAPI source = LAUNCHING_SHIP != null ? LAUNCHING_SHIP : MISSILE.getSource();

        if (source == null || !source.isAlive()) {
            MISSILE.giveCommand(ShipCommand.ACCELERATE);

            return;
        }

        // Acquire a target if none exists, or if the current one is gone.
//        if (target == null || (TARGET_SWITCH && !isValidTarget(target, source))) {
//
//            target = selectTarget(source);
//
//            MISSILE.giveCommand(ShipCommand.ACCELERATE);
//
//            if (target == null) {
//                return;
//            }
//
//            // Calculate a fresh lead point next frame.
//            launch = true;
//        }

        if (target == null || (TARGET_SWITCH && !isValidTarget(target, source))) {
            registry().remove(MISSILE);

            target = selectTarget(source);
            MISSILE.giveCommand(ShipCommand.ACCELERATE);

            if (target == null) {
                return;
            }

            registry().put(MISSILE, target);

            launch = true;
        }

        timer += amount;

        // Recalculate the interception point periodically, in theory it will work, probably...
        if (launch || timer >= check) {
            launch = false;
            timer -= check;

            check = Math.min(0.25f, Math.max(0.05f, org.lazywizard.lazylib.MathUtils.getDistanceSquared(MISSILE.getLocation(), target.getLocation()) / (PRECISION_RANGE * PRECISION_RANGE * 4f)));

            lead = AIUtils.getBestInterceptPoint(MISSILE.getLocation(), MAX_SPEED * ECCM, target.getLocation(), target.getVelocity());

            if (lead == null) {
                lead = new Vector2f(target.getLocation());
            }
        }

        // MagicLib-style guidance, why? Rather than relying on it, with this, we have full control of the whole thing. It will aim at the intercept point
        float correctAngle = VectorUtils.getAngle(MISSILE.getLocation(), lead);

        // No waving motion. It should aim directly at the interception point lol
        float aimAngle = MathUtils.getShortestRotation(MISSILE.getFacing(), correctAngle);

        if (Math.abs(aimAngle) < OVERSHOT_ANGLE) {
            MISSILE.giveCommand(ShipCommand.ACCELERATE);
        }

        if (aimAngle < 0f) {
            MISSILE.giveCommand(ShipCommand.TURN_RIGHT);
        }
        else if (aimAngle > 0f) {
            MISSILE.giveCommand(ShipCommand.TURN_LEFT);
        }

        // Dampen rotation as the missile approaches its desired heading :mambo:
        if (Math.abs(aimAngle)
                < Math.abs(MISSILE.getAngularVelocity()) * DAMPING) {
            MISSILE.setAngularVelocity(aimAngle / DAMPING);
        }
    }

//    private boolean isValidTarget(CombatEntityAPI candidate, ShipAPI source) {
//        if (candidate == null
//                || !engine.isEntityInPlay(candidate)
//                || candidate.getOwner() == MISSILE.getOwner()) {
//            return false;
//        }
//
//        // Restrict defense to threats near the launching ship.
//        if (org.lazywizard.lazylib.MathUtils.getDistance(source.getLocation(), candidate.getLocation()) > DEFENSE_RADIUS) {
//            return false;
//        }
//
//        if (candidate instanceof MissileAPI) {
//            MissileAPI missile = (MissileAPI) candidate;
//
//            return !missile.isFading() && !missile.isFizzling();
//        }
//
//        if (candidate instanceof ShipAPI) {
//            ShipAPI ship = (ShipAPI) candidate;
//
//            return ship.isAlive() && !ship.isHulk() && ship.isFighter();
//        }
//
//        return false;
//    }

    private boolean isValidTarget(CombatEntityAPI candidate, ShipAPI source) {
        if (candidate == null || !engine.isEntityInPlay(candidate) || candidate.getOwner() == MISSILE.getOwner()) {
            return false;
        }

        // Restrict defense to threats near the launching ship. And, if I get the request right it should be done like this?????? Probably...
        if (MathUtils.getDistance(source.getLocation(), candidate.getLocation()) > DEFENSE_RADIUS) {
            return false;
        }

        if (candidate instanceof MissileAPI) {
            MissileAPI missile = (MissileAPI) candidate;
            return !missile.isFading() && !missile.isFizzling();
        }

        if (candidate instanceof ShipAPI) {
            ShipAPI ship = (ShipAPI) candidate;
            return ship.isAlive() && !ship.isHulk();   // removed: && ship.isFighter()
        }

        return false;
    }

//    private CombatEntityAPI selectTarget(ShipAPI source) {
//        CombatEntityAPI bestTarget = null;
//        float bestScore = -Float.MAX_VALUE;
//
//        for (MissileAPI candidate : engine.getMissiles()) {
//            if (!isValidTarget(candidate, source)) {
//                continue;
//            }
//
//            if (countAssignedMissiles(candidate) >= getAllocationCap(candidate)) {
//                continue;
//            }
//
//            float score = getThreatScore(candidate, source);
//
//            if (score > bestScore) {
//                bestScore = score;
//                bestTarget = candidate;
//            }
//        }
//
//        List<ShipAPI> ships = engine.getShips();
//
//        for (ShipAPI candidate : ships) {
//            if (!isValidTarget(candidate, source)) {
//                continue;
//            }
//
//            if (countAssignedMissiles(candidate) >= getAllocationCap(candidate)) {
//                continue;
//            }
//
//            float score = getThreatScore(candidate, source);
//
//            if (score > bestScore) {
//                bestScore = score;
//                bestTarget = candidate;
//            }
//        }
//
//        return bestTarget;
//    }

    private CombatEntityAPI selectTarget(ShipAPI source) {
        Map<CombatEntityAPI, Integer> counts = countAssignments();
        CombatEntityAPI best = null;
        float bestScore = -Float.MAX_VALUE;
        boolean anyPDTarget = false;

        // Okay let's try this...
        // Pass 1: enemy missiles and fighters
        for (MissileAPI m : engine.getMissiles()) {
            if (!isValidTarget(m, source)) {
                continue;
            }

            anyPDTarget = true;
            Integer n = counts.get(m);

            if (n != null && n >= getAllocationCap(m)) {
                continue;
            }

            float score = getThreatScore(m, source);

            if (score > bestScore) {
                bestScore = score; best = m;
            }
        }

        for (ShipAPI s : engine.getShips()) {
            if (!s.isFighter() || !isValidTarget(s, source)) {
                continue;
            }
            anyPDTarget = true;
            Integer n = counts.get(s);

            if (n != null && n >= getAllocationCap(s)) {
                continue;
            }

            float score = getThreatScore(s, source);

            if (score > bestScore) {
                bestScore = score; best = s;
            }
        }

        // Targets exist but are all fully allocated: hold, don't waste missiles on ships.
        if (best != null || anyPDTarget) {
            return best;
        }

        // Pass 2: nothing to shoot down, so fall back to the closest enemy ship!
        float bestDist = Float.MAX_VALUE;

        for (ShipAPI s : engine.getShips()) {
            if (s.isFighter() || !s.isAlive() || s.isHulk() || s.getOwner() == MISSILE.getOwner()) {
                continue;
            }

            float d = MathUtils.getDistance(source.getLocation(), s.getLocation());

            if (d <= DEFENSE_RADIUS && d < bestDist) {
                bestDist = d; best = s;
            }
        }

        return best;
    }

    private int getAllocationCap(CombatEntityAPI candidate) {
        if (candidate instanceof MissileAPI) {
            float damage = ((MissileAPI) candidate).getDamageAmount();

            if (damage >= 800f) {
                return 7;
            }

            if (damage >= 250f) {
                return 4;
            }

            return 2;
        }

        // Initial fighter allocation; tune after testing.
        return 2;
    }

    private float getThreatScore(CombatEntityAPI candidate, ShipAPI source) {
        float distance = org.lazywizard.lazylib.MathUtils.getDistance(source.getLocation(), candidate.getLocation());

        float score = 1000f / (distance + 100f);

        if (candidate instanceof MissileAPI) {
            MissileAPI missile = (MissileAPI) candidate;

            score += 100f;
            score += Math.min(missile.getDamageAmount(), 1500f) * 0.05f;
        }

        Vector2f toSource = new Vector2f(source.getLocation().x - candidate.getLocation().x, source.getLocation().y - candidate.getLocation().y);

        float distanceSquared = toSource.x * toSource.x + toSource.y * toSource.y;

        Vector2f velocity = candidate.getVelocity();

        float speedSquared = velocity.x * velocity.x + velocity.y * velocity.y;

        if (distanceSquared > 0.01f && speedSquared > 1f) {
            float dot = velocity.x * toSource.x + velocity.y * toSource.y;

            float approach = dot / (float) Math.sqrt(distanceSquared * speedSquared);

            if (approach > 0f) {
                score += approach * 150f;
            }
        }

        return score;
    }


    private int countAssignedMissiles(CombatEntityAPI candidate) {
        int count = 0;

        for (MissileAPI other : engine.getMissiles()) {
            if (other == MISSILE || other.isFading() || other.isFizzling()) {
                continue;
            }

            // Count only Mifeng missiles launched by this same ship
            if (other.getSource() != LAUNCHING_SHIP) {
                continue;
            }

            WeaponAPI weapon = other.getWeapon();

            if (weapon == null || !WEAPON_ID.equals(weapon.getId())) {
                continue;
            }

            MissileAIPlugin MifengAI = other.getMissileAI();

            if (MifengAI instanceof eusan_nation_MifengAI) {
                eusan_nation_MifengAI otherAI = (eusan_nation_MifengAI) MifengAI;

                if (otherAI.getTarget() == candidate) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public CombatEntityAPI getTarget() {
        return target;
    }

//    @Override
//    public void setTarget(CombatEntityAPI target) {
//        this.target = target;
//        this.launch = true;
//    }

    @Override
    public void setTarget(CombatEntityAPI target) {
        if (engine == null) {
            engine = Global.getCombatEngine();
        }

        if (engine != null) {
            if (target == null) {
                registry().remove(MISSILE);
            }
            else {
                registry().put(MISSILE, target);
            }
        }

        this.target = target;
        this.launch = true;
    }

}