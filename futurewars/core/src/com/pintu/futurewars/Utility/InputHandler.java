package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 8/9/2017.
 */

public class InputHandler {

    public void hadleInput(GameScreen screen, float dt){
            //float speedLimit = screen.player2.health * (screen.player2.MAX_SPEED/screen.player2.MAX_HEALTH);
       /* System.out.println("SpeedLimit: " +speedLimit);
        System.out.println("Speed: " +screen.player2.body.getLinearVelocity().x);*/
        if((screen.widgets.controles[GameConstants.UP] || Gdx.input.isKeyPressed(Input.Keys.UP))
                && screen.player2.getBody().getLinearVelocity().y <= 7){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || widgets.controles[GameConstants.THROW_SUCKER]){
                player2.getBody().applyLinearImpulse(new Vector2(0,5f),player2.getBody().getWorldCenter(),true);
            }else */
            if(screen.player2.hasFlyingKit){
                screen.player2.fly(dt);
            }else if(screen.player2.canJump){
                screen.player2.getBody().applyLinearImpulse(new Vector2(0, 4f), screen.player2.getBody().getWorldCenter(), true);
            }
        }
        if(screen.widgets.controles[GameConstants.DOWN] || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || widgets.controles[GameConstants.THROW_SUCKER]){
                player2.getBody().applyLinearImpulse(new Vector2(0,-5f),player2.getBody().getWorldCenter(),true);
            }else */{
                screen.player2.getBody().applyLinearImpulse(new Vector2(0, -0.4f), screen.player2.getBody().getWorldCenter(), true);
            }
        }

        if((screen.widgets.controles[GameConstants.LEFT] || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            && screen.player2.getBody().getLinearVelocity().x >= -(screen.player2.maxVelocity)
                ){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || widgets.controles[GameConstants.THROW_SUCKER]){
                player2.getBody().applyLinearImpulse(new Vector2(-5f,0),player2.getBody().getWorldCenter(),true);
            }else */{
                //System.out.println("========= Left" + speedStats);
                screen.player2.getBody().applyLinearImpulse(new Vector2(-.5f, 0), screen.player2.getBody().getWorldCenter(), true);
            }
        }
        if((screen.widgets.controles[GameConstants.RIGHT] || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            && screen.player2.getBody().getLinearVelocity().x <= screen.player2.maxVelocity//20
                ){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || widgets.controles[GameConstants.THROW_SUCKER]){
                player2.getBody().applyLinearImpulse(new Vector2(5f,0),player2.getBody().getWorldCenter(),true);
            }else*/{
                //System.out.println("========= Right" + speedStats);
                screen.player2.getBody().applyLinearImpulse(new Vector2(.5f,0),screen.player2.getBody().getWorldCenter(),true);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || screen.widgets.controles[GameConstants.FIRE_BASIC_BULLET]){
            if(!screen.started){
                screen.player2.getBody().applyLinearImpulse(new Vector2(5f, 2f), screen.player2.getBody().getWorldCenter(), true);
                screen.started = true;
                return;
            }
            synchronized (GameConstants.CATCH_OBJECT) {
                screen.player2.fire();
            }

            if(screen.player2.jointMap.size()!=0){
                List<GameObject> goList = new ArrayList<GameObject>();
                for(GameObject gObj: screen.player2.jointMap.keySet()){
                    AbstractGameObject g = (AbstractGameObject) gObj;
                    Joint j = screen.player2.jointMap.get(g);

                    if(j instanceof WeldJoint && g.isThrowable){
                        goList.add(g);
                        GameUtility.jointHandler.removeJoint(j,screen.world);
                    }
                }

                for(GameObject g: goList) {
                    screen.player2.jointMap.remove(g);
                }

                GameUtility.jointHandler.removeAllJoints();
                for(GameObject g: goList) {
                    screen.player2.jointMap.remove(g);
                    g.getBody().applyLinearImpulse((screen.player2.getBody().getLinearVelocity().x + 1)*10,0,g.getBody().getWorldCenter().x,g.getBody().getWorldCenter().y,true);
                }
                goList.clear();
            }
        }

        if(screen.widgets.controles[GameConstants.CIRCLE_CONTROLLER]) {
            //System.out.println("Velocity== " + screen.player2.getBody().getLinearVelocity().xPos + "," + screen.player2.getBody().getLinearVelocity().yPos);
            //System.out.println("Control position==" + screen.widgets.xPos + "," + screen.widgets.yPos);
            //System.out.println("=== " + (widgets.xPos - 140) + ","  + (widgets.yPos - 140));

            //get the user input
            screen.xImp = (screen.widgets.x-screen.widgets.circleButtonX) / GameConstants.PPM;
            screen.yImp = (screen.widgets.y-screen.widgets.circleButtonY) / GameConstants.PPM;

            /*//get magnitude
            screen.xMag = Math.abs(screen.xImp) + 0.000001f; //avoid divide by 0

            //get player velocity
            screen.xVelo = screen.player2.getBody().getLinearVelocity().x;

            //decide if force/impluse to be applied
            if(Math.abs(screen.xVelo)>screen.player2.maxVelocity){
                if(screen.xImp/screen.xVelo < 0){ //if the force is applied in the opp direction of the body movement
                    //nomalize the force
                    if(screen.xMag>150){ screen.xImp = (screen.xImp/screen.xMag) * 150;}
                    screen.xImp = screen.xImp/10;
                }else{
                    screen.xImp = 0;
                }
            }

            if (screen.yImp < 0){screen.yImp = 0;}else{ if(screen.yImp>150){screen.yImp = 150;}screen.yImp = screen.yImp/5;}

            if (screen.player2.getBody().getLinearVelocity().y > 5) {
                screen.yImp = 0;
            }
*/

            if(screen.player2.body.getLinearVelocity().x!=0) {
                float dir = screen.player2.body.getLinearVelocity().x/Math.abs(screen.player2.body.getLinearVelocity().x);
                float impDir = screen.xImp/Math.abs(screen.xImp);
                if (impDir == dir && Math.abs(screen.player2.body.getLinearVelocity().x) > screen.player2.maxVelocity) {
                    screen.xImp = 0;
                }
            }

            if(!screen.player2.hasFlyingKit && screen.yImp > .5f){
                screen.yImp = 0;
                if(screen.player2.canJump){
                    screen.player2.getBody().applyLinearImpulse(new Vector2(0, 4f), screen.player2.getBody().getWorldCenter(), true);
                }
            }else if(screen.player2.hasFlyingKit && Math.abs(screen.yImp) > .1f){
                if(Math.abs(screen.player2.body.getLinearVelocity().y)<screen.player2.maxVelocity) {
                    screen.yImp = (screen.yImp / Math.abs(screen.yImp)) * .5f;

                    if (screen.yImp > 0)
                        screen.player2.flyFuel -= (2 * dt);
                }
            }else{
                screen.yImp = 0;
            }
            System.out.println(screen.yImp);
            screen.player2.getBody().applyLinearImpulse(new Vector2(screen.xImp, screen.yImp), screen.player2.getBody().getWorldCenter(), true);
            //player2.getBody().applyForceToCenter(xImp,yImp,true);
            //System.out.println("xImp=" + screen.xImp + "," + "yImp=" + screen.yImp);
        }
        //To keep the object afloat
       /* else if (player2.getBody().getLinearVelocity().yPos < -1) {
            player2.getBody().applyForceToCenter(new Vector2(0, 50f), true);
        }*/
    }
}
