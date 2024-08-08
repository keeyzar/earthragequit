package de.keeyzar.earthragequit.testing_purpose;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.StringBuilder;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import net.dermetfan.gdx.Typewriter;

/**
 * Created by Keeyzar on 05.04.2016.
 */
public class Textwriting extends Game {
    StringBuilder stringBuilder;
    Skin skin;
    Table table;
    TextArea textArea;
    ScrollPane scrollPane;
    Stage stage;
    String nice = "Das [RED]ist[] ein langer Text mit ganz vielen Worten, genau wie ichs mag";
    private Typewriter typewriter;

    public Textwriting(){
        stringBuilder = new StringBuilder();
    }


    int i = 1;
    @Override
    public void render() {
        stringBuilder.delete(0, stringBuilder.length);
        stringBuilder.append(typewriter.updateAndType(nice, Gdx.graphics.getDeltaTime()).toString());
        boolean isFinish;
        if(stringBuilder.length() >= nice.length()){
            isFinish = true;
        } else {
            isFinish = false;
        }

        if(isFinish){
            if(Gdx.input.isTouched()){
                nice = "nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text nEUER text " + i;
                typewriter.setTime(0);
                i++;
            }
        }
        textArea.setText(stringBuilder.toString());
        stage.act();
        stage.draw();
    }

    @Override
    public void create() {
        AssetManager assetManager = new AssetManager();
        assetManager.load(AssetVariables.UI_SKIN_ATLAS, TextureAtlas.class);
        assetManager.load(AssetVariables.UI_SKIN_JSON, Skin.class, new SkinLoader.SkinParameter(AssetVariables.UI_SKIN_ATLAS));
        assetManager.finishLoading();

        skin = assetManager.get(AssetVariables.UI_SKIN_JSON, Skin.class);

        typewriter = new Typewriter();
        typewriter.setCursorAfterTyping(true);
        typewriter.setCharsPerSecond(50);

        stage = new Stage();

        textArea = new TextArea("", skin);
        textArea.setDisabled(true);
//        TextField.TextFieldStyle style = textArea.getStyle();
//        style.font.getData().markupEnabled = true;
//        textArea.setStyle(style);
        scrollPane = new ScrollPane(textArea, skin);

        table = new Table();
        table.add(scrollPane).size(400, 400);
        table.setFillParent(true);
        stage.addActor(table);
        stage.setDebugAll(true);
    }
}
