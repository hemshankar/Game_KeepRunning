package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends FutureWarsCast {

    public final float PUSH_INTERVAL = .5f;
    public float pushTimer = 0;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Pusher.class;
        /*gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;*/

        GameUtility.gameObjectCreator.register(GameConstants.PUSHER,gameObjectDetails);
    }

    public Pusher() {
        super(GameConstants.PUSHER_PROPERTY_FILE);
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
            if(Math.abs(xVelocity) > 10 || Math.abs(yVelocity) >10) {
                player2.takeDamage(GameConstants.PUSHER_DAMAGE);
                GameUtility.getGameScreen().isslowMotionEffect = true;
            }
        }else if(gObj instanceof Ground){
            flying = false;
        }
    }

    public void handleEndContact(GameObject gObj) {
        super.handleContact(gObj);
        if(gObj instanceof Ground){
            flying = true;
        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        Player2 player = GameUtility.getGameScreen().player2;
        float pXpose = player.body.getPosition().x;
        float pYpose = player.body.getPosition().y;
        float myXpos = body.getPosition().x;
        float myYpos = body.getPosition().y;

        if( Math.abs(Math.abs(pXpose)-Math.abs(myXpos)) < 5
                && Math.abs(Math.abs(pYpose)-Math.abs(myYpos)) < 5){
            canFly = false;
            if(itsPushTime(dt)) {
                float xForce = (pXpose - myXpos) * Math.abs(player.body.getLinearVelocity().x + 0.1f);
                xForce = xForce > (pXpose - myXpos) ? xForce : (pXpose - myXpos);
                float yForce = (pYpose - myYpos) * Math.abs(player.body.getLinearVelocity().y + 0.1f);
                yForce = yForce > (pYpose - myYpos) ? yForce : (pYpose - myYpos);

                body.setLinearVelocity(xForce, yForce);
            }
        }else{
            canFly = true;
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
        pushTimer += dt;
        if(pushTimer > PUSH_INTERVAL){
            pushTimer = 0;
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
