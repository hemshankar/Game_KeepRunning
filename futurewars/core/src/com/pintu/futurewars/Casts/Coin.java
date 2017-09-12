package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 8/14/2017.
 */

public class Coin extends FutureWarsCast {
    public Coin(int id, World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.COIN_PROPERTY_FILE, w, a, obj);
    }
    @Override
    public void handleContact(GameObject gObj){
        if(gObj instanceof Player2){
            toBeDestroyed = true;
            ((Player2) gObj).totalCoin++;
            //System.out.println(((Player2) gObj).totalCoin);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (GameUtility.getGameScreen().player2.hasMagnet) {
            float pXpose = GameUtility.getGameScreen().player2.body.getPosition().x;
            float pYpose = GameUtility.getGameScreen().player2.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                float xDir = (pXpose - myXpos)/Math.abs(pXpose - myXpos);
                float yDir = (pYpose - myYpos)/Math.abs(pYpose - myYpos);
                body.setLinearVelocity( xDir * 10f, yDir * 10f);
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        //GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}