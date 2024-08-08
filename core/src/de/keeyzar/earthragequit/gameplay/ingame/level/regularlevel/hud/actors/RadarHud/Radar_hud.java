package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.RadarHud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Ingame_Radar;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Radarable;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class Radar_hud extends HudActor {
    private Ingame_Radar radar;
    Sprite sprite;
    Player player;
    ShapeRenderer shapeRenderer;
    Color good = Color.GREEN;
    Color neutral = Color.GRAY;
    Color bad = Color.RED;
    Color special = Color.GOLD;
    Color playerColor = Color.BLACK;
    float scale;
    boolean isInit = false;

    int mode = 1;
    final int MODE_NORMAL = 1;
    final int MODE_ADJUST = 2;


    public Radar_hud(Player player){
        super(HudVars.RADAR);
        setDisplayName(LANG.format("hud_name_radar"));
        this.player = player;
        radar = (Ingame_Radar) player.getPlayerIngameSkills().getActiveIngameSkills(SkillsVars.RADAR);
        init();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        actorVec = new Vector2();
        setFixedSizeRatio(true);

    }

    @Override
    public void initPosition() {
        //Standartwerte, wenn es keine gespeicherten werte gibt, läd er diese hier!
        int _width = 245;
        int _height = 245;
        int _x = 779;
        int _y = 555;
        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);
    }

    private void init() {
        this.sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.HUD_RADAR_BACKGROUND);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX(), getY());
    }

    @Override
    public void normalMode() {
        super.normalMode();
        getListeners().clear();
        mode = MODE_NORMAL;
    }

    @Override
    public void adjustModeListener(Stage stage) {
        super.adjustModeListener(stage);
        mode = MODE_ADJUST;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    private ObjectMap.Entries<Integer, Radarable> iterator;
    private ObjectMap.Entry<Integer, Radarable> iterItem;
    float xPos, yPos;


    public void radarDraw(float parentAlpha){
        iterator = radar.getCollisionMap(EntityVars.GROUP_GOOD).iterator();
        xPos = getX() + getWidth() / 2;
        yPos = getY() + getHeight() / 2;

        //2. clear our depth buffer with 1.0

        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        //3. set the function to LESS
        Gdx.gl.glDepthFunc(GL20.GL_LESS);

        //4. enable depth writing
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        //5. Enable depth writing, disable RGBA color writing
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 0.5f);
        shapeRenderer.circle(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2 - 10);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glColorMask(true, true, true, true);

        //9. Make sure testing is enabled.
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        //10. Now depth discards pixels outside our masked shapes
        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

        shapeRenderer.setColor(good);
        while (iterator.hasNext()) {
            iterItem = iterator.next();
            iterItem.value.drawOnMap(shapeRenderer, player.position, xPos, yPos, scale);
        }

        iterator = radar.getCollisionMap(EntityVars.GROUP_NEUTRAL).iterator();
        shapeRenderer.setColor(neutral);
        while (iterator.hasNext()) {
            iterItem = iterator.next();
            iterItem.value.drawOnMap(shapeRenderer, player.position, xPos, yPos, scale);
        }

        iterator = radar.getCollisionMap(EntityVars.GROUP_BAD).iterator();
        shapeRenderer.setColor(bad);
        while (iterator.hasNext()) {
            iterItem = iterator.next();
            iterItem.value.drawOnMap(shapeRenderer, player.position, xPos, yPos, scale);
        }

        iterator = radar.getCollisionMap(EntityVars.GROUP_SPECIAL).iterator();
        shapeRenderer.setColor(special);
        while (iterator.hasNext()) {
            iterItem = iterator.next();
            iterItem.value.drawOnMap(shapeRenderer, player.position, xPos, yPos, scale);
        }


//
//        shapeRenderer.setColor(playerColor);
//        shapeRenderer.rect(xPos - Player.WIDTH * factor / 2, yPos - Player.HEIGHT * factor / 2, Player.WIDTH * factor / 2, Player.HEIGHT*factor / 2, Player.WIDTH*factor,
//                Player.HEIGHT*factor, 1, 1, player.angle * MathUtils.radiansToDegrees);

        shapeRenderer.end();

        //disable depth func, so that all pixels are drawn
        Gdx.gl.glDepthFunc(GL20.GL_ALWAYS);
    }



    Vector2 actorVec;
    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        if(getWidth() > getHeight()){
            scale = getTop() - getY() + getHeight() / 2;
        } else {
            scale = getRight() - getX() + getWidth() / 2;
        }
        scale = scale / player.getPlayerCalculatedVars().getRadarSize() / 3;
        good.a = getColor().a;
        bad.a = getColor().a;
        neutral.a = getColor().a;
        sprite.setColor(getColor());
        if(!isInit){
            shapeRenderer.setProjectionMatrix(getStage().getViewport().getCamera().combined);
            isInit = true;
        }
    }

    public void dispose(){
        shapeRenderer.dispose();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if(mode == MODE_NORMAL){
            return null;
        }
        return super.hit(x, y, touchable);
    }
}
