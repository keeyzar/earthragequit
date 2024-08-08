package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills.BalthazarSkills;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * @author = Keeyzar on 31.03.2016
 */
public class Balthazar extends BossEnemy implements Cloneable {
    private final ERQGame game;
    private OrthographicCamera camera;

    BalthazarMovement balthMove;
    BalthazarSkills balthSkill;

    Box2DSprite sprite;

    public Balthazar(ERQGame game, World world, Player player, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        setBounds(5, 11, 4, 3);
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BALTHAZAR));
        sprite.setSize(getWidth(), getHeight());
        sprite.setOriginCenter();

        balthMove = new BalthazarMovement(this, camera);
        balthSkill = new BalthazarSkills(this, world, player);
    }


    /////////////////////////////////////////////////////
    /////////////////////MOVEMENT////////////////////////
    /////////////////////////////////////////////////////

    @Override
    public void act(float delta) {
        super.act(delta);
        balthMove.act(delta);
        balthSkill.act(delta);
        balthMove.checkMovement();
        sprite.setPosition(getX(), getY());
        sprite.setColor(getColor());
    }


    /**
     * if called, delegates nextSkill to BalthSkill class
     */
    public void nextSkill() {
        balthSkill.nextSkill();
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public int getLife() {
        return 1;
    }

    @Override
    public int getMaxLife() {
        return 1;
    }

    @Override
    public Sprite getCurrentSprite() {
        return sprite;
    }

    @Override
    public void initBody() {
        //no body needed
    }

}
