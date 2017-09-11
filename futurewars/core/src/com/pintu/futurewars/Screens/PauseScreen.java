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
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class PauseScreen implements Screen {

    JumpingMarblesGame game = null;
    BitmapFont font = null;
    Stage stage = null;
    Label loading = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;

    private final float CHANGE_SCREEN_TIME = 5;
    private float timeHappend = 0;
    public Screen stageScreen;

    public PauseScreen(JumpingMarblesGame game,Screen stageScreen){
        this.game = game;
        this.stageScreen = stageScreen;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);


        initFonts();
        addToStage("imgs/welcome.png","Stage 1");
        //addLoadingLabel();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float dt){
        timeHappend +=dt;
        if(timeHappend>CHANGE_SCREEN_TIME){
            //game.setScreen(game.getGameScreen());
        }
        stage.act();
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(.4f,.6f,.5f,1);
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

    private void addToStage(String imageLocation, String label){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setHeight(StageDetails.imageHeight);
        stageImage.setWidth(StageDetails.imageWidth);
        stageImage.setPosition(StageDetails.stageCount * (StageDetails.imageWidth + StageDetails.padding),
                                stage.getHeight()/2);
        stageImage.addListener(new StageListner(this));
        stage.addActor(stageImage);

        Label stagelabel = new Label(label,new Label.LabelStyle(font,Color.BLACK));
        stagelabel.setWidth(StageDetails.labelWidth);
        stagelabel.setHeight(StageDetails.labelHeight);
        stagelabel.setPosition(StageDetails.stageCount * (StageDetails.labelWidth + StageDetails.padding),
                                stage.getHeight()/2-50);
        stagelabel.addListener(new StageListner(this));
        stage.addActor(stagelabel);

        StageDetails.stageCount++;
    }

    public class StageListner extends InputListener{

        PauseScreen pauseScreen = null;
        public StageListner(PauseScreen pauseScreen){
            this.pauseScreen = pauseScreen;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //stageScreen.show();
            if(stageScreen!=null)
                game.setScreen(stageScreen);
            //pauseScreen.dispose();
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
}
