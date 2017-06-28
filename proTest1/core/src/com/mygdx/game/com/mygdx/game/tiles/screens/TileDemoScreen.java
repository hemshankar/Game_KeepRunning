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

    private Subject player;

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

        //creating bodies
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //All the rectangles
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/GameClassDemo.PPM,
                    (rect.getY()  +rect.getHeight()/2)/GameClassDemo.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/GameClassDemo.PPM,(rect.getHeight()/2)/GameClassDemo.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }




        //all the circles
        CircleShape cshape = new CircleShape();
        BodyDef cbdef = new BodyDef();
        FixtureDef cFixtureDef = new FixtureDef();
        body = null;
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(EllipseMapObject.class)){
            //create player
            player = new Subject(world,object);
            /*Ellipse c = ((EllipseMapObject)object).getEllipse();
            cbdef.type = BodyDef.BodyType.DynamicBody;

            cbdef.position.set((c.x + c.width/2)/GameClassDemo.PPM,(c.y + c.height/2)/GameClassDemo.PPM);

            body = world.createBody(cbdef);

            cshape.setRadius((c.width/2)/GameClassDemo.PPM);

            cFixtureDef.shape = cshape;
            //cFixtureDef.density=10;
            body.createFixture(cFixtureDef);*/
        }

    }

    @Override
    public void show() {

    }


    private void handleIp(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.body.applyLinearImpulse(new Vector2(0,0.4f),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.body.getLinearVelocity().x >= -2){
            player.body.applyLinearImpulse(new Vector2(-0.1f,0),player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2){
            player.body.applyLinearImpulse(new Vector2(0.1f,0),player.body.getWorldCenter(),true);
        }
    }
    public void update(float dt){
        handleIp(dt);

        world.step(1/60f,6,2);
        camera.position.x=player.body.getPosition().x;
        camera.position.y=player.body.getPosition().y;
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

    }
}
