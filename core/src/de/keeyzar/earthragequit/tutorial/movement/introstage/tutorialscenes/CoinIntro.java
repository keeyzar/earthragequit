package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra.BossProgress;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;
import de.keeyzar.earthragequit.tutorial.movement.introstage.TutorialStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TutArrow;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 16.03.2016
 */
public class CoinIntro {
    private World world;
    private TalkingStage tS;
    private final TutorialStage tutStage;
    private final Player player;
    private final Camera camera;
    private BossProgress bossProgress;
    private int currCoins = 0;
    private int maxCoins = 5;

    public CoinIntro(World world, TalkingStage tS, TutorialStage tutStage, Player player, Camera camera){
        this.world = world;
        this.tS = tS;
        this.tutStage = tutStage;
        this.player = player;
        this.camera = camera;
        bossProgress = new BossProgress() {
            @Override
            public float getCurrentProgress() {
                return currCoins;
            }

            @Override
            public float getMaxProgress() {
                return maxCoins;
            }

            @Override
            public Image getImage() {
                return new Image(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_FINISH_LINE));
            }
        };
    }


    private int state = 4;
    private final int S_WAIT = 2;
    private final int COIN_1 = 4;
    private final int COIN_2 = 5;
    private final int COIN_3 = 6;
    private final int COIN_4 = 7;
    private final int COIN_5 = 8;
    private final int COIN_6 = 9;
    private final int STATE_START_GAME = 15;

    private int nextState = -1;

    private final int STATE_COIN_FINISHED = -10;
    public void act() {
        switch (state){
            case S_WAIT:
                if(Gdx.input.isTouched()){
                    tS.endText();
                    state = nextState;
                }
                break;
            case COIN_1:
                spawnCoin(11, 3, false);
                break;
            case COIN_2:
                spawnCoin(1, 3,false);
                break;
            case COIN_3:
                spawnCoin(19, 12,false);
                break;
            case COIN_4:
                spawnCoin(1, 12,false);
                break;
            case COIN_5:
                spawnCoin(10, 5,false);
                break;
            case COIN_6:
                spawnCoin(3, 6, true);
                break;
            case STATE_COIN_FINISHED:
                tS.startText(LANG.format("tutorial_story_text_4"), new TWListener(){
                    @Override
                    public void TShown() {
                        state = S_WAIT;
                        nextState = STATE_START_GAME;
                    }
                });
                state = -1;
                break;
            case STATE_START_GAME:
                tutStage.nextPart(false);
                break;
        }
    }

    private void spawnCoin(int x, int y, boolean isFinal) {
        int dir;
        if(x < GVars.FOV_WIDTH / 2 && y < GVars.FOV_HEIGHT / 2){
            dir = TutArrow.SOUTHWEST;
        } else if( x > GVars.FOV_WIDTH / 2 && y < GVars.FOV_HEIGHT / 2){
            dir = TutArrow.SOUTHEAST;
        } else if (x > GVars.FOV_WIDTH / 2 && y > GVars.FOV_HEIGHT / 2){
            dir = TutArrow.NORTHEAST;
        } else {
            dir = TutArrow.NORTHWEST;
        }

        int xTrans = tCx(x);
        int yTrans = tCy(y);
        final int[] xi = {state};

        state = -1;
        if(isFinal){
            state = STATE_COIN_FINISHED;
        } else {
            tS.startTutArrow(dir, xTrans, yTrans, 30);

            spawnCoin(x, y, new TWListener(){
                @Override
                public void TShown() {
                    tS.stopTutArrow();
                    state = ++xi[0];
                    currCoins ++;
                }
            });
        }
    }

    private void spawnCoin(int x, int y, TWListener twListener){
        TutorialCoin coin = new TutorialCoin(world, 1, x + camera.position.x - camera.viewportWidth / 2, y + camera.position.y - camera.viewportHeight / 2, twListener, tutStage.getPlayer());
        tutStage.addActor(coin);
    }

    private int tCx(int x){
        return (int) ((x - TutorialCoin._WIDTH / 2) * (tS.getCamera().viewportWidth / tutStage.getViewport().getCamera().viewportWidth));
    }
    private int tCy(int y){
        return (int) ((y - TutorialCoin._HEIGHT / 2) * (tS.getCamera().viewportHeight / tutStage.getViewport().getCamera().viewportHeight));
    }

    public BossProgress getBossProgress() {
        return bossProgress;
    }
}
