package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 16.03.2016
 */
public class HYActor extends Actor {
    BitmapFont bmp;
    private OrthographicCamera camera;

    public HYActor(OrthographicCamera camera){
        this.camera = camera;
        bmp = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_XBOLD_164);
        bmp.setColor(Color.RED);
        SequenceAction sequence = Actions.sequence(Actions.sizeTo(1, 1, 0), Actions.sizeTo(300, 300, 1, Interpolation.pow5In), Actions.sizeTo(100, 100, 1f, Interpolation.sine));
        addAction(sequence);
        setPosition(HudVars.HUD_WIDTH / 2, 400);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bmp.getColor().a = getColor().a;
        bmp.getData().setScale(getWidth() / 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bmp.draw(batch, LANG.format("tutorial_story_hey_there"), getX() - getWidth() / 2, getY() + getHeight() / 2, getWidth(), Align.center, false);
    }
}
