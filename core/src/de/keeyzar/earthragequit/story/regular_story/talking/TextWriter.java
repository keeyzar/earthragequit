package de.keeyzar.earthragequit.story.regular_story.talking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.Typewriter;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class TextWriter extends Actor {
    BitmapFont bmpNormal;
    StringBuilder currentlyShownText;
    private boolean allShown = false;

    Table table;
    Label label;
    private Stage stageToAddTW;
    private Stage stageToDisable;
    private InputMultiplexer inputMultiplexer;
    private ScrollPane scrollPane;
    private Typewriter typeWriter;
    String textToShow;

    TWListener TWListener;

    public TextWriter(Stage stage) {
        this.stageToAddTW = stage;
        initFont();
        setColor(Color.GREEN.cpy());
        currentlyShownText = new StringBuilder();
        initLabel();
        initTypeWrite();
    }


    private void clearData(String typingText, TWListener TWListener){
        textToShow = typingText;
        currentlyShownText.replace(0, currentlyShownText.length(), "");
        typeWriter.setTime(0);
        allShown = false;
        this.TWListener = TWListener;
    }
    private void clearText() {
        currentlyShownText.replace(0, currentlyShownText.length(), "");
        label.setText("");
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if(!allShown && textToShow != null) {
            currentlyShownText.delete(0, currentlyShownText.length());
            currentlyShownText.append(typeWriter.updateAndType(textToShow, Gdx.graphics.getDeltaTime()).toString());

            boolean isFinish;
            if (currentlyShownText.length() >= textToShow.length()) {
                isFinish = true;
            } else {
                isFinish = false;
            }
            if (isFinish) {
                if (Gdx.input.isTouched()) {
                    TWListener.TShown();
                    allShown = true;
                }
            }
            label.setText(currentlyShownText.toString());
        }
    }


    public void textStart(final String textToShow, final TWListener tWL) {
        stageToAddTW.addActor(this);
        stageToAddTW.addActor(scrollPane);
        if(inputMultiplexer != null && stageToDisable != null){
            stageToDisable.cancelTouchFocus();
            inputMultiplexer.removeProcessor(stageToDisable);
        }
        scrollPane.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                clearData(textToShow, tWL);
                scrollPane.clearActions();
            }
        })));
    }

    public void textEnd() {
        if(allShown) {
            scrollPane.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    if (TWListener != null) {
                        TWListener.TFadeOutFinish();
                        scrollPane.remove();
                        TextWriter.this.remove();
                        clearText();
                        TWListener = null;
                        if(inputMultiplexer != null && stageToDisable != null){
                            inputMultiplexer.addProcessor(stageToDisable);
                        }
                    }
                }
            })));
        }
    }

    public void newText(String text, TWListener TWListener) {
        clearData(text, TWListener);
    }

    public void setCharsPerSecond(int charsPerSecond) {
        typeWriter.setCharsPerSecond(charsPerSecond);
    }

    private void initLabel() {
        label = new Label("", ERQAssets.SKIN);
        label.setWrap(true);
        label.setAlignment(Align.topLeft);
        scrollPane = new ScrollPane(label, ERQAssets.SKIN);
        scrollPane.setScrollingDisabled(true, false);
    }

    void initFont(){
        bmpNormal = ERQAssets.MANAGER.get(TextureAtlasVariables.FONT_OS_SBOLD_26, BitmapFont.class);
        bmpNormal = new BitmapFont(bmpNormal.getData(), bmpNormal.getRegion(), true);

    }


    private void initTypeWrite() {
        typeWriter = new Typewriter();
        typeWriter.setCharsPerSecond(50);
        typeWriter.setCursorAfterTyping(false);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        label.setBounds(getX() + 50, getY() + 50, getWidth() - 50, getHeight() - 50);
        table = new Table();
        table.add(label).size(getWidth() - 50, getHeight() - 50);
        scrollPane.setWidget(table);
        scrollPane.setBounds(getX(), getY(), getWidth(), getHeight());
        scrollPane.setTouchable(Touchable.disabled);
    }

    public void setEnableDisableInput(InputMultiplexer inputMultiplexer, Stage stageToDisable){
        this.inputMultiplexer = inputMultiplexer;
        this.stageToDisable = stageToDisable;
    }

    public void dispose(){
        bmpNormal.dispose();
    }

}
