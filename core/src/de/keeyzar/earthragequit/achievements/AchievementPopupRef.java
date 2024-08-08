package de.keeyzar.earthragequit.achievements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import java.util.Stack;

/**
 * @author = Keeyzar on 24.03.2016
 */
public class AchievementPopupRef {
    private Stage stage;

    private boolean isActive = false;
    private boolean initialized = false;

    private Sound sound;

    private Group achievementGroup;
    private Label rewardLabel;
    private Label textLabel;
    private Stack<Achievement> stack;
    private ParticleEffect particleEffect;

    public AchievementPopupRef() {

        stage = new Stage(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT));
        stack = new Stack<Achievement>();

    }


    private void initialize() {
        initialized = true;
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_ACHIEVEMENT);
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.ACHIEVEMENT_PARTICLEEFFECT, ParticleEffect.class));
        Skin skin = ERQAssets.SKIN;

        //create actor
        achievementGroup = new Group();
        achievementGroup.getColor().a = 0;
        achievementGroup.setSize(500, 170);
        achievementGroup.setPosition(ScreenVariables.SCREEN_WIDTH / 2 - achievementGroup.getWidth() / 2,
                ScreenVariables.SCREEN_HEIGHT - achievementGroup.getHeight() * 1.5f);

        rewardLabel = new Label("", skin);
        rewardLabel.setWrap(true);
        rewardLabel.setAlignment(Align.center, Align.center);
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.center, Align.center);

        //add all to table
        Table table = new SparklingTable(skin);
        Table table2 = new Table(skin);
        table.setBackground(new ScrollPane(null, skin).getStyle().background);
        table2.setBackground(new ScrollPane(null, skin, "def").getStyle().background);

        table2.add(rewardLabel).size(190, 100).spaceLeft(ScreenVariables.PAD_LR_N).spaceRight(ScreenVariables.PAD_LR_N);
        table.add(table2).pad(ScreenVariables.PAD_LR_N).size(200, 110);
        table.add(textLabel).pad(ScreenVariables.PAD_LR_N).size(200, 110);

        //add table to group and group to stage
        table.setFillParent(true);
        achievementGroup.addActor(table);
        stage.addActor(achievementGroup);
    }

    public void actAndDraw(float delta){
        if(isActive) {
            stage.act(delta);
            stage.getBatch().begin();
            particleEffect.draw(stage.getBatch(), delta);
            stage.getBatch().end();
            stage.draw();
        }
    }

    /**
     * Starts the procedure to draw the new achievement popup
     * if an achievement is shown at the moment, it'll displayed after that.
     */
    public void newAchievement(Achievement achievement){
        if(!initialized) initialize();
        stack.push(achievement);
        if(!isActive) pushStack();
    }


    private void addAction() {
        achievementGroup.addAction(Actions.sequence(Actions.alpha(0.8f, 0.3f), Actions.repeat(2,
                Actions.delay(1)),
                Actions.fadeOut(0.2f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        isActive = false;
                        achievementGroup.getActions().clear();
                        pushStack();
                    }
                })));
    }

    private void pushStack() {
        if(stack.isEmpty()) return;
        particleEffect.reset();
        particleEffect.setPosition(achievementGroup.getX() + achievementGroup.getWidth() / 2 - 100, achievementGroup.getY() + achievementGroup.getHeight() / 2 - 50);
        particleEffect.start();
        final Achievement achievement = stack.pop();
        rewardLabel.setText(achievement.getShortRewardDesc());
        textLabel.setText(achievement.getTitle());
        isActive = true;
        sound.play(MusicHandler.getSoundVolume());
        addAction();

    }

    public void dispose(){
        stage.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
