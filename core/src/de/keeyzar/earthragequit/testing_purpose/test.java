package de.keeyzar.earthragequit.testing_purpose;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * Created by Keeyzar on 06.04.2016.
 */
public class test extends Game {
    Stage stage;

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void create() {
        AssetManager assetManager = new AssetManager();
        assetManager.load(AssetVariables.UI_SKIN_ATLAS, TextureAtlas.class);
        assetManager.load(AssetVariables.UI_SKIN_JSON, Skin.class, new SkinLoader.SkinParameter(AssetVariables.UI_SKIN_ATLAS));
        assetManager.finishLoading();

        Skin skin = assetManager.get(AssetVariables.UI_SKIN_JSON, Skin.class);
        stage = new Stage();


        TextArea textArea = new TextArea("", skin);
        textArea.getStyle().font.getData().markupEnabled = true;
        textArea.setText("[RED] nice text [] i like");
        textArea.setFillParent(true);
        textArea.setPosition(0, 100);
        textArea.setSize(200, 100);
        stage.addActor(textArea);

    }
}
