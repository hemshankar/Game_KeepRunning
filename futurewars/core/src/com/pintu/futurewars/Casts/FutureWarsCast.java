package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Blasts.EnemyBlast;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

abstract public class FutureWarsCast extends AbstractGameObject{

    public int health = 100;
    public boolean destroyed = false;

    public FutureWarsCast(int id, Map<String, String> props, World w, TextureAtlas a, MapObject obj) {
        super(id, props, w, a);
        mapObject = obj;
        initialize();
    }
    public void takeDamage(GameBullet bullet){
        health-=bullet.getDamage();
        Body b = getBody();
        if(health<=0){
            Utility.gameScreen.blastHandler.addBlast(new EnemyBlast(b.getPosition().x,
                                                                b.getPosition().y));
            if(this instanceof Brick)
                Utility.gameScreen.assetManager.get("audio/Fire impact 1.wav",Sound.class).play();
            else if (this instanceof Pusher)
                Utility.gameScreen.assetManager.get("audio/Wind effects 5.wav",Sound.class).play();
            this.toBeDestroyed = true;
        }
    }
}
