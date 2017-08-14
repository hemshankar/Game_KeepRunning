package com.pintu.futurewars.Utility;

import com.badlogic.gdx.math.Vector2;
import com.pintu.futurewars.Blasts.Blast;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Healer;
import com.pintu.futurewars.Casts.SuckerCreator;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;

import java.util.List;

/**
 * Created by hsahu on 8/9/2017.
 */

public class UpdateHandler {
    GameScreen screen=null;

    public void update(GameScreen screen,float dt){
        //update the world every 1/60 of a second?
        screen.world.step(1/60f,6,2);

        //update all the characters
        updateCasts(screen.casts,dt);
        updateBullets(screen.bullets,dt);
        updateBlasts(screen.blastList,dt);

        if( !Utility.world.isLocked()) {
            for (GameBullet bullet : screen.bulletsToBeRemoved) {
                if(bullet.getBody()!=null) {
                    Utility.world.destroyBody(bullet.getBody());
                    bullet.setBody(null);
                    bullet.destroyed = true;
                }
            }
        }

        screen.bullets.remove(screen.bulletsToBeRemoved);
        screen.bulletsToBeRemoved.clear();
        screen.worldCreator.destroyBodies();
        screen.blastHandler.removeDestroyedBlast();

        //update the camera as the player moves (player decides the camera position
        float x = screen.player.body.getPosition().x;
        if(x<(screen.viewport.getWorldWidth()/2))
            x = screen.viewport.getWorldWidth()/2;
        else if(x>(Utility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2))
            x = Utility.worldCreator.boundaryRight/ GameConstants.PPM - screen.viewport.getWorldWidth()/2;

        screen.camera.position.x=x;

        float y = screen.player.body.getPosition().y;
        if(y<screen.viewport.getWorldHeight()/2)
            y = screen.viewport.getWorldHeight()/2;
        else if(y>( Utility.worldCreator.boundaryTop/ GameConstants.PPM - screen.viewport.getWorldHeight()/2))
            y = ( Utility.worldCreator.boundaryTop/ GameConstants.PPM )- screen.viewport.getWorldHeight()/2;

        screen.camera.position.y=y;
        //System.out.println( player.body.getPosition().x + " -- " + player.body.getPosition().y);
        screen.camera.update();

        //orthographic map renderer's position is decided by the camera
        screen.renderer.setView(screen.camera);

        //change the bullet type after reaching a some distance
        //to be removed later
        //if(x > (Utility.worldCreator.boundaryRight/ GameConstants.PPM)/2)
        //player.selectedBullet = GameConstants.BOMB;

        //get the body speed and resize the viewport
        Vector2 velocity = screen.player.body.getLinearVelocity();
        screen.maxVelocity = velocity.x>velocity.y?velocity.x:velocity.y;
        if(Math.abs(screen.maxVelocity-screen.cameraCalibration)>1) {
            if (screen.maxVelocity - screen.cameraCalibration > 0) {
                screen.cameraCalibration += .1;
            } else if (screen.maxVelocity - screen.cameraCalibration < 0) {
                screen.cameraCalibration -= .1;
            }
        }
        if(screen.player.body.getPosition().y > Utility.worldCreator.boundaryTop/ GameConstants.PPM/2){
            if((screen.player.body.getPosition().y - Utility.worldCreator.boundaryTop/ GameConstants.PPM/2) > screen.cameraCalibration){
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
    }

    private void updateCasts(List<? extends FutureWarsCast> casts, float dt){
        for(FutureWarsCast cast: casts){
            cast.update(dt);
        }
    }
    private void updateBullets(List<? extends GameBullet> bullets, float dt){
        for(GameBullet bullet: bullets){
            bullet.update(dt);
            if(bullet.toBeDestroyed){
                Utility.gameScreen.bulletsToBeRemoved.add(bullet);
            }
        }
    }
    private void updateBlasts(List<? extends Blast> blasts, float dt){
        for(Blast blast: blasts){
            blast.update(dt);

        }
    }
}
