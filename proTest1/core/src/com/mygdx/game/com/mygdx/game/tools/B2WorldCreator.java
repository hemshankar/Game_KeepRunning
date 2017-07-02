package com.mygdx.game.com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.com.mygdx.game.tiles.GameClassDemo;
import com.mygdx.game.com.mygdx.game.tiles.screens.sprites.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 7/1/2017.
 */

public class B2WorldCreator {
    public static List<Subject> players;
    public static List<Subject> enemies;

    public B2WorldCreator(World world, TiledMap map){
        //creating bodies
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        players = new ArrayList<Subject>();
        enemies = new ArrayList<Subject>();

        //All the rectangles
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ GameClassDemo.PPM,
                    (rect.getY()  +rect.getHeight()/2)/GameClassDemo.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/GameClassDemo.PPM,(rect.getHeight()/2)/GameClassDemo.PPM);
            fixtureDef.shape = shape;
            Fixture f = body.createFixture(fixtureDef);
            f.setUserData(this);
        }
        //all the circles
        CircleShape cshape = new CircleShape();
        BodyDef cbdef = new BodyDef();
        FixtureDef cFixtureDef = new FixtureDef();
        body = null;
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(EllipseMapObject.class)){
            //create players
            players.add(new Subject(world,object));
        }
        for(MapObject object: map.getLayers().get("enemies").getObjects().getByType(EllipseMapObject.class)){
            //create enemies
            enemies.add(new Subject(world,object));
        }
    }
}
