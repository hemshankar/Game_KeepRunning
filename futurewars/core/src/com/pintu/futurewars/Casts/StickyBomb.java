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

public class StickyBomb extends FutureWarsCast {
    public boolean fired = false;
    public float LIFE_TIME = 5;
    public float lived = 0;
    public boolean isAttached = false;
    public StickyBomb(int id, World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.STICKY_BOMB_PROPERTY_FILE, w, a, obj);
    }
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            GameUtility.jointHandler.createJoint(body,((Player2) gObj).body,world,GameConstants.REVOLUTE);
            isAttached = true;
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
        if(isAttached){
            Body pBody = GameUtility.getGameScreen().player2.body;
            pBody.applyLinearImpulse(new Vector2(50f, 0), pBody.getWorldCenter(), true);
            GameUtility.getGameScreen().player2.takeDamage(10);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (!fired) {
            float pXpose = GameUtility.getGameScreen().player2.body.getPosition().x;
            float pYpose = GameUtility.getGameScreen().player2.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                body.setLinearVelocity((pXpose - myXpos) * 10f, (pYpose - myYpos) * 10f);
                fired = true;
            }
        }else {
            updateMe(dt);
        }
    }

    private void updateMe(float dt){
        lived+=dt;
        if(lived > LIFE_TIME){
            toBeDestroyed = true;
        }
    }
}