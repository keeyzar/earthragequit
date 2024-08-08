package de.keeyzar.earthragequit.tutorial.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.tutorial.TutorialVerwalter;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TextWriter;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 23.03.2016
 */
public class SkillsTutorial implements UpgradeMainDialog.IntroductionHook {
    private final ERQGame game;
    private TalkingStage talkingStage;

    private int state = 3;
    private final int S_WAIT = 1;
    private final int S_WAIT_NEXT_TEXT = 2;
    private final int S_TEXT_1 = 3;
    private final int S_WAIT_FOR_OPEN = 4;
    private final int S_TEXT_2 = 5;
    private final int S_WAIT_FOR_CLOSE = 6;
    private final int S_TEXT_3 = 8;
    private final int S_FINISH = 9;
    private int nextState = -1;
    private boolean closed = false;
    private boolean opened = false;
    private UpgradeMainDialog upgradeMainDialog;

    public SkillsTutorial(ERQGame game) {
        this.game = game;
        talkingStage = new TalkingStage();

        TextWriter tw = talkingStage.getTW();
        tw.setSize(ScreenVariables.SCREEN_WIDTH - 100, ScreenVariables.SCREEN_HEIGHT - 100);
        tw.setPosition(50, 50);

        for(PlayerSkills playerSkills : game.getSkillsVerwalter().getPlayerSkillsArray()){
            if(playerSkills.isLocked())continue;
            if(game.getCoins() < playerSkills.getCostsForNextLevel()){
                game.addCoins(playerSkills.getCostsForNextLevel());
            }
        }
    }

    public void renderBeforeRegularStage() {
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            state = S_FINISH;
        }

        switch (state){
            case S_WAIT:
                if(Gdx.input.isTouched()){
                    talkingStage.endText();
                    state = nextState;
                }
                break;
            case S_WAIT_NEXT_TEXT:
                if(Gdx.input.isTouched()){
                    state = nextState;
                }
                break;
            case S_TEXT_1:
                firstText();
                break;
            case S_WAIT_FOR_OPEN:
                waitForOpen();
                break;
            case S_TEXT_2:
                secondText();
                break;
            case S_WAIT_FOR_CLOSE:
                waitForClose();
                break;
            case S_TEXT_3:
                text_3();
                break;
            case S_FINISH:
                finish();
                break;
        }
    }



    @Override
    public void renderAfterRegularStage() {
        talkingStage.act(Gdx.graphics.getDeltaTime());
        talkingStage.draw();
    }



    public void finish(){
        state = -1;
        TutorialVerwalter tutorialVerwalter = game.getTutorialVerwalter();
        tutorialVerwalter.setLevelAbsolved(TVars.SKILLS_INTRODUCTION, true);

        game.save();
        upgradeMainDialog.finish();
    }

    private void text_3() {
        Gdx.input.setInputProcessor(null);
        talkingStage.startText(LANG.format("upgrade_skills_tutorial_text_3"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT;
                nextState = -1;
            }

            @Override
            public void TFadeOutFinish() {
                state = S_FINISH;
            }
        });
        state = -1;
    }

    private void waitForClose(){
        if(closed){
            state = S_TEXT_3;
        }
    }

    private void secondText() {
        Gdx.input.setInputProcessor(null);
        talkingStage.startText(LANG.format("upgrade_skills_tutorial_text_2"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT;
                nextState = S_WAIT_FOR_CLOSE;
                upgradeMainDialog.getSkillsMenu().startHighlightProcedure();
            }

            @Override
            public void TFadeOutFinish() {
                Gdx.input.setInputProcessor(upgradeMainDialog.getStage());
            }
        });
        state = -1;
    }

    private void waitForOpen() {
        if(opened){
            state = S_TEXT_2;
            upgradeMainDialog.getSkillsMenu().getFirstSkillButton().enableHighlight(false);
        }
    }

    private void firstText() {
        Gdx.input.setInputProcessor(null);
        talkingStage.startText(LANG.format("upgrade_skills_tutorial_text_1"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT;
                nextState = S_WAIT_FOR_OPEN;
                upgradeMainDialog.getSkillsMenu().getFirstSkillButton().enableHighlight(true);
            }

            @Override
            public void TFadeOutFinish() {
                Gdx.input.setInputProcessor(upgradeMainDialog.getStage());
            }

        });
        state = -1;
    }

    @Override
    public void resize(int width, int height) {
        talkingStage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        talkingStage.dispose();
    }

    @Override
    public int getWhichSite() {
        return UpgradeMainDialog.SITE_SKILLS;
    }

    @Override
    public void setDialog(UpgradeMainDialog upgradeMainDialog) {
        this.upgradeMainDialog = upgradeMainDialog;
    }

    public void opened(){
        opened = true;
    }

    public void closed(){
        closed = true;
    }

}
