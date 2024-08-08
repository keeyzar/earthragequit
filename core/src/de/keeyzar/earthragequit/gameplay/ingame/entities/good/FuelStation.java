package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.NonMovingRadarEntity;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class FuelStation extends NonMovingRadarEntity{

    private final World world;
    private Box2DSprite sprite;
    private final BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    public FuelStation(World world, float x, float y) {
        super(EntityVars.FUEL, EntityVars.GROUP_GOOD);
        this.world = world;
        initBounds(x, y, 3, 4);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
        bitmapFont = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_SBOLD_2);
        bitmapFont.setColor(Color.BLACK);
        if(bitmapFont.getScaleX() == 1) {
            bitmapFont.getData().scale(-0.96f);
        }
        bitmapFont.setUseIntegerPositions(false);

        glyphLayout = new GlyphLayout(bitmapFont, LANG.format("gas_station_free_fuel_text"));
    }

    public void init() {
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.FUELSTATION));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            sprite.setAlpha(parentAlpha);
            sprite.draw(batch, body);
            Color color = bitmapFont.getColor();
            color.a = getColor().a;
            bitmapFont.setColor(color);
            bitmapFont.draw(batch, glyphLayout, getX() - glyphLayout.width / 2, getTop() + glyphLayout.height);
        }
    }

    @Override
    protected void collisionStuff() {
            userData.getPlayer().getPlayerCalculatedVars().setU_speed_fuel(
                    userData.getPlayer().getPlayerCalculatedVars().getU_speed_fuel_max());
    }

}
