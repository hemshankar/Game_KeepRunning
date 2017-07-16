package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.Utility;

/**
 * Created by hsahu on 7/15/2017.
 */

public class BasicBullet extends GameBullet {

    public Body body;
    public BasicBullet(float x, float y, int dir) {
        super(x, y, dir);
        defineBullet(x,y);
        setSpriteRegion();
        fire();
    }

    @Override
    public void setSpriteRegion() {
        TextureAtlas atlas = Utility.getAtlas();
        region = atlas.findRegion(GameConstants.BASIC_BULLET_REGION_NAME);
        setBounds(0,0, GameConstants.BASIC_BULLET_SIZE*2/ GameConstants.PPM, GameConstants.BASIC_BULLET_SIZE*2/ GameConstants.PPM);
        setRegion(region);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void setBody(Body b) {
        body = b;
    }

    public void defineBullet(float x, float y){

        //initiate the objects to create a body
        CircleShape cshape = new CircleShape();
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //set the body definition
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set(x,y);


        //create the body using body definition
        body = Utility.world.createBody(bdef);

        //create shape
        cshape.setRadius(GameConstants.BASIC_BULLET_SIZE/GameConstants.PPM);

        //create fixtureDef using shape
        fixtureDef.shape = cshape;
        fixtureDef.restitution=0f;
        //fixtureDef.friction=.2f;
        fixtureDef.density = 1f;
        //fixtureDef.isSensor = true;
        //create the fixture using fixture def
        Fixture f =body.createFixture(fixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);

        //set the time to live
        timeTolive = GameConstants.BASIC_BULLET_TIME_TO_LIVE;
    }

    @Override
    public void fire(){
        if(direction==GameConstants.LEFT)
            body.applyLinearImpulse(new Vector2(-GameConstants.BASIC_BULLET_SPEED, 0), body.getWorldCenter(), true);
        else
            body.applyLinearImpulse(new Vector2(GameConstants.BASIC_BULLET_SPEED, 0), body.getWorldCenter(), true);
    }

    @Override
    public int getDamage() {
        return GameConstants.BASIC_BULLET_DAMAGE;
    }
}
