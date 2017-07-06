package com.mygdx.game.com.mygdx.game.sticks;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by hsahu on 6/20/2017.
 */

public class WalkingStick implements ApplicationListener {

    OrthographicCamera camera;
    SpriteBatch batch;
    TheStick stick;

    //For stage
    Viewport cViewPort;
    Stage stage;
    OrthographicCamera ctrlCam;

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

        //For stage
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(w,h,ctrlCam);
        stage = new Stage(cViewPort, batch);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Label label = new Label("Test String", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        table.add(label);
        stage.addActor(table);
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

        batch.setProjectionMatrix(ctrlCam.combined);
        stage.draw();


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
