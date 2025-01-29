package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import data.kaysaar.ui.KeypadPanel;
import org.lazywizard.lazylib.MathUtils;

import java.util.ArrayList;

public class KeyGenerator {

    protected void hometeKeyGenerator() {
        if(!Global.getSector().getMemory().contains(KeypadPanel.keyMemToKey)){
            ArrayList<Integer> ints = new ArrayList<>();
            int combinations = 5;

            for (int i = 0; i < combinations; i++) {
                while (true){
                    int cur = MathUtils.getRandomNumberInRange(1,9);
                    boolean found = false;
                    for (Integer anInt : ints) {
                        if(cur == anInt){
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        ints.add(cur);
                        break;
                    }
                }
            }
            Global.getSector().getMemory().set(KeypadPanel.keyMemToKey, ints);
        }
    }
}
