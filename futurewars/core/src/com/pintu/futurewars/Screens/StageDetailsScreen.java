package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameSprite;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class StageDetailsScreen implements Screen {

    JumpingMarblesGame game = null;
    BitmapFont font = null;
    Stage stage = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    GameSprite gs = null;

    public StageDetailsScreen(JumpingMarblesGame game){
        this.game = game;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);

        initFonts();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        StageDetails.stageCount = 0;
        //addToStage("imgs/welcome.png","Map",game.getStagesScreen());
        addImage("imgs/welcome.png",10,210,100,100);
        addText("Cahtch the Cat",font,10,30,100,100,Color.BLACK);
        addButton("Button",font,110,50,100,100,
                new StageListner(this,game.getNewGameScreen("colombo")),Color.BLACK);
        addAnimation();

    }

    public void update(float dt){
        gs.updateSprite(dt);
        stage.act();
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(.4f,.6f,.5f,1);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        gs.draw(game.batch);
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
        font.dispose();
        stage.dispose();

        GameUtility.log(this.getClass().getName(), "Disposed");
    }
    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 50;
        params.color = Color.BLACK;

        font = generator.generateFont(params);
    }

    public static class StageDetails{
        public Label stageLabel;
        public Image stageImage;
        public static int stageCount = 0;
        public static float imageWidth = 200;
        public static float imageHeight = 400;
        public static float labelWidth = 200;
        public static float labelHeight = 50;
        public static float padding = 10;
    }

    public void addText(String text, BitmapFont font, float x, float y, float width, float height,
                        Color color){
        Label stagelabel = new Label(text,new Label.LabelStyle(font,color));
        stagelabel.setWidth(width);
        stagelabel.setHeight(height);
        stagelabel.setPosition(x,y);
        stage.addActor(stagelabel);
    }

    public void addImage(String imageLocation, float x, float y, float width, float height){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setHeight(width);
        stageImage.setWidth(height);
        stageImage.setPosition(x,y);
        stage.addActor(stageImage);
    }

    public void addAnimation(){
        gs = new GameSprite(GameConstants.PUSHER_PROPERTY_FILE,200,800);
    }

    public void addButton(String buttonText,
                          BitmapFont font,
                          float x,
                          float y,
                          float width,
                          float height,
                          StageListner stageListner,
                          Color color){

        Label stagelabel = new Label(buttonText,new Label.LabelStyle(font,color));
        stagelabel.setWidth(width);
        stagelabel.setHeight(height);
        stagelabel.setPosition(x,y);
        stagelabel.addListener(stageListner);
        stage.addActor(stagelabel);

    }

    private void addToStage(String imageLocation, String label,Screen screen){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setHeight(StageDetails.imageHeight);
        stageImage.setWidth(StageDetails.imageWidth);
        stageImage.setPosition(StageDetails.stageCount * (StageDetails.imageWidth + StageDetails.padding),
                                stage.getHeight()/2);
        stageImage.addListener(new StageListner(this,screen));
        stage.addActor(stageImage);

        Label stagelabel = new Label(label,new Label.LabelStyle(font,Color.BLACK));
        stagelabel.setWidth(StageDetails.labelWidth);
        stagelabel.setHeight(StageDetails.labelHeight);
        stagelabel.setPosition(StageDetails.stageCount * (StageDetails.labelWidth + StageDetails.padding),
                                stage.getHeight()/2-50);
        stagelabel.addListener(new StageListner(this,screen));
        stage.addActor(stagelabel);

        StageDetails.stageCount++;
    }

    public class StageListner extends InputListener{

        public Screen selectedScreen = null;
        StageDetailsScreen pauseScreen = null;
        public StageListner(StageDetailsScreen pauseScreen, Screen screen){
            this.pauseScreen = pauseScreen;
            selectedScreen = screen;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //stageScreen.show();
            if(selectedScreen!=null)
                game.setScreen(selectedScreen);
            //pauseScreen.dispose();
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
}
