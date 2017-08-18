package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player2 extends FutureWarsCast {
    public float recoilTimeElapsed = 0;
    public String selectedBullet = null;
    public boolean hasJumpingKit = false;
    public float healthReduceTime = 0;
    public final float HEALTH_REDUCETION_TIME_CONSTANT=.5f;//time after which the health will be automatically reduced by a unit
    public float HEALTH_REDUCTION_CONSTANT=1;

    public final float MAX_HEALTH = 200;
    public final float MAX_SPEED = 20;
    public final float MIN_HEALTH = 20;

    public Player2(int id, World w, TextureAtlas a, MapObject object) {
        super(id, GameConstants.PLAYER_PROPERTY_FILE, w, a,object);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        health = MAX_HEALTH;
    }

    public void update(float dt){
        super.update(dt);
        healthReduceTime += dt;
        recoilTimeElapsed +=dt;
        if(healthReduceTime > HEALTH_REDUCETION_TIME_CONSTANT && health > MIN_HEALTH){
            healthReduceTime = 0;
            health -= HEALTH_REDUCTION_CONSTANT;
            System.out.println(health);
        }
    }

    public void fire(){
        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBasicBullet(body.getPosition().x + spriteWidth/GameConstants.PPM,
                        body.getPosition().y - spriteHeight/GameConstants.PPM);
            }
        }
    }

    @Override
    public void handleContact(GameObject gObj){
        //To be implemented
    }
}
