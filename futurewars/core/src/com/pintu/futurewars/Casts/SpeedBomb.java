package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/14/2017.
 */

public class SpeedBomb extends FutureWarsCast {

    @Override
    public void initialize() {
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.BODY_SHAPE,GameObjectConstants.CIRCLE);
        props.put(GameObjectConstants.BODY_TYPE,GameObjectConstants.STATIC);
        props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->Healer");
        props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.ANIMATION_INTERVAL,".9");
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }

    public SpeedBomb(int id, Map<String, String> props, World w, TextureAtlas a) {
        super(id, props, w, a,null);
    }
}
