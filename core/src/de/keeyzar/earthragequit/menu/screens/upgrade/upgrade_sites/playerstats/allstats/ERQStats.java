package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.CoinAnimActor;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public abstract class ERQStats {
    private Table tableInfoText;
    private int current_Level = 0;
    String IDENTIFIER_NAME;
    String displayName;
    public boolean locked = false;
    IntMap<Integer> levelToCosts;
    IntFloatMap levelToAttributeBoost;
    String shortDescription;
    String longDescription;

    public int getCurrentLevel() {
        return current_Level;
    }

    public String getIdentifierName() {
        return IDENTIFIER_NAME;
    }

    public int getCostsForNextLevel() {
        return levelToCosts.get(current_Level + 1, 0);
    }

    public void setCurrentLevel(int level) {
        tableInfoText = null;
        current_Level = level;
    }

    public Float getBoost(){
        return levelToAttributeBoost.get(current_Level, 0);
    }

    /**
     * if this stat should be lockable, override this to a normal setter.
     */
    public void setLocked(boolean locked) {
        //do Nothing, because most of the stats are not locked from early on.
    }

    /**
     * @return if the stat is locked
     */
    public boolean isLocked() {
        return locked;
    }

    public Actor addInfoText(ERQGame game) {
        actualizeInfoTable(game);
        return tableInfoText;
    }

    private void actualizeInfoTable(ERQGame game) {
        Skin skin = ERQAssets.SKIN;
        final int costsAsInt = getCostsForNextLevel();
        final Label costAsLabel = new Label(costsAsInt + "", skin);

        if(tableInfoText == null) {
            tableInfoText = new Table(skin);
        }
        tableInfoText.reset();
        tableInfoText.defaults().padLeft(PAD_LR_S).padRight(PAD_LR_S).padBottom(PAD_TB_S);

        //row 1
        tableInfoText.add(new Label(getDisplayName(), skin)).colspan(3).expandX().row();

        //row 2
        tableInfoText.add(new Label(LANG.format("upgrades_stats_actual_level"), skin)).left().expandX();
        tableInfoText.add(new Label(getCurrentLevel() + "/" + getMaxLevel(), skin)).width(200).expandX().left().colspan(2).row();

        //row 3
        tableInfoText.add(new Label(LANG.format("upgrades_stats_actual_costs"), skin)).left().growX();
        tableInfoText.add(new CoinAnimActor()).size(30, 30).right();
        tableInfoText.add(costAsLabel).padLeft(0).left().width(200).expandX().row();

        //row 4
        Label label = new Label(getShortDescription(), skin);
        label.setWrap(true);
        tableInfoText.add(label).pad(PAD_TB_S).padTop(PAD_TB_N).colspan(3).growX().row();
        tableInfoText.add("").grow().colspan(3); //push whole table to top (like a spacer)

        MenuUtils.colorLabelDependingOnCosts(costsAsInt, game.getCoins(), costAsLabel);
    }

    /**
     * short description for info table
     * @return
     */
    private String getShortDescription(){ return ERQAssets.LANG.format(shortDescription); }

    public String getDisplayName(){
        return ERQAssets.LANG.format(displayName);
    }

    /**
     * long description for info field
     * @return
     */
    public String getLongDescription(){
        return ERQAssets.LANG.format(longDescription);
    }

    /**
     * @return if stat is max level
     */
    public boolean isMaxLevel(){
        return current_Level == getMaxLevel();
    }

    /**
     * @return stat max level
     */
    public int getMaxLevel() {
        return levelToCosts.size;
    }
}
