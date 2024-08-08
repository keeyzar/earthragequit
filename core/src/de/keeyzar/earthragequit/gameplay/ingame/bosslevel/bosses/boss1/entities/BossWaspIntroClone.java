package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class BossWaspIntroClone extends Actor {

    private BossWaspMother bossWaspMother;
    Sprite sprite;

    public BossWaspIntroClone(BossWaspMother bossWaspMother){
        this.bossWaspMother = bossWaspMother;
        setBounds(-3, 5, bossWaspMother.getWidth(), bossWaspMother.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        sprite = new Sprite(bossWaspMother.getCurrentSprite());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();

        Interpolation interpolation = randomInterpolation();
        Interpolation interpolation2 = randomInterpolation();


        ParallelAction parallel = Actions.parallel(Actions.moveBy(20, 0, 4),
                Actions.repeat(2, Actions.sequence(Actions.moveBy(0, 5, 1, interpolation),
                        Actions.moveBy(0, -5, 1, interpolation2))),
                Actions.sequence(Actions.sizeBy(2, 2, 2), Actions.sizeBy(-2, -2, 2)));

         //back to place movement
        MoveToAction action2 = Actions.moveTo(MathUtils.random(3, 17), 7, 1);

        SequenceAction sequence = Actions.sequence(parallel, action2);
        addAction(sequence);

    }

    private Interpolation randomInterpolation() {
        int i = MathUtils.random(1, 5);
        switch (i){
            case 1:
                return Interpolation.bounce;
            case 2:
                return Interpolation.pow5;
            case 3:
                return Interpolation.fade;
            case 4:
                return Interpolation.swing;
            case 5:
                return Interpolation.pow5In;
        }
        return Interpolation.linear;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());
        if(!hasActions()){
            bossWaspMother.body.setTransform(getX(), getY(), 0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
