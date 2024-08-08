package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.*;
import de.keeyzar.earthragequit.saving.Safeable;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class SkillsVerwalter implements Safeable {
    SkillSaferLoader skillSafer;
    Array<PlayerSkills> playerSkillsArray;

    //aktuelle Skills, von 1 bis 4
    Map<Integer, PlayerSkills> act_PlayerskillsMap;
    //Status welche Skillplätze gelockt sind
    public Map<Integer, Boolean> lockedStatus;


    public SkillsVerwalter(ERQGame game){
        playerSkillsArray = new Array<PlayerSkills>();
        playerSkillsArray.add(new PlayerSkill_Magnet());
        playerSkillsArray.add(new PlayerSkill_MegaBoost());
        playerSkillsArray.add(new PlayerSkill_Shield());
        playerSkillsArray.add(new PlayerSkill_Radar());
        //        playerSkillsArray.add(new PlayerSkill_Lightning());

        act_PlayerskillsMap = new HashMap<Integer, PlayerSkills>();
        act_PlayerskillsMap.put(1, null);
        act_PlayerskillsMap.put(2, null);
        act_PlayerskillsMap.put(3, null);
        act_PlayerskillsMap.put(4, null);

        lockedStatus = new HashMap<Integer, Boolean>();
        lockedStatus.put(1, true);
        lockedStatus.put(2, true);
        lockedStatus.put(3, true);
        lockedStatus.put(4, true);

        skillSafer = new SkillSaferLoader(this);
        skillSafer.load();

        //FIXME REMOVE BEFORE RELEASE
//        playerSkillsArray.get(0).setLocked(false);
//        playerSkillsArray.get(1).setLocked(false);
//        playerSkillsArray.get(2).setLocked(false);
//        playerSkillsArray.get(3).setLocked(false);
//        lockedStatus.put(1, false);
//        lockedStatus.put(2, false);
//        lockedStatus.put(3, false);
//        lockedStatus.put(4, false);
//        playerSkillsArray.get(0).setCurrentLevel(2);
//        playerSkillsArray.get(1).setCurrentLevel(2);
//        playerSkillsArray.get(2).setCurrentLevel(2);
//        playerSkillsArray.get(3).setCurrentLevel(2);
//        game.getGlobalPlayerInformation().setCoins(5000);
    }

    public Array<PlayerSkills> getPlayerSkillsArray() {
        return playerSkillsArray;
    }

    public SpriteDrawable getSpriteDrawableFromSkill(String skillName){
        TextureAtlas textureAtlas = ERQAssets.MANAGER.get(AssetVariables.TEXTURE_ATLAS_GAME, TextureAtlas.class);
        Sprite sprite = textureAtlas.createSprite("skills/" + skillName.toLowerCase());
        return new SpriteDrawable(sprite);
    }

    public SpriteDrawable getSpriteDrawableFromActSkill(int place){
        boolean locked = false;
        String name = "";

        if(lockedStatus.get(place)){
            locked = true;
        }
        if(act_PlayerskillsMap.get(place) != null){
            name = act_PlayerskillsMap.get(place).getIdentifier_name();
        }
        if(locked) {
            return new SpriteDrawable(
                    ERQAssets.MANAGER.get(AssetVariables.TEXTURE_ATLAS_GAME, TextureAtlas.class)
                            .createSprite(TextureAtlasVariables.ICON_LOCK));
        } else if(name.equals("")){
            return new SpriteDrawable(
                    ERQAssets.MANAGER.get(AssetVariables.TEXTURE_ATLAS_GAME, TextureAtlas.class)
                            .createSprite(TextureAtlasVariables.ICON_QUESTION_MARK));
        }else{
            return getSpriteDrawableFromSkill(name);
        }


    }


    public PlayerSkills getSkillByName(String name) {
        for(PlayerSkills playerSkills : playerSkillsArray){
            if(playerSkills.getIdentifier_name().equals(name)){
                return playerSkills;
            }
        }
        return null;
    }

    /**
     * returns the place, which is free, or -1 if nothing is unlocked and free
     * @return -1 or free, unlocked skillPlace
     */
    public int getFreePlace(){
        int freePlace = -1;
        for(int i = 1; i<=lockedStatus.size(); i++){
            if(isLocked(i)) continue;
            if(act_PlayerskillsMap.get(i) == null){
                freePlace = i;
                break;
            }
        }
        return freePlace;
    }

    public void setPlayerSkills(int place, String currentItem) {
        if(currentItem == null){
            act_PlayerskillsMap.put(place, null);
        }
        PlayerSkills skill = getSkillByName(currentItem);
        if(skill == null) return;
        for(int i = 1; i<5; i++){
            if(i == place){
                continue;
            }
            PlayerSkills skillToCheck = act_PlayerskillsMap.get(i);
            if(skillToCheck != null && skillToCheck.getIdentifier_name().equals(skill.getIdentifier_name())){
                if(skill != null){
                    act_PlayerskillsMap.put(i, act_PlayerskillsMap.get(place));
                } else {
                    act_PlayerskillsMap.put(i, null);
                }
            }
        }
        act_PlayerskillsMap.put(place, skill);
    }

    public Map<Integer, PlayerSkills> getAct_PlayerskillsMap() {
        return act_PlayerskillsMap;
    }

    @Override
    public void save() {
        skillSafer.safe();
    }

    @Override
    public void reset() {
        skillSafer.reset();
        skillSafer.load();
    }

    public void unlock(int i) {
        lockedStatus.put(i, false);
    }

    public boolean isLocked(int i){
        return lockedStatus.get(i);
    }
}
