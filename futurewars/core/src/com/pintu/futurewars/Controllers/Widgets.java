package com.pintu.futurewars.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Directions.ControlButton;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.pintu.futurewars.Screens.GameScreen;

/**
 * Created by hsahu on 7/5/2017.
 */

public class Widgets {
    Viewport cViewPort;
    Stage stage;
    //Health bar related
    Pixmap pixmap=null;
    ProgressBarStyle progressBarStyle =null;
    ProgressBar healthBar;
    ProgressBar flyFuelBar;
    float time = 0;
    GameScreen screen = null;
    BitmapFont font=new BitmapFont();


    public boolean [] controles = {false,false,false,false,false,false};
    public float x,y;
                                //upPressed, downPressed, leftPressed, rightPressed,throwPressed;
    OrthographicCamera ctrlCam;
    CustomLabel speedStats = null;
    CustomLabel coinsCollected = null;

    public Widgets(GameScreen screen){
        this.screen = screen;
        initFonts();
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort, screen.batch);
        Gdx.input.setInputProcessor(stage);

        //create directions
        Table directions = new Table();

        directions.setFillParent(true);
        directions.left().bottom();

        ControlButton up = new ControlButton(this, GameConstants.UP, GameConstants.UP_IMAGE);
        ControlButton down = new ControlButton(this, GameConstants.DOWN, GameConstants.DOWN_IMAGE);
        ControlButton left = new ControlButton(this, GameConstants.LEFT, GameConstants.LEFT_IMAGE);
        ControlButton right = new ControlButton(this, GameConstants.RIGHT, GameConstants.RIGHT_IMAGE);
        ControlButton circleButton
                = new ControlButton(this, GameConstants.CIRCLE_CONTROLLER, GameConstants.CIRCLE_IMAGE);

        float w = GameConstants.CONTROL_BUTTON_SIZE;
        float h = GameConstants.CONTROL_BUTTON_SIZE;

        ControlButton FireButton = new ControlButton(this, GameConstants.FIRE_BASIC_BULLET, GameConstants.POWER_IMAGE);

        FireButton.image.setWidth(w);
        FireButton.image.setHeight(h);
        FireButton.image.setPosition(stage.getWidth()-150,50);
        stage.addActor(FireButton.image);

        up.image.setWidth(w);
        up.image.setHeight(h);
        up.image.setPosition(50,50);
        stage.addActor(up.image);

        healthBar();
        flyFuelBar();


        speedStats = new CustomLabel("0000", new Label.LabelStyle(font,Color.BLACK));
        speedStats.setWidth(10);
        speedStats.setHeight(10);
        speedStats.setPosition(stage.getWidth()-200,stage.getHeight()-50);
        //speedStats.setPosition(200,100);
        speedStats.text = "200";
        stage.addActor(speedStats);

        coinsCollected = new  CustomLabel("0", new Label.LabelStyle(font,Color.BLACK));
        coinsCollected.setWidth(10);
        coinsCollected.setHeight(10);
        coinsCollected.setPosition(stage.getWidth()-400,stage.getHeight()-50);
        //speedStats.setPosition(200,100);
        coinsCollected.text = "0";
        stage.addActor(coinsCollected);
    }

    public void draw(){
        stage.draw();
    }

    public void update(float dt){
        healthBar.setValue(screen.player2.health/screen.player2.MAX_HEALTH);
        flyFuelBar.setValue(screen.player2.flyFuel/screen.player2.MAX_FLY_FUEL);
        speedStats.text = "Speed: " + (int)(screen.player2.body.getLinearVelocity().x) + "";
        coinsCollected.text = "" + screen.player2.totalCoin;
        stage.act();
    }

    public void resize(int w,int h){
        cViewPort.update(w,h);
    }


    private void healthBar(){
        //===================Replace the label Live with some image==============================
        CustomLabel healthLabel = new CustomLabel("Life", new Label.LabelStyle(font,Color.BLACK));
        healthLabel.setWidth(10);
        healthLabel.setHeight(10);
        healthLabel.setPosition(10,stage.getHeight()-50);
        stage.addActor(healthLabel);
        //===================End of Replace the label live with some image=======================

        Pixmap pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBarStyle progressBarStyle = new ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        healthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);

        healthBar.setValue(1.0f);
        healthBar.setAnimateDuration(0.025f);
        healthBar.setBounds(10, 10, 500, 20);
        healthBar.setFillParent(true);
        healthBar.setPosition(130,stage.getHeight()-50);

        //return healthBar;
        stage.addActor(healthBar);
    }

    private void flyFuelBar(){
        //===================Replace the label fuel with some image==============================
        CustomLabel fuelLabel = new CustomLabel("Fuel", new Label.LabelStyle(font,Color.BLACK));
        fuelLabel.setWidth(10);
        fuelLabel.setHeight(10);
        fuelLabel.setPosition(10,stage.getHeight()-100);
        stage.addActor(fuelLabel);
        //===================End of Replace the label fuel with some image=======================

        Pixmap pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBarStyle progressBarStyle = new ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        flyFuelBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);

        flyFuelBar.setValue(1.0f);
        flyFuelBar.setAnimateDuration(0.025f);
        flyFuelBar.setBounds(10, 10, 500, 20);
        flyFuelBar.setFillParent(true);
        flyFuelBar.setPosition(130,stage.getHeight()-100);

        //return healthBar;
        stage.addActor(flyFuelBar);
    }

    public void dispose(){
        //speedStats.dispose();
    }

    public class CustomLabel extends com.badlogic.gdx.scenes.scene2d.ui.Label {
        private String text;

        public CustomLabel(final CharSequence text, final LabelStyle style) {
            super(text, style);
            this.text = text.toString();
        }

        @Override
        public void act(final float delta) {
            this.setText(text);
            super.act(delta);
        }

        public void updateText(final String text) {
            this.text = text;
        }
    }

    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 40;
        params.color = Color.BLACK;

        font = generator.generateFont(params);
    }
}
