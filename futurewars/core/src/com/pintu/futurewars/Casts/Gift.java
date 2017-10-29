package com.pintu.futurewars.Casts;

import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class Gift extends FutureWarsCast {

    public float attTimer = 0;
    public float ATTRACT_INTERVAL = .001f;

   /* public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Gift.class;
        gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;

        GameUtility.gameObjectCreator.register(GameConstants.COIN,gameObjectDetails);
    }
*/
    public Gift() {
        super(GameConstants.GIFT_PROPERTY_FILE);
    }

    @Override
    public void handleContact(GameObject gObj) {
        if(!following) {
            super.handleContact(gObj);
            doneCatching = true;
            //currentState = "STATE_2";
            spriteWidth = spriteWidth / 3;
            spriteHeight = spriteHeight / 3;
            sprite.setBounds(0, 0, spriteWidth * 2 / getPPM(), spriteHeight * 2 / getPPM());
        }
    }

    /*@Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(gObj instanceof Player2){
            //toBeDestroyed = true;
            //((Player2) gObj).totalCoin++;
            //System.out.println(((Player2) gObj).totalCoin);
        }
    }*/

   /* @Override
    public void update(float dt) {
        super.update(dt);
        Player2 player = GameUtility.getGameScreen().player2;
        if (player.hasMagnet) {
            float pXpose = player.body.getPosition().x;
            float pYpose = player.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (itsAttractTime(dt) && Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                float xDir = (pXpose - myXpos)/Math.abs(pXpose - myXpos);
                float yDir = (pYpose - myYpos)/Math.abs(pYpose - myYpos);
                //body.setLinearVelocity( xDir * (Math.abs(player.body.getLinearVelocity().x) + 10f), yDir * (Math.abs(player.body.getLinearVelocity().y) + 10f));
                body.setLinearVelocity(xDir * (1.5f*Math.abs(pXpose - myXpos) +(Math.abs(player.body.getLinearVelocity().x))) ,
                        yDir * (1.5f*Math.abs(pYpose - myYpos) +(Math.abs(player.body.getLinearVelocity().y))));
                canFly = false;
            }
        }
    }*/

   /* @Override
    public void destroy(){
        super.destroy();
        //GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }*/

    /*public boolean itsAttractTime(float dt){
        attTimer += dt;
        if(attTimer > ATTRACT_INTERVAL){
            attTimer = 0;
            return true;
        }
        return false;
    }*/
}