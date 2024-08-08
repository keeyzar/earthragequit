package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.background;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class BossBackgroundStage extends Stage {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    OrthographicCamera camera;

    public BossBackgroundStage(OrthographicCamera camera, BossLevelDefining bld){
        this.camera = camera;
        tiledMap = new TmxMapLoader().load(bld.getBackgroundName());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);
    }

    public void draw(){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
