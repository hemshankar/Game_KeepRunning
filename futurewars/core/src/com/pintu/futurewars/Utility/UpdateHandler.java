package com.pintu.futurewars.Utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Pivot;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.Set;

/**
 * Created by hsahu on 8/9/2017.
 */

public class UpdateHandler {

    public void update(GameScreen screen,float dt){

        if(screen.bodyThatWasHit!=null) {
            AbstractGameObject hitGameObject = (AbstractGameObject)screen.bodyThatWasHit.getUserData();
            if(hitGameObject==null
                    || hitGameObject instanceof Player2
                    || hitGameObject instanceof Ground
                    || hitGameObject.nonCatchable
                    || screen.player2.jointMap.get(hitGameObject)!=null
                    || hitGameObject.destroyed
                    || hitGameObject.toBeDestroyed
                    || hitGameObject.doneCatching
                    || hitGameObject.isBackground) {
                //do nothing
            }else{
                //first remove any existing pivot object
                if(hitGameObject instanceof Pivot){
                    for(GameObject obj: screen.player2.jointMap.keySet()){
                        if(obj instanceof Pivot){
                            GameUtility.jointHandler.removeJoint(screen.player2.jointMap.remove(obj),screen.world);
                        }
                    }
                }
                GameUtility.jointHandler.createJoint(screen.player2,
                        hitGameObject,
                        screen.world,
                        GameConstants.ROPE,
                        hitGameObject.ropeLength);
                if(hitGameObject instanceof Pivot){
                    screen.player2.body.applyLinearImpulse(new Vector2(screen.player2.body.getMass() * 15f, 0), screen.player2.body.getWorldCenter(), true);

                }
                hitGameObject.ropeConnection =
                        GameUtility.shapeHelper.drawLine(screen.player2, hitGameObject, GameConstants.ROPE_WIDTH, Color.BROWN, Color.BROWN);
                hitGameObject.doneCatching = false;
            }
            screen.bodyThatWasHit = null;
        }



        screen.timePassed +=dt;
        if(screen.timePassed >=screen.gameTime){
            screen.game.setScreen(screen.game.getGameEndScreen(GameConstants.STAGE1));
            screen.gameMusic.stop();
        }
        float fps = 45f;
        if(screen.isslowMotionEffect){
            //fps = 5f;
            try{
                if(screen.slowMotionEffect < screen.slowMotionEffectTime/2) {
                    if(screen.sleepTime<=100) {
                        screen.sleepTime += 10;
                    }
                }else{
                    if(screen.sleepTime>=20) {
                        screen.sleepTime -= 5;
                    }
                }
                Thread.sleep(screen.sleepTime);
            }catch(Exception e){

            }
            screen.slowMotionEffect+=dt;
            if(screen.slowMotionEffect>screen.slowMotionEffectTime){
                screen.slowMotionEffect = 0;
                screen.isslowMotionEffect = false;
            }
        }
        //update the world every 1/60 of a second?
        screen.world.step(1/fps,8,3);

        //update all the game objects at the start
        updateAllGameObjects(screen.gameObjects,dt);

        //update the camera as the player moves (player decides the camera position
        float x = screen.player2.getBody().getPosition().x;

        if(x<(screen.viewport.getWorldWidth()/2))
            x = screen.viewport.getWorldWidth()/2;
        else if(x>(GameUtility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2))
            x = GameUtility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2;

        //keep the player on slightly left of the center
        screen.camera.position.x=x+4;

        //stage Completed
        if(screen.player2.getBody().getPosition().x>GameUtility.worldCreator.boundaryRight/GameConstants.PPM ){
            screen.game.setScreen(screen.game.getGameEndScreen(GameConstants.STAGE1));
            screen.gameMusic.stop();
            //screen.game.getGameScreen(GameConstants.STAGE1).dispose();
        }


        float y = screen.player2.getBody().getPosition().y;
        if(y<screen.viewport.getWorldHeight()/2)
            y = screen.viewport.getWorldHeight()/2;
        else if(y>( GameUtility.worldCreator.boundaryTop/ GameConstants.PPM - screen.viewport.getWorldHeight()/2))
            y = ( GameUtility.worldCreator.boundaryTop/ GameConstants.PPM )- screen.viewport.getWorldHeight()/2;

        //keep the player on slightly bottom of the center
        screen.camera.position.y=y+2;
        //System.out.println( player.body.getPosition().xPos + " -- " + player.body.getPosition().yPos);
        screen.camera.update();

        //orthographic map renderer's position is decided by the camera
        //screen.renderer.setView(screen.camera);

        //get the body speed and resize the viewport
        Vector2 velocity = screen.player2.getBody().getLinearVelocity();
        screen.maxVelocity = velocity.x>velocity.y?velocity.x:velocity.y;
        /*if(Math.abs(screen.maxVelocity-screen.cameraCalibration)>1) {
            if (screen.maxVelocity - screen.cameraCalibration > 0) {
                screen.cameraCalibration += .1;
            } else if (screen.maxVelocity - screen.cameraCalibration < 0) {
                screen.cameraCalibration -= .1;
            }
        }
        if(screen.player2.getBody().getPosition().y > GameUtility.worldCreator.boundaryTop/ GameConstants.PPM/2){
            if((screen.player2.getBody().getPosition().y - GameUtility.worldCreator.boundaryTop/ GameConstants.PPM/2) > screen.cameraCalibration){
                screen.cameraCalibration+=(.1);
            }
        }

        float yCal = 4*screen.cameraCalibration/3;
        if(yCal > 6){
            yCal = 6;
        }

        float xCal = 9 * yCal/4;
       *//* if(xCal>15){
            xCal = 15;
        }*/


       /* screen.viewport.setWorldSize(GameConstants.VIEW_PORT_WIDTH / GameConstants.PPM ,
                GameConstants.VIEW_PORT_HIGHT/ GameConstants.PPM );*/
        //screen.viewport.setWorldSize(xCal,yCal);

        float xCal=0;
        if(screen.isslowMotionEffect){
            if(0f != screen.cameraCalibration){
                screen.cameraCalibration += ((0f - screen.cameraCalibration)/2);
            }
        }else if(screen.cameraCalibration != screen.maxVelocity){
            screen.cameraCalibration += ((screen.maxVelocity - screen.cameraCalibration)/50);
        }
        xCal = (screen.cameraCalibration/50 + 1f) *  GameConstants.VIEW_PORT_WIDTH;

        if(xCal>4000){
            xCal = 4000;
        }
        float yCal = (xCal)/2;//(screen.cameraCalibration /10 + 1f);
        screen.viewport.setWorldSize(xCal / GameConstants.PPM , yCal / GameConstants.PPM );

        screen.viewport.apply();

        //update players directions based on movement
        if(screen.player2.body.getLinearVelocity().x > 0) {
            screen.player2.currentState = GameObjectConstants.STATE_1;
        }
        if(screen.player2.body.getLinearVelocity().x < 0) {
            screen.player2.currentState = GameObjectConstants.STATE_2;
        }
        if(screen.player2.canJump)
            screen.player2.ANIMATION_INTERVAL = .2f/Math.abs(screen.player2.body.getLinearVelocity().x+0.000001f);
        else
            screen.player2.ANIMATION_INTERVAL = Float.MAX_VALUE;

        GameUtility.jointHandler.createAllJoints();
        GameUtility.jointHandler.removeAllJoints();

        //Always destroy the bodies at the end
        screen.worldCreator.removeBodies();
        screen.worldCreator.destroyBodies();
        screen.widgets.update(dt);

        if(screen.player2.getBody().getPosition().x > screen.numOfBackImgs * screen.backImgWidth - 35){
            if(screen.numOfBackImgs%2==1){
                screen.backImgX2 = screen.numOfBackImgs * screen.backImgWidth;
            }else {
                screen.backImgX1 = screen.numOfBackImgs * screen.backImgWidth;
            }
            screen.numOfBackImgs++;
        }
        try {
            GameUtility.gameObjectCreator.createNextObject(screen.player2);
        }catch (Exception e) {
            GameUtility.log(UpdateHandler.class.getName(),"Failed while creating gameObject: " + e.getMessage());
        }
    }

    private void updateAllGameObjects(Set<GameObject> gObjs, float dt){
        for(GameObject obj: gObjs){
            obj.update(dt);
        }
    }
}
