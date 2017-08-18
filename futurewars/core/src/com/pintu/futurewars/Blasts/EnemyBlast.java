package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/10/2017.
 */

public class EnemyBlast extends Blast {
    public EnemyBlast(int id,World w, TextureAtlas a, float x_, float y_) {
        super(id, GameConstants.ENEMY_BLAST_PROPERTY_FILE, w, a, x_,y_);
    }
}
