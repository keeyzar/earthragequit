package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author = Keeyzar on 23.03.2016
 */
public class Ingame_Megaboost implements IngameSkill {
    private Player player;
    private Action startMegaboostAction;
    private Action endMegaboostAction;
    private Action completeAction;
    private float currProgress = 0;
    private float maxUSpeed;
    private float lerpDown;
    private boolean hasStarted = false;
    private Actor actor;

    private float multiplikator;
    private float timeTillFullBoost = 2f;
    private float playerUSpeed = 0f;
    private float timer = 0;
    private DelayAction delay;

    private boolean activated = false;
    private boolean over = false;


    @Override
    public void init(ERQGame game, World world, Player player) {
        this.player = player;
        maxUSpeed = player.getPlayerCalculatedVars().getU_speed_max();
        playerUSpeed = player.getPlayerCalculatedVars().getU_speed_power();
        actor = new Actor();
        multiplikator = player.getPlayerCalculatedVars().getMegaboostMultiplikator();
        initStartMegaBoostAction();
        initEndMegaBoostAction();
        initCompleteAction();
    }


    public void start(){
        completeAction.restart();
        actor.addAction(completeAction);
        hasStarted = true;
        activated = true;
    }

    public void act(float delta){
        if(hasStarted){
            timer += delta;
            actor.act(delta);
        }
    }

    @Override
    public void activate() {
        start();
    }

    @Override
    public String getName() {
        return SkillsVars.MEGABOOST;
    }


    private void initCompleteAction() {
        delay = delay(player.getPlayerCalculatedVars().getMegaboostTime() * 2);
        SequenceAction sequence = sequence(run(new Runnable(){
                    @Override
                    public void run() {
                        player.getPlayerCurrentStates().setHasMegaboost(true);
                    }
                    }),
                startMegaboostAction,
                delay,
                run(new Runnable() {
                    @Override
                    public void run() {
                                currProgress = 0;
                        player.getPlayerCurrentStates().setHasMegaboost(false);
                        player.getPlayerCurrentStates().setShakingDecrease(0.003f);
                    }
                    }),
                endMegaboostAction, run(new Runnable() {
            @Override
            public void run() {
                hasStarted = false;
            }
        }));
        completeAction = sequence;
        actor.addAction(completeAction);
    }

    private void initStartMegaBoostAction() {
        startMegaboostAction = sequence(run(new Runnable() {
            @Override
            public void run() {
                player.getPlayerCalculatedVars().setU_speed_power(playerUSpeed * multiplikator);
                player.getPlayerCalculatedVars().setU_speed_max(maxUSpeed * multiplikator);
                player.getPlayerCurrentStates().setShaking(player.getPlayerCalculatedVars().getMegaboostTime() * 2, 0.1f, 0);
            }
        }));


    }

    private void initEndMegaBoostAction(){
        //create lerp downwards;
        final SequenceAction lerpSpeedDownOnce = sequence(run(new Runnable() {
            @Override
            public void run() {
                lerpDown = MathUtils.lerp(maxUSpeed * multiplikator, maxUSpeed, currProgress);
                player.getPlayerCalculatedVars().setU_speed_max(lerpDown);
            }
        }), delay(1f/(timeTillFullBoost * 15f)), run(new Runnable() {
                            @Override
                            public void run() {
                                currProgress += 1f/(timeTillFullBoost * 30f);
                            }
                        }
        ));
        RepeatAction lerpSpeedDown = repeat((int)timeTillFullBoost * 30, lerpSpeedDownOnce);
        RunnableAction speedDown = run(new Runnable() {
            @Override
            public void run() {
                player.getPlayerCalculatedVars().setU_speed_power(playerUSpeed);
                over = true;
            }
        });
        endMegaboostAction = sequence(speedDown, lerpSpeedDown);
    }

    public float getProgress() {
        if(!activated) return 1f;
        if(over) return 0f;
        return 1f - delay.getTime() / delay.getDuration();
    }
}
