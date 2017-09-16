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

    public float attTimer = 0;
    public float ATTRACT_INTERVAL = .001f;
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
        Player2 player = GameUtility.getGameScreen().player2;
        if (player.hasMagnet) {
            float pXpose = player.body.getPosition().x;
            float pYpose = player.body.getPosition().y;
            float myXpos = body.getPosition().x;
            float myYpos = body.getPosition().y;

            if (itsAttractTime(dt) && Math.abs(Math.abs(pXpose) - Math.abs(myXpos)) < 5
                    && Math.abs(Math.abs(pYpose) - Math.abs(myYpos)) < 5) {
                float xDir = (pXpose - myXpos)/Math.abs(pXpose - myXpos);
                float yDir = (pYpose - myYpos)/Math.abs(pYpose - myYpos);
                //body.setLinearVelocity( xDir * (Math.abs(player.body.getLinearVelocity().x) + 10f), yDir * (Math.abs(player.body.getLinearVelocity().y) + 10f));
                body.setLinearVelocity((pXpose - myXpos) *(player.body.getLinearVelocity().x+10) ,
                        (pYpose - myYpos) * (player.body.getLinearVelocity().x+10));
                canFly = false;
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        //GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }

    public boolean itsAttractTime(float dt){
        attTimer += dt;
        if(attTimer > ATTRACT_INTERVAL){
            attTimer = 0;
            return true;
        }
        return false;
    }
}