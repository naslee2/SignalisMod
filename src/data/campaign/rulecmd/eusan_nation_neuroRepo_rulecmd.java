//package data.campaign.rulecmd;
//
//import com.fs.starfarer.api.campaign.InteractionDialogAPI;
//import com.fs.starfarer.api.campaign.rules.MemoryAPI;
//import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
//import com.fs.starfarer.api.util.Misc;
//
//import data.campaign.intel.Eusan_Nation_MessageIntel;
//
//import java.util.List;
//import java.util.Map;
//
//public class eusan_nation_neuroRepo_rulecmd extends BaseCommandPlugin {
//    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
//        if(dialog == null){
//            return false;
//        }
//        if(params.isEmpty()){
//            return false;
//        }
//        String message = params.get(0).getString(memoryMap);
//        switch(message){
//            case "sendArianeMessage":
//                Eusan_Nation_MessageIntel.arianeMessage_neuroRepo();
//                break;
//            default:
//                dialog.getTextPanel().addPara("Uh oh an error has occurred!");
//                dialog.getTextPanel().addPara("No intel for code: " + message);
//                break;
//        }
//
//        return true;
//    }
//}
