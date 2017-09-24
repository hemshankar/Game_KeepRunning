package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.Map;

/**
 * Created by hsahu on 7/15/2017.
 */

abstract public class GameBullet extends AbstractGameObject {

    public GameBullet(int id, String propFilename, World w, TextureAtlas a,float x, float y) {
        super(id, propFilename, w, a);
        this.xPos =x;
        this.yPos =y;
        canFly = false;
        nonCatchable = false;
    }

    abstract public void fire();
    abstract public int getDamage();
}
