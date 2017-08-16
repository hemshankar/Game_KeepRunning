package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Ground extends FutureWarsCast {

    public Ground(int id, World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.GROUND_PROPERTY_FILE, w, a, obj);
    }
}
