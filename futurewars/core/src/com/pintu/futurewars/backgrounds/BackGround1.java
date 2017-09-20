package com.pintu.futurewars.backgrounds;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;

/**
 * Created by hsahu on 9/18/2017.
 */

public class BackGround1 extends AbstractGameObject {
    public BackGround1(int id, String propFile, World w, TextureAtlas a) {
        super(id, propFile, w, a);
    }

    @Override
    public void destroy(){
        super.destroy();
        GameUtility.addPowerBlast(sprite.getX()-sprite.getWidth()/2,sprite.getY()-sprite.getHeight()/2);
    }
}
