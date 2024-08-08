package de.keeyzar.earthragequit.tutorial.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * @author = Keeyzar on 02.02.2017.
 */
public class HighlightableButton extends TextButton implements Highlightable {
    private RepeatAction highlighting;
    private Color oldColor;
    private Color newColor;

    public HighlightableButton(String text, Skin skin){
        super(text, skin);
        init();
    }

    public HighlightableButton(String text, Skin skin, String styleName){
        super(text, skin, styleName);
        init();
    }

    private void init() {
        oldColor = getColor();
        newColor = Color.GREEN.cpy();
//        highlighting = Actions.forever(Actions.sequence(Actions.sizeBy(1.2f,1.2f, 2f), Actions.sizeBy(-1.2f, -1.2f, 2f)));
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
