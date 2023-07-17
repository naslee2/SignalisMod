package data.scripts.plugins;
import javax.management.RuntimeErrorException;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.CoreScript;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.util.Misc;

import exerelin.campaign.SectorManager;

public class SignalisModPlugin extends BaseModPlugin {
    
    static boolean lazyLibPresent = Global.getSettings().getModManager().isModEnabled("lw_lazylib");
    static boolean nexerlinPresent = Global.getSettings().getModManager().isModEnabled("nexerelin");
    
    @Override
    public void onNewGame(){
        initPluginChecks();
        initModPlugin();
    }

    private static void initModPlugin(){
        //boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!nexerlinPresent || SectorManager.getManager().isCorvusMode()){
            new SignalisModGen().generateNew(Global.getSector());
        }
    }

    private static void initPluginChecks(){
        //boolean lazyLibPresent = Global.getSettings().getModManager().isModEnabled("lw_lazylib");
        //boolean hasNexerlin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!lazyLibPresent){
            throw new RuntimeException("This mod requires LazyLib" + "\nGet it at http://fractalsoftworks.com/forum/index.php?topic=5444");
        }
        if (!nexerlinPresent){
            throw new RuntimeException("This mod requires Nexerelin");
        }

    }
}
