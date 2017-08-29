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
import com.pintu.futurewars.Casts.BombAmo;
import com.pintu.futurewars.Casts.Coin;
import com.pintu.futurewars.Casts.CowBoyHat;
import com.pintu.futurewars.Casts.JumpingKit;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.PowerDrink;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Controller;
import com.pintu.futurewars.Controllers.InputHandler;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.Utility.UpdateHandler;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Utility.WorldContactListner;
import com.pintu.futurewars.commons.GameObject;

import java.util.Set;

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
    //public Player playerOld;

    //all other gameObjects
    public Set<GameObject> gameObjects;

    //Controller
    public Controller controller;

    //mas velocity of the player
    public float maxVelocity=0;
    public float cameraCalibration=0;
    public float heightCamCalibration=0;
    public float recoilTimeElapsed = 0;
    public InputHandler inputHandler = new InputHandler();
    public UpdateHandler updateHandler = new UpdateHandler();
    public float xImp;
    public float yImp;
    public float xMag;
    public float yMag;
    public float xVelo;
    public float yVelo;
    public boolean started = false;

    public AssetManager assetManager = null;
    Music gameMusic = null;
    public Player2 player2;
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
        GameUtility.setWorld(world);

        //set the contact listener

        //set the camera position
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);

        worldCreator = new JumpingMarbleWorldCreator(world, map);
        GameUtility.worldCreator = worldCreator;
        gameObjects = worldCreator.gameObjects;
        player2 = worldCreator.player;//new Player2(22,null,world,GameUtility.getAtlas(),worldCreator.player.mapObject);

        controller = new Controller(batch);

        GameUtility.gameScreen = this;

        gameMusic = assetManager.get("music/Flying me softly.ogg",Music.class);
        gameMusic.setLooping(true);
        gameMusic.play();

        //add speedBombs
        SpeedBomb b;
        for(int i = 0;i<10;i++){
            b = new SpeedBomb(111,world, GameUtility.getBlastAtlas(),null);
            b.xPos = 10 + i*50;
            b.yPos = 10;
            b.initialize();
            gameObjects.add(b);
        }

        JumpingKit jKit;
        for(int i = 0;i<15;i++){
            jKit = new JumpingKit(3,world, GameUtility.getAtlas(),null);
            jKit.xPos = 30 + i*30;
            jKit.yPos = 5;
            jKit.initialize();
            gameObjects.add(jKit);
        }

        PowerDrink drink;
        for(int i = 0;i<15;i++){
            drink = new PowerDrink(3,world, GameUtility.getBlastAtlas(),null);
            drink.xPos = 20 + i*30;
            drink.yPos = 10;
            drink.initialize();
            gameObjects.add(drink);
        }
        CowBoyHat hat;
        for(int i = 0;i<15;i++){
            hat = new CowBoyHat(3,world, GameUtility.getAtlas(),null);
            hat.xPos = 20 + i*30;
            hat.yPos = 5;
            hat.initialize();
            gameObjects.add(hat);
        }
        BombAmo amo;
        for(int i = 0;i<15;i++){
            amo = new BombAmo(3,world, GameUtility.getAtlas(),null);
            amo.xPos = 15 + i*30;
            amo.yPos = 10;
            amo.initialize();
            gameObjects.add(amo);
        }
        Coin coin;
        for(int i = 0;i<10;i++){
            coin = new Coin(3,world, GameUtility.getAtlas(),null);
            coin.xPos = 2 + i*30;
            coin.yPos = 7;
            coin.initialize();
            gameObjects.add(coin);
        }
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

        GameUtility.renderGameObjects(batch, gameObjects);

        batch.end();
        controller.draw();
    }

    //utility method to be used in render
    private void update(float dt){
        //handle the i/p every delta time
        handleIp(dt);

        //update the game after handling the IP
        updateHandler.update(this,dt);

        //         player2.update(dt);

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
