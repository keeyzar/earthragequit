package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class NewDimensionBackground extends Actor {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    OrthographicCamera camera;

    public NewDimensionBackground(OrthographicCamera camera){
        this.camera = camera;
        tiledMap = new TmxMapLoader().load(LevelVars.BACKGROUND_TMX_1);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 3);
    }

    public void draw(){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
