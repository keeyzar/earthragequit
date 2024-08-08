package de.keeyzar.earthragequit.tutorial;

import com.badlogic.gdx.utils.Array;
import com.github.czyzby.kiwi.util.tuple.mutable.MutableTriple;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.saving.Safeable;
import de.keeyzar.earthragequit.story.regular_story.first_info_about_ronax.StoryBoard_FirstInfoAboutRonax;
import de.keeyzar.earthragequit.story.regular_story.first_meeting.StoryBoard_FirstMeeting;
import de.keeyzar.earthragequit.story.regular_story.landing_story.StoryBoard_LandingStory;
import de.keeyzar.earthragequit.story.regular_story.open_new_dimension.StoryBoard_NewDimension;
import de.keeyzar.earthragequit.tutorial.movement.GamePlayTutorialScreen;

import java.util.Iterator;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class TutorialVerwalter implements Safeable {
    private final ERQGame game;
    private StorySaferLoader storySaferLoader;

    //integer = story element, 1. bool = is absolved, 2. bool = should be solved.
    private Array<MutableTriple<Integer, Boolean, Boolean>> storys;
    private boolean redoLevel = false;

    public TutorialVerwalter(ERQGame game) {
        this.game = game;
        storySaferLoader = new StorySaferLoader(this);
        storys = new Array<MutableTriple<Integer, Boolean, Boolean>>();
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.TUTORIAL, false, true));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.ROCKET_EXPLODED, false, false));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.FIRST_MEETING, false, false));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.UPGRADE_INTRODUCTION, false, false));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.FIRST_INFO_ABOUT_RONAX, false, false));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.SKILLS_INTRODUCTION, false, false));
        storys.add(new MutableTriple<Integer, Boolean, Boolean>(TVars.NEW_DIMENSION, false, false));
        storySaferLoader.load();

        //FIXME REMOVE BEFORE RELEASE
//        setLevelAbsolved(1, true);
//        setLevelAbsolved(2, true);
//        setLevelAbsolved(3, true);
//        setLevelAbsolved(4, true);
//        setLevelAbsolved(5, true);
//        setLevelAbsolved(6, true);
//        setLevelAbsolved(7, true);
//        setLevelAbsolved(TVars.UPGRADE_INTRODUCTION, false);
//        setLevelAbsolved(TVars.SKILLS_INTRODUCTION, false);
//        setLevelShouldBeAbsolved(TVars.NEW_DIMENSION, true);
//        setLevelShouldBeAbsolved(TVars.SKILLS_INTRODUCTION, false);
//        setLevelAbsolved(6, false);
////        setLevelAbsolved(3, true);
////        setLevelAbsolved(4, true);
////        game.getGlobalPlayerInformation().setCoins(10);
////        setLevelAbsolved(5, true);
////        setLevelAbsolved(6, true);
    }

    public boolean shouldShowStory(){
        //ask for highest tutorial
        for (MutableTriple<Integer, Boolean, Boolean> next : storys) {
            if (next.getFirst() != TVars.UPGRADE_INTRODUCTION && next.getFirst() != TVars.SKILLS_INTRODUCTION && next.getThird()) {
                return true;
            }
        }
        return false;
    }

    /**
     * when called, allows to do a single screen once (intro, etc.)
     */
    public void redoLevel(){
        redoLevel = true;
    }

    public void startLevel(ERQGame game, Transitionable transitionAbleScreen){
        Iterator<MutableTriple<Integer, Boolean, Boolean>> iterator = storys.iterator();
        int smallestLevel = Integer.MAX_VALUE;


        while(iterator.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iterator.next();
            // if this one should be solved
            if(next.getThird()){
                //check if the level is smaller than the last one. (to play in row)
                if(next.getFirst() < smallestLevel){
                    smallestLevel = next.getFirst();
                }
            }
        }
        if(smallestLevel >= Integer.MAX_VALUE){
            //check if the method is called with the correct parameter if not, go to menu
            game.setScreen(new TransitionScreen(game, transitionAbleScreen, new MainMenuScreen(game)));
            return;
        }
        if(redoLevel){
            //ensure, that if the player cancels the game and he was in
            //the same tutorial the second time, that the smallest level is set to should be absolved false
            setLevelAbsolved(smallestLevel, true);
        }

        Transitionable transitionable = null;
        switch (smallestLevel){
            case TVars.TUTORIAL:
                transitionable = new GamePlayTutorialScreen(game);
                break;
            case TVars.ROCKET_EXPLODED:
                transitionable = new StoryBoard_LandingStory(game);
                break;
            case TVars.FIRST_MEETING:
                transitionable = new StoryBoard_FirstMeeting(game);
                break;
            case TVars.UPGRADE_INTRODUCTION:
                //do Nothing
                return;
            case TVars.FIRST_INFO_ABOUT_RONAX:
                transitionable = new StoryBoard_FirstInfoAboutRonax(game);
                break;
            case TVars.SKILLS_INTRODUCTION:
                //do Nothing
                return;
            case TVars.NEW_DIMENSION:
                transitionable = new StoryBoard_NewDimension(game);
                break;
        }
        game.setScreen(new TransitionScreen(game, transitionAbleScreen, transitionable));
    }

    /**
     * sets the current level absolved, and should be absolved false, IF
     * and only IF param isAbsolved is true
     * @param whichLevel should be setted
     * @param isAbsolved if it is absolved or not
     */
    public void setLevelAbsolved(int whichLevel, boolean isAbsolved){
        Iterator<MutableTriple<Integer, Boolean, Boolean>> iterator = storys.iterator();

        while(iterator.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iterator.next();
            // if this one should be solved
            if(next.getFirst() == whichLevel){
                if(isAbsolved){
                    next.setThird(!isAbsolved);
                }
                next.setSecond(isAbsolved);
            }
        }
    }

    public void setLevelShouldBeAbsolved(int whichLevel, boolean shouldBeAbsolved){
        if(redoLevel){
            redoLevel = false;
            return; // checks whether the last run was a _RERUN_ or not. if yes, break.
        }
        Iterator<MutableTriple<Integer, Boolean, Boolean>> iterator = storys.iterator();

        while(iterator.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iterator.next();
            // if this one should be solved
            if(next.getFirst() == whichLevel){
                next.setThird(shouldBeAbsolved);
            }
        }
    }

    public boolean isAbsolved(int whichLevel){
        Iterator<MutableTriple<Integer, Boolean, Boolean>> iterator = storys.iterator();


        while(iterator.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iterator.next();
            // if this one should be solved
            if(next.getFirst() == whichLevel){
                return next.getSecond();
            }
        }
        System.err.println("Error - StoryManager.isAbsolved only accepts Ints out of TVars class");
        return false;
    }
    public boolean shouldBeAbsolved(int whichLevel){
        Iterator<MutableTriple<Integer, Boolean, Boolean>> iterator = storys.iterator();


        while(iterator.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iterator.next();
            // if this one should be solved
            if(next.getFirst() == whichLevel){
                return next.getThird();
            }
        }
        throw new RuntimeException("Error - StoryManager.shouldBeAsbolved only accepts Ints out of TVars class");
    }


    @Override
    public void save() {
        storySaferLoader.safe();
    }

    @Override
    public void reset() {
        storySaferLoader.reset();
    }

    public Array<MutableTriple<Integer, Boolean, Boolean>> getStorys() {
        return storys;
    }

}
