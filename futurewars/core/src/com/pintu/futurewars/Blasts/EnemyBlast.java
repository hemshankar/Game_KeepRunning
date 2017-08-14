package com.pintu.futurewars.Blasts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.Utility;

/**
 * Created by hsahu on 8/10/2017.
 */

public class EnemyBlast extends Blast {
    AtlasRegion region;
    private static String []enemyBlastRegions = {"004","005","006","007","009","010"};
    private static final int TOTAL_BLAST_STATES = enemyBlastRegions.length;
    private float x,y;
    private final static float STATE_CHANGE_CONSTANT = .1f;
    private float timeInCurrentState = 0f;
    private int blastState = 0;
    TextureAtlas atlas = null;
    public EnemyBlast(float x_, float y_){
        x = x_;
        y = y_;
        //super(Utility.getAtlas().findRegion(GameConstants.PLAYER_ATLAS_NAME));
        atlas = Utility.getBlastAtlas();

        region = atlas.findRegion(enemyBlastRegions[0]);
        //should these be used
        setBounds(0,0, GameConstants.SIZE_SCALE*4/ GameConstants.PPM, GameConstants.SIZE_SCALE*4/ GameConstants.PPM);
        setRegion(region);
        setPosition(x_,y_);
    }

    public void update(float dt){
        timeInCurrentState += dt;
        if(!destroyed) {
            if (timeInCurrentState > STATE_CHANGE_CONSTANT) {
                timeInCurrentState = 0;
                blastState++;
            }
            if(blastState<TOTAL_BLAST_STATES) {
                region = atlas.findRegion(enemyBlastRegions[blastState]);
                setRegion(region);
                //setPosition(x - getWidth() / 2, y - getHeight() / 2);
            }else{
                destroyed =true;
            }
        }
    }
}
