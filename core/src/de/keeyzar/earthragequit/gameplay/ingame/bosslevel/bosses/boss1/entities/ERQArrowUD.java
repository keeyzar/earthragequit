package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities;

import com.badlogic.gdx.physics.box2d.Fixture;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class ERQArrowUD {
    public Fixture fixThatPickedArrowUp;
    public boolean isAlive = true;
    public Object userDataHolder;
    public int id;
    /**
     * the actual collisioning item
     */
    public ERQUserDataEntities actualCollisioningUserData;

    public ERQArrowUD(int id, Object userDataHolder){
        this.id = id;
        this.userDataHolder = userDataHolder;
    }
}
