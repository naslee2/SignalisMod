package data.kaysaar.ui;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CustomUIPanelPlugin;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.apache.log4j.Logger;

public class KeypadPanel implements CustomUIPanelPlugin {
    InteractionDialogAPI dialog;
    CustomPanelAPI panel;
    CustomPanelAPI mainPanel;
    transient SpriteAPI background = Global.getSettings().getSprite("ui", "keypad_bg");
    public static float paddingX = 10;
    public static float paddingY = 50;
    public static float buttonSize = 40;
    ArrayList<ButtonAPI> patternButtons = new ArrayList<>();;
    boolean codeShowcaseMode = false;
    boolean guessedIt = false;
    ArrayList<Integer> ints = new ArrayList<>();
    public static String keyMemToKey = "$homete_unique_key";

    Logger logger = Global.getLogger(KeypadPanel.class);

    //width around 480 due to dialog limitations
    public KeypadPanel(InteractionDialogAPI dialog, float width, float height, boolean isItToShowCode) {
        this.dialog = dialog;
        panel = Global.getSettings().createCustom(width, height, this);
        createUIForFirstTime();
        ints = (ArrayList<Integer>) Global.getSector().getMemory().get(keyMemToKey);
        logger.info("Logger Active: UNORDERED PENROSE-512 CRYO KEYCODE IS: " + ints.toString());
        codeShowcaseMode = isItToShowCode;
    }

    public void createUIForFirstTime() {
        mainPanel = panel.createCustomPanel(panel.getPosition().getWidth(), panel.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = mainPanel.createUIElement(panel.getPosition().getWidth(), panel.getPosition().getHeight(), false);
        float currX = paddingX + 20;
        float currY = 65;
        for (int i = 1; i <= 9; i++) {
            ButtonAPI button = tooltip.addAreaCheckbox(null, i + "_code", Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Misc.getBrightPlayerColor(), buttonSize, buttonSize, 0f);
            button.getPosition().inTL(currX, currY);
            currX += paddingX + buttonSize;
            if (i % 3 == 0) {
                currX = paddingX + 20;
                currY += paddingY + buttonSize;
            }
            patternButtons.add(button);
        }
        ;
        mainPanel.addUIElement(tooltip).inTL(0, 0);
        panel.addComponent(mainPanel).inTL(dialog.getTextWidth() / 2 - panel.getPosition().getWidth() / 2, 0);
    }

    public CustomPanelAPI getPanel() {
        return panel;
    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {
        if (background != null && !codeShowcaseMode) {
            background.setSize(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight());
            background.setAlphaMult(alphaMult);
            background.renderAtCenter(mainPanel.getPosition().getCenterX(), mainPanel.getPosition().getCenterY());
        }

    }

    @Override
    public void render(float alphaMult) {

    }

    @Override
    public void advance(float amount) {
        if (guessedIt) {
            return;
        }
        if (codeShowcaseMode) {
            for (Integer anInt : ints) {
                for (ButtonAPI patternButton : patternButtons) {
                    String data = (String) patternButton.getCustomData();
                    int current = Integer.parseInt(data.split("_")[0]);
                    if (anInt == current) {
                        patternButton.setChecked(true);
                    }
                }

            }

        }
        boolean found = true;
        for (Integer anInt : ints) {
            found = false;
            for (ButtonAPI patternButton : patternButtons) {
                if (patternButton.isChecked()) {
                    String data = (String) patternButton.getCustomData();
                    int current = Integer.parseInt(data.split("_")[0]);
                    if (anInt == current) {
                        found = true;
                        break;
                    }
                }

            }
            if (!found) {
                break;
            }
        }
        // Now validation if ONLY those who are checked are combination we want
        for (ButtonAPI patternButton : patternButtons) {
            if (patternButton.isChecked()) {
                String data = (String) patternButton.getCustomData();
                int current = Integer.parseInt(data.split("_")[0]);
                if (!ints.contains(current)) {
                    found = false;
                    break;
                }
            }
        }
        if (found) {
            onConfirm();
        }
    }

    public void onConfirm() {
        for (ButtonAPI patternButton : patternButtons) {
            patternButton.setClickable(false);
            patternButton.setMouseOverSound(null);
        }
        guessedIt = true;
        patternButtons.clear();

        if (!codeShowcaseMode) {
            // Here you can add / disable /enable options after combination has been correctly hit
            // Also buttons are cleared so we won't cause memory leaks after exiting dialog or going to another option.
            dialog.getOptionPanel().setEnabled("officeryeongPenrose512_cryo_explore2a", true);
            // disable hints if the user gets the combination correct.
            dialog.getOptionPanel().setEnabled("officeryeongPenrose512_cryo_explore2a_hint", false);
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
