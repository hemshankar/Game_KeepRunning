package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by hsahu on 9/6/2017.
 */

public class WelcomeScreen implements Screen {

    JumpingMarblesGame game = null;
    BitmapFont font = null;
    Stage stage = null;
    Label welcomeMessage = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    Image welcomeImage = null;

    private final float CHANGE_SCREEN_TIME = 0;
    private float timeHappend = 0;

    public WelcomeScreen(JumpingMarblesGame game){
        this.game = game;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);


        Texture texture = new Texture(Gdx.files.internal("imgs/welcome.png"));
        welcomeImage = new Image(texture);
        welcomeImage.setHeight(stage.getHeight());
        welcomeImage.setWidth(stage.getWidth());
        //stageImage.addAction(alpha(.2f));
        stage.addActor(welcomeImage);

        initFonts();
        welcomeMessage = new Label("Welcome Bro",new Label.LabelStyle(font,Color.BLACK));
        welcomeMessage.setWidth(20);
        welcomeMessage.setHeight(10);
        //stagelabel.setPosition(20,20);
        stage.addActor(welcomeMessage);
    }

    @Override
    public void show() {
        welcomeMessage.setPosition(200,stage.getHeight()/2);
        welcomeMessage.addAction(alpha(0f));
        welcomeMessage.addAction(fadeIn(3f));
        welcomeImage.addAction(alpha(.5f));
    }

    public void update(float dt){
        timeHappend +=dt;
        if(timeHappend>CHANGE_SCREEN_TIME){
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
        font.dispose();
        stage.dispose();
    }
    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 150;
        params.color = Color.BLACK;

        font = generator.generateFont(params);
    }
}
