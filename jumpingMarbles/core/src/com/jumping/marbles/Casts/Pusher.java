package com.jumping.marbles.Casts;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends JumpingMarblesCast{

    public World world;
    public Body body;
    MapObject mapObject;

    public Pusher(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        definePusher();
    }

    public void definePusher(){

        //initiate the objects to create a body
        CircleShape cshape = new CircleShape();
        BodyDef cbdef = new BodyDef();
        FixtureDef cFixtureDef = new FixtureDef();

        //get the map object
        Ellipse c = ((EllipseMapObject)mapObject).getEllipse();

        //set the body definition
        cbdef.type = BodyDef.BodyType.DynamicBody;
        cbdef.position.set((c.x + c.width/2)/ GameConstants.PPM,(c.y + c.height/2)/GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(cbdef);

        //create shape
        cshape.setRadius((c.width/2)/GameConstants.PPM);

        //create fixtureDef using shape
        cFixtureDef.shape = cshape;
        cFixtureDef.restitution=0.5f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(cFixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    @Override
    public String getCastName() {
        return GameConstants.PUSHER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
