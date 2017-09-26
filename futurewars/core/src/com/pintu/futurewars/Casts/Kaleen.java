package com.pintu.futurewars.Casts;

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

    private float FLY_FORCE_INTERVAL = 1f;
    private float flyForceApplideTime = 0f;

    private float MOVE_FORCE_INTERVAL = 1f;
    private float moveForceApplideTime = 0f;

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Kaleen.class;
/*
        gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;
*/

        GameUtility.gameObjectCreator.register(GameConstants.KALEEN,gameObjectDetails);
    }

    public Kaleen() {
        super(GameConstants.KALEEN_PROPERTY_FILE);
        background = new BackGround(GameConstants.BACKGROUND3_PROPERTY_FILE,this);
    }

    /*public Kaleen(int id, World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.KALEEN_PROPERTY_FILE, w, a, obj);
        background = new BackGround(234,GameConstants.BACKGROUND3_PROPERTY_FILE,w,a,this);
    }*/
    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        if(gObj instanceof Player2){
            //background.toBeDestroyed = true;
            applyDamping = false;
            Player2 player2 = ((Player2) gObj);
            /*if(player2.jointMap.get(this)!=null) {
                GameUtility.jointHandler.removeJoint(player2.jointMap.remove(this), world);
                if (ropeConnection != null) {
                    GameUtility.shapeHelper.removeShape(ropeConnection);
                }
                ropeConnection = null;
            }*/
            GameUtility.jointHandler.createJoint(player2,this,world,GameConstants.WELD);
            hasPlayer = true;
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
        /*Player2 player = GameUtility.getGameScreen().player2;
        float pXpose = player.body.getPosition().x;
        float pYpose = player.body.getPosition().y;
        float myXpos = body.getPosition().x;
        float myYpos = body.getPosition().y;

        if(!hasPlayer && player.jointMap.get(this)==null && Math.abs(Math.abs(pXpose)-Math.abs(myXpos)) < 5
                && Math.abs(Math.abs(pYpose)-Math.abs(myYpos)) < 5){
             GameUtility.jointHandler.createJoint(player,this,world,GameConstants.ROPE);
             ropeConnection = GameUtility.shapeHelper.drawLine(player,this,.1f, Color.BLUE,Color.BLUE);
        }*/

        if(hasPlayer){
            travelled +=dt;
            if(travelled > TRAVEL_TIME){
                toBeDestroyed = true;
            }else{
                move(dt);
            }
        }/*else {
            fly(dt);
        }*/
    }

    @Override
    public void destroy(){
        super.destroy();
        background.toBeDestroyed = true;
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    /*public void fly(float dt){
        flyForceApplideTime += dt;
        if(flyForceApplideTime>FLY_FORCE_INTERVAL){
            body.applyLinearImpulse(new Vector2(0, 5), this.body.getWorldCenter(), true);
            flyForceApplideTime = 0;
        }
    }*/

    public void move(float dt){
        moveForceApplideTime += dt;
        if(moveForceApplideTime>MOVE_FORCE_INTERVAL){
            body.applyLinearImpulse(new Vector2(15, 0), this.body.getWorldCenter(), true);
            moveForceApplideTime = 0;
        }
    }
}
