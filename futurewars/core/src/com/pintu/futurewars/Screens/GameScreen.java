package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Blasts.Blast;
import com.pintu.futurewars.Blasts.BlastHandler.BlastHandler;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Controller;
import com.pintu.futurewars.Controllers.InputHandler;
import com.pintu.futurewars.Utility.UpdateHandler;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Utility.WorldContactListner;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 7/1/2017.
 */

public class GameScreen implements Screen {

    //=================Basic LibGDX
    //OrthographicCamera which is the eye of the user, i.e. which is that user sees.
    public OrthographicCamera camera;
    //Viewport handles the way our screen will be rendered in different screen size, it decides what aspect ration to use and how much of the screen/game area to be shown
    public Viewport viewport;
    //Batch used for dumping all the graphics to be rendered
    public SpriteBatch batch;

    //===================From Tiled
    //loads a specific map
    public TmxMapLoader mapLoader;
    //the map object
    public TiledMap map;
    //Used to render the map on the camera/screen
    public OrthogonalTiledMapRenderer renderer;

    //===================Box 2D related
    //the world where all the physics simulation will take place
    public World world;
    //the shape renderer for box 2d so that we can see the shapes of various fixtures
    public Box2DDebugRenderer b2dr;
    //A utility function to create the world from the map
    public JumpingMarbleWorldCreator worldCreator;

    //player handle that will be created by world
    public Player player;

    //all other casts
    public List<FutureWarsCast> casts;

    //Controller
    public Controller controller;

    //mas velocity of the player
    public float maxVelocity=0;
    public float cameraCalibration=0;
    public float heightCamCalibration=0;
    public float recoilTimeElapsed = 0;
    public InputHandler inputHandler = new InputHandler();
    public UpdateHandler updateHandler = new UpdateHandler();
    public List<GameBullet> bullets = new ArrayList<GameBullet>();
    public List<GameBullet> bulletsToBeRemoved = new ArrayList<GameBullet>();
    public List<Blast> blastList = new ArrayList<Blast>();
    public BlastHandler blastHandler = null;
    public float xImp;
    public float yImp;
    public float xMag;
    public float yMag;
    public float xVelo;
    public float yVelo;
    public boolean started = false;

    public AssetManager assetManager = null;
    Music gameMusic = null;

    public GameScreen(SpriteBatch batch, AssetManager assetManager){
        //Initialize all the variables
        float scrWidth = Gdx.graphics.getWidth();
        float scrHight = Gdx.graphics.getHeight();
        this.batch = batch;
        this.assetManager = assetManager;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,camera);
        //viewport = new FitViewport(scrWidth,scrHight,camera);
        System.out.println("=====" + scrWidth + "--" + scrHight );

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(GameConstants.FUTURE_WARS_MAP);
        renderer = new OrthogonalTiledMapRenderer(map,1/ GameConstants.PPM);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListner());
        Utility.setWorld(world);

        //set the contact listener

        //set the camera position
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);

        worldCreator = new JumpingMarbleWorldCreator(world, map);
        Utility.worldCreator = worldCreator;
        player = worldCreator.player;
        casts = worldCreator.casts;
        for(FutureWarsCast cast : casts){
            cast.initialize();
        }

        controller = new Controller(batch);
        blastHandler = new BlastHandler(blastList);
        Utility.gameScreen = this;

        gameMusic = assetManager.get("music/Flying me softly.ogg",Music.class);
        gameMusic.setLooping(true);
        gameMusic.play();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world,camera.combined);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //player.draw(batch);
        Utility.render(batch,casts);
        /*for(FutureWarsCast cast : casts){
            cast.draw(batch);
        }*/
        Utility.render(batch,bullets);/*
        for(GameBullet bullet : bullets){
            bullet.draw(batch);
        }*/

        Utility.render(batch,blastList);
        batch.end();
        controller.draw();
    }

    //utility method to be used in render
    private void update(float dt){
        //handle the i/p every delta time
        handleIp(dt);

        //update the game after handling the IP
        updateHandler.update(this,dt);
    }

    //utility method to handle Ip
    private void handleIp(float dt){
        inputHandler.hadleInput(this,dt);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        controller.resize(width,height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

        //Never call ----batch.dispose();
    }
}
