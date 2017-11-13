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

    public Kite() {
        super(GameConstants.KITE_PROPERTY_FILE);
        ropeLength = 5f;
        removeRopeConnectionOnContact = false;
        maxVelocity = GameUtility.game.preferences.getInteger(GameConstants.PERF_HORSE)/GameConstants.MPS_TO_KPH;;
    }


    @Override
    public void update(float dt){
        super.update(dt);
        if(ropeConnection!=null){
            caughtSince+=dt;
            if(caughtSince>catchTime){
                toBeDestroyed = true;
            }else{
                if(body.getLinearVelocity().x < this.maxVelocity) {
                    body.applyLinearImpulse(new Vector2(5, 0), this.body.getWorldCenter(), true);
                }
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
