package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.background;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import src.com.rahul.parallax.ParallaxBackground;
import src.com.rahul.parallax.ParallaxLayer;
import src.com.rahul.parallax.ParticleEffectparallaxLayer;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class BackgroundStage {
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private ParallaxBackground parallaxBackground;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public BackgroundStage(OrthographicCamera camera, GameScreen gameScreen){
        this.camera = camera;
        tiledMap = new TmxMapLoader().load(gameScreen.getLevelDefining().getLevelTmxPath());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);

        parallaxBackground = new ParallaxBackground();
        ParallaxLayer[] layers = initLayers();
        parallaxBackground.addLayers(layers);
        batch = new SpriteBatch();

    }

    private ParallaxLayer[] initLayers() {
        final ParallaxLayer tx1 = getParticleEffectparallaxLayer(AssetVariables.STARFIELD_EMITTER1, 0.1f);
        final ParallaxLayer tx2 = getParticleEffectparallaxLayer(AssetVariables.STARFIELD_EMITTER2, 0.14f);
        final ParallaxLayer tx3 = getParticleEffectparallaxLayer(AssetVariables.STARFIELD_EMITTER3, 0.2f);
        return new ParallaxLayer[]{tx1, tx2, tx3};
    }

    private ParallaxLayer getParticleEffectparallaxLayer(String particleEffectName, float scrollAmount) {
        final ParticleEffectparallaxLayer tx1 =
                new ParticleEffectparallaxLayer(ERQAssets.MANAGER.get(particleEffectName, ParticleEffect.class),
                        41, 27, new Vector2(scrollAmount, scrollAmount));
        tx1.setTileModeX(ParallaxLayer.TileMode.repeat);
        tx1.setTileModeY(ParallaxLayer.TileMode.repeat);
        return tx1;
    }

    public void draw(){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        parallaxBackground.draw(camera, batch);
        batch.end();
    }

    public void dispose() {
        tiledMap.dispose();

    }
}
