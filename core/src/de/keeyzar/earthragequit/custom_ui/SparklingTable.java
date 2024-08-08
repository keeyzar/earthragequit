package de.keeyzar.earthragequit.custom_ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 27.06.2016.
 */
public class SparklingTable extends Table {
    Array<ParticleEffect> particleEffects;
    int multiplikator;
    boolean randomcolor;
    public SparklingTable(Skin skin) {
        this(skin, 2, false);

    }

    public SparklingTable(Skin skin, int multiplikator, boolean randomcolor){
        super(skin);
        particleEffects = new Array<ParticleEffect>();

        for(int i = 0; i<multiplikator; i++){
            ParticleEffect particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.TABLE_SPARK, ParticleEffect.class));
            particleEffect.start();
            particleEffects.add(particleEffect);
        }
        if(randomcolor) randomizeColor();
        particleEffects.add(new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.TABLE_SPARK2, ParticleEffect.class)));
        this.multiplikator = multiplikator;
        this.randomcolor = randomcolor;
    }

    private void randomizeColor() {
        for(ParticleEffect particleEffect : particleEffects){
            float[] colors = {MathUtils.random(0, 256), MathUtils.random(0, 256), MathUtils.random(0, 256)};
            particleEffect.getEmitters().first().getTint().setColors(colors);

        }
    }


    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, parentAlpha, x, y);
        for(ParticleEffect particleEffect : particleEffects){
            particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void layout() {
        super.layout();
        for(ParticleEffect particleEffect : particleEffects){
            particleEffect.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
            //the particles should spawn everywhere in the table
            Array<ParticleEmitter> emitters = particleEffect.getEmitters();
            for(ParticleEmitter particleEmitter : emitters) {
                particleEmitter.getXOffsetValue().setLow(-getWidth() / 2, getWidth() / 2);
                particleEmitter.getYOffsetValue().setLow(-getHeight() / 2, getHeight() / 2);

            }
        }
    }
}
