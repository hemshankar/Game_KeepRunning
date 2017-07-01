package com.mygdx.game.com.mygdx.game.tiles.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.com.mygdx.game.tiles.GameClassDemo;
import com.mygdx.game.com.mygdx.game.tiles.screens.sprites.Subject;
import com.mygdx.game.com.mygdx.game.tools.B2WorldCreator;
import com.mygdx.game.com.mygdx.game.tools.MyWorldContactListner;

/**
 * Created by hsahu on 6/28/2017.
 */

public class TileDemoScreen implements Screen {

    public GameClassDemo game;
    private OrthographicCamera camera;
    private Viewport viewport;
    int w,h;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;

    //Box 2D related
    private World world;
    private Box2DDebugRenderer b2dr;

    public TileDemoScreen(GameClassDemo game){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800/GameClassDemo.PPM,600/GameClassDemo.PPM,camera);// StretchViewport(200,200,camera);

        //Tile related
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tiles/Map_100x20.tmx");
        render = new OrthogonalTiledMapRenderer(map,1/GameClassDemo.PPM);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);

        //Box 2D
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new MyWorldContactListner());
        new B2WorldCreator(world,map);

    }

    @Override
    public void show() {

    }


    private void handleIp(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            B2WorldCreator.player.body.applyLinearImpulse(new Vector2(0,0.4f),B2WorldCreator.player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& B2WorldCreator.player.body.getLinearVelocity().x >= -2){
            B2WorldCreator.player.body.applyLinearImpulse(new Vector2(-0.1f,0),B2WorldCreator.player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && B2WorldCreator.player.body.getLinearVelocity().x <= 2){
            B2WorldCreator.player.body.applyLinearImpulse(new Vector2(0.1f,0),B2WorldCreator.player.body.getWorldCenter(),true);
        }
    }
    public void update(float dt){
        handleIp(dt);

        world.step(1/60f,6,2);
        camera.position.x=B2WorldCreator.player.body.getPosition().x;
        camera.position.y=B2WorldCreator.player.body.getPosition().y;
        camera.update();

        render.setView(camera);
    }


    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        render.render();
        b2dr.render(world,camera.combined);
        game.batch.setProjectionMatrix(camera.combined);

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
        render.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
