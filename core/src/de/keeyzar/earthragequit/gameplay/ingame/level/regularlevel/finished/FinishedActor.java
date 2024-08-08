package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.finished;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

/**
 * @author = Keeyzar on 07.03.2016
 */
public class FinishedActor extends Actor {
    BitmapFont bmp;
    GlyphLayout glyphLayout;
    String danger = "D-A-N-G-E-R";
    public FinishedActor(){
        bmp = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_XBOLD_164);
        setColor(Color.RED);
        Action action = Actions.repeat(5,
                            Actions.sequence(Actions.fadeIn(0.2f, Interpolation.fade),
                            Actions.fadeOut(0.2f, Interpolation.fade)));
        addAction(Actions.sequence(action, Actions.run(new Runnable() {
            @Override
            public void run() {
                //the bmp gets changed everywhere. so, undo color change
                bmp.setColor(Color.WHITE);
            }
        })));
        glyphLayout = new GlyphLayout(bmp, "D-A-N-G-E-R");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        bmp.setColor(getColor());
        glyphLayout.setText(bmp, danger);
        bmp.draw(batch, glyphLayout, HudVars.HUD_WIDTH / 2 - glyphLayout.width / 2, HudVars.HUD_HEIGHT / 2 - glyphLayout.height / 2);
    }
}
