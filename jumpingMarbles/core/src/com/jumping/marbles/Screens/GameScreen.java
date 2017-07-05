package com.jumping.marbles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.jumping.marbles.Casts.Healer;
import com.jumping.marbles.Casts.JumpingMarblesCast;
import com.jumping.marbles.Casts.Player;
import com.jumping.marbles.Casts.SuckerCreator;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.JumpingMarbleWorldCreator;
import com.jumping.marbles.Utility.Utility;
import com.jumping.marbles.Utility.WorldContactListner;

import org.omg.CORBA.MARSHAL;

import java.util.List;

/**
 * Created by hsahu on 7/1/2017.
 */

public class GameScreen implements Screen {

    //=================Basic LibGDX
    //OrthographicCamera which is the eye of the user, i.e. which is that user sees.
    OrthographicCamera camera;
    //Viewport handles the way our screen will be rendered in different screen size, it decides what aspect ration to use and how much of the screen/game area to be shown
    Viewport viewport;
    //Batch used for dumping all the graphics to be rendered
    SpriteBatch batch;

    //===================From Tiled
    //loads a specific map
    TmxMapLoader mapLoader;
    //the map object
    TiledMap map;
    //Used to render the map on the camera/screen
    OrthogonalTiledMapRenderer renderer;

    //===================Box 2D related
    //the world where all the physics simulation will take place
    World world;
    //the shape renderer for box 2d so that we can see the shapes of various fixtures
    Box2DDebugRenderer b2dr;
    //A utility function to create the world from the map
    JumpingMarbleWorldCreator worldCreator;

    //player handle that will be created by world
    Player player;

    //all other casts
    List<JumpingMarblesCast> casts;
    public GameScreen(SpriteBatch batch){
        //Initialize all the variables
        this.batch = batch;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1200/ GameConstants.PPM,800/GameConstants.PPM,camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tiles/JumpingMarblesMap2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/GameConstants.PPM);

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
        for(JumpingMarblesCast cast : casts){
            cast.initialize();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //b2dr.render(world,camera.combined);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //player.draw(batch);
        for(JumpingMarblesCast cast : casts){
            cast.draw(batch);
        }
        batch.end();
    }

    //utility method to be used in render
    private void update(float dt){
        //handle the i/p every delta time
        handleIp();

        //update the world every 1/60 of a second?
        world.step(1/60f,6,2);

        //update all the characters
        //player.update();
        for(JumpingMarblesCast cast : casts){
            cast.update(dt);
        }

        for(SuckerCreator creator : worldCreator.suckersCreator){
            creator.createSucker();
        }

        for(Healer healer: worldCreator.healers){
            healer.healPlayer();
        }

        worldCreator.destroyBodies();

        //update the camera as the player moves (player decides the camera position
        float x = player.body.getPosition().x;
        if(x<400/GameConstants.PPM)
            x = 400/GameConstants.PPM;
        else if(x>9600/GameConstants.PPM)
            x = 9600/GameConstants.PPM;
        camera.position.x=x;

        float y = player.body.getPosition().y;
        if(y<200/GameConstants.PPM)
            y = 200/GameConstants.PPM;
        else if(y>950/GameConstants.PPM)
            y = 950/GameConstants.PPM;

        camera.position.y=y;
        //System.out.println( player.body.getPosition().x + " -- " + player.body.getPosition().y);
        camera.update();

        //orthographic map renderer's position is decided by the camera
        renderer.setView(camera);
    }

    //utility method to handle Ip
    private void handleIp(){
        if(Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth()/2){
            player.body.applyLinearImpulse(new Vector2(0.4f,0),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth()/2){
            player.body.applyLinearImpulse(new Vector2(0,0.4f),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.body.applyLinearImpulse(new Vector2(0,0.4f),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.body.getLinearVelocity().x >= -2){
            player.body.applyLinearImpulse(new Vector2(-0.4f,0),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2){
            player.body.applyLinearImpulse(new Vector2(0.4f,0),player.body.getWorldCenter(),true);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
