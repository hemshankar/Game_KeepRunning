package com.pintu.futurewars.Casts;

import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Skates extends FutureWarsCast {

    private static final float TRAVEL_TIME = 5f;
    private float travelled = 0f;
    private boolean hasPlayer = false;
    private float MOVE_FORCE_INTERVAL = .1f;
    private float moveForceApplideTime = 0f;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Skates.class;
        gameObjectDetails.flyPos= 5.5f;
        gameObjectDetails.yPos = 5.5f;
        GameUtility.gameObjectCreator.register(GameConstants.SKATES,gameObjectDetails);
    }

    public Skates() {
        super(GameConstants.SKATES_PROPERTY_FILE);
        canFly = true;
        isThrowable = true;
        maxVelocity = GameUtility.game.preferences.getInteger(GameConstants.PERF_HORSE)/GameConstants.MPS_TO_KPH;;
    }

    @Override
    public void handleContact(GameObject gObj){
        //super.handleContact(gObj);
        if(gObj instanceof Player2) {
            Player2 player2 = (Player2)gObj;
            toBeDestroyed = true;
            player2.totalSkates++;
            /*doneCatching = false;
            hasPlayer = true;
            GameUtility.jointHandler.createJoint(player2, this, world, GameConstants.WELD, 1f);
            currentState = "STATE_2";
            canFly = false;*/
        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        /*if(hasPlayer){
            travelled +=dt;
            if(travelled > TRAVEL_TIME){
                toBeDestroyed = true;
            }else{
                move(dt);
            }
        }*/
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    public void move(float dt){
        moveForceApplideTime += dt;
        if(moveForceApplideTime>MOVE_FORCE_INTERVAL){
            if(body.getLinearVelocity().x < maxVelocity) {
                body.applyLinearImpulse(new Vector2(2, 0), this.body.getWorldCenter(), true);
                moveForceApplideTime = 0;
            }
        }
    }
}
