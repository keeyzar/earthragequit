package de.keeyzar.earthragequit.custom_ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * @author = Keeyzar on 26.01.2017.
 */
public class ImageTextButtonModified extends TextButton {

    public ImageTextButtonModified(String text, Skin skin, Sprite sprite) {
        super(text, skin);
        clearChildren();
        final ImageButton actor = new ImageButton(new SpriteDrawable(sprite));
        actor.setTouchable(Touchable.childrenOnly);
        add(actor).size(80, 80).pad(10);
        getLabel().setWrap(true);
        add(getLabel()).growX();
    }
}
