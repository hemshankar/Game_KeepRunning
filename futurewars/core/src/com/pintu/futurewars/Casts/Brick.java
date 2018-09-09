package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameObjectDetails;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.backgrounds.BackGround;
import com.pintu.futurewars.commons.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Brick extends FutureWarsCast {

    public static void init(){
        GameObjectDetails gameObjectDetails = new GameObjectDetails();
        gameObjectDetails.objectClass = Brick.class;
       /* gameObjectDetails.yPos = 10;
        gameObjectDetails.flyPos = 9;
*/
        GameUtility.gameObjectCreator.register(GameConstants.BRICK,gameObjectDetails);
    }

    public Brick() {
        super(GameConstants.BRICK_PROPERTY_FILE);
        moveWithPlayer = false;
        canFly = false;
        isThrowable = true;
    }

    public static void createBricks(){

    }

    @Override
    public void handleContact(GameObject gObj) {
        super.handleContact(gObj);
        doneCatching = false;
        if(gObj instanceof Player2){
            if(((Player2)gObj).jointMap.get(this)==null){
                body.applyLinearImpulse(((gObj).getBody().getLinearVelocity().x + 1) * 2, 0,
                        body.getWorldCenter().x, body.getWorldCenter().y, true);
                GameUtility.playSound(GameConstants.KICK_SOUND);
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
