package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 13.03.2016
 */
public class PlayerDrawings {
    private final Player player;

    private final Group rocketGroup;

    //PlayerSkin
    private Image playerShipImage;
    private Sprite playerShipSprite;
    private ERQGame game;

    private RocketShield shieldImage;

    private RocketParticleFire rocketParticleFire;
    private RocketParticleEffect rocketParticleEffect;



    public PlayerDrawings(Player player, ERQGame game){
        this.player = player;
        this.game = game;

        //fire
        rocketParticleFire = new RocketParticleFire(player);
        rocketParticleEffect = new RocketParticleEffect(player);

        //skin
        playerShipSprite = new Sprite(game.getSkinVerwalter().getSkinImageFromActualSkin().getSprite());
        playerShipImage = new Image(playerShipSprite.getTexture());

        TextureRegion textureRegion = new TextureRegion(playerShipSprite.getTexture());
        textureRegion.setRegion(playerShipSprite.getU(), playerShipSprite.getV(), playerShipSprite.getU2(), playerShipSprite.getV2());
        playerShipImage.setDrawable(new TextureRegionDrawable(textureRegion));
        playerShipImage.setSize(Player.WIDTH, player.HEIGHT);

        //shield
        shieldImage = new RocketShield();


        rocketGroup = new Group();
        rocketGroup.setSize(Player.WIDTH, Player.HEIGHT);
        rocketGroup.setOrigin(rocketGroup.getWidth() / 2, rocketGroup.getHeight() / 2);
        rocketGroup.addActor(rocketParticleFire);
        rocketGroup.addActor(playerShipImage);
        rocketGroup.addActor(shieldImage);

        act();
    }


    boolean shouldScaleBack = false;
    boolean scaled = false;
    public void act(){
        rocketGroup.act(Gdx.graphics.getDeltaTime());
        rocketGroup.setPosition(player.position.x - Player.WIDTH / 2, player.position.y - Player.HEIGHT / 2);
        rocketGroup.setRotation(player.angle * MathUtils.radiansToDegrees);
        rocketParticleFire.act(Gdx.graphics.getDeltaTime());
        rocketParticleEffect.act(Gdx.graphics.getDeltaTime(), rocketParticleFire.getStageCoords());
        if(player.getPlayerCurrentStates().hasMegaboost() && !scaled){
            rocketParticleFire.scaleBy(1.5f);
            shouldScaleBack = true;
            scaled = true;
        } else if(!player.getPlayerCurrentStates().hasMegaboost() && shouldScaleBack) {
            shouldScaleBack = false;
            scaled = false;
            rocketParticleFire.scaleBy(0.5f);
        }
    }


    boolean hasFireSprite = false;
    public void draw(Batch batch, float parentAlpha){
        hasFireSprite = false;
        if(player.getPlayerCalculatedVars().getLife() > 0){
            if(player.getPlayerMovement().isMoveUp() && player.getPlayerCalculatedVars().getU_speed_fuel() > 0){
                hasFireSprite = true;
            }
        }

        if(hasFireSprite){
            rocketParticleFire.enable(true);
            rocketParticleEffect.enableEmitting(true);
        } else {
            rocketParticleFire.enable(false);
            rocketParticleEffect.enableEmitting(false);
        }
        if(player.getPlayerCalculatedVars().getLife() > 0) {
            if (player.getPlayerCurrentStates().hasShield()) {
                shieldImage.enable(true);
            }
        }

        //draws: rocketSprite, fire Effect and Shield
        rocketGroup.draw(batch, parentAlpha);

        //can't be in rocketGroup, because position needs to be global!
        rocketParticleEffect.draw(batch, parentAlpha);

    }

    public Sprite getCurrentPlayerSprite() {
        return playerShipSprite;
    }

    /**
     * uses any shipSkinImage, which is declared as "best"
     */
    public void useBestImage() {
        Sprite sprite = new Sprite(game.getSkinVerwalter().getBestShipSkin().getSprite());

        TextureRegion textureRegion = new TextureRegion(sprite.getTexture());
        textureRegion.setRegion(sprite.getU(), sprite.getV(), sprite.getU2(), sprite.getV2());
        playerShipImage.setDrawable(new TextureRegionDrawable(textureRegion));
        playerShipImage.setSize(Player.WIDTH, Player.HEIGHT);
    }

    public void hit(){
        playerShipImage.addAction(Actions.sequence(Actions.repeat(5, Actions.sequence(Actions.color(Color.BLACK, 0.05f), Actions.color(Color.RED, 0.05f))),
                Actions.color(Color.WHITE)));
    }
}


