package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class PlatformWithText extends Platform {
    private final BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private World world;

    public PlatformWithText(World world, float x, float y, Stage stage){
        super(world, x, y, stage);
        this.world = world;
        bitmapFont = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_SBOLD_2);
        bitmapFont.setColor(Color.BLACK);
        if(bitmapFont.getScaleX() == 1) {
            bitmapFont.getData().scale(-0.96f);
        }
        bitmapFont.setUseIntegerPositions(false);
        glyphLayout = new GlyphLayout(bitmapFont, LANG.format("land_here_text"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(userData.isEnabled()){
            if(body==null)return;
            Color color = bitmapFont.getColor();
            color.a = getColor().a;
            bitmapFont.setColor(color);
            bitmapFont.draw(batch, glyphLayout, getX() - glyphLayout.width / 2, getTop() + glyphLayout.height);
        }
    }
}
