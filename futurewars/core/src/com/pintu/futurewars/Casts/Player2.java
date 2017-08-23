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

    public final float JUMP_KIT_EFFECT_TIME = 10f;
    public float jumpEffectRemainig = 0;

    public Player2(int id, World w, TextureAtlas a, MapObject object) {
        super(id, GameConstants.PLAYER_PROPERTY_FILE, w, a,object);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        health = MAX_HEALTH;
    }

    public void update(float dt){
        healthReduceTime += dt;
        recoilTimeElapsed +=dt;
        if(jumpEffectRemainig>0){
            jumpEffectRemainig -= dt;
        }
        if(healthReduceTime > HEALTH_REDUCETION_TIME_CONSTANT && health > MIN_HEALTH){
            healthReduceTime = 0;
            health -= HEALTH_REDUCTION_CONSTANT;
            System.out.println(health);
        }
        super.update(dt);
    }

    public void fire(){
        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBasicBullet(body.getPosition().x + ((4*spriteWidth)/2)/GameConstants.PPM,
                        body.getPosition().y);
            }
        }else if(GameConstants.BURST_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBurstBullet(body.getPosition().x + ((4*spriteWidth)/2)/GameConstants.PPM,
                        body.getPosition().y,(spriteHeight/4)/GameConstants.PPM);
            }
        }else if(GameConstants.BOMB.equals(selectedBullet)){
        if(recoilTimeElapsed > GameConstants.BOMB_RECOIL_TIME) {
            recoilTimeElapsed = 0;
            GameUtility.Bomb(body.getPosition().x + ((4*spriteWidth)/2)/GameConstants.PPM,
                    body.getPosition().y);
        }
    }

    }

    @Override
    public void handleContact(GameObject gObj){
        gObj.handleContact(this);
        /*if(gObj instanceof  Pusher){
            takeDamage(GameConstants.PUSHER_DAMAGE);
        }*/

    }

    @Override
    public void handleEndContact(GameObject gObj){
        if(gObj instanceof Ground){
            if(hasJumpingKit && jumpEffectRemainig > 0){
                body.applyLinearImpulse(new Vector2(0, 6), body.getWorldCenter(), true);
            }
        }
    }
}
