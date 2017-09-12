package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class Magnet extends FutureWarsCast {
    public Magnet(int id, World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.MAGNET_PROPERTY_FILE, w, a, obj);
    }

    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            toBeDestroyed = true;
            Player2 p = ((Player2) gObj);
            p.hasMagnet = true;
            p.magnetEffectRemainig = p.MAGNET_EFFECT_TIME;
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
