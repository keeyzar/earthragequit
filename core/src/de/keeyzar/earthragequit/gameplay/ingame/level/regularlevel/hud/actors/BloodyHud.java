package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * This is only a sprite, which is a bloody hud. Activateable via bloodyHud.activate();
 * $author Keeyzar on 21.08.2016
 */
public class BloodyHud extends Actor {
    Sprite sprite;
    Action action;


    public BloodyHud(Player player){
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.HUD_BLOODY);
        sprite.setPosition(0, 0);
        sprite.setAlpha(0);
        getColor().a = 0;
        sprite.setSize(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);

        player.setHitListener(new Player.HitListener() {
            @Override
            public void playerGotHit() {
                activate();
            }
        });
    }

    private void createAction() {
        float alpha = 0.3f;
        action = Actions.sequence(Actions.alpha(alpha + 0.3f, 0.1f), Actions.repeat(2, Actions.sequence(
                Actions.alpha(alpha - 0.0f, 0.1f), Actions.alpha(alpha + 0.2f, 0.1f), Actions.alpha(alpha - 0.1f, 0.1f),
                Actions.alpha(alpha + 0.1f, 0.1f))), Actions.fadeOut(0.1f, Interpolation.swing));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setAlpha(getColor().a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void activate(){
        if(!hasActions()) {
            createAction();
            addAction(action);
        }
    }
}
