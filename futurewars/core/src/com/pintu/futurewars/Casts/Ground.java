package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Ground extends FutureWarsCast {

    public Ground(int id, World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.GROUND_PROPERTY_FILE, w, a, obj);
    }
    public Ground() {
        super(GameConstants.GROUND_PROPERTY_FILE);
    }

    @Override
    public void handleEndContact(GameObject gObj){
        if(gObj instanceof Player2){
            Player2 p = (Player2) gObj;
            p.canJump = false;
            if(p.hasJumpingKit && p.jumpEffectRemainig > 0){
                p.body.applyLinearImpulse(new Vector2(0, 6), p.body.getWorldCenter(), true);
                GameUtility.playSound(GameConstants.JUMP_SOUND);
            }
        }else{
            gObj.handleEndContact(this);
        }
    }

    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            Player2 p = (Player2) gObj;
            p.canJump = true;
        }else{
            gObj.handleContact(this);
        }
    }
}
