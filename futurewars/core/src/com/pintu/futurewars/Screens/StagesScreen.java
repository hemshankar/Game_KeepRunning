package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by hsahu on 9/6/2017.
 */

public class StagesScreen implements Screen,GestureDetector.GestureListener {

    JumpingMarblesGame game = null;
    BitmapFont font = null;
    Stage stage = null;
    Label loading = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    World world = null;//new World(new Vector2(0,0),true);

    private final float CHANGE_SCREEN_TIME = 5;
    private float timeHappend = 1;

    public TmxMapLoader mapLoader = null;
    public TiledMap map = null;
    public OrthogonalTiledMapRenderer renderer = null;
    private float currentZoom = 1;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public StagesScreen(JumpingMarblesGame game){
        this.game = game;
        world = new World(new Vector2(0,0),true);
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(GameConstants.WORLD_MAP);
        renderer = new OrthogonalTiledMapRenderer(map,1);


        initFonts();
        addStage("imgs/welcome.png",GameConstants.STAGE1);
        addStage("imgs/welcome.png",GameConstants.STAGE2);
        addStage("imgs/welcome.png",GameConstants.STAGE3);
        //addLoadingLabel();

        for (MapObject object : map.getLayers().get("cities").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse eclipse = ((EllipseMapObject) object).getEllipse();
            addCity("imgs/danger.png",object.getName(),eclipse.x,eclipse.y);
        }
    }

    @Override
    public void show() {
        InputProcessor inputProcessorOne = new GestureDetector(this);
        InputProcessor inputProcessorTwo = stage;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);
        //Gdx.input.setInputProcessor(new GestureDetector(this));
        ctrlCam.translate(600,1500);
        ctrlCam.update();
    }

    public void update(float dt){
        timeHappend +=dt;
        if(timeHappend>CHANGE_SCREEN_TIME){
            //game.setScreen(game.getGameScreen());
        }
        for(Actor a: stage.getActors()){
            a.setWidth(20*10*ctrlCam.zoom);
            a.setHeight(20*10*ctrlCam.zoom);
        }
        stage.act();
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(.4f,.6f,.5f,1);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1/60f, 6, 2);
        renderer.setView(ctrlCam);
        renderer.render();
        update(delta);
        stage.draw();
        debugRenderer.render(world, ctrlCam.combined);
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
        map.dispose();
        renderer.dispose();
        GameUtility.log(this.getClass().getName(), "Disposed");
    }
    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 20;
        params.color = Color.YELLOW;

        font = generator.generateFont(params);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        //ctrlCam.zoom = 2f * currentZoom;
        ctrlCam.update();
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        /*ctrlCam.translate(-deltaX,deltaY);
        ctrlCam.update();*/
        ctrlCam.translate(-deltaX * currentZoom,deltaY * currentZoom);
        ctrlCam.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        currentZoom = ctrlCam.zoom;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        /*cViewPort.setWorldSize(cViewPort.getWorldWidth()-distance/100,
                cViewPort.getWorldHeight()-distance/100);
        cViewPort.apply();*/
        ctrlCam.zoom = (initialDistance / distance) * currentZoom;
        ctrlCam.update();
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
       /* cViewPort.setWorldSize(cViewPort.getWorldWidth()+pointer1.y/100,
                cViewPort.getWorldHeight()+pointer2.y/100);
        cViewPort.apply();*/
        return false;
    }

    @Override
    public void pinchStop() {

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

    private void addStage(String imageLocation, String label){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setHeight(StageDetails.imageHeight);
        stageImage.setWidth(StageDetails.imageWidth);
        stageImage.setPosition(StageDetails.stageCount * (StageDetails.imageWidth + StageDetails.padding),
                                stage.getHeight()/2);
        stageImage.addListener(new StageListner(label));
        stage.addActor(stageImage);

        Label stagelabel = new Label(label,new Label.LabelStyle(font,Color.SKY));
        stagelabel.setWidth(StageDetails.labelWidth);
        stagelabel.setHeight(StageDetails.labelHeight);
        stagelabel.setPosition(StageDetails.stageCount * (StageDetails.labelWidth + StageDetails.padding),
                                stage.getHeight()/2-50);
        stagelabel.addListener(new StageListner(label));
        stage.addActor(stagelabel);

        StageDetails.stageCount++;
    }
    private void addCity(String imageLocation, String label, float x, float y){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setHeight(50);
        stageImage.setWidth(50);
        stageImage.setPosition(x,y);
        stageImage.addListener(new StageListner(label));
        stage.addActor(stageImage);

        Label stagelabel = new Label(label,new Label.LabelStyle(font,Color.SKY));
        stagelabel.setWidth(5);
        stagelabel.setHeight(5);
        stagelabel.setPosition(x,y-20);
        stagelabel.addListener(new StageListner(label));
        stage.addActor(stagelabel);
        //StageDetails.stageCount++;
    }

    public void addLoadingLabel(){
        loading = new Label("Loading...",new Label.LabelStyle(font,Color.BLACK));
        loading.setWidth(StageDetails.labelWidth);
        loading.setHeight(StageDetails.labelHeight);
        loading.setPosition(stage.getWidth()-300,50);
        //loading.addListener(new StageListner(stage));
        stage.addActor(loading);
    }

    public class StageListner extends InputListener{

        String stage=null;
        public StageListner(String stage){
            this.stage = stage;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(game.getNewGameScreen(stage));
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            addLoadingLabel();
            return true;
        }
    }
}
