package de.keeyzar.earthragequit.tutorial.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author = Keeyzar on 02.02.2017.
 */
public class HighlightableImageButton extends ImageButton implements Highlightable {
    private RepeatAction highlighting;
    private Color oldColor;
    private Color newColor;

    public HighlightableImageButton(Skin skin, String stylename){
        super(skin, stylename);
        init();
    }
    public HighlightableImageButton(Skin skin){
        super(skin);
        init();
    }

    private void init() {
        oldColor = getColor();
        newColor = Color.GREEN.cpy();
        highlighting = Actions.forever(Actions.sequence(Actions.color(newColor, 1f, Interpolation.exp5), Actions.color(oldColor, 1f, Interpolation.pow5Out)));
    }

    @Override
    public void enableHighlight(boolean enable){
        if (enable) {
            addAction(highlighting);
        } else {
            addAction(Actions.color(Color.WHITE.cpy(), 0.3f));
            removeAction(highlighting);
            highlighting.restart();
        }
    }
}
