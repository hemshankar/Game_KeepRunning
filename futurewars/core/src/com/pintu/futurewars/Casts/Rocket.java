package com.pintu.futurewars.Casts;

import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.backgrounds.BackGround;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class Rocket extends FutureWarsCast {

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Rocket.class;
       /* gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;*/
        GameUtility.gameObjectCreator.register(GameConstants.BOMB_AMO,gameObjectDetails);
    }

    public Rocket() {
        super(GameConstants.BOMB_AMO_PROPERTY_FILE);
        background = new BackGround(GameConstants.BACKGROUND4_PROPERTY_FILE,this);
    }

    @Override
    public void initialize() {
        super.initialize();
        background.flyPosition = 9;
        background.xPos = this.xPos;
        background.yPos = this.yPos;
        background.initialize();
        GameUtility.getGameScreen().gameObjects.add(background);
    }

    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(gObj instanceof Player2){
            toBeDestroyed = true;
            ((Player2) gObj).selectedBullet = GameConstants.BOMB;
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        background.toBeDestroyed = true;
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
