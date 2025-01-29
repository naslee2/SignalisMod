package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

import data.kaysaar.ui.KeypadPanel;
import data.scripts.systems.Eusan_Nation_System;
import data.scripts.plugins.KeyGenerator;
import data.campaign.fleets.Eusan_Nation_PersonalFleetFalke;
import exerelin.campaign.SectorManager;
import org.lazywizard.lazylib.MathUtils;

import java.util.ArrayList;

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
        if (!nexerlinPresent || SectorManager.getManager().isCorvusMode()){
            new Eusan_Nation_System().generate(Global.getSector());
            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("eusan_nation");
            new Eusan_Nation_SetFactionRelations().SetFactionRelations(Global.getSector());
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

    @Override
    public void onApplicationLoad() throws Exception{
        super.onApplicationLoad();
    }

    // @Override
    // public void onGameLoad(boolean newGame) {
    // }
    
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
        //hometeKeyGenerator();
        new KeyGenerator().hometeKeyGenerator();
    }
    private  void hometeKeyGenerator() {
        if(!Global.getSector().getMemory().contains(KeypadPanel.keyMemToKey)){
            ArrayList<Integer> ints = new ArrayList<>();
            int combinations = 5;

            for (int i = 0; i < combinations; i++) {
                while (true){
                    int cur = MathUtils.getRandomNumberInRange(1,9);
                    boolean found = false;
                    for (Integer anInt : ints) {
                        if(cur == anInt){
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        ints.add(cur);
                        break;
                    }
                }
            }
            Global.getSector().getMemory().set(KeypadPanel.keyMemToKey, ints);
        }
    }
}
