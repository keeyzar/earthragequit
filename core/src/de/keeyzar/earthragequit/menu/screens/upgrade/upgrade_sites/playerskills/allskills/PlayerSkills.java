package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;

/**
 * interface for a playerskill
 * Created by Keeyzar on 12.02.2016.
 */
public interface PlayerSkills {

    /**
     *
     * @return the skillname
     */
    String getIdentifier_name();

    /**
     * @return the display name
     */
    String getDisplayName();

    /**
     * whether or not this skill is passiv
     */
    boolean isPassive();

    /**
     * @return the cost for the current level
     */
    int getCostsForNextLevel();

    /**
     * return the current level
     */
    int getCurrentLevel();


    /**
     * @return short info text about skill
     */
    String getShortInfoText();

    /**
     * get skill describing texts
     * @return text which describes the skill
     */
    String getSkillDescribingText();

    /**
     * set current level
     * @param currentLevel which level
     */
    void setCurrentLevel(int currentLevel);

    /**
     *
     * @return whether or not the skill is locked
     */
    boolean isLocked();

    /**
     * set lock state from skill
     * @param locked if locked
     */
    void setLocked(boolean locked);


    /**
     * activates this skill
     * @param player on which
     */
    void activate(Player player);

    /**
     * @return the boost which this skill give
     */
    float getBoostForCurrentLevel();

    /**
     * @return maximum level
     */
    int getMaxLevel();

    /**
     * do not do time depending upgdate of progress here! use actOnProgress
     * @return 0-1f
     */
    float getProgress(Player player);

    /**
     * clearUp
     */
    void dispose();

    IngameSkill getIngameSkill();

    boolean isMaxLevel();
}
