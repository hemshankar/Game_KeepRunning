package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Enemy;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/15/2017.
 */

public class BubbleBullet extends GameBullet {

    public GameObject target = null;
    public BubbleBullet(int id,World w, TextureAtlas a, float x, float y) {
        super(id, GameConstants.BUBBLE_BULLET_PROPERTY_FILE, w, a, x,y);
        canFly = true;
    }

    @Override
    public void fire(){
        if(target == null) {
            body.applyLinearImpulse(new Vector2(GameConstants.BUBBLE_BULLET_SPEED, 0), body.getWorldCenter(), true);
        }else{
            flyPosition = target.getBody().getPosition().y;
            try {
                float pXpose = target.getBody().getPosition().x;
                float pYpose = target.getBody().getPosition().y;
                float myXpos = body.getPosition().x;
                float myYpos = body.getPosition().y;

                float xForce = (pXpose - myXpos);// * Math.abs(target.getBody().getLinearVelocity().x + 0.1f);
                //xForce = xForce > (pXpose - myXpos) ? xForce : (pXpose - myXpos);
                float yForce = (pYpose - myYpos);// * Math.abs(target.getBody().getLinearVelocity().y + 0.1f);
                //yForce = yForce > (pYpose - myYpos) ? yForce : (pYpose - myYpos);
                body.applyLinearImpulse(new Vector2(xForce, yForce), body.getWorldCenter(), true);
            }catch (Exception e){
                GameUtility.log(this.getClass().getName(),e.getMessage());
                body.applyLinearImpulse(new Vector2(GameConstants.BUBBLE_BULLET_SPEED, 0), body.getWorldCenter(), true);
            }
        }
    }

    @Override
    public int getDamage() {
        return GameConstants.BASIC_BULLET_DAMAGE;
    }

    @Override
    public void handleContact(GameObject obj){
        toBeDestroyed = true;
        if(obj instanceof Enemy){
            FutureWarsCast cast = (FutureWarsCast)obj;
            cast.takeDamage(getDamage());
        }
    }
}
