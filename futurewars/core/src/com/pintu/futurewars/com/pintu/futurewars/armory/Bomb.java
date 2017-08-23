package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/15/2017.
 */

public class Bomb extends GameBullet {

    public Bomb(int id, World w, TextureAtlas a, float x, float y) {
        super(id, GameConstants.BOMB_PROPERTY_FILE, w, a, x,y);
    }

    @Override
    public void fire(){
        body.applyLinearImpulse(new Vector2(GameConstants.BOMB_SPEED, 0), body.getWorldCenter(), true);
    }

    @Override
    public int getDamage() {
        return GameConstants.BASIC_BULLET_DAMAGE;
    }

    @Override
    public void handleContact(GameObject obj){
        toBeDestroyed = true;
        if(obj instanceof FutureWarsCast && !(obj instanceof Player2)&& !(obj instanceof Ground)){
            FutureWarsCast cast = (FutureWarsCast)obj;
            cast.takeDamage(getDamage());
        }
    }
}
