package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pivot extends FutureWarsCast {

    public Pivot() {
        super(GameConstants.PIVOT_PROPERTY_FILE);
        ropeLength = GameConstants.PIVOT_ROPE_LENGTH;
        health = Float.MAX_VALUE;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        /*if(body.getPosition().y<flyPosition){
            body.applyLinearImpulse(
                    new Vector2(0,body.getMass() * 10f),
                    this.body.getWorldCenter(), true);
        }

        float diff = GameUtility.getGameScreen().player2.body.getLinearVelocity().x - body.getLinearVelocity().x;
        if (diff > 20) {
            body.applyLinearImpulse(
                    new Vector2(diff * body.getMass(), 0),
                    this.body.getWorldCenter(), true);
        }*/
    }
}
