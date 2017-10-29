package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pivot extends FutureWarsCast {

    public Pivot() {
        super(GameConstants.PIVOT_PROPERTY_FILE);
        ropeLength = 3;
    }

}
