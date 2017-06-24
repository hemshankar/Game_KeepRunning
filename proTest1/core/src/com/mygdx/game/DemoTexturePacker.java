package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/**
 * Created by hsahu on 6/20/2017.
 */

public class DemoTexturePacker implements ApplicationListener {

    AssetManager manager;
    Sprite box;
    Sprite circle;
    TextureAtlas atlas;
    OrthographicCamera camera;
    SpriteBatch batch;
    int w,h;
    @Override
    public void create() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        manager = new AssetManager();
        //manager.load("Pack1.atlas",TextureAtlas.class);
        atlas =new TextureAtlas(Gdx.files.internal("Pack1.atlas"));// manager.get("Pack1.atlas");

        //one way
        box = new Sprite(atlas.findRegion("box"));
        box.setRegion(atlas.findRegion("circle"));
        //other way
        circle = atlas.createSprite("circle");

        camera = new OrthographicCamera(w,h);
        batch = new SpriteBatch();
        box.setSize(w/20,h/20);
        circle.setSize(w/10,h/10);

        circle.setPosition(-w/2,0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        box.draw(batch);
        circle.draw(batch);
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

    }
}
