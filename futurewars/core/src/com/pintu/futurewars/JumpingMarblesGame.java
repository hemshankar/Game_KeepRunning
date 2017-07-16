package com.pintu.futurewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pintu.futurewars.Screens.GameScreen;

public class JumpingMarblesGame extends Game {
	public static SpriteBatch batch;

	//-----------Game Screens
	private GameScreen gameScreen;
	//private PurchaseScreen purchaseScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameScreen.dispose();
	}
}
