package com.mygdx.game.com.mygdx.game.sticks;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by hsahu on 6/20/2017.
 */

public class WalkingStick implements ApplicationListener {

    OrthographicCamera camera;
    SpriteBatch batch;
    TheStick stick;

    float dt =0;
    float time = 0;
    float timeElapsed = 0;
    int state=0;
    int w,h;
    @Override
    public void create() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w,h);
        batch = new SpriteBatch();

        stick = new TheStick("s",batch,"stick/WalkingStick.atlas");
        stick.setSize(w,h);
        stick.setPosition(-w/2,-h/2);


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        dt = Gdx.graphics.getDeltaTime();
        time = time + 1 * dt;

        if(timeElapsed + .2 < time) {
            timeElapsed = time;
            state = (state) % 4 + 1;
            stick.setState(state);
        }
        stick.render();

        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
