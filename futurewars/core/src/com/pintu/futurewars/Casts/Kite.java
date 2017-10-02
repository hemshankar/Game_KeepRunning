package com.pintu.futurewars.Casts;

import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Kite extends FutureWarsCast {

    public float catchTime = 5f;
    public float caughtSince = 0f;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Kite.class;
        gameObjectDetails.yPos = 11;
        gameObjectDetails.flyPos = 12;

        GameUtility.gameObjectCreator.register(GameConstants.KITE,gameObjectDetails);
    }

    public Kite() {
        super(GameConstants.KITE_PROPERTY_FILE);
        ropeLength = 5f;
        removeRopeConnectionOnContact = false;
    }

    /*public Pusher(int id,World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.PUSHER_PROPERTY_FILE, w, a, obj);
    }*/
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            //toBeDestroyed = true;
            Player2 player2 = ((Player2) gObj);//.body.applyLinearImpulse(new Vector2(20, 10), body.getWorldCenter(), true);
            float xVelocity = body.getLinearVelocity().x;
            float yVelocity = body.getLinearVelocity().y;
            //System.out.println("xVelocity: " + xVelocity + ", YVelocity: " + yVelocity);
            /*if(Math.abs(xVelocity) > 10 || Math.abs(yVelocity) >10) {
                player2.takeDamage(GameConstants.PUSHER_DAMAGE);
                GameUtility.getGameScreen().isslowMotionEffect = true;
            }*/
        }else if(gObj instanceof Ground){
            flying = false;
        }
    }

    public void handleEndContact(GameObject gObj) {
        super.handleContact(gObj);
    }

    @Override
    public void update(float dt){
        super.update(dt);
        if(ropeConnection!=null){
            caughtSince+=dt;
            if(caughtSince>catchTime){
                toBeDestroyed = true;
            }else{
                body.applyLinearImpulse(new Vector2(1, 0), this.body.getWorldCenter(), true);
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
