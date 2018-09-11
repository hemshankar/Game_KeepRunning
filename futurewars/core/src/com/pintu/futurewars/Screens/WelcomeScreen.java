package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by hsahu on 9/6/2017.
 */

public class WelcomeScreen implements Screen {

    JumpingMarblesGame game = null;
    BitmapFont loadingFont = null;
    BitmapFont titleFont = null;
    Stage stage = null;
    Label welcomeMessage = null;
    Label titleMessage = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    Image welcomeImage = null;

    private final float CHANGE_SCREEN_TIME = 3;
    private float timeHappend = 0;

    public WelcomeScreen(JumpingMarblesGame game){
        this.game = game;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);


        Texture texture = new Texture(Gdx.files.internal("welcome/welcome2.jpg"));
        welcomeImage = new Image(texture);
        welcomeImage.setHeight(stage.getHeight());
        welcomeImage.setWidth(stage.getWidth());
        welcomeImage.addAction(scaleBy(.2f,.2f,10));
        stage.addActor(welcomeImage);

        initFonts();
        welcomeMessage = new Label("Loading...",new Label.LabelStyle(loadingFont,Color.BLACK));
        stage.addActor(welcomeMessage);

        titleMessage = new Label("The Explorer",new Label.LabelStyle(titleFont,Color.BLACK));
        stage.addActor(titleMessage);
    }

    @Override
    public void show() {

        titleMessage.setPosition(600,stage.getHeight()/4.5f);
        titleMessage.addAction(fadeIn(1f));

        welcomeMessage.setPosition(600,stage.getHeight()/7f);
        //welcomeMessage.addAction(alpha(0f));
        welcomeMessage.addAction(fadeIn(3f));
        //welcomeImage.addAction(alpha(.5f));
        try {
            game.loadAll();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void update(float dt){
        timeHappend +=dt;
        if(game.resourceLoaded && game.assetManager.update()){//timeHappend>CHANGE_SCREEN_TIME){
            game.setScreen(game.getStagesScreen());
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
        loadingFont.dispose();
        stage.dispose();
        GameUtility.log(this.getClass().getName(), "Disposed");
    }
    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 50;
        params.color = Color.BLACK;
        params.borderColor = Color.BLUE;



        loadingFont = generator.generateFont(params);

        params.size = 120;
        params.color = Color.GOLD;
        params.borderColor = Color.SKY;

        titleFont = generator.generateFont(params);
    }
}
