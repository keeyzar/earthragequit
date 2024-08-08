package de.keeyzar.earthragequit.menu.screens.mainmenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.sound.MusicHandler;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 27.01.2017.
 */
public class SoundMusicButton extends ImageTextButton{

    public static SoundMusicButton getToogleButton(final boolean musicButton, final ERQGame game, Skin skin, Drawable up, Drawable checked){
        final ImageTextButtonStyle imageButtonStyle = new ImageTextButtonStyle(skin.get(ImageTextButtonStyle.class));
        imageButtonStyle.imageUp = up;
        imageButtonStyle.imageChecked = checked;
        return new SoundMusicButton(musicButton, game, imageButtonStyle);
    }

    public SoundMusicButton(final boolean music, final ERQGame game, ImageTextButtonStyle style) {
        super(music ? LANG.format("main_bt_toggle_music") : LANG.format("main_bt_toggle_sound"), style);
        clearChildren();
        add(getImage()).row();
        add(getLabel());
        if(music){
            setChecked(!game.getMusicHandler().isMusicAllowed());
        } else {
            setChecked(!game.getMusicHandler().isSoundAllowed());
        }
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final boolean checked = isChecked();
                final MusicHandler musicHandler = game.getMusicHandler();
                if(music) {
                    musicHandler.setMusicAllowed(!checked);
                    musicHandler.actualiseMusicState();
                } else {
                    musicHandler.setSoundAllowed(!checked);
                }
                MenuUtils.playClickSound();
            }
        });
    }
}
