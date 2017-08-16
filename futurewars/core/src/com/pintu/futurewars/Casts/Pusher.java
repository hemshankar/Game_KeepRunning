package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.common.utilities.Utility;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Pusher extends FutureWarsCast {
    public Pusher(int id,World w, TextureAtlas a, MapObject obj) {
        super(id,GameConstants.PUSHER_PROPERTY_FILE, w, a, obj);
    }
}
