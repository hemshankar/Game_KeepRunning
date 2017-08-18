package com.pintu.futurewars.commons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by hsahu on 8/15/2017.
 */

public interface GameObject {

    public void initialize();
    public void update(float dt);
    public void updateProp(String key, String value);
    public Sprite getSprite();
    public Body getBody();
    public void setToDestroyed();
    public boolean toBeDestroyed();
    public void destroy();
    public void handleContact(GameObject gObj);

}
