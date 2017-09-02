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

public class Kaleen extends FutureWarsCast {

    private static final float TRAVEL_TIME = 5f;
    private float travelled = 0f;
    private boolean hasPlayer = false;

    private float FLY_FORCE_INTERVAL = 1f;
    private float flyForceApplideTime = 0f;

    private float MOVE_FORCE_INTERVAL = 1f;
    private float moveForceApplideTime = 0f;

    public Kaleen(int id, World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.KALEEN_PROPERTY_FILE, w, a, obj);
    }
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            //toBeDestroyed = true;
            Player2 player2 = ((Player2) gObj);//.body.applyLinearImpulse(new Vector2(20, 10), body.getWorldCenter(), true);
            GameUtility.jointHandler.createJoint(body,player2.body,world,GameConstants.REVOLUTE);
            hasPlayer = true;
        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        if(hasPlayer){
            travelled +=dt;
            if(travelled > TRAVEL_TIME){
                toBeDestroyed = true;
            }else{
                move(dt);
            }
        }else {
            fly(dt);
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    public void fly(float dt){
        flyForceApplideTime += dt;
        if(flyForceApplideTime>FLY_FORCE_INTERVAL){
            body.applyLinearImpulse(new Vector2(0, 5), this.body.getWorldCenter(), true);
            flyForceApplideTime = 0;
        }
    }

    public void move(float dt){
        moveForceApplideTime += dt;
        if(moveForceApplideTime>MOVE_FORCE_INTERVAL){
            body.applyLinearImpulse(new Vector2(15, 0), this.body.getWorldCenter(), true);
            moveForceApplideTime = 0;
        }
    }
}
