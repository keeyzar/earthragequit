package de.keeyzar.earthragequit.story.regular_story.first_info_about_ronax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.story.regular_story.motherstory_screen.StoryScreen;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.tutorial.TutorialVerwalter;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.assets.ERQAssets.SKIN;

/**
 * This Storyboard shows some infos about Ronax. (He and Al' are talking
 * @author = Keeyzar on 23.03.2016
 */
public class StoryBoard_FirstInfoAboutRonax extends StoryScreen {
//    SB_Background sb_background;
//    WorkingStage workingStage;
    private ERQGame game;
    TextButton tbSkip;

    public StoryBoard_FirstInfoAboutRonax(ERQGame game) {
        super(game);
        this.game = game;


        game.getMusicHandler().playMusic(MusicHandler.STORY_MUSIC);

    }

    @Override
    public void init() {
//        sb_background = new SB_Background(game);
//        workingStage = new WorkingStage(game);
//        setBackgroundStage(sb_background);
//        setWorkingStage(workingStage);
        state = S_SHOW_TEXT_1;
        tS.getTW().setBounds(0, 0, tS.getTW().getWidth(), tS.getTW().getHeight() * 1.5f);


        tbSkip = new TextButton(LANG.format("tutorial_story_skip_story_button"), SKIN);
        tbSkip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                state = S_GO_TO_SKILLS_PAGE;
            }
        });

        tbSkip.setSize(300, 100);
        tbSkip.setPosition(tS.getCamera().position.x - tbSkip.getWidth() / 2, tS.getCamera().position.y + tS.getCamera().viewportHeight / 2 - tbSkip.getHeight() * 1.5f);
        tS.addActor(tbSkip);


        Gdx.input.setInputProcessor(tS);
    }

    @Override
    public void show() {
        //do noddinHill.
    }


    private final int S_SHOW_TEXT_1 = 1;
    private final int S_SHOW_TEXT_2 = 2;
    private final int S_SHOW_TEXT_3 = 3;
    private final int S_SHOW_TEXT_4 = 4;
    private final int S_SHOW_TEXT_5 = 5;
    private final int S_SHOW_TEXT_6 = 6;
    private final int S_SHOW_TEXT_7 = 7;
    private final int S_GO_TO_SKILLS_PAGE = 8;
    @Override
    public void checkState() {
        switch (state){
            case S_SHOW_TEXT_1:
                text1();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_2:
                text2();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_3:
                text3();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_4:
                text4();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_5:
                text5();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_6:
                text6();
                state = S_NOTHING;
                break;
            case S_SHOW_TEXT_7:
                text7();
                state = S_NOTHING;
                break;
            case S_GO_TO_SKILLS_PAGE:
                goToSkillsPage();
                state = S_NOTHING;
        }
    }

    private void goToSkillsPage() {
        TutorialVerwalter tutorialVerwalter = game.getTutorialVerwalter();
        tutorialVerwalter.setLevelAbsolved(TVars.FIRST_INFO_ABOUT_RONAX, true);
        game.save();
    }


    private void text7(){
        String textToShow = "Al':\n" +
                "Man.. That sounds really bad. For your money problem. Well, as you killed that enemy some" +
                " minutes ago, you provided me enough materials to implement a magnet in your rocket. With" +
                " this you'll definitely gain your money faster. Let us look together into the \"Skill\" thing.";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT;
                nextState = S_NOTHING;
            }

            @Override
            public void TFadeOutFinish() {
                state = S_GO_TO_SKILLS_PAGE;
            }
        });
    }

    private void text6(){
        String textToShow = "Ronax:\n" +
                "You may think it's egoistical, but we were poor.. Really really poor, and he couldn't afford" +
                " to buy food on a regular basis, as he's now alone, he can pay his bills, and afford a normal live." +
                " And thats one of the most things I wish for him. His life should be troubleless as much as possible.";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_7;
            }
        });
    }
    private void text5(){
        String textToShow = "Ronax:\n" +
                "...And as we finished " +
                "he forced me to follow my dreams. He does not know, but I left him, so I can gather " +
                "galactical money to get him a vacation in a beatiful cluster, which is expensive as hell.";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_6;
            }
        });
    }

    private void text4() {
        String textToShow = "Ronax:\n" +
                "Now.. My father had cared for me, til' he got multiple diseases, which destroyed" +
                " his quality of live phenomenally. So, we started to built this rocket. As I gathered" +
                " the things outside in our galaxy, he started to built it at home...";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_5;
            }
        });
    }

    private void text3() {
        String textToShow = "Ronax:\n" +
                "So. I'm from far.. far away, out there.. It's this starcluster XY323\n" +
                "The rocket you've seen, I've built it with my father. We have a good relationship." +
                " It all started, when I was young.. My mother died when I was young. I know her only" +
                " by some memories.. And that memories fade away. It's so long ago, I forgot how her voice" +
                " sounded.";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_4;
            }
        });
    }

    private void text2() {
        String textToShow = "YOU:\n" +
                "Thank you Al'. First of all I wanna say you my.. I guess, you call it name." +
                "It's Ronax. I'm Ronax!";
        tS.newText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_3;
            }
        });
    }

    private void text1() {
        String textToShow = "Al':\n" +
                "It's impressive how fast you've learned our language! So.. The last days we" +
                " have talked only about me.. I'd love to hear something about you!";
        tS.startText(textToShow, new TWListener(){
            @Override
            public void TShown() {
                state=S_WAIT_NEXT_TEXT;
                nextState = S_SHOW_TEXT_2;
            }
        });
    }
}
