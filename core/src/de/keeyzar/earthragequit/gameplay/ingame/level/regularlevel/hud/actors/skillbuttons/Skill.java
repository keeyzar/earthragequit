package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.custom_ui.CircleProgressActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.SkillsVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;

/**
 * @author = Keeyzar on 30.03.2016
 */
public abstract class Skill extends HudActor {
    private final Player player;
    private ERQGame erqGame;
    private final int skillPlace;

    private CircleProgressActor actor;
    private Sprite sprite;
    private PlayerSkills playerSkills;
    private boolean activateAble = false;
    private boolean shouldShow = false;

    private Sprite activationSprite;
    private Actor activationActor;
    private int activationSize = 300;

    public Skill(String name, ERQGame erqGame, Player player, int skillPlace){
        super(name);
        this.player = player;
        this.erqGame = erqGame;
        this.skillPlace = skillPlace;
        init();
    }



    private void init() {
        SkillsVerwalter skillsVerwalter = erqGame.getSkillsVerwalter();
        playerSkills = skillsVerwalter.getAct_PlayerskillsMap().get(skillPlace);
        if(playerSkills == null){
            //remove from parent, if null, because than it is not a skill;
            remove();
            return;
        } else if(playerSkills.isPassive()) {
            remove();
            return;
        }

        sprite = new Sprite(skillsVerwalter.getSpriteDrawableFromActSkill(skillPlace).getSprite());


        actor = new CircleProgressActor(new CircleProgressActor.CircleProgressListener() {
            @Override
            public float getProgress() {
                return Math.max(0, playerSkills.getProgress(player));
            }
        });
        setBounds(getX(), getY(), getWidth(), getHeight());
        shouldShow = true;

        initActivationSprite();

    }

    private void initActivationSprite() {

        activationActor = new Actor();
        activationActor.getColor().a = 0f;
        activationActor.setSize(activationSize, activationSize);
        activationActor.setPosition(HudVars.HUD_WIDTH / 2 - activationActor.getWidth() / 2, HudVars.HUD_HEIGHT / 2 - activationActor.getHeight() / 2);

        activationSprite = new Sprite(sprite);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actor.act(delta);

        //normal sprite
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setColor(getColor());
        if(!activateAble) {
            check();
        }

        //activationAction
        activationActor.act(delta);
        if(activationActor.hasActions()) {
            activationSprite.setColor(activationActor.getColor());
            activationSprite.setSize(activationActor.getWidth(), activationActor.getHeight());
            activationSprite.setPosition(HudVars.HUD_WIDTH / 2 - activationActor.getWidth() / 2, HudVars.HUD_HEIGHT / 2 - activationActor.getHeight() / 2);
        }
    }

    protected void check(){
        if(playerSkills.getProgress(player) >= 1f){
            addAction(Actions.fadeIn(0.2f));
            activateAble = true;
            activationActor.setSize(activationSize, activationSize);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        actor.draw(batch, parentAlpha);
        sprite.draw(batch);
        if(activationActor.hasActions()){
            activationSprite.draw(batch, parentAlpha);
        }
    }

    @Override
    public void normalMode() {
        super.normalMode();
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(activateAble){
                    activateAble = false;
                    MenuUtils.playClickSound();
                    activateSkill();
                }
                return false;
            }
        });
    }

    @Override
    public void adjustModeListener(Stage stage) {
        super.adjustModeListener(stage);
    }

    private void activateSkill() {
        player.getPlayerIngameSkills().activateSkill(skillPlace);
        addAction(Actions.alpha(0.5f, 0.2f));
        activationActor.addAction(Actions.sequence(Actions.alpha(0.5f), Actions.parallel(Actions.alpha(0, 0.5f), Actions.sizeTo(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, 0.5f))));
    }


    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        if(actor != null)
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);

        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void sizeBy(float width, float height) {
        super.sizeBy(width, height);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        if(actor != null){
            actor.setBounds(getX() - getWidth() * 0.25f, getY() - getHeight() * 0.25f, getWidth() * 1.5f, getHeight() * 1.5f);
        }
    }

    public boolean isShouldShow() {
        return shouldShow;
    }

    @Override
    public boolean isFixedTransparency() {
        return true;
    }

}
