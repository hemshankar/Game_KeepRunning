package com.pintu.futurewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pintu.futurewars.Screens.GameScreen;

public class JumpingMarblesGame extends Game {
	public static SpriteBatch batch;
	public AssetManager assetManager = null;//new AssetManager();

	//-----------Game Screens
	private GameScreen gameScreen;
	//private PurchaseScreen purchaseScreen;

	@Override
	public void create () {
		assetManager = new AssetManager();
        //load assets
        assetManager.load("music/Flying me softly.ogg", Music.class);
        assetManager.load("audio/Wind effects 5.wav",Sound.class);
        assetManager.load("audio/Fire impact 1.wav",Sound.class);
        assetManager.load("audio/SHOOT008.mp3",Sound.class);

        //load all the assets synchronously
        assetManager.finishLoading();

		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch,assetManager);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
		gameScreen.dispose();
	}
}
