package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.commons.AbstractGameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/14/2017.
 */

public class SpeedBomb extends FutureWarsCast {
    public SpeedBomb(int id,World w, TextureAtlas a, MapObject obj) {
        super(id, GameConstants.SPEED_BOMB_PROPERTY_FILE, w, a, obj);
    }
}
