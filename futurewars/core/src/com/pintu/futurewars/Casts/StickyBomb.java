package com.pintu.futurewars.Casts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class StickyBomb extends FutureWarsCast  implements Enemy {
    public boolean fired = false;
    public float LIFE_TIME = 3f;
    public float lived = 0;
    public boolean isAttached = false;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = StickyBomb.class;
/*
        gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;
*/

        GameUtility.gameObjectCreator.register(GameConstants.STICKY_BOMB,gameObjectDetails);
    }

    public StickyBomb() {
        super(GameConstants.STICKY_BOMB_PROPERTY_FILE);
    }

    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(gObj instanceof Player2){
            GameUtility.jointHandler.createJoint(gObj,this,world,GameConstants.WELD);
            isAttached = true;
            //GameUtility.playSound(GameConstants.FUNNY_SPRING_SOUND);
            GameUtility.playSound(GameConstants.BOMB_TIMER_SOUND);
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
        if(isAttached){
            Body pBody = GameUtility.getGameScreen().player2.body;
            pBody.applyLinearImpulse(new Vector2(50f, 0), pBody.getWorldCenter(), true);
            GameUtility.getGameScreen().player2.takeDamage(10);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (!fired) {
            Player2 player = GameUtility.getGameScreen().player2;
            float pXpose = player.body.getPosition().x;
            float pYpose = player.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {

                float xForce = (pXpose - myXpos) * Math.abs(player.body.getLinearVelocity().x + 0.1f);
                xForce = xForce > (pXpose - myXpos)*5 ? xForce : (pXpose - myXpos)*5;
                float yForce = (pYpose - myYpos) * Math.abs(player.body.getLinearVelocity().y + 0.1f);
                yForce = yForce > (pYpose - myYpos)*5 ? yForce : (pYpose - myYpos)*5;

                body.setLinearVelocity(xForce, yForce);

                fired = true;
                canFly = false;
            }
        }else {
            updateMe(dt);
        }
    }

    private void updateMe(float dt){
        lived+=dt;
        if(lived > LIFE_TIME){
            toBeDestroyed = true;
        }
    }
}