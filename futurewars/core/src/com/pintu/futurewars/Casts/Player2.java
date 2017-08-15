package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player2 extends AbstractGameObject {
    public float recoilTimeElapsed = 0;
    public String selectedBullet = null;

    @Override
    public void initialize() {
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.BODY_SHAPE,GameObjectConstants.CIRCLE);
        props.put(GameObjectConstants.OBJECT_RADIUS,GameConstants.PLAYER_SIZE + "");
        props.put(GameObjectConstants.BODY_TYPE,GameObjectConstants.DYNAMIC);
        //props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->player");
        //props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.ANIMATION_INTERVAL,".9");
        props.put(GameObjectConstants.IS_BULET,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }

    public Player2(int id, Map<String, String> props, World w, TextureAtlas a, MapObject object) {
        super(id, props, w, a);
        this.mapObject = object;
        Utility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        initialize();
    }

    public void update(float dt){
        super.update(dt);
        recoilTimeElapsed +=dt;
    }

    public void fire(){
        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                Utility.fireBasicBullet(body.getPosition().x + GameConstants.PLAYER_SIZE/GameConstants.PPM,
                        body.getPosition().y + + GameConstants.PLAYER_SIZE/GameConstants.PPM/2);
            }
        }
    }
}
