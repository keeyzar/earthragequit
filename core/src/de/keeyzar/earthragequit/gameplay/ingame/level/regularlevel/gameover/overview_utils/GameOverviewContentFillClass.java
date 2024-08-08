package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.ImageTextButtonModified;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.PlayerStatistic;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.SingleButtonDialog;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.PAD_TB_N;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.PAD_TB_S;

public class GameOverviewContentFillClass {
    private Array<PlayerHasUnlocked> unlockedArray;
    private ERQGame game;
    private PlayerStatistic playerStatistic;
    private int counter;
    private Actor invisibleActor;
    private float progress = 0;
    private float fadeTime = 0.4f;
    private Listener listener;

    public GameOverviewContentFillClass(Array<PlayerHasUnlocked> unlockedArray, ERQGame game, PlayerStatistic playerStatistic){
        this.unlockedArray = unlockedArray;
        this.game = game;
        this.playerStatistic = playerStatistic;
    }

    public void fillContentTable(Table tableToFill, ScrollPane scrollPane, boolean wonLevel, Listener listener){
        this.listener = listener;
        boolean hasUnlocksToShow = false;
        if(wonLevel){
            hasUnlocksToShow = insertUnlocks(tableToFill);
        }

        if(hasUnlocksToShow) tableToFill.top();

        invisibleActor = new Actor(){
            @Override
            public void act(float delta) {
                super.act(delta);
                progress = getColor().a;
            }
        };
        invisibleActor.getColor().a = 0;
        invisibleActor.addAction(Actions.fadeIn(2, Interpolation.exp5));

        insertCoinsGathered(tableToFill, scrollPane);

    }

    private void insertCoinsGathered(final Table tableToFill, final ScrollPane scrollPane) {
        final int coinsIngame = playerStatistic.getCoinValueGatheredIngame() + playerStatistic.getCoinsThroughAchievement();


        final Image actor2 = new CoinAnimActor();
        final Label actor3 = new Label("", ERQAssets.SKIN){
            @Override
            public void act(float delta) {
                super.act(delta);
                setText("x    " + (int)MathUtils.lerp(0, game.getCoins(), progress) +
                " (+" + (int) MathUtils.lerp(0,  coinsIngame, progress)+")");
                scrollPane.setScrollingDisabled(true, false);
            }
        };

        tableToFill.add(actor2).size(100, 100).padTop(PAD_TB_S * 2).padRight(PAD_TB_N).expandX().right();
        tableToFill.add(actor3).left().expandX().padTop(PAD_TB_S * 2).width(200).row();

        actor2.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(fadeTime * counter), Actions.fadeIn(0.4f)));
        actor3.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(fadeTime * counter), Actions.fadeIn(0.4f), Actions.run(new Runnable() {
            @Override
            public void run() {
                startFinalCoinsAnim(tableToFill);
            }
        })));
    }

    private void startFinalCoinsAnim(Table tableToFill) {
        tableToFill.addActor(invisibleActor);
    }

    private boolean insertUnlocks(final Table tableToFill) {
        boolean hasUnlocksToShow = false;
        if(unlockedArray != null) {
            counter = 0;
            for (final PlayerHasUnlocked playerHasUnlocked : unlockedArray) {
                if(playerHasUnlocked.isUnlocked()) continue;
                playerHasUnlocked.unlock();
                if(!playerHasUnlocked.shouldBeDisplayed()) continue;
                hasUnlocksToShow = true;
                TextButton textButton = new ImageTextButtonModified(playerHasUnlocked.getUnlockTitel(), ERQAssets.SKIN, playerHasUnlocked.getImage());
                textButton.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(fadeTime * counter), Actions.fadeIn(fadeTime)));
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(playerHasUnlocked.getType() == 0) {
                            MenuUtils.playClickSound();
                            SingleButtonDialog.createDialog(tableToFill.getStage(), playerHasUnlocked.getUnlockText(), "Close"
                                    , 800, 300);
                        } else {
                            listener.clicked(playerHasUnlocked.getType());
                        }
                    }
                });
                tableToFill.add(textButton).height(100).growX().pad(PAD_TB_S).colspan(2).row();
                counter++;
            }
        }
        return hasUnlocksToShow;
    }

    public interface Listener{
        void clicked(int whatType);
    }
}