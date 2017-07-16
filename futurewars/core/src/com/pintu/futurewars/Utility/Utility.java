package com.pintu.futurewars.Utility;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarbleWorldCreator;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Utility {
    private static TextureAtlas atlas = new TextureAtlas(GameConstants.ATLUS_FILE);
    private static Player player = null;
    public static TextureAtlas getAtlas(){
        return atlas;
    }
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

}
