package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.Balthazar;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.tools.BodyEditorLoader;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 * @author = Keeyzar on 01.04.2016
 */
public class Schrott2Improved extends Actor {
    private static Sound shootSound;
    private static Sound hitSound;

    Actor actor;
    private Balthazar balthazar;
    private final World world;
    private final Player player;
    private FinishListener listener;
    Body body;
    Box2DSprite sprite;
    Vector2 moveTo;
    private ERQUserDataEntities userData;
    ParticleEffect particleEffect;
    boolean hit = false;


    public Schrott2Improved(Balthazar balthazar, World world, Player player, FinishListener listener) {
        this.balthazar = balthazar;
        this.world = world;
        this.player = player;
        this.listener = listener;
        initBodyViaLoader(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY, ERQAssets.BODY_LOADER, "planet_1",
                balthazar.getX() + balthazar.getWidth() / 2, balthazar.getY() + balthazar.getHeight() / 2, 2);
        setBounds(balthazar.getX(), balthazar.getY(), Box2DUtils.width(body), Box2DUtils.height(body));
        moveTo = new Vector2();
        init();
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.EXPLOSION_DEBRIS, ParticleEffect.class));
    }

    private void init() {
        actor = new Actor();
        actor.setPosition(balthazar.getX(), balthazar.getY());
        actor.setSize(0.2f, 0.2f);
        actor.addAction(Actions.sequence(Actions.sizeTo(getWidth(), getHeight(), 0.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                })));
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANETS, 1));
        sprite.setAdjustSize(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actor.act(delta);
        sprite.setSize(actor.getWidth(), actor.getHeight());
        if(userData.isCollisionWithPlayer()){
            if (!hit) {
                hit = true;
                particleEffect.start();
                playSound();
                if (!player.getPlayerCurrentStates().hasShield()) {
                    body.setLinearVelocity(0, 0);
                    player.getPlayerCalculatedVars().addLife(-1);
                    player.getPlayerCollision().createShield(1f);
                }
            }
        }
        if(hit){
            if(particleEffect.isComplete()) {
                finish();
            }
        }
        particleEffect.setPosition(body.getPosition().x, body.getPosition().y);
        particleEffect.update(delta);
        if(body.getPosition().y <= 0){
            finish();
        }
    }

    private static void playSound() {
        if(hitSound == null){
            hitSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_EXPLOSION);
        }
        hitSound.play(MusicHandler.getSoundVolume());
    }

    private void finish() {
        listener.finished();
        remove();
        world.destroyBody(body);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!hit) {
            sprite.setAlpha(parentAlpha);
            sprite.draw(batch, body);
        } else {
            particleEffect.draw(batch);
        }
    }


    private void start() {
        moveToPlayer();
    }


    private void moveToPlayer() {
        moveTo.set(player.body.getPosition());
        moveTo.sub(body.getPosition());
        moveTo.scl(1.3f);
        body.setLinearVelocity(moveTo);
        body.setAngularVelocity(6);
    }


    public void initBodyViaLoader(World world, short category, short mask, BodyEditorLoader loader, String bodyName, float x, float y, float scale) {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = category;
        fd.filter.maskBits = mask;

        body = world.createBody(bd);
        body.setAwake(false);
        loader.attachFixture(body, bodyName, fd, scale);
        userData = new ERQUserDataEntities(this, EntityVars.SCHROTT_2);
        for(int i = 0; i<body.getFixtureList().size; i++){
            body.getFixtureList().get(i).setUserData(userData);
        }
    }
}
