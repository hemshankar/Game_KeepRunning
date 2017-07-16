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
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends FutureWarsCast {

    public World world;
    public Body body;
    MapObject mapObject;
    Player player = null;
    public static final float IMPLUSE_APPLY_INTERVAL=.1f;
    public static final float PUSH_RECOIL_TIME = 5;
    public static float impluseTempTime = 0f;
    public static float pushTempTime = 0f;

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
        cbdef.position.set((c.x + c.width/2)/ GameConstants.PPM,(c.y + c.height/2)/ GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(cbdef);

        //create shape
        cshape.setRadius(GameConstants.PUSHER_SIZE/ GameConstants.PPM);

        //create fixtureDef using shape
        cFixtureDef.shape = cshape;
        cFixtureDef.restitution=0.5f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(cFixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    public void pushPlayer(){

        if(player!=null){
            body.setLinearVelocity((player.body.getPosition().x - body.getPosition().x) * 10f,
                                    (player.body.getPosition().y - body.getPosition().y) * 10f);
        }
    }

    @Override
    public String getCastName() {
        return GameConstants.PUSHER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        impluseTempTime += dt;
        pushTempTime += dt;
        player = Utility.getPlayer();
        if(player!=null) {
            float playerX = player.body.getPosition().x;
            float playerY = player.body.getPosition().y;

            if (Math.abs(body.getPosition().x - playerX) < 100 / GameConstants.PPM
                    && Math.abs(body.getPosition().y - playerY) < 100 / GameConstants.PPM
                    && pushTempTime > PUSH_RECOIL_TIME) {
                pushPlayer();
                pushTempTime = 0;
            }
            else if(impluseTempTime >IMPLUSE_APPLY_INTERVAL) {
                this.body.applyLinearImpulse(new Vector2(0, .9f), this.body.getWorldCenter(), true);
                impluseTempTime = 0;
            }
        }
    }
}
