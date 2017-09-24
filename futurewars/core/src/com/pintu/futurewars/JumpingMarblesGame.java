package com.pintu.futurewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Casts.BombAmo;
import com.pintu.futurewars.Casts.Coin;
import com.pintu.futurewars.Casts.CowBoyHat;
import com.pintu.futurewars.Casts.FlyingKit;
import com.pintu.futurewars.Casts.JumpingKit;
import com.pintu.futurewars.Casts.Kaleen;
import com.pintu.futurewars.Casts.Magnet;
import com.pintu.futurewars.Casts.PowerDrink;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Casts.StickyBomb;
import com.pintu.futurewars.Casts.WaterBalloon;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Screens.GameEndScreen;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.Screens.PauseScreen;
import com.pintu.futurewars.Screens.StagesScreen;
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
	public PauseScreen pauseScreen = null;
	public GameEndScreen gameEndScreen = null;

	@Override
	public void create () {
		Kaleen.init();
		/*Pusher.init();
		PowerDrink.init();
		JumpingKit.init();
		Coin.init();
		CowBoyHat.init();
		SpeedBomb.init();
		FlyingKit.init();
		StickyBomb.init();
		Magnet.init();
		WaterBalloon.init();
		BombAmo.init();*/
		assetManager = new AssetManager();
        //load assets
        assetManager.load("music/Flying me softly.ogg", Music.class);
        assetManager.load("audio/Wind effects 5.wav",Sound.class);
        assetManager.load("audio/Fire impact 1.wav",Sound.class);
        assetManager.load("audio/SHOOT008.mp3",Sound.class);

        //load all the assets synchronously
        assetManager.finishLoading();

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		viewport = new FitViewport(GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,camera);

		/*gameScreen = new GameScreen(this);
		setScreen(gameScreen);*/
		welcomeScreen = new WelcomeScreen(this);
		pauseScreen = new PauseScreen(this,null);
		setScreen(welcomeScreen);
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
		if(gameEndScreen!=null)
			gameEndScreen.dispose();

	}

	public GameScreen getGameScreen(String stage){
		if(GameConstants.STAGE1.equals(stage)){
			if(gameScreen==null)
				gameScreen = new GameScreen(this);
			return gameScreen;
		}

		return null;
	}

	public StagesScreen getStagesScreen(){
		if(stagesScreen == null)
			stagesScreen = new StagesScreen(this);
		return stagesScreen;
	}

	public GameScreen getNewGameScreen(String stage){
		if(gameScreen!=null)
			gameScreen.dispose();
		gameScreen = null;
		return getGameScreen(stage);
	}

	public GameEndScreen getGameEndScreen(String currentStage, String nextStage){
		if(gameEndScreen == null)
			gameEndScreen = new GameEndScreen(this);
		gameEndScreen.updateStages(currentStage,nextStage);
		return gameEndScreen;
	}
}
