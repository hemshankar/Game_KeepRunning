package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/15/2017.
 */

public class BasicBullet extends GameBullet {

    public BasicBullet(int id, Map<String, String> props,
                       World w, TextureAtlas a,
                       float x, float y) {
        super(id, props, w, a, x, y);
        initialize();
        fire();
    }

    @Override
    public void initialize() {
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.BODY_SHAPE,GameObjectConstants.CIRCLE);
        props.put(GameObjectConstants.BODY_TYPE,GameObjectConstants.DYNAMIC);
        props.put(GameObjectConstants.OBJECT_RADIUS,GameConstants.BASIC_BULLET_SIZE/2 + "");
        props.put(GameObjectConstants.SPRITE_WIDTH,GameConstants.BASIC_BULLET_SIZE + "");
        props.put(GameObjectConstants.SPRITE_HEIGHT,GameConstants.BASIC_BULLET_SIZE + "");
        //props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->" + GameConstants.BASIC_BULLET_REGION_NAME);
        //props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.TIME_TO_LIVE,GameConstants.BASIC_BULLET_TIME_TO_LIVE + "");
        props.put(GameObjectConstants.IS_BULLET,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        props.put(GameObjectConstants.RESTITUTION,GameConstants.BASIC_BULLET_RETITUTION + "");
        props.put(GameObjectConstants.DENSITY,GameConstants.BASIC_BULLET_DENSITY + "");
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }

    @Override
    public void fire(){
        body.applyLinearImpulse(new Vector2(GameConstants.BASIC_BULLET_SPEED, 0), body.getWorldCenter(), true);
    }

    @Override
    public int getDamage() {
        return GameConstants.BASIC_BULLET_DAMAGE;
    }
}
