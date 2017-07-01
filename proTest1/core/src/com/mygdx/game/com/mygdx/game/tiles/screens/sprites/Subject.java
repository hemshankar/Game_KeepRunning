package com.mygdx.game.com.mygdx.game.tiles.screens.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.com.mygdx.game.tiles.GameClassDemo;

/**
 * Created by hsahu on 6/29/2017.
 */

public class Subject extends Sprite {

    public World world;
    public Body body;
    MapObject mapObject;
    public Subject(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        defineSubject();
    }

    public void defineSubject(){
        BodyDef bDef = new BodyDef();
        CircleShape cshape = new CircleShape();
        BodyDef cbdef = new BodyDef();
        FixtureDef cFixtureDef = new FixtureDef();
        body = null;

        Ellipse c = ((EllipseMapObject)mapObject).getEllipse();
        cbdef.type = BodyDef.BodyType.DynamicBody;
        System.out.println(c.x + " " + c.y);
        //cbdef.position.set(c.x + c.width/2,c.y + c.height/2);
        cbdef.position.set((c.x + c.width/2)/ GameClassDemo.PPM,(c.y + c.height/2)/GameClassDemo.PPM);

        body = world.createBody(cbdef);

        cshape.setRadius((c.width/2)/GameClassDemo.PPM);

        cFixtureDef.shape = cshape;
        cFixtureDef.restitution=0.2f;

        Fixture f =body.createFixture(cFixtureDef);
        f.setUserData(this);
    }
}
