package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class WaterBalloon extends FutureWarsCast {
    public boolean fired = false;

    public WaterBalloon(int id, World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.WATER_BALLOON_PROPERTY_FILE, w, a, obj);
    }
    @Override
    public void handleContact(GameObject gObj){
        if(fired) {
            toBeDestroyed = true;
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addEnemyBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(!fired) {
            float pXpose = GameUtility.gameScreen.player2.body.getPosition().x;
            float pYpose = GameUtility.gameScreen.player2.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                body.setLinearVelocity((pXpose - myXpos) * 10f, (pYpose - myYpos) * 10f);
                fired = true;
            }
        }
    }
}