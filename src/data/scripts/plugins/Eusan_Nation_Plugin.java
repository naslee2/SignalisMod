package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.ui.Fonts;
import data.scripts.ai.projectiles.eusan_nation_MifengAI;
import data.scripts.systems.Eusan_Nation_System;
import data.campaign.fleets.Eusan_Nation_PersonalFleetFalke;
import exerelin.campaign.SectorManager;
import java.awt.*;

public class Eusan_Nation_Plugin extends BaseModPlugin {
    
    static boolean lazyLibPresent = Global.getSettings().getModManager().isModEnabled("lw_lazylib");
    static boolean nexerlinPresent = Global.getSettings().getModManager().isModEnabled("nexerelin");
    static boolean magicLibPresent = Global.getSettings().getModManager().isModEnabled("MagicLib");

    //Missile AIs
    public static final String MIFENG_MISILE_ID = "eusan_nation_mifeng_srm";

    @Override
    public void onNewGame(){
        initPluginChecks();
        initModPlugin();
    }

    private static void initModPlugin(){
        if (!nexerlinPresent || SectorManager.getManager().isCorvusMode()) {
            new Eusan_Nation_System().generate(Global.getSector());
            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("eusan_nation");
            new Eusan_Nation_SetFactionRelations().SetFactionRelations(Global.getSector());
        }
    }

    private static void initPluginChecks(){
        if (!lazyLibPresent) {
            throw new RuntimeException("This mod requires LazyLib" + "\nGet it at http://fractalsoftworks.com/forum/index.php?topic=5444");
        }

        // if (!nexerlinPresent){
        //     throw new RuntimeException("This mod requires Nexerelin");
        // }

        if (!magicLibPresent) {
            throw new RuntimeException("This mod requires Magic Lib");
        }
    }

    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(final MissileAPI missile, final ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case MIFENG_MISILE_ID:
                return new PluginPick<>(new eusan_nation_MifengAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            default:
                return null;
        }
    }

    @Override
    public void onApplicationLoad() throws Exception{
        super.onApplicationLoad();
        Global.getSettings().loadFont("graphics/fonts/silver18.fnt");
        Global.getSettings().loadFont("graphics/fonts/silver28.fnt");
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        SectorAPI sector = Global.getSector();
        new Eusan_Nation_PeopleData().create();
        if (!sector.hasScript(Eusan_Nation_PersonalFleetFalke.class)) {
            sector.addScript(new Eusan_Nation_PersonalFleetFalke());
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
        new KeyGenerator().hometeKeyGenerator();
        //Global.getSector().addTransientScript(new Eusan_Nation_TimeCheckScript());
    }

}