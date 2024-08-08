package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.CoinAnimActor;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.PAD_TB_S;

/**
 * @author = Keeyzar on 06.02.2017.
 */
public abstract class Def_PlayerSkill implements PlayerSkills {
    //modified via setter/getter
    private int currentLevel = 0;
    private boolean locked = true;

    /**
     * Look in {@link SkillsVars}
     */
    String identifier_name;
    String displayName;

    /**
     * default is false
     */
    boolean isPassive = false;
    /**
     * the maximumlevel
     */
    int maxLevel;

    //maps
    IntMap<Integer> levelToCost;
    IntFloatMap levelToBoostValue;

    /**
     * can be set with identifier_name = ...
     */
    public String getIdentifier_name() {
        if(identifier_name == null){
            throw new RuntimeException("Name should be set in init!");
        }
        return identifier_name;
    }

    @Override
    public String getDisplayName(){
        return ERQAssets.LANG.format(displayName);
    }

    /**
     * default is false, set isPassive bool, if otherwise.
     */
    @Override
    public boolean isPassive() {
        return isPassive;
    }

    @Override
    public boolean isMaxLevel() {
        return currentLevel == maxLevel;
    }

    @Override
    public int getCostsForNextLevel() {
        return levelToCost.get(currentLevel + 1, 0);
    }

    @Override
    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public float getBoostForCurrentLevel() {
        return levelToBoostValue.get(currentLevel, 0);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    float getBoostForNextLevel(){
        return levelToBoostValue.get(currentLevel + 1, 0f);
    }

    @Override
    public void activate(Player player) {}

    @Override
    public float getProgress(Player player) {
        return 0;
    }

    @Override
    public void dispose() {
    }


    /**
     * Creates a regular infotable;
     */
    public static void createSkillsInfos(PlayerSkills playerSkill, int actualCoins, Table table){
        Skin skin = ERQAssets.SKIN;

        table.reset();
        //row 1
        table.add(new Label(playerSkill.getIdentifier_name(), skin)).pad(0, PAD_TB_S, PAD_TB_S, PAD_TB_S).colspan(3).row();

        //row 2
        table.add(new Label(LANG.format("upgrade_dialog_actual_level"), skin)).width(100).padLeft(PAD_TB_S).padRight(PAD_TB_S).left();
        table.add(new Label(playerSkill.getCurrentLevel() + "/" + playerSkill.getMaxLevel(), skin)).padLeft(PAD_TB_S).padRight(PAD_TB_S).left().expandX().colspan(2).row();

        //row 3
        int costs = playerSkill.getCostsForNextLevel();

        table.add(new Label(LANG.format("upgrade_dialog_actual_costs"), skin)).width(100).padLeft(PAD_TB_S).padRight(PAD_TB_S).left();
        table.add(new CoinAnimActor()).size(30, 30).pad(PAD_TB_S).left();
        final Label costsLabel = new Label("" + costs, skin);
        table.add(costsLabel).left().expandX().padLeft(0).padRight(PAD_TB_S).row();

        //row 4
        Label label = new Label(playerSkill.getShortInfoText(), skin);
        label.setWrap(true);
        table.add(label).grow().left().colspan(3).padTop(PAD_TB_S).padLeft(PAD_TB_S).padRight(PAD_TB_S).row();

        table.add("").grow().colspan(3);

        MenuUtils.colorLabelDependingOnCosts(costs, actualCoins, costsLabel);
    }
}
