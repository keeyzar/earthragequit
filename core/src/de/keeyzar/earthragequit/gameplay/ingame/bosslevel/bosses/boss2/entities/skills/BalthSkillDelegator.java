package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.Balthazar;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 01.04.2016
 */
public class BalthSkillDelegator {
    private final Balthazar balthazar;
    private final World world;
    private final Player player;
    int counter = 0;

    public BalthSkillDelegator(Balthazar balthazar, World world, Player player){
        this.balthazar = balthazar;
        this.world = world;
        this.player = player;

    }

    private int s = 1;
    private final int s_Start = 1;
    private final int s_Fin = 2;

    public void act(float delta, int state){
        if(s == s_Start){
            startSkill(state);
        } else if(s== s_Fin){
            balthazar.nextSkill();
            s = s_Start;
        }
    }

    public void startSkill(int whichSkill){
        switch (whichSkill){
            case 1:
                balthazar.getStage().addActor(new Schrott2Improved(balthazar, world, player, new FinishListener() {
                    @Override
                    public void finished() {
                        s = s_Fin;
                        }
                }));
                break;
            case 2:
                FinishListener finishListener;
                if(counter < 2){
                    counter++;
                    finishListener = new FinishListener() {
                        @Override
                        public void finished() {
                            s = s_Start;
                        }
                    };
                } else {
                    counter = 0;
                    finishListener = new FinishListener() {
                        @Override
                        public void finished() {
                            s = s_Fin;
                        }
                    };
                }
                balthazar.getStage().addActor(new RingDestroyer(world, player, finishListener));
                break;
            case 3:
                balthazar.getStage().addActor(new LineDestroyer(world, player, (OrthographicCamera) balthazar.getStage().getCamera(), new FinishListener() {
                    @Override
                    public void finished() {
                        s = s_Fin;
                    }
                }));
                break;
        }
        s = -1;
    }
}
