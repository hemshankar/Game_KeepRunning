package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

abstract public class FutureWarsCast extends AbstractGameObject{

    public float health = 100;

    public FutureWarsCast(String propsFile) {
        super(3, propsFile, GameUtility.world, null);
    }

    public FutureWarsCast(int id, String propsFile, World w, TextureAtlas a, MapObject obj) {
        super(id, propsFile, w, a);
        mapObject = obj;
    }

    public void takeDamage(float damage){

        if(this instanceof Enemy){
            toBeDestroyed = true;
        }
        else if(this instanceof Player2){
            health-=damage;
            if(health<0){
                toBeDestroyed = true;
            }
        }

        /*health-=damage;
        //System.out.println("Damage taken: " + damage);
        if(health<=0 && this instanceof Enemy){
           *//*if(this instanceof Brick)
                GameUtility.getGameScreen().assetManager.get("audio/Fire impact 1.wav",Sound.class).play();
            else if (this instanceof Pusher)
                GameUtility.getGameScreen().assetManager.get("audio/Wind effects 5.wav",Sound.class).play();*//*

            this.toBeDestroyed = true;
        }*/
    }
}
