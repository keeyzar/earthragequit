package de.keeyzar.earthragequit;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.achievements.AchievementVerwalter;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.loading.LoadingScreen;
import de.keeyzar.earthragequit.menu.screens.level_selection.LevelVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.SkillsVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.SkinVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.StatsVerwalter;
import de.keeyzar.earthragequit.saving.Safeable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.tools.BackNavigationInterface;
import de.keeyzar.earthragequit.tools.ERQTools;
import de.keeyzar.earthragequit.tools.ToastInterface;
import de.keeyzar.earthragequit.tools.TweeningAccessors.ActorAccessor;
import de.keeyzar.earthragequit.tutorial.TutorialVerwalter;

/**
 * Loads via ERQAssets relevant data for menu etc
 */
public class ERQGame extends Game {
	TweenManager manager;

	private MusicHandler musicHandler;
	private SkinVerwalter skinVerwalter;
	private GlobalPlayerInformation globalPlayerInformation;
	private StatsVerwalter statsVerwalter;
	private AchievementVerwalter achievementVerwalter;
	private TutorialVerwalter tutorialVerwalter;

	private Array<Safeable> safeableArray;
	private SkillsVerwalter skillsVerwalter;
    private LevelVerwalter levelVerwalter;


	//extra
	private ToastInterface toastInterface;
	private ERQTools erqTools;

	/**
	 *
	 * @param toi can be null (if not android)
     */
	public ERQGame(ToastInterface toi){
		this.toastInterface = toi;
	}


    @Override
	public void create () {
		ERQAssets.create();
		ERQAssets.loadLocalization(ERQAssets.COUNTRY_CODE_EN);


		musicHandler = new MusicHandler();
		skinVerwalter = new SkinVerwalter();
		globalPlayerInformation = new GlobalPlayerInformation();
		statsVerwalter = new StatsVerwalter();
		skillsVerwalter = new SkillsVerwalter(this);
		achievementVerwalter = new AchievementVerwalter(this);
		levelVerwalter = new LevelVerwalter();
		tutorialVerwalter = new TutorialVerwalter(this);
		setScreen(new LoadingScreen(this));

		safeableArray = new Array<Safeable>();
		safeableArray.add(musicHandler);
		safeableArray.add(tutorialVerwalter);
		safeableArray.add(levelVerwalter);
		safeableArray.add(statsVerwalter);
		safeableArray.add(skillsVerwalter);
		safeableArray.add(skinVerwalter);
		safeableArray.add(globalPlayerInformation);
		safeableArray.add(achievementVerwalter);

		//extra tools, like catch back key globally
		erqTools = new ERQTools(this, toastInterface);

		//Tweening
		manager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		Tween.setWaypointsLimit(10);
	}



	@Override
	public void render () {
		super.render();
		manager.update(Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyJustPressed(Input.Keys.C) || Gdx.input.isTouched(7)){
		    globalPlayerInformation.addCoins(100);
        }
        if(Gdx.input.isTouched(9)){
			resetPlayerData();
		}
		//checks for the back key
		erqTools.catchBackKey(Gdx.graphics.getDeltaTime());
		//if a new Achievement was gathered.
		erqTools.showAchievement(Gdx.graphics.getDeltaTime());
		musicHandler.actOnMusic(Gdx.graphics.getDeltaTime());
		AVars.TIME_PLAYED += Gdx.graphics.getRawDeltaTime();
	}

	@Override
	public void pause() {
		save();
		musicHandler.pauseMusic();
	}

	@Override
	public void resume() {
		musicHandler.resumeMusic();
	}

	@Override
	public void dispose() {
        save();
        ERQAssets.dispose();
        erqTools.dispose();
    }

	@Override
	public void resize(int width, int height) {
        erqTools.resize(width, height);
	}

	public void playMenuMusic() {
		musicHandler.playMusic(MusicHandler.MENU_MUSIC);
	}

	public SkinVerwalter getSkinVerwalter() {
		return skinVerwalter;
	}

	public GlobalPlayerInformation getGlobalPlayerInformation() {
		return globalPlayerInformation;
	}

	public int getCoins() {
		return globalPlayerInformation.getCoins();
	}

	public void addCoins(int coins) {
		globalPlayerInformation.addCoins(coins);
	}

	public StatsVerwalter getStatsVerwalter() {
		return statsVerwalter;
	}

	public void save() {
		for(Safeable safeable : safeableArray){
			safeable.save();
		}
	}

	public SkillsVerwalter getSkillsVerwalter() {
		return skillsVerwalter;
	}

	public MusicHandler getMusicHandler() {
		return musicHandler;
	}

	public AchievementVerwalter getAchievementVerwalter() {
		return achievementVerwalter;
	}

	public void resetPlayerData() {
		for(Safeable safeable : safeableArray){
			safeable.reset();
		}
	}

    public LevelVerwalter getLevelVerwalter() {
        return levelVerwalter;
    }

	public TutorialVerwalter getTutorialVerwalter() {
		return tutorialVerwalter;
	}

	/**
	 * if a new Achievement was gathered.
	 */
	public void newAchievement(Achievement achievement){
		erqTools.newAchievement(achievement);
	}

	public boolean hasEnoughCoins(float price){
		return price <= getCoins();
	}

	public void registerBackNavigation(BackNavigationInterface backNavigationInterface){
		erqTools.registerBackNavigation(backNavigationInterface);
	}

    public void unregisterBackNavigation(){
        erqTools.unregisterBackNavigation();
    }

	public TweenManager getTweenManager(){
        return manager;
    }

}
