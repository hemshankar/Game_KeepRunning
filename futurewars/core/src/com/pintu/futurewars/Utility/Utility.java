package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Screens.GameScreen;

import java.util.List;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Utility {
    private static TextureAtlas atlas = new TextureAtlas(GameConstants.ATLUS_FILE);
    private static TextureAtlas blastAtlas = new TextureAtlas(GameConstants.BLAST_ATLUS_FILE);
    private static Player player = null;
    public static TextureAtlas getAtlas(){
        return atlas;
    }
    public static TextureAtlas getBlastAtlas(){
        return blastAtlas;
    }

    public static GameScreen gameScreen= null;
    public static World world;
    public static void setPlayer(Player pler){
            player = pler;
    }
    public static JumpingMarbleWorldCreator worldCreator = null;
    public static Player getPlayer(){
        return player;
    }

    public static void setWorld(World w){
        world = w;
    }

    public static JumpingMarbleWorldCreator getWorldCreator(){
        return worldCreator;
    }

    public static void render(SpriteBatch batch, List<? extends Sprite> sprites){
        for(Sprite s: sprites){
            s.draw(batch);
        }
    }
}
