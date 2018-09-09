package com.pintu.futurewars.Screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Pivot;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 10/1/2017.
 */

abstract public class BaseScreen extends InputAdapter implements Screen {

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

    public Vector3 point = new Vector3();
    public Body bodyThatWasHit;
    public Body pivotBody;

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {

            Body body = fixture.getBody();
            GameUtility.log("1" + this.getClass().getName(),body.getUserData().toString());
            if(!(body.getUserData() instanceof Ground) && !(body.getUserData() instanceof Player2)){
                if(body.getUserData() instanceof Pivot){
                    pivotBody = body;
                }else {
                    bodyThatWasHit = body;
                }
            }
            if (fixture.testPoint(point.x, point.y)) {
                //bodyThatWasHit = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        point.set(screenX, screenY, 0); // Translate to world coordinates.
        camera.unproject(point);
        // Ask the world for bodies within the bounding box.
        bodyThatWasHit = null;
        pivotBody = null;
        world.QueryAABB(callback, point.x - 1, point.y - 1, point.x + 1, point.y + 1);

        //GameUtility.log(this.getClass().getName(),bodyThatWasHit.getUserData().toString());

        if(bodyThatWasHit != null) {
            // Do something with the body
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }
}
