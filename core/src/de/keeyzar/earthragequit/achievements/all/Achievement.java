package de.keeyzar.earthragequit.achievements.all;

/**
 * @author = Keeyzar on 08.03.2016
 */
public interface Achievement {

    /**
     * is achiev finished?
     */
    boolean isFinished();

    /**
     * set the state of the achievement to finished or not
     */
    void setFinished(boolean finished);

    /**
     * the full achievement text: "You just destroyed 5 bees, nice!"
     */
    String getAchievementText();

    /**
     * if this method is called, the achievement is applied! do your checks like "is already applied" in here
     */
    void applyAchievement();

    /**
     * how much coins are given by Achievement can be 0
     */
    int coinsGatheredByAchievement();

    /**
     * Title of achievement should be quite short. or may be ellipsed. (Your title is too lo...(ng))
     */
    String getTitle();

    /**
     * Something like:
     * <ul>
     *     <li>
     *         +50 coins
     *     </li>
     *     <li>
     *         +1 skin
     *     </li>
     *     <li>
     *         +1 skillplace
     *     </li>
     * </ul>
     * @return
     */
    String getShortRewardDesc();

    void checkConditionsAndApplyIfTrue();

    /**
     * 1 = 1percent 100 = 100percent
     */
    int getProgress();

    boolean shouldHide();
}
