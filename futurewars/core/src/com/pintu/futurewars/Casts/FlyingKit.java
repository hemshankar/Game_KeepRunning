package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.backgrounds.BackGround;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class FlyingKit extends FutureWarsCast {
    BackGround background = null;
    public FlyingKit(int id, World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.FLYING_KIT_PROPERTY_FILE, w, a, obj);
        background = new BackGround(234,GameConstants.BACKGROUND1_PROPERTY_FILE,w,a,this);
    }

    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            toBeDestroyed = true;
            Player2 p = ((Player2) gObj);
            p.hasFlyingKit = true;
            p.flyFuel = p.MAX_FLY_FUEL;
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
