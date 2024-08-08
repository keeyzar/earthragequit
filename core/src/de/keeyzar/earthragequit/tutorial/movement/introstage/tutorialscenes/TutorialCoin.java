package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Coin;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class TutorialCoin extends Coin{
    TWListener twListener;
    private Player player;

    public TutorialCoin(World world, int value, float x, float y, TWListener twListener, Player player) {
        super(world, value, x, y);
        this.twListener = twListener;
        this.player = player;
        userData.setEnabled(true); // this is always enabled.
    }

    @Override
    protected void collisionStuff() {
        player.addCoin(value);
        world.destroyBody(body);
        body = null;
        remove();
        userData.setCollisionWithPlayer(false);
        Coin.playCoinSound();
        twListener.TShown();
    }
}
