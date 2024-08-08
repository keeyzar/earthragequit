package de.keeyzar.earthragequit.tutorial.upgrades;

import com.badlogic.gdx.Gdx;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.StatsVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.tutorial.TutorialVerwalter;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TextWriter;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * This one introduces the normal UpgradeStuff(Rocketstuff, no Skins)
 * @author = Keeyzar on 20.03.2016
 */
public class UpgradeTutorial implements UpgradeMainDialog.IntroductionHook {
    private final ERQGame game;
    private TalkingStage talkingStage;


    private int state = 3;
    private final int S_WAIT = 1;
    private final int S_WAIT_NEXT_TEXT = 2;
    private final int S_TEXT_1 = 3;
    private final int S_TEXT_2 = 4;
    private final int S_UPGRADE = 5;
    private final int S_TEXT_3 = 6;
    private final int S_FINISH = 7;
    private int nextState = -1;
    private UpgradeMainDialog upgradeMainDialog;

    public UpgradeTutorial(ERQGame game) {
        this.game = game;
        talkingStage = new TalkingStage();

        TextWriter tw = talkingStage.getTW();
        tw.setSize(ScreenVariables.SCREEN_WIDTH - 100, ScreenVariables.SCREEN_HEIGHT - 100);
        tw.setPosition(50, 50);
        //is to save the player (if a bug happens, so he can finish his quest)
        if(game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL).getCurrentLevel() == 0) {
            if (game.getGlobalPlayerInformation().getCoins() < game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL).getCostsForNextLevel()) {
                game.getGlobalPlayerInformation().setCoins(game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL).getCostsForNextLevel());
            }
        } else {
            state = S_FINISH;
        }
    }

    public void renderBeforeRegularStage() {
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
            case S_TEXT_2:
                secondText();
                break;
            case S_UPGRADE:
                mustUpgrade();
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
        tutorialVerwalter.setLevelAbsolved(TVars.UPGRADE_INTRODUCTION, true);

        StatsVerwalter statsVerwalter = game.getStatsVerwalter();
        statsVerwalter.getStatByName(STAT_VARS.STAT_PLATING).setLocked(false);
        statsVerwalter.getStatByName(STAT_VARS.STAT_ROCKET_MAXIMUM_SPEED).setLocked(false);
        game.save();
        upgradeMainDialog.finish();
    }

    private void text_3() {
        Gdx.input.setInputProcessor(null);
        talkingStage.startText(LANG.format("upgrade_stats_tutorial_text_3"), new TWListener(){
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

    private void mustUpgrade() {
        if(game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL).getCurrentLevel() >= 1){
            state = S_TEXT_3;
            upgradeMainDialog.getStatsMenu().getUpgradeButton().enableHighlight(true);
        }
    }

    private void secondText() {
        Gdx.input.setInputProcessor(null);
        talkingStage.newText(LANG.format("upgrade_stats_tutorial_text_2"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT;
                nextState = S_UPGRADE;
                upgradeMainDialog.getStatsMenu().getUpgradeButton().enableHighlight(true);
            }

            @Override
            public void TFadeOutFinish() {
                Gdx.input.setInputProcessor(upgradeMainDialog.getStage());
            }
        });
        state = -1;
    }

    private void firstText() {
        Gdx.input.setInputProcessor(null);
        talkingStage.startText(LANG.format("upgrade_stats_tutorial_text_1"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_NEXT_TEXT;
                nextState = S_TEXT_2;
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
        return UpgradeMainDialog.SITE_STATS;
    }

    @Override
    public void setDialog(UpgradeMainDialog upgradeMainDialog) {
        this.upgradeMainDialog = upgradeMainDialog;
    }
}
