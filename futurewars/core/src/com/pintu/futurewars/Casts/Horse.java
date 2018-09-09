package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Horse extends FutureWarsCast {

    private static final float TRAVEL_TIME = 5f;
    private float travelled = 0f;
    private boolean hasPlayer = false;
    private float MOVE_FORCE_INTERVAL = .01f;
    private float moveForceApplideTime = 0f;
    private Sound sound = null;
    public Horse() {
        super(GameConstants.HORSE_PROPERTY_FILE);
        canFly = false;
        maxVelocity = GameUtility.game.preferences.getInteger(GameConstants.PERF_HORSE)/GameConstants.MPS_TO_KPH;
        //sound = GameUtility.loopSound(GameConstants.HORSE_RUNNING_SOUND);
    }

    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2) {
            Player2 player2 = ((Player2) gObj);
            GameUtility.jointHandler.createJoint(player2, this, world, GameConstants.WELD,1f);
            hasPlayer = true;
            GameUtility.playSound(GameConstants.HORSE_SHOUTING_SOUND);
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
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        if(sound!=null)
            sound.stop();

        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    public void move(float dt){
        moveForceApplideTime += dt;
        if(moveForceApplideTime>MOVE_FORCE_INTERVAL){
            if(body.getLinearVelocity().x < this.maxVelocity) {
                body.applyLinearImpulse(new Vector2(5, 0), this.body.getWorldCenter(), true);
                moveForceApplideTime = 0;
            }
        }
    }
}
