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

/**
 * Created by hsahu on 7/1/2017.
 */

public class B2WorldCreator {
    public static Subject player;
    public B2WorldCreator(World world, TiledMap map){
//creating bodies
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;



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
}
