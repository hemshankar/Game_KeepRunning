package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends FutureWarsCast {

    public static final float PUSH_INTERVAL = 1f;
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
        }else if(gObj instanceof Ground){
            flying = false;
        }
    }

    public void handleEndContact(GameObject gObj) {
        if(gObj instanceof Ground){
            flying = true;
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
            if(false){//itsPushTime(dt)) {
                body.setLinearVelocity((pXpose - myXpos) * 10f, (pYpose - myYpos) * 10f);
            }
        }

        if(!flying){
            body.applyLinearImpulse(new Vector2(0, 1f), this.body.getWorldCenter(), true);
        }
        if(pXpose < myXpos){
            this.currentState = GameObjectConstants.STATE_2;
        }else{
            this.currentState = GameObjectConstants.STATE_1;
        }

    }

    private boolean itsPushTime(float dt){
        flyTimer += dt;
        if(flyTimer > PUSH_INTERVAL){
            flyTimer = 0;
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
