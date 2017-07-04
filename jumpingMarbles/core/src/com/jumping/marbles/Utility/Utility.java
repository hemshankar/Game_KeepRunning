package com.jumping.marbles.Utility;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Casts.JumpingMarblesCast;
import com.jumping.marbles.Casts.Player;
import com.jumping.marbles.Casts.Pusher;
import com.jumping.marbles.Constants.GameConstants;
import com.sun.org.apache.bcel.internal.generic.PUSH;

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

    public static Player getPlayer(){
        return player;
    }

    public static void setWorld(World w){
        world = w;
    }

}
