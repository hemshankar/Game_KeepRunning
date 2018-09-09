package com.pintu.futurewars.Casts;

import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class WaterBalloon extends FutureWarsCast  implements Enemy {
    public boolean fired = false;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = WaterBalloon.class;
//        gameObjectDetails.yPos = 7;
//        gameObjectDetails.flyPos = 8;

        GameUtility.gameObjectCreator.register(GameConstants.WATER_BALOON,gameObjectDetails);
    }

    public WaterBalloon() {
        super(GameConstants.WATER_BALLOON_PROPERTY_FILE);
    }
    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(fired) {
            toBeDestroyed = true;
            GameUtility.playSound(GameConstants.TOING_SOUND);
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(!fired) {
            float pXpose = GameUtility.getGameScreen().player2.body.getPosition().x;
            float pYpose = GameUtility.getGameScreen().player2.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                body.setLinearVelocity((pXpose - myXpos) * 5f, (pYpose - myYpos) * 5f);
                fired = true;
                canFly = false;
            }
        }
    }
}