package com.pintu.futurewars.Casts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.backgrounds.BackGround;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Kaleen extends FutureWarsCast {

    private static final float TRAVEL_TIME = 5f;
    private float travelled = 0f;
    private boolean hasPlayer = false;

    private float MOVE_FORCE_INTERVAL = 1f;
    private float moveForceApplideTime = 0f;


    public Kaleen() {
        super(GameConstants.KALEEN_PROPERTY_FILE);
        background = new BackGround(GameConstants.BACKGROUND3_PROPERTY_FILE,this);
        maxVelocity = GameUtility.game.preferences.getInteger(GameConstants.PERF_KALEEN)/GameConstants.MPS_TO_KPH;;
    }

    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(gObj instanceof Player2){
            //background.toBeDestroyed = true;
            applyDamping = false;
            Player2 player2 = ((Player2) gObj);
            GameUtility.jointHandler.createJoint(player2,this,world,GameConstants.WELD);
            hasPlayer = true;
            GameUtility.playSound(GameConstants.KALEEN_SOUND);
        }
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
        background.toBeDestroyed = true;
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
