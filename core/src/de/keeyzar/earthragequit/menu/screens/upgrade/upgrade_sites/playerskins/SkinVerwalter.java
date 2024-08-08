package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins.*;
import de.keeyzar.earthragequit.saving.Safeable;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * Created by Keeyzar on 10.02.2016.
 */
public class SkinVerwalter implements Safeable{
    private Array<ShipSkin> skinArray;
    private int actualSkin;
    private SkinSaferLoader skinSaferLoader;

    public SkinVerwalter(){
        skinArray = new Array<ShipSkin>();
        skinArray.add(new Skin_Default());
        skinArray.add(new Skin_New_1());
        skinArray.add(new Skin_New_2());
        skinArray.add(new Skin_New_3());
        actualSkin = SkinVars.SKIN_DEFAULT;

        skinSaferLoader = new SkinSaferLoader(this);
        skinSaferLoader.load();
    }

    public Array<ShipSkin> getSkinArray() {
        return skinArray;
    }


    public ShipSkin getActualSkin() {
        return getShipSkinById(actualSkin);
    }

    /**
     *
     * @param id
     * @return NULL if not found
     */
    public ShipSkin getShipSkinById(int id){
        for(ShipSkin shipSkin : skinArray){
            if(shipSkin.getSkinId() == id){
                return shipSkin;
            }
        }
        return null;
    }

    public void setActualSkin(int actualSkin) {
        this.actualSkin = actualSkin;
    }

    public SpriteDrawable getSkinImage(ShipSkin fromWhich){
        TextureAtlas textureAtlas = ERQAssets.T_ATLAS_GAME;
        Sprite sprite = textureAtlas.createSprite(TextureAtlasVariables.ROCKET_SKINS, fromWhich.getSkinId());
        return new SpriteDrawable(sprite);
    }

    public SpriteDrawable getSkinImageFromActualSkin(){
        TextureAtlas textureAtlas = ERQAssets.T_ATLAS_GAME;
        Sprite sprite = textureAtlas.createSprite(TextureAtlasVariables.ROCKET_SKINS, actualSkin);
        return new SpriteDrawable(sprite);
    }

    @Override
    public void save() {
        skinSaferLoader.safe();
    }

    @Override
    public void reset() {
        skinSaferLoader.reset();
        skinSaferLoader.load();
    }

    public SpriteDrawable getBestShipSkin() {
        return new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_SKINS, 7));
    }
}
