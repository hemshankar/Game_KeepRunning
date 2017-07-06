package com.jumping.marbles.Casts;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Brick extends JumpingMarblesCast{
    public World world;
    public Body body;
    MapObject mapObject;

    public Brick(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        definePusher();
        setBounds(0,0,GameConstants.BRICK_SIZE*2/GameConstants.PPM,GameConstants.BRICK_SIZE/GameConstants.PPM);
    }

    public void definePusher(){

        //initiate the objects to create a body
        PolygonShape shape = new PolygonShape();
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //get the map object
        Rectangle rect = ((RectangleMapObject)mapObject).getRectangle();

        //set the body definition
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((rect.x + rect.width/2)/ GameConstants.PPM,(rect.y + rect.height/2)/GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(bdef);

        //create shape
        shape.setAsBox(GameConstants.BRICK_SIZE/GameConstants.PPM,GameConstants.BRICK_SIZE/2/GameConstants.PPM);

        //create fixtureDef using shape
        fixtureDef.shape = shape;
        fixtureDef.restitution=0.5f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(fixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    @Override
    public String getCastName() {
        return GameConstants.BRICK_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
