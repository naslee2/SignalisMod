package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;
import com.fs.starfarer.api.campaign.SectorEntityToken;
//import org.apache.log4j.Logger;

public class eusan_nation_personRelationChecker_backup extends BaseCommandPlugin{

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        
        String relationLevel = params.get(0).getString(memoryMap);
        SectorEntityToken targetPerson = dialog.getInteractionTarget();
        int relationLevelToPlayer = 0;
        
        if(params.isEmpty()){
            return false;
        }    
        if (targetPerson.getActivePerson() == null){
            return false;
        }
        else{
            relationLevelToPlayer = targetPerson.getActivePerson().getRelToPlayer().getRepInt();
        }
        switch(relationLevel){
            case "COOPERATIVE":
                return (relationLevelToPlayer > 74);
            case "FRIENDLY":
                return (relationLevelToPlayer > 49);
            case "WELCOMING":
                return (relationLevelToPlayer > 24);
            case "FAVORABLE":
                return (relationLevelToPlayer > 9);
            case "NEUTRAL":
                return (relationLevelToPlayer < 9 || relationLevelToPlayer > -9);
            case "SUSPICIOUS":
                return (relationLevelToPlayer < -9);
            case "INHOSPITABLE":
                return (relationLevelToPlayer < -24);
            case "HOSTILE":
                return (relationLevelToPlayer < -49);
            case "VENGEFUL":
                return (relationLevelToPlayer < -74);
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                dialog.getTextPanel().addPara("No releation code for type: " + relationLevel);
                break;
        }

    return true;
    }
    
}
