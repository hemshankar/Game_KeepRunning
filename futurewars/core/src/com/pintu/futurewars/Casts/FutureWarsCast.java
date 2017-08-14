package com.pintu.futurewars.Casts;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.pintu.futurewars.Blasts.Blast;
import com.pintu.futurewars.Blasts.EnemyBlast;
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
        Body b = getBody();
        if(health<=0){
            Utility.gameScreen.blastHandler.addBlast(new EnemyBlast(b.getPosition().x,
                                                                b.getPosition().y));
            if(this instanceof Brick)
                Utility.gameScreen.assetManager.get("audio/Fire impact 1.wav",Sound.class).play();
            else if (this instanceof Pusher)
                Utility.gameScreen.assetManager.get("audio/Wind effects 5.wav",Sound.class).play();
            Utility.worldCreator.removeBody(this);
        }
    }
}
