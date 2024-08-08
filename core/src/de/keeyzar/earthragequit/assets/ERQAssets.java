package de.keeyzar.earthragequit.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import de.keeyzar.earthragequit.tools.BodyEditorAssetLoader;
import de.keeyzar.earthragequit.tools.BodyEditorLoader;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import java.util.Locale;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQAssets {
    public static AssetManager MANAGER;
    public static TextureAtlas T_ATLAS_GAME;
    public static ParticleEffect GLITTER_PARTICLE_EFFECT;
    public static Skin SKIN;
    public static BodyEditorLoader BODY_LOADER;
    public static TextButton.TextButtonStyle TB_ENABLED;
    public static TextButton.TextButtonStyle TB_DISABLED;
    public static TextButton.TextButtonStyle TB_SPECIAL;
    public static TextButton.TextButtonStyle TB_SELECTED;
    public static final String COUNTRY_CODE_DE = "DE";
    public static final String COUNTRY_CODE_EN = "";
    private static final String path = "data/localization/translation";
    public static I18NBundle LANG;

    public static void create() {
        MANAGER = new AssetManager();
    }

    public static void startLoading(){
        load();
    }

    private static void load() {
        MANAGER.load(AssetVariables.TEXTURE_ATLAS_GAME, TextureAtlas.class);
        MANAGER.load(AssetVariables.GLITTER_PARTICLE_EFFECT, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.STORY_XPLO_3, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.RING_EXPLO, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.BOSS3_EMITTER_RED, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.BOSS3_EMITTER_BLUE, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.STARFIELD_EMITTER1, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.STARFIELD_EMITTER2, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.STARFIELD_EMITTER3, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.PARTICLE_WALL, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.ROCKET_PARTICLE, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.ROCKET_FIRE, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.BOSS3_EMITTER_GREEN, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.DIMENSION_ORB_TRAIL, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.PORTAL_EMITTER, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.TABLE_SPARK, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.TABLE_SPARK2, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.TREASURE_CHEST_SPARKLE, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.SPAWNABLE_SPARKLE, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.EXPLOSION_DEBRIS, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.CLOUD_PARTICLEEFFECT + "1", ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.CLOUD_PARTICLEEFFECT + "2", ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.CLOUD_PARTICLEEFFECT + "3", ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.ACHIEVEMENT_PARTICLEEFFECT, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectParameter());
        MANAGER.load(AssetVariables.SOUND_EXPLOSION, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_COIN, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_JUMP, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_ACHIEVEMENT, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_LASERBEAM, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_LASERCHARGE, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_LEVEL_WON, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.MUSIC_BOSS_1, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_BOSS_2, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_GAME_1, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_GAME_2, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_INTRO, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_MENU_1, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_LEVEL_WON, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_LEVEL_LOST, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_CREDITS, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_STORY, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.MUSIC_TO_BE_CONTINUED, Music.class, new MusicLoader.MusicParameter());
        MANAGER.load(AssetVariables.ROCKET_MUSIC, Sound.class, new SoundLoader.SoundParameter());

        loadGenericSounds();
        loadIngameSounds();
        loadMenuSounds();
        loadFonts();
        loadBodyEditor();
    }

    private static void loadBodyEditor() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        MANAGER.setLoader(BodyEditorLoader.class, new BodyEditorAssetLoader(resolver));
        Box2D.init();
        MANAGER.load(AssetVariables.ERQ_BODIES, BodyEditorLoader.class);
    }

    private static void loadFonts(){
        //setLoader
        FileHandleResolver resolver = new InternalFileHandleResolver();
        MANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        MANAGER.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 164;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        loader.fontParameters = parameter;
        loader.fontFileName = AssetVariables.F_OpSans_EXTRA_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_XBOLD_164, BitmapFont.class, loader);

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader3 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 26;
        parameter3.magFilter = Texture.TextureFilter.Linear;
        parameter3.minFilter = Texture.TextureFilter.Linear;
        loader3.fontParameters = parameter3;
        loader3.fontFileName = AssetVariables.F_OpSans_EXTRA_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_XBOLD_26, BitmapFont.class, loader3);

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.size = 26;
        param2.magFilter = Texture.TextureFilter.Linear;
        param2.minFilter = Texture.TextureFilter.Linear;
        loader2.fontParameters = param2;
        loader2.fontFileName = AssetVariables.F_OpSans_SEMI_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_SBOLD_26, BitmapFont.class, loader2);

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader4 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter param4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param4.size = 30;
        param4.magFilter = Texture.TextureFilter.Linear;
        param4.minFilter = Texture.TextureFilter.Linear;
        loader4.fontParameters = param4;
        loader4.fontFileName = AssetVariables.F_OpSans_SEMI_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_SBOLD_30, BitmapFont.class, loader4);

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader5 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter5.size = 26;
        parameter5.magFilter = Texture.TextureFilter.Linear;
        parameter5.minFilter = Texture.TextureFilter.Linear;
        loader5.fontParameters = parameter5;
        loader5.fontFileName = AssetVariables.F_OpSans_SEMI_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_SBOLD_2, BitmapFont.class, loader5);

        FreetypeFontLoader.FreeTypeFontLoaderParameter loader6 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter6.size = 45;
        parameter6.magFilter = Texture.TextureFilter.Linear;
        parameter6.minFilter = Texture.TextureFilter.Linear;
        loader6.fontParameters = parameter6;
        loader6.fontFileName = AssetVariables.F_OpSans_SEMI_BOLD;
        MANAGER.load(TextureAtlasVariables.FONT_OS_SBOLD_45, BitmapFont.class, loader6);
    }

    public static void loadLocalization(final String code){
        if(MANAGER.isLoaded(path)){
            MANAGER.unload(path);
        }
        Locale locale;
        if(code.equals("")){
            locale = new Locale("EN");
        } else {
            locale = new Locale(code);
        }
        I18NBundleLoader.I18NBundleParameter parameter = new I18NBundleLoader.I18NBundleParameter(locale);
        MANAGER.load(path, I18NBundle.class, parameter);
        MANAGER.finishLoading();
        LANG = MANAGER.get(path, I18NBundle.class);
    }

    public static void done() {
        T_ATLAS_GAME = MANAGER.get(AssetVariables.TEXTURE_ATLAS_GAME, TextureAtlas.class);
        SKIN = MANAGER.get(AssetVariables.UI_SKIN_JSON, Skin.class);
        GLITTER_PARTICLE_EFFECT = MANAGER.get(AssetVariables.GLITTER_PARTICLE_EFFECT, ParticleEffect.class);
        BODY_LOADER = MANAGER.get(AssetVariables.ERQ_BODIES, BodyEditorLoader.class);

        TB_ENABLED = SKIN.get(TextButton.TextButtonStyle.class);
        TB_DISABLED = SKIN.get("disabled", TextButton.TextButtonStyle.class);
        TB_SPECIAL = SKIN.get("special", TextButton.TextButtonStyle.class);
        TB_SELECTED = SKIN.get("selected", TextButton.TextButtonStyle.class);
        setDefaultFont();
    }

    /**
     * set default font for all styles
     */
    private static void setDefaultFont() {
        BitmapFont bmp = MANAGER.get(TextureAtlasVariables.FONT_OS_SBOLD_30, BitmapFont.class);

        new Label("", SKIN).getStyle().font = bmp;
        new TextButton("", SKIN).getStyle().font = bmp;
        new TextButton("", SKIN, "disabled").getStyle().font = bmp;
        new TextButton("", SKIN, "toggle").getStyle().font = bmp;
        new TextButton("", SKIN, "special").getStyle().font = MANAGER.get(TextureAtlasVariables.FONT_OS_XBOLD_26, BitmapFont.class);
        new TextButton("", SKIN, "selected").getStyle().font = bmp;
        new TextButton("", SKIN, "finnish").getStyle().font = bmp;
    }

    public static void loadIngameSounds(){
        MANAGER.load(AssetVariables.SOUND_OIL_CATCHED, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_STRANGE_COLLISION, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_PICK_UP_SOUND, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_HIT_ENEMY_EXPLO, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_REJECT, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_UNLOCK, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_SWITCH_ON, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_SWITCH_OFF, Sound.class, new SoundLoader.SoundParameter());

    }

    private static void loadGenericSounds(){
        MANAGER.load(AssetVariables.SOUND_MISTERY_ORB, Sound.class, new SoundLoader.SoundParameter());
    }

    public static void loadMenuSounds(){
        MANAGER.load(AssetVariables.SOUND_BUTTON_CLICK, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_BUY, Sound.class, new SoundLoader.SoundParameter());
        MANAGER.load(AssetVariables.SOUND_EQUIP, Sound.class, new SoundLoader.SoundParameter());
    }


    public static void dispose() {
        MANAGER.dispose();
    }
}
