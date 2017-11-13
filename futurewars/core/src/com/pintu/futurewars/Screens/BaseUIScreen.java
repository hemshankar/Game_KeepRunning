package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class BaseUIScreen implements Screen {

    public JumpingMarblesGame game = null;
    public Stage stage = null;
    public OrthographicCamera ctrlCam = null;
    public FitViewport cViewPort = null;
    public Skin skin = null;

    public BaseUIScreen(JumpingMarblesGame game,String skinId){
        this.game = game;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);
        if(skinId!=null){
            skin = GameUtility.getSkin(skinId);
        }
    }


    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float dt){
        stage.act();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        GameUtility.log(this.getClass().getName(), "Disposed");
    }

    public static class StageItem {
        public Label label;
        public Image image;
        public float imageWidth = 200;
        public float imageHeight = 400;
        public float imageXPos = 0;
        public float imageYPos = 0;
        public float labelXPos = 0;
        public float labelYPos = 0;
        public float labelWidth = 200;
        public float labelHeight = 50;


        public StageItem(float iw,float  ih,float  ix,float  iy,float  lw,float lh,float lx,float ly){
            imageWidth = iw;
            imageHeight = ih;
            imageXPos = ix;
            imageYPos = iy;
            labelXPos = lx;
            labelYPos = ly;
            labelWidth = lw;
            labelHeight = lh;
        }
        public void init(){
            if(image!=null) {
                image.setHeight(imageHeight);
                image.setWidth(imageWidth);
                image.setPosition(imageXPos, imageYPos);
            }
            if(label!=null) {
                label.setWidth(labelWidth);
                label.setHeight(labelHeight);
                label.setPosition(labelXPos, labelYPos);
            }
        }
    }

    public void addItem(String imageLocation, String label, EventListener listner, StageItem stageDetails, Skin skin, String skinType){

        if(imageLocation!=null) {
            Texture texture = new Texture(Gdx.files.internal(imageLocation));
            stageDetails.image = new Image(texture);
            if(listner!=null){
                stageDetails.image.addListener(listner);
            }
            stage.addActor(stageDetails.image);
        }
        if(label!=null) {
            stageDetails.label = new Label(label, skin, skinType);
            if(listner!=null){
                stageDetails.label.addListener(listner);
            }
            stage.addActor(stageDetails.label);
        }
        if(stageDetails!=null) {
            stageDetails.init();
        }
    }

}
