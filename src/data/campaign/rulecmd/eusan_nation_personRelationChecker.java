package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc.Token;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class eusan_nation_personRelationChecker extends BaseCommandPlugin{

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        String relationLevel = params.get(0).getString(memoryMap);
        SectorEntityToken targetPerson = dialog.getInteractionTarget();
        float relationLevelToPlayer = 0f;
        
        if(params.isEmpty()){
            return false;
        }    
        if (targetPerson.getActivePerson() == null){
            return false;
        }
        else{
            relationLevelToPlayer = targetPerson.getActivePerson().getRelToPlayer().getRel();
        }
        switch(relationLevel){
            case "COOPERATIVE":
                break;
            case "FRIENDLY":
                break;
            case "WELCOMING":
                break;
            case "FAVORABLE":
                break;
            case "NEUTRAL":
                break;
            case "SUSPICIOUS":
                break;
            case "INHOSPITABLE":
                break;
            case "HOSTILE":
                break;
            case "VENGEFUL":
                break;
            default:
                dialog.getTextPanel().addPara("Uh oh an error has occured!");
                break;
        }

    return true;
    }
    
}
