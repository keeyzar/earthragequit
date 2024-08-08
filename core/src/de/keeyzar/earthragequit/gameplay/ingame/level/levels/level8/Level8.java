package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level8;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level9Unlocker;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 *
 */
public class Level8 extends LevelDefining {
    private int state = 3;
    private final int WAIT = 2;
    private final int text = 3;

    private TalkingStage ts;

    @Override
    public void initLevel(ERQGame game) {
        super.initLevel(game);
        ts = new TalkingStage();
    }

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new Level8EntitieCreator().createEntities(world, stage, getWorldWidth(), getWorldHeight(), player, levelDescription.coinValue);
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
        unlockeds.add(new Level9Unlocker(game));
        return unlockeds;
    }


    @Override
    public TalkingStage getTalkingStage() {
        return ts;
    }

    @Override
    public void talkingAct(float delta) {
        switch (state){
            case WAIT:
                ts.endText();
                break;
            case text:
                ts.startText(LANG.format("8_level_quest"), new TWListener(){
                    @Override
                    public void TShown() {
                        state = WAIT;
                    }

                    @Override
                    public void TFadeOutFinish() {
                        state = -1;
                    }
                });
                state = -1;
                break;
        }
    }

    @Override
    public boolean isStartingBoostDisabled() {
        return true;
    }


    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription();
        levelDescription.width = 100;
        levelDescription.height = 350;
        levelDescription.level = 8;
        levelDescription.coinValue = 25;
        levelDescription.levelTmxPath = LevelVars.LEVEL_GREEN;
        levelDescription.levelName = LANG.format("8_level_title");
        return levelDescription;
    }
}
