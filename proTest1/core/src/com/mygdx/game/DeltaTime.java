package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by hsahu on 6/20/2017.
 */

public class DeltaTime implements ApplicationListener {

    float dt =0;
    float time = 0;
    float timeElapsed = 0;


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        dt = Gdx.graphics.getDeltaTime();

        time = time + 1 * dt;

        if(timeElapsed + 1 < time){
            timeElapsed = time;
            System.out.println("Time elapsed: " + timeElapsed + " sec");
        }
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
