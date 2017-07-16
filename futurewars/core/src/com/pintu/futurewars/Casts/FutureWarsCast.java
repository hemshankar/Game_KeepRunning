package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.pintu.futurewars.Utility.Utility;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;

/**
 * Created by hsahu on 7/2/2017.
 */

abstract public class FutureWarsCast extends Sprite{

    AtlasRegion region;
    public int health = 100;
    public boolean destroyed = false;

    public FutureWarsCast(){
        //super(Utility.getAtlas().findRegion(GameConstants.PLAYER_ATLAS_NAME));
        TextureAtlas atlas = Utility.getAtlas();
        region = atlas.findRegion(getCastName());
        //should these be used
        setBounds(0,0, GameConstants.SIZE_SCALE*2/ GameConstants.PPM, GameConstants.SIZE_SCALE*2/ GameConstants.PPM);
        setRegion(region);
    }

    public void update(float dt){
        if(!destroyed){
            setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
        }else if(destroyed){

        }
    }

    public void initialize(){
        if(!getCastName().equals(GameConstants.PLAYER_ATLAS_NAME)) {
            getBody().getFixtureList().get(0).setRestitution(0.5f);
            getBody().getFixtureList().get(0).setFriction(0.5f);
        }
    }
    abstract public String getCastName();
    abstract public Body getBody();
    public void takeDamage(GameBullet bullet){
        health-=bullet.getDamage();
        if(health<=0){
            Utility.worldCreator.removeBody(this);
        }
    }
}
