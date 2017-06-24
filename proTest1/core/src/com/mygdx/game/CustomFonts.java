package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hsahu on 6/21/2017.
 */

public class CustomFonts implements ApplicationListener {

    BitmapFont font;
    OrthographicCamera camera;
    SpriteBatch batch;

    @Override
    public void create() {

        font = new BitmapFont(Gdx.files.internal("fonts.fnt"),false);
        camera = new OrthographicCamera(200,200);
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        batch.setProjectionMatrix(camera.combined);

        GdxUtil.clearScrean();
        GdxUtil.draw(batch,font,"I am game",-50,0);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
