package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;
/**
 * Created by hsahu on 7/2/2017.
 */

public class Player2 extends FutureWarsCast {
    public float recoilTimeElapsed = 0;
    public String selectedBullet = null;
    public boolean hasJumpingKit = false;

    public Player2(int id, World w, TextureAtlas a, MapObject object) {
        super(id, GameConstants.PLAYER_PROPERTY_FILE, w, a,object);
        GameUtility.setPlayer(this);
        selectedBullet = GameConstants.BASIC_BULLET;
    }

    public void update(float dt){
        super.update(dt);
        recoilTimeElapsed +=dt;
    }

    public void fire(){
        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                GameUtility.fireBasicBullet(body.getPosition().x + GameConstants.PLAYER_SIZE/GameConstants.PPM,
                        body.getPosition().y + + GameConstants.PLAYER_SIZE/GameConstants.PPM/2);
            }
        }
    }
}
