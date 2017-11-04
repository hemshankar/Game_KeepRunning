package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Widgets_old;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 11/4/2017.
 */

public class MenuStage implements InputProcessor {

    Stage stage = null;
    CustomButton coinsCollected = null;
    OrthographicCamera ctrlCam;
    SpriteBatch batch = null;

    List<Actor> actors = new ArrayList<Actor>();
    Viewport cViewPort;
    JumpingMarblesGame game = null;
    public int totalCoins = 0;
    List<BitmapFont> fontList = new ArrayList<BitmapFont>();

    public MenuStage(JumpingMarblesGame g){
        game = g;
        batch = g.batch;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort, batch);
        totalCoins = game.preferences.getInteger(GameConstants.PERF_COIN);

    }

    public void show(){
        stage.clear();
        coinsCollected = addItem("imgs/coin.png",null,"0",1270,910,1290,870,null,80,80,getFonts("fonts/leadcoat.ttf",25,Color.YELLOW));
        for(Actor a:actors) {
            stage.addActor(a);
        }
    }

    public void act(){
        stage.act();
    }

    public void draw(){
        stage.draw();
    }

    public void addActor(Actor actor){
        actors.add(actor);
        stage.addActor(actor);
    }

    public void update(){
        //totalCoins = game.preferences.getInteger(GameConstants.PERF_COIN);
        coinsCollected.label.setText(totalCoins+"");
    }

    public void dispose(){
        for(BitmapFont f: fontList){
            f.dispose();
        }
        actors.clear();
        stage.dispose();
    }

    private BitmapFont getFonts(String fontTypeTTFFile,int size, Color color){
        BitmapFont f = null;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontTypeTTFFile));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        params.color = color;
        f = generator.generateFont(params);
        fontList.add(f);
        return f;
    }

    private CustomButton addItem(String offImage, String onImage, String label, float imageX, float imageY,
                                         float labelX, float labelY, InputListener listner, float imageWidth, float imageHeght,
                                         BitmapFont font){
        CustomButton custBut = new CustomButton();

        Texture texture = new Texture(Gdx.files.internal(offImage));
        Image stageImage = new Image(texture);
        stageImage.setWidth(imageWidth);
        stageImage.setHeight(imageHeght);
        stageImage.setPosition(imageX,imageY);
        if(listner!=null)
            stageImage.addListener(listner);
        custBut.m_offImage = stageImage;
        stage.addActor(custBut.m_offImage);

        if(!GameUtility.isEmptyString(onImage)) {
            Texture texture2 = new Texture(Gdx.files.internal(onImage));
            Image stageImage2 = new Image(texture2);
            stageImage2.setWidth(imageWidth);
            stageImage2.setHeight(imageHeght);
            stageImage2.setPosition(imageX, imageY);
            stageImage2.setVisible(false);
            if (listner != null)
                stageImage2.addListener(listner);
            custBut.m_onImage = stageImage2;
            stage.addActor(custBut.m_onImage);
        }

        if(font!=null) {
            Label stagelabel = new Label(label, new Label.LabelStyle(font, Color.BLACK));
            stagelabel.setWidth(40);
            stagelabel.setHeight(40);
            stagelabel.setPosition(labelX, labelY);
            if (listner != null)
                stagelabel.addListener(listner);
            stage.addActor(stagelabel);
            //font.dispose();
            custBut.label = stagelabel;

        }
        return custBut;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public class CustomButton{
        public Image m_offImage;
        public Image m_onImage;
        public Label label;
    }
}
