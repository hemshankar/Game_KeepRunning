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

    public GameBullet(int id, Map<String, String> props, World w, TextureAtlas a,float x, float y) {
        super(id, props, w, a);
        this.xPos =x;
        this.yPos =y;
    }

    abstract public void fire();
    abstract public int getDamage();
}
