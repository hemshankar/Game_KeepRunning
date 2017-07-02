package com.jumping.marbles.Utility;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jumping.marbles.Constants.GameConstants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Utility {
    private static TextureAtlas atlas = new TextureAtlas(GameConstants.ATLUS_FILE);

    public static TextureAtlas getAtlas(){
        return atlas;
    }
}
