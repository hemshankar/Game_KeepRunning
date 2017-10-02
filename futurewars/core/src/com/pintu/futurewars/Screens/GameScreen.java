package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Controllers.Widgets;
import com.pintu.futurewars.Utility.InputHandler;
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

public class GameScreen extends BaseScreen {


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
    public Texture screenBackgroundImages1, screenBackgroundImages2;//,backgroundImage2;
    public float backImgX1 = 0;
    public float backImgY1 = 0;
    public float backImgX2 = 0;
    public float backImgY2 = 0;
    public float backImgWidth = 60;
    public float backImgHeight = 30;
    public float numOfBackImgs = 1;

    public float timePassed = 0;
    public float gameTime = 120;

    public float slowMotionEffect = 0;
    public float slowMotionEffectTime = 2f;
    public boolean isslowMotionEffect = false;
    public long sleepTime = 0;

    public ShapeRenderer shapeRenderer = null;
    //Comparator<GameObject> comparator = new GameObjectComparator();
    //PriorityQueue<GameObject> gameObjectsPQ = new PriorityQueue<GameObject>(10,comparator);
    public float nearestDist = Float.MAX_VALUE; //(x*x + y*y)^2
    public GameObject nearestGameObj = null;

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

        //background Image-Should keep rotating
        screenBackgroundImages1 = new Texture(Gdx.files.internal("imgs/sky1.png"));//("imgs/stage1BackGround3.jpg"));
        screenBackgroundImages2 = new Texture(Gdx.files.internal("imgs/sky1.png"));

        System.out.println("Total game objects at start: " + gameObjects.size() + ": " + gameObjects);

        player2 = new Player2();
        player2.initialize();
        gameObjects.add(player2);
        player2.body.setUserData(player2);

        Ground g = new Ground();
        g.xPos = 0;
        g.yPos = 4;
        g.initialize();
        gameObjects.add(g);

        Ground roof = new Ground();
        roof.xPos = 0;
        roof.yPos = 50;
        roof.initialize();
        gameObjects.add(roof);

        //handling pause
        game.pauseScreen.stageScreen = this;

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void show() {
        System.out.println("Show called");
        InputProcessor inputProcessorOne = this;
        InputProcessor inputProcessorTwo = this.widgets.stage;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(widgets.stage);
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
        batch.draw(screenBackgroundImages1, backImgX1, backImgY1,backImgWidth,backImgHeight);//Gdx.graphics.getWidth()/GameConstants.PPM,Gdx.graphics.getHeight()/GameConstants.PPM);
        batch.draw(screenBackgroundImages2, backImgX2, backImgY2,backImgWidth,backImgHeight);
        GameUtility.renderGameObjects(batch, gameObjects);

        batch.end();
        widgets.draw();

        GameUtility.shapeHelper.drawShapes(shapeRenderer,camera);

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
        gameObjects.clear();
        screenBackgroundImages1.dispose();
        screenBackgroundImages2.dispose();
        shapeRenderer.dispose();
        GameUtility.disposeAllAtlas();
        GameUtility.gameObjectCreator.reset();
        GameUtility.log(this.getClass().getName(), "Disposed");
        //Never call ----batch.dispose();
    }

    /*public class GameObjectComparator implements Comparator<GameObject>
    {
        @Override
        public int compare(GameObject gameObject, GameObject t1) {
            if(gameObject.getBody().getPosition().x<t1.getBody().getPosition().x)
                return 1;
            if(gameObject.getBody().getPosition().x>t1.getBody().getPosition().x)
                return -1;

            return 0;
        }
    }*/
}
