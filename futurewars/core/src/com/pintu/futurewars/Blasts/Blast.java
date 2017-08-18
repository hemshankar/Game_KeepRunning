package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.Map;

/**
 * Created by hsahu on 8/10/2017.
 */

abstract public class Blast extends AbstractGameObject{

    public Blast(int id, String fileName, World w, TextureAtlas a,float x_, float y_) {
        super(id, fileName, w, a);
        xPos = x_;
        yPos = y_;
    }
}

