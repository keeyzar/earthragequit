package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Ground extends GameActor {

    public float GROUND_WIDTH;
    public static final float GROUND_HEIGHT = 2f;
    private final float GROUND_DENSITY = 0f;
    Sprite sprite;

    private World world;
    public Ground(World world, float worldWidth) {
        create(world, worldWidth, 0);
    }

    public Ground(World world, float worldWidth, float yPos){
        create(world, worldWidth, yPos);
    }

    public void create(World world, float worldWidth, float yPos){
        this.world = world;
        GROUND_WIDTH = worldWidth;
        init(yPos);
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.GROUND);
        sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        sprite.setSize(worldWidth, GROUND_HEIGHT);
        sprite.setPosition(body.getPosition().x - worldWidth / 2, body.getPosition().y - GROUND_HEIGHT / 3);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void init(float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(GROUND_WIDTH / 2, yPos));
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GROUND_WIDTH / 2, GROUND_HEIGHT / 2);
        Fixture fixture = body.createFixture(shape, GROUND_DENSITY);
        shape.dispose();

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_GROUND;
        filterData.maskBits = EntityVars.MASK_GROUND;
        fixture.setFilterData(filterData);

        ERQUserDataEntities erqUserDataEntities = new ERQUserDataEntities(this, EntityVars.GROUND);
        erqUserDataEntities.setShouldContactWithPlayer(true);
        fixture.setUserData(erqUserDataEntities);
    }
}
