package com.pintu.futurewars.Utility;

import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.commons.GameObject;

import java.util.Set;

/**
 * Created by hsahu on 8/9/2017.
 */

public class UpdateHandler {

    public void update(GameScreen screen,float dt){
        //update the world every 1/60 of a second?
        screen.world.step(1/60f,6,2);

        //update all the game objects at the start
        updateGameObjects(screen.gameObjects,dt);

        //update the camera as the player moves (player decides the camera position
        float x = screen.player2.getBody().getPosition().x;
        if(x<(screen.viewport.getWorldWidth()/2))
            x = screen.viewport.getWorldWidth()/2;
        else if(x>(GameUtility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2))
            x = GameUtility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2;

        screen.camera.position.x=x;

        float y = screen.player2.getBody().getPosition().y;
        if(y<screen.viewport.getWorldHeight()/2)
            y = screen.viewport.getWorldHeight()/2;
        else if(y>( GameUtility.worldCreator.boundaryTop/ GameConstants.PPM - screen.viewport.getWorldHeight()/2))
            y = ( GameUtility.worldCreator.boundaryTop/ GameConstants.PPM )- screen.viewport.getWorldHeight()/2;

        screen.camera.position.y=y;
        //System.out.println( player.body.getPosition().xPos + " -- " + player.body.getPosition().yPos);
        screen.camera.update();

        //orthographic map renderer's position is decided by the camera
        //screen.renderer.setView(screen.camera);

        //get the body speed and resize the viewport
        Vector2 velocity = screen.player2.getBody().getLinearVelocity();
        screen.maxVelocity = velocity.x>velocity.y?velocity.x:velocity.y;
        if(Math.abs(screen.maxVelocity-screen.cameraCalibration)>1) {
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
       /* if(xCal>15){
            xCal = 15;
        }*/


        screen.viewport.setWorldSize(GameConstants.VIEW_PORT_WIDTH / GameConstants.PPM /*xCal*/,
                GameConstants.VIEW_PORT_HIGHT / GameConstants.PPM /*yCal*/);

        screen.viewport.apply();

        //update players directions based on movement
        if(screen.player2.body.getLinearVelocity().x > 0) {
            screen.player2.currentState = GameObjectConstants.STATE_1;
        }
        if(screen.player2.body.getLinearVelocity().x < 0) {
            screen.player2.currentState = GameObjectConstants.STATE_2;
        }
        screen.player2.ANIMATION_INTERVAL = 1/Math.abs(screen.player2.body.getLinearVelocity().x+0.000001f);

        GameUtility.jointHandler.createAllJoints();

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
    }

    private void updateGameObjects(Set<GameObject> gObjs, float dt){
        for(GameObject obj: gObjs){
            obj.update(dt);
        }
    }
}
