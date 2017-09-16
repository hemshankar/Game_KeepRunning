package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Casts.BombAmo;
import com.pintu.futurewars.Casts.Coin;
import com.pintu.futurewars.Casts.CowBoyHat;
import com.pintu.futurewars.Casts.FlyingKit;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.JumpingKit;
import com.pintu.futurewars.Casts.Kaleen;
import com.pintu.futurewars.Casts.Magnet;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.PowerDrink;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Casts.StickyBomb;
import com.pintu.futurewars.Casts.WaterBalloon;
import com.pintu.futurewars.Controllers.Widgets;
import com.pintu.futurewars.Controllers.InputHandler;
import com.pintu.futurewars.JumpingMarblesGame;
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

    //Widgets
    public Widgets widgets;

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
    public Music gameMusic = null;
    public Player2 player2 = null;
    public JumpingMarblesGame game = null;

    //Handle background separately
    public Texture backgroundImages1,backgroundImages2;//,backgroundImage2;
    public float backImgX1 = 0;
    public float backImgY1 = 0;
    public float backImgX2 = 0;
    public float backImgY2 = 0;
    public float backImgWidth = 60;
    public float backImgHeight = 30;
    public float numOfBackImgs = 1;

    public float timePassed = 0;
    public float gameTime = 120;

    public GameScreen(JumpingMarblesGame game){

        //Initialize all the variables
        float scrWidth = Gdx.graphics.getWidth();
        float scrHight = Gdx.graphics.getHeight();
        this.game = game;
        this.batch = game.batch;
        this.assetManager = game.assetManager;
        camera = game.camera;//new OrthographicCamera();
        viewport = game.viewport;//new FitViewport(GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,camera);
        //viewport = new FitViewport(scrWidth,scrHight,camera);
        //System.out.println("=====" + scrWidth + "--" + scrHight );

        /*mapLoader = new TmxMapLoader();
        map = mapLoader.load(GameConstants.FUTURE_WARS_MAP);
        renderer = new OrthogonalTiledMapRenderer(map,1/ GameConstants.PPM);*/

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

        widgets = new Widgets(game,this);
        GameUtility.setGameScreen(this);

        gameMusic = assetManager.get("music/Flying me softly.ogg",Music.class);
        gameMusic.setLooping(true);
        gameMusic.play();

        //add speedBombs

        player2 = new Player2(222, world, GameUtility.stickAtlas, null);
        player2.initialize();
        gameObjects.add(player2);
        player2.body.setUserData(player2);

        //background Image-Should keep rotating
        backgroundImages1 = new Texture(Gdx.files.internal("imgs/sky1.png"));//("imgs/stage1BackGround3.jpg"));
        backgroundImages2 = new Texture(Gdx.files.internal("imgs/sky1.png"));

        SpeedBomb b;
        for(int i = 0;i<10;i++){
            b = new SpeedBomb(111,world, GameUtility.getBlastAtlas(),null);
            b.xPos = 10 + i*10;
            b.yPos = 10;
            b.flyPosition = 10;
            b.initialize();
            gameObjects.add(b);
        }
        Pusher p;
        for(int i = 0;i<20;i++){
            p = new Pusher(143,world, GameUtility.getAtlas(),null);
            p.xPos = 10 + i*10;
            p.yPos = 10;
            p.flyPosition = 10;
            p.initialize();
            gameObjects.add(p);
        }

        StickyBomb s;
        for(int i = 0;i<20;i++){
            s = new StickyBomb(181,world, GameUtility.getBlastAtlas(),null);
            s.xPos = 37 + i*20;
            s.yPos = 10;
            s.flyPosition = 10;
            s.initialize();
            gameObjects.add(s);
        }

        JumpingKit jKit;
        for(int i = 0;i<5;i++){
            jKit = new JumpingKit(3,world, GameUtility.getAtlas(),null);
            jKit.xPos = 130 + i*100;
            jKit.yPos = 5;
            jKit.flyPosition = 5;
            jKit.initialize();
            gameObjects.add(jKit);
        }

        FlyingKit fKit;
        for(int i = 0;i<20;i++){
            fKit = new FlyingKit(3,world, GameUtility.getAtlas(),null);
            fKit.xPos = 130 + i*50;
            fKit.yPos = 7;
            fKit.flyPosition = 7;
            fKit.initialize();
            gameObjects.add(fKit);
        }

        PowerDrink drink;
        for(int i = 0;i<5;i++){
            drink = new PowerDrink(3,world, GameUtility.getBlastAtlas(),null);
            drink.xPos = 120 + i*50;
            drink.yPos = 10;
            drink.flyPosition = 10;
            drink.initialize();
            gameObjects.add(drink);
        }
        CowBoyHat hat;
        for(int i = 0;i<5;i++){
            hat = new CowBoyHat(3,world, GameUtility.getAtlas(),null);
            hat.xPos = 120 + i*100;
            hat.yPos = 5;
            hat.flyPosition = 5;
            hat.initialize();
            gameObjects.add(hat);
        }
        BombAmo amo;
        for(int i = 0;i<5;i++){
            amo = new BombAmo(3,world, GameUtility.getAtlas(),null);
            amo.xPos = 115 + i*50;
            amo.yPos = 10;
            amo.flyPosition = 10;
            amo.initialize();
            gameObjects.add(amo);
        }
        Coin coin;
        for(int i = 0;i<200;i++){
            coin = new Coin(3,world, GameUtility.getAtlas(),null);
            coin.xPos = 42 + i*10;
            coin.yPos = 7;
            coin.flyPosition = 7;
            coin.initialize();
            gameObjects.add(coin);
        }

        Magnet m;
        for(int i = 0;i<20;i++){
            m = new Magnet(3,world, GameUtility.getAtlas(),null);
            m.xPos = 5 + i*10;
            m.yPos = 5;
            m.flyPosition = 5;
            m.initialize();
            gameObjects.add(m);
        }

        WaterBalloon balloon;
        for(int i = 0;i<20;i++){
            balloon = new WaterBalloon(3,world, GameUtility.getBlastAtlas(),null);
            balloon.xPos = 122 + i*100;
            balloon.yPos = 7;
            balloon.flyPosition = 7;
            balloon.initialize();
            gameObjects.add(balloon);
        }

        Kaleen kaleen;
        for(int i = 0;i<10;i++){
            kaleen = new Kaleen(33,world, GameUtility.getAtlas(),null);
            kaleen.xPos = 150 + i*100;
            kaleen.yPos = 15;
            kaleen.flyPosition = 15;
            kaleen.initialize();
            gameObjects.add(kaleen);
        }

        Ground g = new Ground(34,world, GameUtility.getAtlas(),null);
        g.xPos = 0;
        g.yPos = 4;
        g.initialize();
        gameObjects.add(g);

        Ground roof = new Ground(134,world, GameUtility.getAtlas(),null);
        roof.xPos = 0;
        roof.yPos = 20;
        roof.initialize();
        gameObjects.add(roof);

        //handling pause
        game.pauseScreen.stageScreen = this;

    }

    @Override
    public void show() {
        System.out.println("Show called");
        Gdx.input.setInputProcessor(widgets.stage);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderer.render();
        batch.setProjectionMatrix(camera.combined);

        //Background Image
        batch.begin();
        batch.draw(backgroundImages1, backImgX1, backImgY1,backImgWidth,backImgHeight);//Gdx.graphics.getWidth()/GameConstants.PPM,Gdx.graphics.getHeight()/GameConstants.PPM);
        batch.draw(backgroundImages2, backImgX2, backImgY2,backImgWidth,backImgHeight);
        GameUtility.renderGameObjects(batch, gameObjects);

        batch.end();
        widgets.draw();

        b2dr.render(world,camera.combined);

    }

    //utility method to be used in render
    private void update(float dt){

        //handle the i/p every delta time
        handleIp(dt);

        //update the game after handling the IP
        updateHandler.update(this,dt);

        //player2.update(dt);
    }

    //utility method to handle Ip
    private void handleIp(float dt){
        inputHandler.hadleInput(this,dt);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        widgets.resize(width,height);
    }

    @Override
    public void pause() {
        System.out.println("GameScreen: Pause called");
    }

    @Override
    public void resume() {
        System.out.println("Show called");
    }

    @Override
    public void hide() {
        pause();
        System.out.println("GameScreen: Hide called");
    }

    @Override
    public void dispose() {
        System.out.println("GameScreen: Dispose called");
        //map.dispose();
        //renderer.dispose();
        world.dispose();
        b2dr.dispose();
        widgets.dispose();
        backgroundImages1.dispose();
        backgroundImages2.dispose();
        GameUtility.log(this.getClass().getName(), "Disposed");
        //Never call ----batch.dispose();
    }
}
