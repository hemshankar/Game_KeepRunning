package com.jumping.marbles.Casts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.JumpingMarbleWorldCreator;
import com.jumping.marbles.Utility.Utility;
import com.sun.corba.se.spi.legacy.interceptor.UnknownType;

/**
 * Created by hsahu on 7/2/2017.
 */

abstract public class JumpingMarblesCast extends Sprite{

    AtlasRegion region;
    public boolean setToDestroy = false;
    public boolean destroyed = false;
    public JumpingMarblesCast(){
        //super(Utility.getAtlas().findRegion(GameConstants.PLAYER_ATLAS_NAME));
        TextureAtlas atlas = Utility.getAtlas();
        region = atlas.findRegion(getCastName());
        //should these be used
        setBounds(0,0,32/GameConstants.PPM,32/GameConstants.PPM);
        setRegion(region);
    }

    public void update(float dt){
        if(setToDestroy && !destroyed){
            Utility.world.destroyBody(getBody());
            destroyed = true;
        }else if(!destroyed){
            setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
        }else if(destroyed){

        }
    }

    public void initialize(){
        if(!getCastName().equals(GameConstants.PLAYER_ATLAS_NAME)) {
            getBody().getFixtureList().get(0).setRestitution(0.5f);
            getBody().getFixtureList().get(0).setFriction(0.0f);
        }
    }
    abstract public String getCastName();
    abstract public Body getBody();

    public void destroy(){
        setToDestroy = true;
        setPosition(0,0);
       /* getBody().getPosition().x =0;
        getBody().getPosition().y=0;*/

    }
}
