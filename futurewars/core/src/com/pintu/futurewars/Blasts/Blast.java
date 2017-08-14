package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by hsahu on 8/10/2017.
 */

abstract public class Blast extends Sprite{
    public boolean destroyed = false;
    abstract public void update(float dt);
}

