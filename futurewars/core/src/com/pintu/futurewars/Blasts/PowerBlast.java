package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;

/**
 * Created by hsahu on 8/10/2017.
 */

public class PowerBlast extends Blast {
    public PowerBlast(int id, World w, TextureAtlas a, float x_, float y_) {
        super(id, GameConstants.POWER_BLAST_PROPERTY_FILE, w, a, x_,y_);
    }
}
