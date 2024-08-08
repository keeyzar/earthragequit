package de.keeyzar.earthragequit.tutorial.movement.introstage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class BackgroundIntroStage {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    OrthographicCamera camera;

    public BackgroundIntroStage(OrthographicCamera camera){
        this.camera = camera;
        tiledMap = new TmxMapLoader().load(LevelVars.BACKGROUND_TMX_1);
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
