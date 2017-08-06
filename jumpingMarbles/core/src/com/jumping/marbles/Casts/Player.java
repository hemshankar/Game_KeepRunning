package com.jumping.marbles.Casts;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player extends JumpingMarblesCast{

    public World world;
    public Body body;
    MapObject mapObject;
    public boolean removeSuckers = false;
    public List<Sucker> suckers = new ArrayList<Sucker>();
    public List<Joint> joints = new ArrayList<Joint>();
    public Player(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        definePusher();
        Utility.setPlayer(this);
    }

    public void definePusher(){

        //initiate the objects to create a body
        CircleShape cshape = new CircleShape();
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //get the map object
        Ellipse c = ((EllipseMapObject)mapObject).getEllipse();

        //set the body definition
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set((c.x + c.width/2)/ GameConstants.PPM,(c.y + c.height/2)/GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(bdef);

        //create shape
        cshape.setRadius(GameConstants.PLAYER_SIZE/GameConstants.PPM);

        //create fixtureDef using shape
        fixtureDef.shape = cshape;
        fixtureDef.restitution=.5f;
        fixtureDef.friction=.2f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(fixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    public void throwSuckers(){
        for(Sucker s: suckers){
            float xDirection = this.body.getPosition().x - s.body.getPosition().x;
            xDirection = Math.abs(xDirection)/xDirection;

            float yDirection = this.body.getPosition().y - s.body.getPosition().y;
            yDirection = Math.abs(yDirection)/yDirection;
            s.body.setLinearVelocity((-xDirection) * 20f,  (-yDirection) * 20f);
            s.canSuck = false;
            s.suckking=false;
            s.justThrown=true;
        }
        suckers.clear();
    }

    public void setFree(){
        Utility.worldCreator.addJointsToRemove(this.joints);
        this.joints.clear();
        Utility.worldCreator.removeJoints();
        throwSuckers();

    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public String getCastName() {
        return GameConstants.PLAYER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
