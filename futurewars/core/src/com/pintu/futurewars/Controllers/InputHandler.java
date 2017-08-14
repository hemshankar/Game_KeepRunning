package com.pintu.futurewars.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Screens.GameScreen;

/**
 * Created by hsahu on 8/9/2017.
 */

public class InputHandler {

    public void hadleInput(GameScreen screen, float dt){

        if((screen.controller.controles[GameConstants.UP] || Gdx.input.isKeyPressed(Input.Keys.UP))
                && screen.player.body.getLinearVelocity().y <= 5){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(0,5f),player.body.getWorldCenter(),true);
            }else */{
                screen.player.body.applyLinearImpulse(new Vector2(0, 1f), screen.player.body.getWorldCenter(), true);
            }
        }
        if(screen.controller.controles[GameConstants.DOWN] || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(0,-5f),player.body.getWorldCenter(),true);
            }else */{
                screen.player.body.applyLinearImpulse(new Vector2(0, -0.4f), screen.player.body.getWorldCenter(), true);
            }
        }

        if((screen.controller.controles[GameConstants.LEFT] || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            && screen.player.body.getLinearVelocity().x >= -5
                ){
           /* if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(-5f,0),player.body.getWorldCenter(),true);
            }else */{
                screen.player.body.applyLinearImpulse(new Vector2(-0.4f, 0), screen.player.body.getWorldCenter(), true);
            }
        }
        if((screen.controller.controles[GameConstants.RIGHT] || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            && screen.player.body.getLinearVelocity().x <= 5
                ){
            /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.controles[GameConstants.THROW_SUCKER]){
                player.body.applyLinearImpulse(new Vector2(5f,0),player.body.getWorldCenter(),true);
            }else*/{
                screen.player.body.applyLinearImpulse(new Vector2(0.4f,0),screen.player.body.getWorldCenter(),true);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || screen.controller.controles[GameConstants.FIRE_BASIC_BULLET]){
            if(!screen.started){
                screen.player.body.applyLinearImpulse(new Vector2(5f, 2f), screen.player.body.getWorldCenter(), true);
                screen.started = true;
                return;
            }
            screen.player.fire(screen.bullets,dt);
        }

        if(screen.controller.controles[GameConstants.CIRCLE_CONTROLLER]) {
            System.out.println("Velocity== " + screen.player.body.getLinearVelocity().x + "," + screen.player.body.getLinearVelocity().y);
            System.out.println("Control position==" + screen.controller.x + "," + screen.controller.y);
            //System.out.println("=== " + (controller.x - 140) + ","  + (controller.y - 140));

            //get the user input
            screen.xImp = (screen.controller.x - 130) / GameConstants.PPM;
            screen.yImp = (screen.controller.y - 130) / GameConstants.PPM;

            //get magnitude
            screen.xMag = Math.abs(screen.xImp) + 0.000001f; //avoid divide by 0

            //get player velocity
            screen.xVelo = screen.player.body.getLinearVelocity().x;

            //decide if force/impluse to be applied
            if(Math.abs(screen.xVelo)>5){
                if(screen.xImp/screen.xVelo < 0){ //if the force is applied in the opp direction of the body movement
                    //nomalize the force
                    if(screen.xMag>150){ screen.xImp = (screen.xImp/screen.xMag) * 150;}
                    screen.xImp = screen.xImp/10;
                }else{
                    screen.xImp = 0;
                }
            }

            if (screen.yImp < 0){screen.yImp = 0;}else{ if(screen.yImp>150){screen.yImp = 150;}screen.yImp = screen.yImp/5;}

            if (screen.player.body.getLinearVelocity().y > 5) {
                screen.yImp = 0;
            }

            screen.player.body.applyLinearImpulse(new Vector2(screen.xImp, screen.yImp), screen.player.body.getWorldCenter(), true);
            //player.body.applyForceToCenter(xImp,yImp,true);
            System.out.println("xImp=" + screen.xImp + "," + "yImp=" + screen.yImp);
        }
        //To keep the object afloat
       /* else if (player.body.getLinearVelocity().y < -1) {
            player.body.applyForceToCenter(new Vector2(0, 50f), true);
        }*/
    }
}
