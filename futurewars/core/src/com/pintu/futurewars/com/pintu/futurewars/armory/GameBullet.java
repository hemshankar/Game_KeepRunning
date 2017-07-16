package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by hsahu on 7/15/2017.
 */

abstract public class GameBullet extends Sprite {

    public TextureAtlas.AtlasRegion region;

    public float timeTolive = 1;
    public float timeLived = 0;

    public boolean destroyed = false;
    public boolean toBeDestroyed = false;
    public boolean hasHit;

    public float x;
    public float y;
    public int direction;

    public GameBullet(float x, float y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void update(float dt){
        timeLived += dt;
        if(timeLived>=timeTolive || hasHit){
            toBeDestroyed = true;
        }
        if(!destroyed){
            setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
        }else if(destroyed){
            setPosition(-100,-100);
        }
    }

    abstract public void setSpriteRegion();
    abstract public Body getBody();
    abstract public void fire();
    abstract public void setBody(Body b);
    abstract public int getDamage();
}
