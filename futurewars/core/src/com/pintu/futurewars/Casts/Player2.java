package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player2 extends FutureWarsCast {
    public float recoilTimeElapsed = 0;
    public String selectedBullet = null;
    public boolean hasJumpingKit = false;
    public boolean hasMagnet = false;
    public float healthReduceTime = 0;
    public int totalCoin = 0;
    public final float HEALTH_REDUCETION_TIME_CONSTANT=.5f;//time after which the health will be automatically reduced by a unit
    public float HEALTH_REDUCTION_CONSTANT=1;

    public final float MAX_HEALTH = 200;
    public final float MAX_SPEED = 10;
    public final float MIN_HEALTH = 20;

    public final float JUMP_KIT_EFFECT_TIME = 10f;
    public final float MAGNET_EFFECT_TIME = 5f;
    public float jumpEffectRemainig = 0;
    public float magnetEffectRemainig = 0;
    
    public float flyFuel = 10;
    public final float MAX_FLY_FUEL = 10;
    public final float FLY_FUEL_REFILL_INTERVAL = 5;
    public float flyFuelRechargeInterval = 0;
    public boolean canJump = false;
    public boolean hasFlyingKit = false;

    public Map<GameObject,Joint> jointMap = new HashMap<GameObject, Joint>();

    public Player2() {
        super(2, GameConstants.PLAYER_PROPERTY_FILE, GameUtility.world, null,null);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        health = MAX_HEALTH;
        canFly = false;
        hasFlyingKit = true;
        flyFuel = MAX_FLY_FUEL;
    }

    @Deprecated
    public Player2(int id, World w, TextureAtlas a, MapObject object) {
        super(id, GameConstants.PLAYER_PROPERTY_FILE, w, a,object);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        health = MAX_HEALTH;
        canFly = false;
        hasFlyingKit = true;
        flyFuel = MAX_FLY_FUEL;
    }

    public void update(float dt){
        float speedLimit = health * (MAX_SPEED/MAX_HEALTH);
        healthReduceTime += dt;
        recoilTimeElapsed +=dt;
        if(jumpEffectRemainig>0){
            jumpEffectRemainig -= dt;
        }
        if(magnetEffectRemainig>0){
            magnetEffectRemainig -= dt;
        }else{
            hasMagnet = false;
        }
        if(healthReduceTime > HEALTH_REDUCETION_TIME_CONSTANT && health > MIN_HEALTH){
            healthReduceTime = 0;
            health -= HEALTH_REDUCTION_CONSTANT;
            //System.out.println(health);
        }
        if(getBody().getLinearVelocity().x <= speedLimit && canJump == true) {
            //getBody().applyLinearImpulse(new Vector2(2f, 0), getBody().getWorldCenter(), true);
        }
        super.update(dt);
        flyFuelUpdate(dt);
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

        //create a RopeJoint with the nearest gameObject
        /*AbstractGameObject nearestGO = (AbstractGameObject)GameUtility.getGameScreen().nearestGameObj;
        GameUtility.log(this.getClass().getName(),"Nearest Object: "+nearestGO);
        if(nearestGO!=null && this.jointMap.get(nearestGO)==null && GameUtility.getGameScreen().nearestDist<GameConstants.CATCH_RANGE) {
            //this sets the nearestObject in the player.jointMap
            GameUtility.jointHandler.createJoint(this, nearestGO, world, GameConstants.ROPE);
            nearestGO.ropeConnection =
                    GameUtility.shapeHelper.drawLine(this,nearestGO,GameConstants.ROPE_WIDTH, Color.BROWN,Color.BROWN);
            nearestGO.doneCatching = true;
            GameUtility.getGameScreen().nearestGameObj = null;
            GameUtility.getGameScreen().nearestDist = Float.MAX_VALUE;
        }*/
    }

    @Override
    public void handleContact(GameObject gObj){
        super.handleContact(gObj);
        /*gObj.handleContact(this);
        if(gObj instanceof  Pusher) {
            float xVelocity = ((Pusher) gObj).body.getLinearVelocity().x;
            float yVelocity = ((Pusher) gObj).body.getLinearVelocity().y;
            //System.out.println("xVelocity: " + xVelocity + ", YVelocity: " + yVelocity);
            if (Math.abs(xVelocity) > 10 || Math.abs(yVelocity) > 10) {
                takeDamage(GameConstants.PUSHER_DAMAGE);
                //GameUtility.jointHandler.createJoint(body,((Pusher) gObj).body,world,GameConstants.REVOLUTE);
            }
        }
        if(gObj instanceof  StickyBomb) {
            GameUtility.jointHandler.createJoint(body,((StickyBomb) gObj).body,world,GameConstants.REVOLUTE);
        }*/
    }



    public void flyFuelUpdate(float dt){
        if(flyFuel<=0){
            hasFlyingKit = false;
        }
        /*
        //logic to recharge fuel.
        flyFuelRechargeInterval += dt;
        if(flyFuelRechargeInterval > FLY_FUEL_REFILL_INTERVAL){
            if(flyFuel<MAX_FLY_FUEL){
                flyFuel++;
            }
            flyFuelRechargeInterval = 0;
        }*/
    }

    public void fly(float dt){
        if(flyFuel>0) {
            flyFuel -= (4 * dt);
            body.applyLinearImpulse(new Vector2(0, 1f), body.getWorldCenter(), true);
        }
    }
}
