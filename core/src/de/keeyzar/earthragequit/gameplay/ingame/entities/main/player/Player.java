package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.DynamicActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing.PlayerDrawings;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Player extends DynamicActor {

    public static final float WIDTH = 1f;
    public static final float HEIGHT = 2f;
    public static float PLAYER_DENSITY = 0.5f; //dichte der rakete
    public static float PLAYER_ANGULAR_DAMPING = 0.1f;
    public static float PLAYER_FRICTION = 0.3f; //Reibung

    private final ERQGame game;
    private Camera camera;
    private WorldUtils worldUtils;
    private final World world;
    private final PlayerCalculatedVars playerCalculatedVars;
    private final PlayerMovement playerMovement;
    private final PlayerCollision playerCollision;
    private final PlayerStatistic playerStatistic;
    private final PlayerIngameSkills playerIngameSkills;
    private final de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing.PlayerDrawings playerDrawings;
    private final PlayerCurrentStates playerCurrentStates;

    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    private ERQUserDataPlayer userData;

    private HitListener hitListener;
    private HudStage hudStage;

    /**
     * das repräsentiert den Player
     * @param world the Box2dWorld
     * @param game the MotherGame
     */
    public Player(World world, ERQGame game, WorldUtils worldUtils) {
        this.world = world;
        this.game = game;
        this.camera = worldUtils.getCamera();
        this.worldUtils = worldUtils;
        setX(worldUtils.getWorldWidth() / 2);
        setY(Ground.GROUND_HEIGHT);
        position.x = getX();
        position.y = getY();
        playerCurrentStates = new PlayerCurrentStates(this);
        playerCalculatedVars = new PlayerCalculatedVars(this, game.getStatsVerwalter(), game.getSkillsVerwalter(), game.getGlobalPlayerInformation());
        playerMovement = new PlayerMovement(this);
        playerCollision = new PlayerCollision(this, game);
        playerIngameSkills = new PlayerIngameSkills(this, game, world);
        playerStatistic = new PlayerStatistic(this, game);
        playerDrawings = new de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing.PlayerDrawings(this, game);
        initBody();
    }

    @Override
    public void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / 2, HEIGHT / 2);
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, PLAYER_DENSITY);
        fixture.setFriction(PLAYER_FRICTION);
        fixture.setRestitution(0);
        body.setAngularDamping(0.4f);
        body.setLinearDamping(0.1f);
        body.setGravityScale(1.4f);
//        body.resetMassData();
        body.setTransform(body.getPosition(), 0);
        body.setBullet(true);
        shape.dispose();

        userData = new ERQUserDataPlayer(this);
        userData.setId(EntityVars.PLAYER);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_PLAYER;
        filterData.maskBits = EntityVars.MASK_PLAYER;
        fixture.setFilterData(filterData);
        playerIngameSkills.init();
    }

    /**
     * draws the ingame skills. (like a magnet, shield, or whatever you wish
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        playerDrawings.draw(batch, parentAlpha);
        playerMovement.stopMoveUp();
    }

    /**
     * This actAndDraw draws sets the actor at the current position of the world body
     * and calls other acts, like skills, statics, collision, movement acts.
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        setX(body.getPosition().x);
        setY(body.getPosition().y);
        if(playerCalculatedVars.getLife() > 0) {
            playerIngameSkills.act(delta, camera);
            playerCollision.act(delta);
            playerStatistic.act();
        }
        playerDrawings.act();
        playerMovement.act();
        playerCurrentStates.act(delta);

    }

    public void checkPlayerPosition(){
        playerMovement.checkIfPlayerIsInWorld();
    }

    public PlayerCalculatedVars getPlayerCalculatedVars() {
        return playerCalculatedVars;
    }

    public PlayerMovement getPlayerMovement() {
        return playerMovement;
    }

    public PlayerStatistic getPlayerStatistic() {
        return playerStatistic;
    }

    public void addCoin(int value) {
        game.getGlobalPlayerInformation().addCoins(value);
        playerStatistic.addCoin(value);
    }

    public PlayerIngameSkills getPlayerIngameSkills() {
        return playerIngameSkills;
    }


    public WorldUtils getRegularWorld() {
        return worldUtils;
    }

    public Sprite getCurrentSprite(){
        return playerDrawings.getCurrentPlayerSprite();
    }

    public PlayerCollision getPlayerCollision() {
        return playerCollision;
    }

    public PlayerCurrentStates getPlayerCurrentStates() {
        return playerCurrentStates;
    }
    public ERQUserDataPlayer getUserData(){
        return userData;
    }

    /**
     * calls on listener a hit, if any is registered
     */
    public void playerHit() {
        if(hitListener != null){
            hitListener.playerGotHit();
            playerDrawings.hit();
        }
    }

    public void setHitListener(HitListener hitListener) {
        this.hitListener = hitListener;
    }

    public void setHudStage(HudStage hudStage){
        this.hudStage = hudStage;
    }
    public void displayText(String whichText) {
        if(hudStage != null){
            hudStage.showInfoInLevel(whichText);
        }
    }

    public ERQGame getGame() {
        return game;
    }

    public void useBestImage() {
        playerDrawings.useBestImage();
    }

    public PlayerDrawings getPlayerDrawings() {
        return playerDrawings;
    }

    /**
     * Interface for a callback if needed, so a listener can get notified
     */
    public interface HitListener {
        /**
         * is called, when the has lost life.
         */
        public void playerGotHit();
    }

    public void dispose(){
        getPlayerIngameSkills().dispose();
        getPlayerMovement().dispose();
    }
}
