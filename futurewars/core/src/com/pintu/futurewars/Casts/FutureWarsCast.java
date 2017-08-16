package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

abstract public class FutureWarsCast extends AbstractGameObject{

    public int health = 100;

    public FutureWarsCast(int id, String propsFile, World w, TextureAtlas a, MapObject obj) {
        super(id, propsFile, w, a);
        mapObject = obj;
    }
    public void takeDamage(GameBullet bullet){
        health-=bullet.getDamage();
        if(health<=0){
            GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
            if(this instanceof Brick)
                GameUtility.gameScreen.assetManager.get("audio/Fire impact 1.wav",Sound.class).play();
            else if (this instanceof Pusher)
                GameUtility.gameScreen.assetManager.get("audio/Wind effects 5.wav",Sound.class).play();
            this.toBeDestroyed = true;
        }
    }
}
