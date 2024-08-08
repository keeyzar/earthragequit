package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class CameraUtils {
    public static boolean centerPlayer = false;
    Vector3 desiredPosition;

    public CameraUtils(Vector2 pos){
        desiredPosition = new Vector3(pos.x, pos.y, 0);
    }

    public void updateCamera(Player player, OrthographicCamera camera, int worldWidth, int worldHeight, boolean shakeCamera,
                             float intensity) {

        //moveCamera To Player
        if (player.getBody().getLinearVelocity().y > 0) {
            desiredPosition.set(desiredPosition.x, desiredPosition.y + (player.getBody().getLinearVelocity().y * Gdx.graphics.getDeltaTime() * 3), 0);
            if (desiredPosition.y - camera.viewportHeight / 2 + Player.HEIGHT > player.position.y) {
                desiredPosition.y = player.position.y + camera.viewportHeight / 2 - Player.HEIGHT;
            }
        } else {
            desiredPosition.set(desiredPosition.x, desiredPosition.y + (player.getBody().getLinearVelocity().y * Gdx.graphics.getDeltaTime() * 3), 0);
            if (desiredPosition.y + camera.viewportHeight / 2 + Player.HEIGHT / 2 < player.position.y + Player.HEIGHT) {
                desiredPosition.y = player.position.y + Player.HEIGHT - camera.viewportHeight / 2 - Player.HEIGHT / 2;
            }
        }
//        if(player.getBody().getLinearVelocity().x == 0){
//            desiredPosition.x = player.body.getPosition().x;
//        } else
        if (player.getBody().getLinearVelocity().x >= 0) {
            desiredPosition.set(desiredPosition.x + (player.getBody().getLinearVelocity().x * Gdx.graphics.getDeltaTime() * 3), desiredPosition.y, 0);
            if (desiredPosition.x - camera.viewportWidth / 5 > player.position.x + player.getWidth() / 2) {
                desiredPosition.x = player.position.x + player.getWidth() / 2 + camera.viewportWidth / 5;
            }
        } else {
            desiredPosition.set(desiredPosition.x + (player.getBody().getLinearVelocity().x * Gdx.graphics.getDeltaTime() * 3), desiredPosition.y, 0);
            if (desiredPosition.x + camera.viewportWidth / 5 < player.position.x - player.getWidth() / 2) {
                desiredPosition.x = player.position.x + player.getWidth() / 2 - camera.viewportWidth / 5;
            }
        }

        if(centerPlayer){
            camera.position.set(player.position.x, desiredPosition.y, 0);
        } else {
            camera.position.set(desiredPosition);
        }
        //add Shakey Effect
        if(shakeCamera){
            camera.position.add(MathUtils.random(-1f, 1f) * intensity, MathUtils.random(-1f, 1f) * intensity, 0);
        }

        //make sure camera is in gameStage
        if (camera.position.x < 0 + camera.viewportWidth / 2) {
            camera.position.set(0 + camera.viewportWidth / 2, camera.position.y, 0);
        } else if (camera.position.x > worldWidth - camera.viewportWidth / 2) {
            camera.position.set(worldWidth - camera.viewportWidth / 2, camera.position.y, 0);
        }

        if (camera.position.y < 0 + camera.viewportHeight / 2) {
            camera.position.set(camera.position.x, 0 + camera.viewportHeight / 2, 0);
        } else if (camera.position.y > worldHeight - camera.viewportHeight / 2) {
            camera.position.set(camera.position.x, worldHeight - camera.viewportHeight / 2, 0);
        }

        camera.update();

    }

    static float worldHeightx;
    public static void centerPlayerTutorial(Player player, OrthographicCamera camera, float worldHeight) {
        if(player.position.x < camera.position.x - camera.viewportWidth / 2){
            player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
            player.body.applyLinearImpulse(4, 0, player.body.getPosition().x, player.body.getPosition().y, true);
        } else if(player.position.x + player.getWidth() / 2 > camera.position.x + camera.viewportWidth / 2){
            player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
            player.body.applyLinearImpulse(-4, 0, player.body.getPosition().x, player.body.getPosition().y, true);
        }

        if(player.position.y + player.HEIGHT / 2 > camera.position.y + camera.viewportHeight / 2){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
            player.body.applyLinearImpulse(0, -4, player.body.getPosition().x, player.body.getPosition().y, true);
        }
    }
}
