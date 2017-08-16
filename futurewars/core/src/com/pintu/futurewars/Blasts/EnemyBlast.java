package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/10/2017.
 */

public class EnemyBlast extends Blast {
    private static String []enemyBlastRegions = {"004","005","006","007","009","010"};
    private static final int TOTAL_BLAST_STATES = enemyBlastRegions.length;


    public EnemyBlast(int id, Map<String, String> props, World w, TextureAtlas a, float x_, float y_) {
        super(id, props, w, a);
        xPos = x_;
        yPos = y_;
    }

    @Override
    public void initialize() {
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.SPRITE_WIDTH,"100");
        props.put(GameObjectConstants.SPRITE_HEIGHT,"100");
        props.put(GameObjectConstants.NO_BODY,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.REMOVE_AFTER_ANIMATION,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->004,005,006,007,009,010");
        props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.ANIMATION_INTERVAL,".1");
        //props.put(GameObjectConstants.IS_BULLET,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }
}
