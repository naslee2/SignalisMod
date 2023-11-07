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
	public static String ADMIRAL_FALKE = "eusan_nation_admiral_falke";

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

	PersonAPI admiral_falke = Global.getFactory().createPerson();
            admiral_falke.setId(ADMIRAL_FALKE);
            admiral_falke.setFaction(EUSAN_NATION);
            admiral_falke.setGender(Gender.FEMALE);
            admiral_falke.setRankId();
            admiral_falke.setPostId();
            admiral_falke.getName().setFirst();
            admiral_falke.getName().setLast();
            admiral_falke.setImportance(PersonImportance.VERY_HIGH);
            admiral_falke.getStats().setLevel(15);
            admiral_falke.getStats().setSkillLevel(Skills.BALLISTIC_MASTERY, 2);
            admiral_falke.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            admiral_falke.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 2);
            admiral_falke.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
            admiral_falke.getStats().setSkillLevel(Skills.IMPACT_MITIGATION, 2);
            admiral_falke.getStats().setSkillLevel(Skills.POLARIZED_ARMOR, 2);
            admiral_falke.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 1);
            admiral_falke.getStats().setSkillLevel(Skills.HELMSMANSHIP, 1);
            admiral_falke.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
            admiral_falke.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 1);
            admiral_falke.getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 1);
            admiral_falke.getStats().setSkillLevel(Skills.CREW_TRAINING, 1);
            admiral_falke.getStats().setSkillLevel(Skills.SUPPORT_DOCTRINE, 1);
            admiral_falke.getStats().setSkillLevel(Skills.TACTICAL_DRILLS, 1);
            admiral_falke.getStats().setSkillLevel(Skills.OFFICER_MANAGEMENT, 1);
            admiral_falke.addTag("coff_nocapture");
            importantpeople_heimat.addPerson(admiral_falke);
        }

    }
    
}

