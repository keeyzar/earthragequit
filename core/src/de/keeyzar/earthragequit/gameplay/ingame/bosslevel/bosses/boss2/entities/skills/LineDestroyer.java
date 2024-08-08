package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static net.dermetfan.gdx.physics.box2d.Box2DUtils.height;

/**
 * @author = Keeyzar on 01.04.2016
 */
public class LineDestroyer extends Actor {
    private final World world;
    private final Player player;
    private OrthographicCamera camera;
    private FinishListener listener;
    Body body;
    ERQUserDataEntities userData;
    Box2DSprite sprite;
    boolean isActive = false;

    Sound charge;
    Sound beam;

    public LineDestroyer(World world, Player player, OrthographicCamera camera, FinishListener listener) {
        this.world = world;
        this.player = player;
        this.camera = camera;
        this.listener = listener;
        initBody(player.getX(), player.getY());

        charge = ERQAssets.MANAGER.get(AssetVariables.SOUND_LASERCHARGE);
        beam = ERQAssets.MANAGER.get(AssetVariables.SOUND_LASERBEAM);
    }

    private void initBody(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(camera.position.x, y);
        body = world.createBody(bodyDef);
        body.setAwake(false);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(camera.viewportWidth, 0.5f);
        Fixture fixture = body.createFixture(shape, 0);
        shape.dispose();

        userData = new ERQUserDataEntities(this, EntityVars.RING_DESTROYER);
        fixture.setUserData(userData);

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_ENTITY;
        filterData.maskBits = EntityVars.MASK_ENTITY;
        fixture.setFilterData(filterData);


        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.LINE_DETROYER));
        sprite.setAdjustSize(false);
        sprite.setSize(camera.viewportWidth, 0.1f);

        isActive = false;
        resetMoves();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isActive){
            if(userData.isCollisionWithPlayer()){
                playerHit();
            }
        }
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(body != null) {
            sprite.setColor(getColor());
            sprite.draw(batch);

        }
    }

    private void playerHit() {
        if(!player.getPlayerCurrentStates().hasShield()) {
            player.getPlayerCalculatedVars().addLife(-1);
            player.getPlayerCollision().createShield(0.5f);
        }
    }

    private void resetMoves(){
        setSize(camera.viewportWidth, height(body));
        sprite.setSize(getWidth(), getHeight());
        addAction(new SequenceAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                charge.play(MusicHandler.getSoundVolume());
            }
        }), Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                startDmg();
                beam.play(MusicHandler.getSoundVolume());
            }
        })));
    }

    private void startDmg() {
        clearActions();
        SequenceAction sequence = Actions.sequence(Actions.alpha(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                isActive = true;
                charge.stop();
            }
        }), Actions.delay(0.6f), Actions.run(new Runnable() {
            @Override
            public void run() {
                isActive = false;
                finish();
                beam.stop();

            }
        }));
        addAction(sequence);
    }

    private void finish() {
        world.destroyBody(body);
        remove();
        listener.finished();
    }

}
