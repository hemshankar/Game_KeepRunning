package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.common.utilities.Utility;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends FutureWarsCast {

    private static final float PUSH_INTERVAL = 1f;
    private static final float FLY_INTERVAL = .1f;
    private float timer = 0f;
    public Pusher(int id,World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.PUSHER_PROPERTY_FILE, w, a, obj);
    }
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            //toBeDestroyed = true;
            Player2 player2 = ((Player2) gObj);//.body.applyLinearImpulse(new Vector2(20, 10), body.getWorldCenter(), true);
            float xVelocity = body.getLinearVelocity().x;
            float yVelocity = body.getLinearVelocity().y;
            //System.out.println("xVelocity: " + xVelocity + ", YVelocity: " + yVelocity);
            if(Math.abs(xVelocity) > 10 || Math.abs(yVelocity) >10) {
                player2.takeDamage(GameConstants.PUSHER_DAMAGE);
            }
        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        float pXpose = GameUtility.getGameScreen().player2.body.getPosition().x;
        float pYpose = GameUtility.getGameScreen().player2.body.getPosition().y;
        float myXpos = body.getPosition().x;
        float myYpos = body.getPosition().y;

        if(Math.abs(Math.abs(pXpose)-Math.abs(myXpos)) < 5
                && Math.abs(Math.abs(pYpose)-Math.abs(myYpos)) < 5){
            if(itsPushTime(dt)) {
                body.setLinearVelocity((pXpose - myXpos) * 10f, (pYpose - myYpos) * 10f);
            }
        }else{
            if(itsFlyTime(dt)) {
                body.applyLinearImpulse(new Vector2(0, .5f), this.body.getWorldCenter(), true);
            }
        }
    }

    private boolean itsPushTime(float dt){
        timer += dt;
        if(timer > PUSH_INTERVAL){
            timer = 0;
            return true;
        }
        return false;
    }

    private boolean itsFlyTime(float dt){
        timer += dt;
        if(timer > FLY_INTERVAL){
            timer = 0;
            return true;
        }
        return false;
    }
    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
