package data.scripts.plugins;
//import javax.management.RuntimeErrorException;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import data.campaign.fleets.Eusan_Nation_personalFleetFalke;

import exerelin.campaign.SectorManager;

public class Eusan_Nation_Plugin extends BaseModPlugin {
    
    static boolean lazyLibPresent = Global.getSettings().getModManager().isModEnabled("lw_lazylib");
    static boolean nexerlinPresent = Global.getSettings().getModManager().isModEnabled("nexerelin");
    static boolean magicLibPresent = Global.getSettings().getModManager().isModEnabled("MagicLib");
    
    @Override
    public void onNewGame(){
        initPluginChecks();
        initModPlugin();
    }

    private static void initModPlugin(){
        //boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!nexerlinPresent || SectorManager.getManager().isCorvusMode()){
            new Eusan_Nation_ModGen().generateNew(Global.getSector());
        }
    }

    private static void initPluginChecks(){
        if (!lazyLibPresent){
            throw new RuntimeException("This mod requires LazyLib" + "\nGet it at http://fractalsoftworks.com/forum/index.php?topic=5444");
        }
        // if (!nexerlinPresent){
        //     throw new RuntimeException("This mod requires Nexerelin");
        // }
        if(!magicLibPresent){
            throw new RuntimeException("This mod requires Magic Lib");
        }
    }

    // @Override
    // public void onGameLoad(boolean newGame) {
    //     SectorAPI sector = Global.getSector();
    //         if (!sector.hasScript(Eusan_Nation_personalFleetFalke.class)) {
    //             sector.addScript(new Eusan_Nation_personalFleetFalke());
    //         }
    // }
    
    @Override
    public void onNewGameAfterEconomyLoad() {
        SectorAPI sector = Global.getSector();
        if (!sector.hasScript(Eusan_Nation_personalFleetFalke.class)) {
            sector.addScript(new Eusan_Nation_personalFleetFalke());
        }
    }
}
