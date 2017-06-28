package com.mygdx.game.com.mygdx.game.tiles;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.com.mygdx.game.tiles.screens.TileDemoScreen;

/**
 * Created by hsahu on 6/28/2017.
 */

public class GameClassDemo extends Game {

    public SpriteBatch batch;
    public static final float PPM = 100f;
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new TileDemoScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
