package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.Balthazar;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 31.03.2016
 */
public class BalthazarSkills {
    private Balthazar balthazar;
    private BalthSkillDelegator balthSkillOne;
    Actor rda; //randomDelayActor

    public BalthazarSkills(Balthazar balthazar, World world, Player player){
        this.balthazar = balthazar;
        rda = new Actor();
        balthSkillOne = new BalthSkillDelegator(balthazar, world, player);
        newDelay();
    }

    private int currSkill;
    private int state = -1;
    private final int state_skill = 1;
    private final int s_delay = -1;
    public void act(float delta){
        switch (state){
            case state_skill:
                actSkill(delta);
                break;
            case s_delay:
                rda.act(delta);
                balthazar.setColor(rda.getColor());
                break;
        }
    }

    /**
     * a new delay on the actor, which starts moveToPortalAndFadeOut
     */
    private void newDelay(){
        rda.clearActions();
        rda.addAction(Actions.sequence(Actions.delay(MathUtils.random(1f, 3f)), Actions.run(new Runnable() {
            @Override
            public void run() {
                fadeOut();
            }
        })));
    }

    /**
     * a new fadeout action, which starts a randomSkill
     */
    private void fadeOut() {
        rda.clearActions();
        rda.addAction(Actions.sequence(Actions.fadeOut(MathUtils.random(0.5f, 1f), Interpolation.elastic), Actions.run(new Runnable() {
            @Override
            public void run() {
                randomSkill();
            }
        })));
    }

    private void randomSkill() {
        rda.clearActions();
        currSkill = MathUtils.random(1, 3);
        state = state_skill;
    }


    public void actSkill(float delta) {
        balthSkillOne.act(delta, currSkill);
    }

    /**
     * resets state and starts a new randomDelay (which calls a new state_skill)
     */
    public void nextSkill(){
        rda.clearActions();
        rda.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                newDelay();
            }
        })));
        state = s_delay;
    }
}
