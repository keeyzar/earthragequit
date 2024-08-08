package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.beforeend;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class BossClone extends Actor implements Startable {
    public final float WIDTH;
    public final float HEIGHT;
    private Sprite sprite;
    private OrthographicCamera camera;
    private BossEnemy bossEnemy;

    public BossClone(BossEnemy bossEnemy, OrthographicCamera camera){
        this.camera = camera;
        this.bossEnemy = bossEnemy;
        WIDTH = bossEnemy.getWidth();
        HEIGHT = bossEnemy.getHeight();
        setSize(WIDTH, HEIGHT);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite = new Sprite(bossEnemy.getCurrentSprite());
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setPosition(getX(), getY());
        sprite.setSize(getWidth(), getHeight());
        sprite.setRotation(getRotation());
        sprite.setAlpha(getColor().a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void start(boolean playerWon) {
        if(bossEnemy.body != null){
            setPosition(bossEnemy.body.getPosition().x, bossEnemy.body.getPosition().y);
            setRotation(bossEnemy.body.getAngle() * MathUtils.radiansToDegrees);
        } else {
            setPosition(bossEnemy.getX(), bossEnemy.getY());
            setRotation(bossEnemy.getRotation());
        }
        if(playerWon) {
            Action action = Actions.parallel(
                    Actions.sizeTo(WIDTH, 0f, 3, Interpolation.bounceOut)
            );

            Action action2 = Actions.sequence(action, Actions.fadeOut(1, Interpolation.sineIn));
            addAction(action2);
        } else {
            Action action = Actions.parallel(
                    Actions.sizeTo(WIDTH * 3, HEIGHT * 3, 2, Interpolation.bounceIn),
                    Actions.moveTo(camera.position.x - WIDTH * 3 / 2, camera.position.y - HEIGHT * 3 / 2, 2f));
            Action action2 = Actions.sequence(
                    action,
                    Actions.rotateBy(720, 2)
            );
            addAction(action2);
        }
    }
}
