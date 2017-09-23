package com.pintu.futurewars.backgrounds;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 9/18/2017.
 */

public class BackGround extends AbstractGameObject {
    GameObject foreGroundObj = null;
    public BackGround(int id, String propFile, World w, TextureAtlas a, GameObject f) {
        super(id, propFile, w, a);
        foreGroundObj = f;
        isBackground = true;
    }

    public BackGround(String propFile,GameObject f) {
        super(5, propFile, GameUtility.world,null);
        foreGroundObj = f;
        isBackground = true;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        //xPos = foreGroundObj.getBody().getPosition().x - sprite.getWidth() / 2;
        //yPos = foreGroundObj.getBody().getPosition().y - sprite.getHeight() / 2;
    }

    @Override
    public void destroy(){
        super.destroy();
    }
}
