package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hsahu on 6/20/2017.
 */

public class SpriteRendering implements ApplicationListener {

    float dt =0;
    float time = 0;
    float timeElapsed = 0;

    OrthographicCamera camera;
    SpriteBatch batch;
    Sprite sprite;
    int w;
    int h;
    float pos=0;
    @Override
    public void create() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        pos = -w/2;
        camera = new OrthographicCamera(w,h);
        batch = new SpriteBatch();
        sprite = new Sprite( new Texture("arrow.png"));
        sprite.setSize(80,20);
        sprite.setPosition(-w/2,0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        dt = Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        pos = pos + 6000*dt;
        sprite.setPosition(pos,0);


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
