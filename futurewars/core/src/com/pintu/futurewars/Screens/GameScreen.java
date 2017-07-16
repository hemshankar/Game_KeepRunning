package com.pintu.futurewars.Screens;

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
import com.pintu.futurewars.Casts.Healer;
import com.pintu.futurewars.Casts.SuckerCreator;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Controller;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Utility.WorldContactListner;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;

import java.util.ArrayList;
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
    List<FutureWarsCast> casts;

    //Controller
    Controller controller;

    //mas velocity of the player
    float maxVelocity=0;
    float cameraCalibration=0;
    float heightCamCalibration=0;
    float recoilTimeElapsed = 0;

    public List<GameBullet> bullets = new ArrayList<GameBullet>();
    public List<GameBullet> bulletsToBeRemoved = new ArrayList<GameBullet>();

    public GameScreen(SpriteBatch batch){
        //Initialize all the variables
        float scrWidth = Gdx.graphics.getWidth();
        float scrHight = Gdx.graphics.getHeight();
        this.batch = batch;
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
        for(FutureWarsCast cast : casts){
            cast.draw(batch);
        }
        for(GameBullet bullet : bullets){
            bullet.draw(batch);
        }
        batch.end();
        controller.draw();
    }

    //utility method to be used in render
    private void update(float dt){
        //handle the i/p every delta time
        handleIp(dt);

        //update the world every 1/60 of a second?
        world.step(1/60f,6,2);

        //update all the characters
        //player.update();
        for(FutureWarsCast cast : casts){
            cast.update(dt);
        }

        for(SuckerCreator creator : worldCreator.suckersCreator){
            creator.createSucker();
        }

        for(Healer healer: worldCreator.healers){
            healer.healPlayer();
        }

        for(GameBullet bullet: bullets){
            bullet.update(dt);
            if(bullet.toBeDestroyed){
                bulletsToBeRemoved.add(bullet);
            }
        }
        if( !Utility.world.isLocked()) {
            for (GameBullet bullet : bulletsToBeRemoved) {
                if(bullet.getBody()!=null) {
                    Utility.world.destroyBody(bullet.getBody());
                    bullet.setBody(null);
                    bullet.destroyed = true;
                }
            }
        }

        bullets.remove(bulletsToBeRemoved);
        bulletsToBeRemoved.clear();

        worldCreator.destroyBodies();

        //update the camera as the player moves (player decides the camera position
        float x = player.body.getPosition().x;
        if(x<(viewport.getWorldWidth()/2))
            x = viewport.getWorldWidth()/2;
        else if(x>(Utility.worldCreator.boundaryRight/ GameConstants.PPM - viewport.getWorldWidth()/2))
            x = Utility.worldCreator.boundaryRight/ GameConstants.PPM - viewport.getWorldWidth()/2;

        camera.position.x=x;

        float y = player.body.getPosition().y;
        if(y<viewport.getWorldHeight()/2)
            y = viewport.getWorldHeight()/2;
        else if(y>( Utility.worldCreator.boundaryTop/ GameConstants.PPM - viewport.getWorldHeight()/2))
            y = ( Utility.worldCreator.boundaryTop/ GameConstants.PPM )- viewport.getWorldHeight()/2;

        camera.position.y=y;
        //System.out.println( player.body.getPosition().x + " -- " + player.body.getPosition().y);
        camera.update();

        //orthographic map renderer's position is decided by the camera
        renderer.setView(camera);

        //get the body speed and resize the viewport
        Vector2 velocity = player.body.getLinearVelocity();
        maxVelocity = velocity.x>velocity.y?velocity.x:velocity.y;
        if(Math.abs(maxVelocity-cameraCalibration)>1) {
            if (maxVelocity - cameraCalibration > 0) {
                cameraCalibration += .1;
            } else if (maxVelocity - cameraCalibration < 0) {
                cameraCalibration -= .1;
            }
        }
        if(player.body.getPosition().y > Utility.worldCreator.boundaryTop/ GameConstants.PPM/2){
            if((player.body.getPosition().y - Utility.worldCreator.boundaryTop/ GameConstants.PPM/2) > cameraCalibration){
                cameraCalibration+=(.1);
            }
        }

        float yCal = 4*cameraCalibration/3;
        if(yCal > 6){
            yCal = 6;
        }

        float xCal = 9 * yCal/4;
       /* if(xCal>15){
            xCal = 15;
        }*/


        viewport.setWorldSize(GameConstants.VIEW_PORT_WIDTH / GameConstants.PPM /*xCal*/,
                GameConstants.VIEW_PORT_HIGHT / GameConstants.PPM /*yCal*/);

        viewport.apply();
    }

    //utility method to handle Ip
    private void handleIp(float dt){

        recoilTimeElapsed +=dt;

        if(controller.controles[GameConstants.UP] || Gdx.input.isKeyPressed(Input.Keys.UP)){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(0,5f),player.body.getWorldCenter(),true);
            }else */{
                player.body.applyLinearImpulse(new Vector2(0, 0.4f), player.body.getWorldCenter(), true);
            }
        }
        if(controller.controles[GameConstants.DOWN] || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(0,-5f),player.body.getWorldCenter(),true);
            }else */{
                player.body.applyLinearImpulse(new Vector2(0, -0.4f), player.body.getWorldCenter(), true);
            }
        }

        if((controller.controles[GameConstants.LEFT] || Gdx.input.isKeyPressed(Input.Keys.LEFT))
                //&& player.body.getLinearVelocity().x >= -2
                ){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(-5f,0),player.body.getWorldCenter(),true);
            }else */{
                player.body.applyLinearImpulse(new Vector2(-0.4f, 0), player.body.getWorldCenter(), true);
            }
        }
        if((controller.controles[GameConstants.RIGHT] || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                //&& player.body.getLinearVelocity().x <= 2
                ){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(5f,0),player.body.getWorldCenter(),true);
            }else*/{
                player.body.applyLinearImpulse(new Vector2(0.4f,0),player.body.getWorldCenter(),true);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){

            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                bullets.add(new BasicBullet(player.getX() + player.getWidth() + 10 / GameConstants.PPM, player.getY() + player.getHeight() / 2, GameConstants.RIGHT));
            }
        }

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
