package data.campaign.intel;

import com.fs.starfarer.api.Global;
//import com.fs.starfarer.api.campaign.comm.CommMessageAPI;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.util.Misc;

//import org.apache.log4j.Logger;

public class Eusan_Nation_MessageIntel {
    //static Logger logger = Global.getLogger(Eusan_Nation_MessageIntel.class);

    static String messageIntel_header = "Message from Ariane";
    static String messageIntel_line1 = "\"I sent word to our agent on Vineta. He is expecting you. If you succeed in this very simple task, the Nation will be grateful of course.\"";

    public static void arianeMessage_neuroRepo(){
        if (Global.getSector().getMemoryWithoutUpdate().getBoolean("$officeryeong_neuralrepositorystart")){
           //logger.info("Logger active. Report MessageIntel is: " + "true@!");
           MessageIntel arianeMessage_neuroRepo_a = new MessageIntel(messageIntel_header,Misc.getBasePlayerColor());

           arianeMessage_neuroRepo_a.setIcon("graphics/portraits/eusan_nation_officer_yeong.png");
           arianeMessage_neuroRepo_a.addLine(messageIntel_line1);
           Global.getSector().getIntelManager().addIntel(arianeMessage_neuroRepo_a);

            //todo: override .createSmallDescription()

        }


    }
}

