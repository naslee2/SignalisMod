package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;

import data.campaign.ids.Eusan_Nation_PeopleStrings;

public class Eusan_Nation_PeopleData {
    public String EUSAN_NATION = "eusan_nation";

    public void create() {
        createImportantPeople_heimat();
        createImportantPeople_vineta();
        createImportantPeople_rotfront();
        createImportantPeople_leng();
        createImportantPeople_miscOfficers();
        createImportantPeople_miscPeople();
	}

    public void createImportantPeople_heimat(){
        ImportantPeopleAPI importantPeople_heimat = Global.getSector().getImportantPeople();
        MarketAPI market_heimat = Global.getSector().getEconomy().getMarket("heimat_market");
        if(market_heimat != null){
            //Officer Yeong
            PersonAPI officer_yeong = Global.getFactory().createPerson();
            officer_yeong.setId(Eusan_Nation_PeopleStrings.OFFICER_YEONG);
            officer_yeong.setFaction(EUSAN_NATION);
            officer_yeong.setGender(Gender.FEMALE);
            officer_yeong.setRankId("gestaltOfficerLt");
            officer_yeong.setPostId("gestaltInvestigator");
            officer_yeong.setImportance(PersonImportance.HIGH);
            officer_yeong.getName().setFirst(Eusan_Nation_PeopleStrings.ARIANE_YEONG_FIRSTNAME);
            officer_yeong.getName().setLast(Eusan_Nation_PeopleStrings.ARIANE_YEONG_LASTNAME);
            officer_yeong.setPortraitSprite("graphics/portraits/characters/eusan_nation_officer_yeong.png");
            officer_yeong.addTag(Tags.CONTACT_MILITARY);
            officer_yeong.setVoice(Voices.SOLDIER);
            market_heimat.getCommDirectory().addPerson(officer_yeong, 1);
            market_heimat.getCommDirectory().getEntryForPerson(officer_yeong).setHidden(false);
            market_heimat.addPerson(officer_yeong);
            importantPeople_heimat.addPerson(officer_yeong);

            //Admiral Falke
	        PersonAPI admiral_falke = Global.getFactory().createPerson();
            admiral_falke.setId(Eusan_Nation_PeopleStrings.ADMIRAL_FALKE);
            admiral_falke.setAICoreId("beta_core");
            admiral_falke.setFaction(EUSAN_NATION);
            admiral_falke.setGender(Gender.FEMALE);
            admiral_falke.setRankId(Ranks.SPACE_ADMIRAL);
            admiral_falke.setPostId(Ranks.POST_FLEET_COMMANDER);
            admiral_falke.getName().setFirst(Eusan_Nation_PeopleStrings.FALKE_FIRSTNAME);
            admiral_falke.getName().setLast(Eusan_Nation_PeopleStrings.ADMIRAL_FALKE_LASTNAME);
            admiral_falke.setPortraitSprite("graphics/portraits/characters/eusan_nation_admiralFalke_128.png");
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
            admiral_falke.isAICore();
            importantPeople_heimat.addPerson(admiral_falke);

            //GreatRevolutionary
            PersonAPI greatRevolutionary = Global.getFactory().createPerson();
            greatRevolutionary.setId(Eusan_Nation_PeopleStrings.GREAT_REVOLUTIONARY);
            greatRevolutionary.setFaction(EUSAN_NATION);
            greatRevolutionary.setGender(Gender.FEMALE);
            greatRevolutionary.setRankId(Ranks.FACTION_LEADER);
            greatRevolutionary.setPostId(Ranks.POST_FACTION_LEADER);
            greatRevolutionary.setVoice(Voices.OFFICIAL);
            greatRevolutionary.setImportance(PersonImportance.VERY_HIGH);
            greatRevolutionary.addTag(Tags.CONTACT_MILITARY);
            greatRevolutionary.getName().setFirst(Eusan_Nation_PeopleStrings.GREAT_REVOLUTIONARY_FIRSTNAME);
            greatRevolutionary.setPortraitSprite("graphics/portraits/characters/eusan_nation_theGreatRevolutionary_128.png");
            greatRevolutionary.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
            market_heimat.setAdmin(greatRevolutionary);
            market_heimat.getCommDirectory().addPerson(greatRevolutionary,0);
            market_heimat.getCommDirectory().getEntryForPerson(greatRevolutionary).setHidden(false);;
            market_heimat.addPerson(greatRevolutionary);
            importantPeople_heimat.addPerson(greatRevolutionary);

            //The Daughter
            PersonAPI theDaughter = Global.getFactory().createPerson();
            theDaughter.setId(Eusan_Nation_PeopleStrings.THE_DAUGHTER);
            theDaughter.setFaction(EUSAN_NATION);
            theDaughter.setGender(Gender.FEMALE);
            theDaughter.setRankId("daughterNationRank");
            theDaughter.setPostId("daughterNation");
            theDaughter.setVoice(Voices.OFFICIAL);
            theDaughter.setImportance(PersonImportance.VERY_HIGH);
            theDaughter.addTag(Tags.CONTACT_UNDERWORLD);
            theDaughter.getName().setFirst(Eusan_Nation_PeopleStrings.THE_DAUGHTER_FIRSTNAME);
            theDaughter.setPortraitSprite("graphics/portraits/characters/eusan_nation_theDaughter_128.png");
            market_heimat.getCommDirectory().addPerson(theDaughter,1);
            market_heimat.getCommDirectory().getEntryForPerson(theDaughter).setHidden(false);
            market_heimat.addPerson(theDaughter);
            importantPeople_heimat.addPerson(theDaughter);

        }
    }

    public void createImportantPeople_vineta(){
        ImportantPeopleAPI importantPeople_vineta = Global.getSector().getImportantPeople();
        MarketAPI market_vineta = Global.getSector().getEconomy().getMarket("vineta_market");
        if(market_vineta !=null){
            //Special Agent
            PersonAPI special_agent = Global.getFactory().createPerson();
            special_agent.setId(Eusan_Nation_PeopleStrings.SPECIAL_AGENT);
            special_agent.setFaction(EUSAN_NATION);
            special_agent.setGender(Gender.FEMALE);
            special_agent.setRankId("gestaltOfficerULt");
            special_agent.setPostId("gestaltAgent");
            special_agent.getName().setFirst("Special");
            special_agent.getName().setLast("Agent");
            special_agent.setPortraitSprite("graphics/portraits/eusan_nation_male_128_8.png");
            special_agent.setImportance(PersonImportance.HIGH);
            special_agent.addTag(Tags.CONTACT_MILITARY);
            special_agent.setVoice(Voices.SOLDIER);
            market_vineta.getCommDirectory().addPerson(special_agent,1);
            market_vineta.getCommDirectory().getEntryForPerson(special_agent).setHidden(true);
            market_vineta.addPerson(special_agent);
            importantPeople_vineta.addPerson(special_agent);
        }
    }

    public void createImportantPeople_rotfront(){
        ImportantPeopleAPI importantPeople_rotfront = Global.getSector().getImportantPeople();
        MarketAPI market_rotfront = Global.getSector().getEconomy().getMarket("rotfront_market");
        if(market_rotfront != null){
            //Rotfront Defense Fleet Admiral
            PersonAPI rotfront_fleetAdmiral = Global.getFactory().createPerson();
            rotfront_fleetAdmiral.setId(Eusan_Nation_PeopleStrings.ROTFRONT_FLEETADMIRAL);
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
            importantPeople_rotfront.addPerson(rotfront_fleetAdmiral);

            //Alyssa
            PersonAPI rotfront_alyssa = Global.getFactory().createPerson();
            rotfront_alyssa.setId(Eusan_Nation_PeopleStrings.ALYSSA);
            rotfront_alyssa.setFaction(EUSAN_NATION);
            rotfront_alyssa.setGender(Gender.FEMALE);
            rotfront_alyssa.getName().setFirst(Eusan_Nation_PeopleStrings.ALYSSA_FIRST_NAME);
            rotfront_alyssa.getName().setLast(Eusan_Nation_PeopleStrings.ALYSSA_LAST_NAME);
            rotfront_alyssa.setGender(Gender.FEMALE);
            rotfront_alyssa.setPortraitSprite("graphics/portraits/characters/eusan_nation_alyssa_128.png");
            rotfront_alyssa.setRankId("replika");
            rotfront_alyssa.setPostId("bartender");
            rotfront_alyssa.setVoice(Voices.BUSINESS);
            importantPeople_rotfront.addPerson(rotfront_alyssa);
        }
    }

    public void createImportantPeople_leng(){
        ImportantPeopleAPI importantPeople_leng = Global.getSector().getImportantPeople();
        MarketAPI market_leng = Global.getSector().getEconomy().getMarket("leng_market");
        if(market_leng != null) {
            //the very same Aldr
            PersonAPI leng_aldr = Global.getFactory().createPerson();
            leng_aldr.setId(Eusan_Nation_PeopleStrings.ALDR);
            leng_aldr.setAICoreId("beta_core");
            leng_aldr.setFaction(EUSAN_NATION);
            leng_aldr.getName().setFirst(Eusan_Nation_PeopleStrings.ALDR_FIRSTNAME);
            leng_aldr.getName().setLast(Eusan_Nation_PeopleStrings.ALDR_LASTNAME);
            leng_aldr.setGender(Gender.MALE);
            leng_aldr.setRankId("replika");
            leng_aldr.setPostId("aldr");
            leng_aldr.setPortraitSprite("graphics/portraits/characters/eusan_nation_aldr_128.png");
            leng_aldr.setVoice(Voices.OFFICIAL);
            leng_aldr.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 2);
            leng_aldr.isAICore();
            market_leng.setAdmin(leng_aldr);
            market_leng.getCommDirectory().addPerson(leng_aldr);
            market_leng.addPerson(leng_aldr);
            importantPeople_leng.addPerson(leng_aldr);
        }
    }

    public void createImportantPeople_miscOfficers(){
        ImportantPeopleAPI importantPeople_miscOfficers = Global.getSector().getImportantPeople();
        PersonAPI mary_cheng = Global.getFactory().createPerson();
        mary_cheng.setId(Eusan_Nation_PeopleStrings.MARY_CHENG);
        mary_cheng.setFaction(EUSAN_NATION);
        mary_cheng.getName().setFirst(Eusan_Nation_PeopleStrings.MARY_CHENG_FIRST_NAME);
        mary_cheng.getName().setLast(Eusan_Nation_PeopleStrings.MARY_CHENG_LAST_NAME);
        mary_cheng.setGender(Gender.FEMALE);
        mary_cheng.setRankId("gestaltOfficerLt");
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
        importantPeople_miscOfficers.addPerson(mary_cheng);
    }

    public void createImportantPeople_miscPeople(){
        ImportantPeopleAPI importantPeople_miscPeople = Global.getSector().getImportantPeople();
        PersonAPI hvt = Global.getFactory().createPerson();
        hvt.setId("op1_hvt");
        hvt.setFaction(Factions.INDEPENDENT);
        hvt.setGender(FullName.Gender.MALE);
        hvt.setRankId("unknown");
        hvt.setPostId("unknown");
        hvt.setImportance(PersonImportance.HIGH);
        hvt.setVoice(Voices.SOLDIER);
        importantPeople_miscPeople.addPerson(hvt);
    }
    
}

