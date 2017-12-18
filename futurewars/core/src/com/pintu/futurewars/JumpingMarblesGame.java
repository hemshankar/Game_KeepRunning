package com.pintu.futurewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Screens.EndGameScreen;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.Screens.GiftsScreen;
import com.pintu.futurewars.Screens.PauseScreen;
import com.pintu.futurewars.Screens.StageDetailsScreen;
import com.pintu.futurewars.Screens.StagesScreen;
import com.pintu.futurewars.Screens.UpgradeScreen;
import com.pintu.futurewars.Screens.WelcomeScreen;
import com.pintu.futurewars.Utility.GameUtility;

public class JumpingMarblesGame extends Game {
	public static SpriteBatch batch;
	public AssetManager assetManager = null;//new AssetManager();
	public OrthographicCamera camera;
	//Viewport handles the way our screen will be rendered in different screen size, it decides what aspect ration to use and how much of the screen/game area to be shown
	public Viewport viewport;

	//-----------Game Screens
	public GameScreen gameScreen=null;
	public WelcomeScreen welcomeScreen=null;
	public StagesScreen stagesScreen = null;
	public GiftsScreen giftsScreen = null;
    public StageDetailsScreen stageDetailsScreen = null;
	public PauseScreen pauseScreen = null;
	public EndGameScreen endGameScreen = null;
	public UpgradeScreen upgradeScreen = null;
	public String selectedStage = "";

	public Preferences preferences;

	@Override
	public void create () {

		try {

			/*Brick.init();
			Kaleen.init();
			Horse.init();
			Pusher.init();
			PowerDrink.init();
			JumpingKit.init();
			Coin.init();
			CowBoyHat.init();
			SpeedBomb.init();
			FlyingKit.init();
			StickyBomb.init();
			Magnet.init();
			WaterBalloon.init();
			BombAmo.init();
			SpeedBomb.init();
			Cat.init();
			Kite.init();*/

			//load all propertiesFile and atlas
            try {
                GameUtility.initiate();
            }catch(Exception e){
                e.printStackTrace();
                return;
            }
			//load Stage Map details
			GameUtility.gameObjectCreator.populateStageMapDetails("stages/stageMap.txt");
			preferences = Gdx.app.getPreferences("UserData");
            resetPrefs();
			assetManager = new AssetManager();
			//load assets
			assetManager.load("music/plang_mt_flaming_flares.mp3", Music.class);
			assetManager.load("audio/Wind effects 5.wav",Sound.class);
			assetManager.load("audio/Fire impact 1.wav",Sound.class);
			assetManager.load("audio/SHOOT008.mp3",Sound.class);

			//load all the assets synchronously
			assetManager.finishLoading();

			batch = new SpriteBatch();

			camera = new OrthographicCamera();
			//viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());//GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,camera);
			viewport = new FitViewport(GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,camera);
			/*gameScreen = new GameScreen(this);
			setScreen(gameScreen);*/

			if(false) {
				setScreen(getGiftsScreen());
				return;
			}

			welcomeScreen = new WelcomeScreen(this);
			pauseScreen = new PauseScreen(this,null);
            //stageDetailsScreen = new StageDetailsScreen(this);
			GameUtility.game = this;
			setScreen(welcomeScreen);

		}catch(Exception e){
			GameUtility.log(this.getClass().getName(),e.getMessage());
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();

		//screens
		if(gameScreen!=null)
			gameScreen.dispose();
		if(welcomeScreen!=null)
			welcomeScreen.dispose();
		if(stagesScreen!=null)
			stagesScreen.dispose();
		if(pauseScreen!=null)
			pauseScreen.dispose();
		if(endGameScreen !=null)
			endGameScreen.dispose();
        if(stageDetailsScreen!=null)
            stageDetailsScreen.dispose();
		if(upgradeScreen!=null)
			upgradeScreen.dispose();

		GameUtility.dispose();
	}

	public GameScreen getGameScreen(){
        gameScreen = new GameScreen(this);
        return gameScreen;
	}

	public UpgradeScreen getUpgradeScreen(){
		if(upgradeScreen == null){
			upgradeScreen = new UpgradeScreen(this);
		}
		return upgradeScreen;
	}

	public StagesScreen getStagesScreen(){
		if(stagesScreen == null)
			stagesScreen = new StagesScreen(this);
		return stagesScreen;
	}

	public GiftsScreen getGiftsScreen(){
		if(giftsScreen == null)
			giftsScreen = new GiftsScreen(this);
		return giftsScreen;
	}

	public GameScreen getNewGameScreen(String stage){
		if(gameScreen!=null)
			gameScreen.dispose();
		//gameScreen = null;
		GameUtility.gameObjectCreator.populateObjectDetailsFromFile(stage);
		return getGameScreen();
	}
    public StageDetailsScreen getStageDetailsScreen(String stage){
        if(stageDetailsScreen == null)
            stageDetailsScreen = new StageDetailsScreen(this);
		selectedStage = stage;
        return stageDetailsScreen;
    }


	public EndGameScreen getGameEndScreen(String currentStage){
		if(endGameScreen == null)
			endGameScreen = new EndGameScreen(this,currentStage);
		return endGameScreen;
	}

	public void resetPrefs(){
        preferences.putInteger(GameConstants.PERF_CAR,0);
        preferences.putInteger(GameConstants.PERF_HORSE,0);
        preferences.putInteger(GameConstants.PERF_KALEEN,0);
        preferences.putInteger(GameConstants.PERF_KITE,0);
        preferences.putInteger(GameConstants.PERF_SKATE,0);
    }
}
