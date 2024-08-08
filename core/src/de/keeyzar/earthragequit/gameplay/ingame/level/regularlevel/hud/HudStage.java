package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra.BossProgress;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.*;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.RadarHud.Radar_hud;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress.*;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons.Skill_1;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons.Skill_2;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons.Skill_3;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons.Skill_4;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * This class represents the HUD;
 * @author = Keeyzar on 28.02.2016
 */
public class HudStage extends Stage {
    private final ERQGame game;
    private final Player player;
    private PauseListener pauseListener;
    private OrthographicCamera orthographicCamera;
    private Array<HudActor> hudActors;
    private boolean lifeEnabled = true;
    private boolean fuelEnabled = true;
    private boolean progressBarEnabled = true;
    private boolean enemyLifeEnabled = false;
    private boolean bossLevelProgressbarEnabled = false;
    private boolean tutorial = false;
    private BossEnemy bossEnemy;
    private BossProgress bossProgress;
    private Radar_hud radar_hud;
    private boolean radarAllowed = true;
    private TouchpadArea touchPadArea;
    private Label infoBeforeLevelLabel;
    private Pause pauseHudActor;
    private Label textLabel;

    /**
     *
     * Instructions:
     * 1. create
     * 2. set which Hudactors should be visible
     * 3. createSteuerung
     * @param player the hud must do all kinds of stuff with the player
     */
    public HudStage(ERQGame game, Player player, PauseListener pauseListener) {
        this.game = game;
        this.player = player;
        this.pauseListener = pauseListener;
        init();
    }

    private void init() {
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);
        setViewport(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, orthographicCamera));
    }

    /**
     * should create the HUD (skills, engine power, steering wheel
     * pause button, life, power fuel)
     */
    public void createSteuerung() {
        addActor(new BloodyHud(player));
        touchPadArea = new TouchpadArea(player);
        addActor(touchPadArea);

        hudActors = new Array<HudActor>();
        textLabel = new Label("", ERQAssets.SKIN);
        if(!tutorial) {
            if (fuelEnabled) hudActors.add(new FuelBar(player));
            if (lifeEnabled && player.getPlayerCalculatedVars().getLifeMax() > 1) hudActors.add(new LifeBar(player));
            if (enemyLifeEnabled) hudActors.add(new EnemyLifeBar(bossEnemy));
            if (bossLevelProgressbarEnabled) hudActors.add(new BossProgressBar(bossProgress));
            pauseHudActor = new Pause(pauseListener);
            hudActors.add(pauseHudActor);
            Skill_1 skill_1 = new Skill_1(player, game);
            Skill_2 skill_2 = new Skill_2(player, game);
            Skill_3 skill_3 = new Skill_3(player, game);
            Skill_4 skill_4 = new Skill_4(player, game);
            if(skill_1.isShouldShow()){
                hudActors.add(skill_1);
            }
            if(skill_2.isShouldShow()){
                hudActors.add(skill_2);
            }
            if(skill_3.isShouldShow()){
                hudActors.add(skill_3);
            }
            if(skill_4.isShouldShow()){
                hudActors.add(skill_4);
            }

            if(radarAllowed && player.getPlayerIngameSkills().getActiveIngameSkills(SkillsVars.RADAR) != null){
                radar_hud = new Radar_hud(player);
                hudActors.add(radar_hud);
            }
        }
        if (progressBarEnabled) hudActors.add(new RegularProgressBar(player));
        if (bossLevelProgressbarEnabled) hudActors.add(new BossProgressBar(bossProgress));

        for(HudActor hudActor : hudActors){
            addActor(hudActor);
        }
    }


    /**
     * after the adjustment had finished, they must be placed at the stage again
     */
    public void adjustmentFinished(){
        for(HudActor hudActor : hudActors){
            addActor(hudActor);
        }
    }

    /**
     * used for performance check
     * @param world
     */
    public void setWorld(final World world){
        textLabel = new Label("", ERQAssets.SKIN){
            final String l = "BodyCount: ";
            final String f = "fps: ";
            @Override
            public void act(float delta) {
                setText(l + world.getBodyCount() + "\n" +
                        f + Gdx.graphics.getFramesPerSecond());
            }
        };
        textLabel.setPosition(600, 400);
        addActor(textLabel);
    }

    public void createInfoB4Level(String textToDisplay){
        infoBeforeLevelLabel = new Label(textToDisplay, ERQAssets.SKIN);
        final Label.LabelStyle style = new Label.LabelStyle(infoBeforeLevelLabel.getStyle());
        style.font = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_SBOLD_45, BitmapFont.class);
        infoBeforeLevelLabel.setStyle(style);
        infoBeforeLevelLabel.setPosition(HudVars.HUD_WIDTH / 2 - infoBeforeLevelLabel.getPrefWidth() / 2, HudVars.HUD_HEIGHT / 2 + 100);
        infoBeforeLevelLabel.addAction(Actions.sequence(Actions.color(Color.RED.cpy(), 0.3f), Actions.color(Color.WHITE.cpy(), 0.5f), Actions.fadeOut(0.5f)));
    }

    public void showInfoInLevel(String whichText){
        if(!infoBeforeLevelLabel.hasActions() || !infoBeforeLevelLabel.getText().equals(whichText)) {
            infoBeforeLevelLabel.setText(whichText);
            infoBeforeLevelLabel.setPosition(HudVars.HUD_WIDTH / 2 - infoBeforeLevelLabel.getPrefWidth() / 2, HudVars.HUD_HEIGHT / 2 + 100);
            infoBeforeLevelLabel.addAction(Actions.sequence(Actions.alpha(1, 0.2f), Actions.color(Color.RED.cpy(), 1f), Actions.color(Color.WHITE.cpy(), 1f), Actions.fadeOut(0.4f)));
        }
    }

    public void showInfoBeforeLevel(){
        addActor(infoBeforeLevelLabel);
    }

    /**
     * Here should be checked, if user is pressing some buttons, if yes
     * forward the action
     */
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw() {
        super.draw();
        if(radar_hud != null){
            radar_hud.radarDraw(getRoot().getColor().a);
        }
    }

    /**
     * adjustmenStage needs all the Actors, so they can be added to the adjustStage
     */
    public Array<HudActor> getHudActors() {
        return hudActors;
    }

    public void setEnemyLifeEnabled(BossEnemy bossEnemy) {
        if(bossEnemy == null){
            System.err.println("BossEnemy CANNOT be NULL");
        }
        this.enemyLifeEnabled = true;
        this.bossEnemy = bossEnemy;
    }


    public void setEnemyProgressBarEnabled(BossProgress bossProgress){
        if(bossProgress == null){
            System.err.println("BossProgress CANNOT be NULL");
        }
        this.bossLevelProgressbarEnabled = true;
        this.bossProgress = bossProgress;
    }

    public void setRadarAllow(boolean allowed){
        radarAllowed = allowed;
    }

    public void enable(boolean lifeEnabled, boolean fuelEnabled, boolean progressBarEnabled){
        this.lifeEnabled = lifeEnabled;
        this.fuelEnabled = fuelEnabled;
        this.progressBarEnabled = progressBarEnabled;
    }

    public void setTutorial(boolean tutorial){
        this.tutorial = tutorial;
    }

    public HudActor getHudActor(String item){
        for(HudActor hudActor : hudActors){
            if(hudActor.getName().equals(item)){
                return hudActor;
            }
        }
        return null;
    }

    public void highlightTouchpad(boolean enable){
        touchPadArea.enableHighlight(enable);
    }

    @Override
    public void dispose() {
        super.dispose();
        if(radar_hud != null){
            radar_hud.dispose();
        }
        pauseListener = null;
        hudActors = null;
    }
}
