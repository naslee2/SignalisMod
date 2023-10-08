package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Voices;


public class Eusan_Nation_People {
    public static String EUSAN_NATION = "eusan_nation";
    public static String OFFICER_YEONG = "eusan_nation_officer_yeong";
    public static String ARIANE_YEONG_FIRSTNAME = "Ariane";
    public static String ARIANE_YEONG_LASTNAME = "Yeong";

	public static MarketAPI market = null;

    public static PersonAPI getPerson(String id) {
		return Global.getSector().getImportantPeople().getPerson(id);
	}

    public void create() {
        createImportantPeople_heimat();
	}

    public static void createImportantPeople_heimat(){
        ImportantPeopleAPI importantpeople_heimat = Global.getSector().getImportantPeople();
        market = Global.getSector().getEconomy().getMarket("heimat_market");
        if(market != null){
            PersonAPI officer_yeong = Global.getFactory().createPerson();
            officer_yeong.setId(OFFICER_YEONG);
            officer_yeong.setFaction(EUSAN_NATION);
            officer_yeong.setGender(Gender.FEMALE);
            officer_yeong.setRankId("gestaltOfficer");
            officer_yeong.setPostId("gestaltInvestigator");
            officer_yeong.setImportance(PersonImportance.HIGH);
            officer_yeong.getName().setFirst(ARIANE_YEONG_FIRSTNAME);
            officer_yeong.getName().setLast(ARIANE_YEONG_LASTNAME);
            officer_yeong.setPortraitSprite("graphics/portraits/eusan_nation_officer_yeong.png");
            officer_yeong.addTag(Tags.CONTACT_MILITARY);
            officer_yeong.setVoice(Voices.SOLDIER);
            market.getCommDirectory().addPerson(officer_yeong, 1);
            market.getCommDirectory().getEntryForPerson(officer_yeong).setHidden(false);
            market.addPerson(officer_yeong);
            importantpeople_heimat.addPerson(officer_yeong);
        }

    }
    
}

