package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class SuckerCreator extends FutureWarsCast {
    public SuckerCreator(int id, Map<String, String> props, World w, TextureAtlas a, MapObject obj) {
        super(id, props, w, a, obj);
    }

    public void initialize(){
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.BODY_SHAPE,GameObjectConstants.CIRCLE);
        props.put(GameObjectConstants.BODY_TYPE,GameObjectConstants.DYNAMIC);
        //props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->suckerCreator");
        //props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.ANIMATION_INTERVAL,".9");
        //props.put(GameObjectConstants.IS_BULET,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }
}
