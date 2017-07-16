package com.pintu.futurewars.Casts;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Sucker extends FutureWarsCast {
    public World world;
    public Body body;
    MapObject mapObject;
    Player player=null;
    public static float IMPLUSE_APPLY_INTERVAL=.1f;
    public static float tmpTime = 0f;
    public boolean canSuck=false;
    public boolean suckking = false;
    static int suckerIdCount = 0;
    public int suckerId = 0;
    public boolean justThrown = false;
    public float JUST_THROWN_CONST = 2f;
    public float justThrownTmp = 0;
    public Sucker(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        definePusher();
        suckerId = suckerIdCount++;
        System.out.println(suckerId);
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
        cbdef.position.set((c.x + c.width/2)/ GameConstants.PPM,(c.y + c.height/2)/ GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(cbdef);

        //create shape
        cshape.setRadius(GameConstants.SUCKER_SIZE/ GameConstants.PPM);

        //create fixtureDef using shape
        cFixtureDef.shape = cshape;
        cFixtureDef.restitution=0.5f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(cFixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    public void moveTowardsPlayer(){

        if(player!=null){
            body.setLinearVelocity((player.body.getPosition().x - body.getPosition().x) * 2f,
                    (player.body.getPosition().y - body.getPosition().y) * 2f);
        }
    }

    public void suck(float dt){
        player = Utility.getPlayer();
        if(player !=null && !canSuck && !justThrown) {
            float playerX = player.body.getPosition().x;
            float playerY = player.body.getPosition().y;

            if (Math.abs(body.getPosition().x - playerX) < 200 / GameConstants.PPM
                    && Math.abs(body.getPosition().y - playerY) < 200 / GameConstants.PPM) {
                moveTowardsPlayer();
            }
        }
        else if(canSuck && !suckking) {
            System.out.println("Started Sucking");
            DistanceJointDef wd = new DistanceJointDef();
            wd.bodyA = body;
            wd.bodyB = player.body;
            //wd.collideConnected = true;
            wd.length = 30/ GameConstants.PPM;
            //wd.referenceAngle = 180;// wd.bodyB.getAngle() - wd.bodyA.getAngle();
            Joint j = world.createJoint(wd);
            player.suckers.add(this);
            player.joints.add(j);
            suckking = true;
        }
        if(!suckking && !justThrown){
            tmpTime += dt;
            if(tmpTime>IMPLUSE_APPLY_INTERVAL) {
                this.body.applyLinearImpulse(new Vector2(0, .9f), this.body.getWorldCenter(), true);
                tmpTime-= IMPLUSE_APPLY_INTERVAL;
            }
        }
        if(justThrown){
            justThrownTmp += dt;
            if(justThrownTmp > JUST_THROWN_CONST){
                justThrownTmp = 0;
                justThrown = false;
            }
        }
    }

    @Override
    public String getCastName() {
        return GameConstants.SUCKER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        suck(dt);
    }
}
