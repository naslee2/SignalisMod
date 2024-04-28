package data.scripts.plugins;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.combat.BattleObjectiveAPI.Importance;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Voices;


public class Eusan_Nation_People {
    public static String EUSAN_NATION = "eusan_nation";
    public static String OFFICER_YEONG = "eusan_nation_officer_yeong";
    public static String ARIANE_YEONG_FIRSTNAME = "Ariane";
    public static String ARIANE_YEONG_LASTNAME = "Yeong";
	public static String ADMIRAL_FALKE = "eusan_nation_admiral_falke";
    public static String ADMIRAL_FALKE_FIRSTNAME = "Falke";
    public static String ADMIRAL_FALKE_LASTNAME = "VSF01";
    public static String SPECIAL_AGENT = "eusan_nation_special_agent";
    public static String ROTFRONT_FLEETADMIRAL = "eusan_nation_rotfront_admiral";
    public static String MARY_CHENG = "eusan_nation_mary_cheng";
    public static String MARY_CHENG_FIRST_NAME = "Mary";
	public static String MARY_CHENG_LAST_NAME = "Cheng";
    public static String MARLIS = "eusan_nation_marlis";
    public static String MARLIS_FIRST_NAME = "Marlis";
    public static String MARLIS_LAST_NAME = "EULR-FV R2652";

    
    public static MarketAPI market = null;

    public static PersonAPI getPerson(String id) {
		return Global.getSector().getImportantPeople().getPerson(id);
	}

    public void create() {
        createImportantPeople_heimat();
        createImportantPeople_vineta();
        createImportantPeople_rotfront();
        createImportantPeople_miscOfficers();
	}

    public static void createImportantPeople_heimat(){
        ImportantPeopleAPI importantpeople_heimat = Global.getSector().getImportantPeople();
        market = Global.getSector().getEconomy().getMarket("heimat_market");
        if(market != null){
            //Officer Yeong
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

            //Admiral Falke
	        PersonAPI admiral_falke = Global.getFactory().createPerson();
            admiral_falke.setId(ADMIRAL_FALKE);
            admiral_falke.setFaction(EUSAN_NATION);
            admiral_falke.setGender(Gender.FEMALE);
            admiral_falke.setRankId(Ranks.SPACE_ADMIRAL);
            admiral_falke.setPostId(Ranks.POST_FLEET_COMMANDER);
            admiral_falke.getName().setFirst(ADMIRAL_FALKE_FIRSTNAME);
            admiral_falke.getName().setLast(ADMIRAL_FALKE_LASTNAME);
            admiral_falke.setPortraitSprite("graphics/portraits/eusan_nation_admiralFalke_128.png");
            admiral_falke.setImportance(PersonImportance.VERY_HIGH);
            admiral_falke.setPersonality(Personalities.AGGRESSIVE);
            admiral_falke.getStats().setLevel(9);
            admiral_falke.getStats().setSkillLevel(Skills.BALLISTIC_MASTERY, 2);
            admiral_falke.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            admiral_falke.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 2);
            admiral_falke.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 1);
            admiral_falke.getStats().setSkillLevel(Skills.WOLFPACK_TACTICS, 1);
            admiral_falke.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
            admiral_falke.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 1);
            admiral_falke.getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 1);
            admiral_falke.setVoice(Voices.SOLDIER);
            importantpeople_heimat.addPerson(admiral_falke);
        }
    }

    public static void createImportantPeople_vineta(){
        ImportantPeopleAPI importantpeople_vineta = Global.getSector().getImportantPeople();
        market = Global.getSector().getEconomy().getMarket("vineta_market");
        if(market !=null){
            //Special Agent
            PersonAPI special_agent = Global.getFactory().createPerson();
            special_agent.setId(SPECIAL_AGENT);
            special_agent.setFaction(EUSAN_NATION);
            special_agent.setGender(Gender.FEMALE);
            special_agent.setRankId("gestaltOfficer");
            special_agent.setPostId("gestaltAgent");
            special_agent.getName().setFirst("Special");
            special_agent.getName().setLast("Agent");
            special_agent.setPortraitSprite("graphics/portraits/portrait_hegemony15.png");
            special_agent.setImportance(PersonImportance.HIGH);
            special_agent.addTag(Tags.CONTACT_MILITARY);
            special_agent.setVoice(Voices.SOLDIER);
            market.addPerson(special_agent); 
            importantpeople_vineta.addPerson(special_agent);
        }
    }

    public static void createImportantPeople_rotfront(){
        ImportantPeopleAPI importantpeople_rotfront = Global.getSector().getImportantPeople();
        market = Global.getSector().getEconomy().getMarket("rotfront_market");
        if(market != null){
            //Rotfront Defense Fleet Admiral
            PersonAPI rotfront_fleetAdmiral = Global.getFactory().createPerson();
            rotfront_fleetAdmiral.setId(ROTFRONT_FLEETADMIRAL);
            rotfront_fleetAdmiral.setFaction(EUSAN_NATION);
            rotfront_fleetAdmiral.setGender(Gender.FEMALE);
            rotfront_fleetAdmiral.setRankId(Ranks.SPACE_ADMIRAL);
            rotfront_fleetAdmiral.setPostId(Ranks.POST_FLEET_COMMANDER);
            rotfront_fleetAdmiral.setPersonality(Personalities.CAUTIOUS);
            rotfront_fleetAdmiral.getStats().setLevel(7);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.CREW_TRAINING, 1);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 1);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 2);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 2);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.WOLFPACK_TACTICS, 1);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
            rotfront_fleetAdmiral.getStats().setSkillLevel(Skills.POINT_DEFENSE,1);
            rotfront_fleetAdmiral.setVoice(Voices.SOLDIER);
            importantpeople_rotfront.addPerson(rotfront_fleetAdmiral);    

            PersonAPI rotfront_marlis = Global.getFactory().createPerson();
            rotfront_marlis.setId(MARLIS);
            rotfront_marlis.setFaction(EUSAN_NATION);
            rotfront_marlis.setGender(Gender.FEMALE);
            rotfront_marlis.getName().setFirst(MARLIS_FIRST_NAME);
            rotfront_marlis.getName().setLast(MARLIS_LAST_NAME);
            rotfront_marlis.setGender(Gender.FEMALE);
            rotfront_marlis.setPortraitSprite("graphics/portraits/eusan_nation_marlis_128.png");
            rotfront_marlis.setRankId("replika");
            rotfront_marlis.setPostId("bartender");
            rotfront_marlis.setVoice(Voices.BUSINESS);
            importantpeople_rotfront.addPerson(rotfront_marlis);
        }
    }

    public static void createImportantPeople_miscOfficers(){
        ImportantPeopleAPI importantpeople_miscOfficers = Global.getSector().getImportantPeople();
        PersonAPI mary_cheng = Global.getFactory().createPerson();
        mary_cheng.setId(MARY_CHENG);
        mary_cheng.setFaction(EUSAN_NATION);
        mary_cheng.getName().setFirst(MARY_CHENG_FIRST_NAME);
        mary_cheng.getName().setLast(MARY_CHENG_LAST_NAME);
        mary_cheng.setGender(Gender.FEMALE);
        mary_cheng.setRankId("gestaltOfficer");
        mary_cheng.setPostId("gestaltPenrosePilot");
        mary_cheng.setPortraitSprite("graphics/portraits/eusan_nation_NoDataPortrait_2_128.png");
        mary_cheng.setImportance(PersonImportance.MEDIUM);
        mary_cheng.setPersonality(Personalities.STEADY);
        mary_cheng.getStats().setLevel(5);
        mary_cheng.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
        mary_cheng.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 1);
        mary_cheng.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
        mary_cheng.getStats().setSkillLevel(Skills.IMPACT_MITIGATION, 1);
        mary_cheng.getStats().setSkillLevel(Skills.BALLISTIC_MASTERY, 1);
        mary_cheng.setVoice(Voices.SOLDIER);
        importantpeople_miscOfficers.addPerson(mary_cheng);
    }
    
}

