package data.scripts.plugins;

public class Eusan_Nation_CustomRewards {

    protected int numCaptials;
    protected int numCruisers;
    protected int numDestroyers;
    protected int numFrigates;

    public Eusan_Nation_CustomRewards(int capitals, int cruisers, int destroyers, int frigates){
        numCaptials = capitals;
        numCruisers = cruisers;
        numDestroyers = destroyers;
        numFrigates = frigates;
    }
    public int rewardScale(){
        int reward = 50000;

        for(int i = 0; i < numCaptials; i++){
            reward = reward + 650000;
        }
        for(int i = 0; i < numCruisers; i++){
            reward = reward + 55000;
        }
        for(int i = 0; i < numDestroyers; i++){
            reward = reward + 45000;
        }
        for(int i = 0; i < numFrigates; i++){
            reward = reward + 35000;
        }
        return reward;
    }
}
