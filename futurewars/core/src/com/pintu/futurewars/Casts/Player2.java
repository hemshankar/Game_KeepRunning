package com.pintu.futurewars.Casts;

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
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player2 extends FutureWarsCast {
    public float recoilTimeElapsed = 0;
    public String selectedBullet = null;
    public boolean hasJumpingKit = false;
    public boolean hasMagnet = false;
    public int numberOfMagnet = 10;
    public float healthReduceTime = 0;
    public int totalCoin = 0;

    public final float MAX_HEALTH = 200;
    public final float MAX_SPEED = 10;

    public final float JUMP_KIT_EFFECT_TIME = 10f;
    public final float MAGNET_EFFECT_TIME = 15f;
    public float jumpEffectRemainig = 0;
    public float magnetEffectRemainig = 0;
    
    public float flyFuel = 10;
    public final float MAX_FLY_FUEL = 10;
    public boolean canJump = false;
    public boolean hasFlyingKit = false;
    public float maxPossibleSpeed = 50/GameConstants.MPS_TO_KPH;
    public float glideSpeed = 10/GameConstants.MPS_TO_KPH;
    public boolean hasRocket = false;
    public int totalRockets = 10;
    public final float ROCKET_EFFECT_TIME = 10f;
    public float rocketEffectRemainig = 0;

    public boolean hasSkates = false;
    public int totalSkates = 10;
    public final float SKATES_EFFECT_TIME = 10f;
    public float skatesEffectRemainig = 0;
    public float skatesMaxSpeed = 70/GameConstants.MPS_TO_KPH;

    public boolean hasRifle = false;
    public final float RIFLE_EFFECT_TIME = 10f;
    public float rifleEffectRemainig = 0;

    public boolean hasParachute = false;
    public int totalParachute = 10;
    public final float PARACHUTE_EFFECT_TIME = 10f;
    public float parachuteEffectRemainig = 0;
    public Map<GameObject,Joint> jointMap = new HashMap<GameObject, Joint>();

    public Player2() {
        super(2, GameConstants.PLAYER_PROPERTY_FILE, GameUtility.world, null,null);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
        health = MAX_HEALTH;
        canFly = false;
        hasFlyingKit = true;
        flyFuel = MAX_FLY_FUEL;
        maxVelocity = 15/GameConstants.MPS_TO_KPH;
    }

    public void update(float dt){

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

        if(parachuteEffectRemainig>0){
            parachuteEffectRemainig -= dt;
        }else{
            hasParachute = false;
        }


        if(hasRocket){
            rocketEffectRemainig +=dt;
            if(rocketEffectRemainig>ROCKET_EFFECT_TIME){
                rocketEffectRemainig = 0;
                hasRocket = false;
            }else{
                float yImp = 0;
                if(body.getPosition().y<10){
                    yImp = 1f;
                }
                getBody().applyLinearImpulse(new Vector2(2, yImp), getBody().getWorldCenter(), true);
            }
        }else if(hasSkates){
            skatesEffectRemainig +=dt;
            if(skatesEffectRemainig>SKATES_EFFECT_TIME){
                skatesEffectRemainig = 0;
                hasSkates = false;
            }else{
                if (getBody().getLinearVelocity().x < skatesMaxSpeed) {
                    getBody().applyLinearImpulse(new Vector2(1, 0), getBody().getWorldCenter(), true);
                }
            }
        }else {

            if (getBody().getLinearVelocity().x > this.maxVelocity) {
                getBody().applyLinearImpulse(new Vector2(-0.01f, 0), getBody().getWorldCenter(), true);
            }else if(getBody().getLinearVelocity().x < this.maxVelocity) {
                getBody().applyLinearImpulse(new Vector2(+2f, 0), getBody().getWorldCenter(), true);
            }
            if (getBody().getLinearVelocity().x > maxPossibleSpeed) {
                getBody().applyLinearImpulse(new Vector2(-1f, 0), getBody().getWorldCenter(), true);
            }
        }

        if(hasParachute && getBody().getLinearVelocity().y < -1 * glideSpeed) {
            getBody().applyLinearImpulse(new Vector2(0, 1), getBody().getWorldCenter(), true);
        }
        super.update(dt);
        flyFuelUpdate(dt);
    }

    @Override
    public void destroy() {
        super.destroy();
        GameUtility.getGameScreen().game.setScreen(GameUtility.getGameScreen().game.getGameEndScreen(GameConstants.STAGE1));
        GameUtility.getGameScreen().gameMusic.stop();
    }

    public void fire(){
        AbstractGameObject target = (AbstractGameObject) GameUtility.getGameScreen().nearestEnemy;

        if(target==null)
            return;
        int direction = 1;
        if(target.body.getPosition().x - body.getPosition().x<0){
            direction = -1;
        }

        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBasicBullet(body.getPosition().x + ((4*direction*spriteWidth)/2)/GameConstants.PPM,
                        body.getPosition().y,target);
            }
        }else if(GameConstants.BURST_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBurstBullet(body.getPosition().x + ((4*direction*spriteWidth)/2)/GameConstants.PPM,
                        body.getPosition().y,(spriteHeight/4)/GameConstants.PPM,GameUtility.getGameScreen().nearestEnemy);
            }
        }else if(GameConstants.BOMB.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BOMB_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.Bomb(body.getPosition().x + ((4*direction*spriteWidth)/2)/GameConstants.PPM,
                        body.getPosition().y,target);
            }
        }

        //create a RopeJoint with the nearest gameObject
        /*AbstractGameObject nearestGO = (AbstractGameObject)GameUtility.getGameScreen().nearestEnemy;
        GameUtility.log(this.getClass().getName(),"Nearest Object: "+nearestGO);
        if(nearestGO!=null && this.jointMap.get(nearestGO)==null && GameUtility.getGameScreen().nearestDist<GameConstants.CATCH_RANGE) {
            //this sets the nearestObject in the player.jointMap
            GameUtility.jointHandler.createJoint(this, nearestGO, world, GameConstants.ROPE);
            nearestGO.ropeConnection =
                    GameUtility.shapeHelper.drawLine(this,nearestGO,GameConstants.ROPE_WIDTH, Color.BROWN,Color.BROWN);
            nearestGO.doneCatching = true;
            GameUtility.getGameScreen().nearestEnemy = null;
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
