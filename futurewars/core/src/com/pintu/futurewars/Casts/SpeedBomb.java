package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.backgrounds.BackGround;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class SpeedBomb extends FutureWarsCast {

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = SpeedBomb.class;
        gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;

        GameUtility.gameObjectCreator.register(GameConstants.SPEED_BOMB,gameObjectDetails);
    }

    public SpeedBomb() {
        super(GameConstants.SPEED_BOMB_PROPERTY_FILE);
        background = new BackGround(GameConstants.BACKGROUND1_PROPERTY_FILE,this);
    }
/*
    public SpeedBomb(int id,World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.SPEED_BOMB_PROPERTY_FILE, w, a, obj);
        flyPosition = 9;
        background = new BackGround(GameConstants.BACKGROUND1_PROPERTY_FILE,this);
    }*/
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            toBeDestroyed = true;
            ((Player2) gObj).body.applyLinearImpulse(new Vector2(20, 10), body.getWorldCenter(), true);
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
    public void destroy(){
        super.destroy();
        background.toBeDestroyed = true;
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}