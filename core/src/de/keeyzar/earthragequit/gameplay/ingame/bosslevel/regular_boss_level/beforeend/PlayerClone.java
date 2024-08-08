package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.beforeend;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class PlayerClone extends Actor implements Startable{
    public final float WIDTH = Player.WIDTH;
    public final float HEIGHT = Player.HEIGHT;
    private Sprite sprite;
    private Player player;
    private OrthographicCamera camera;

    public PlayerClone(Player player, OrthographicCamera camera){
        this.player = player;
        this.camera = camera;
        setSize(WIDTH, HEIGHT);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite = new Sprite(player.getCurrentSprite());
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        setOriginX(getWidth() / 2);
        setOriginY(getHeight() / 2);
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setRotation(getRotation());
        sprite.setAlpha(getColor().a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void start(boolean playerWon) {
        setPosition(player.getX(), player.getY());
        setRotation(player.body.getAngle() * MathUtils.radiansToDegrees);
        if(playerWon) {
            Action action = Actions.parallel(
                    Actions.sizeTo(WIDTH * 3, HEIGHT * 3, 3, Interpolation.linear),
                    Actions.rotateTo(3600, 3, Interpolation.fade),
                    Actions.sequence(Actions.delay(0.5f), Actions.moveTo(camera.position.x - getWidth() * 3 / 2, camera.position.y - getHeight() * 3 / 2, 2f))
            );

            Action action2 = Actions.sequence(action, Actions.fadeOut(1, Interpolation.sineIn));
            addAction(action2);
        } else {
            Action action = Actions.fadeOut(1, Interpolation.fade);
            addAction(action);
        }
    }
}
