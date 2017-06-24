package com.mygdx.game.com.mygdx.game.sticks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by hsahu on 6/24/2017.
 */

public class TheStick  extends Sprite{

    //Sprite me = new Sprite();
    String id=null;

    SpriteBatch batch;
    TextureAtlas atlas;

    public int state = 0;
    public int totalStats = 4;

    public TheStick(String id, SpriteBatch batch, String atlasLocation){
        this.id = id;
        this.batch = batch;
        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));
        this.setRegion(atlas.findRegion(id+"1"));
    }

    public void render(){
        this.draw(this.batch);
    }

    public void setState(int state){
        this.state = state;
        this.setRegion(atlas.findRegion(id+this.state));
    }
}
