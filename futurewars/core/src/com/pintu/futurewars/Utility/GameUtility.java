package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Blasts.EnemyBlast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.commons.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameUtility {
    private static TextureAtlas atlas = new TextureAtlas(GameConstants.ATLUS_FILE);
    private static TextureAtlas blastAtlas = new TextureAtlas(GameConstants.BLAST_ATLUS_FILE);
    private static Player player = null;
    private static Player2 player2 = null;
    private static Map<String ,Map<String,String[]>> stateFrameDetailsMap
                                                            = new HashMap<String, Map<String, String[]>>();
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
    public static void setPlayer(Player2 pler){
        player2 = pler;
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

    public static int id=0;

    public static void render(SpriteBatch batch, List<? extends Sprite> sprites){
        for(Sprite s: sprites){
            s.draw(batch);
        }
    }

    public static void renderGameObjects(SpriteBatch batch, Set<GameObject> gos){
        for(GameObject obj: gos){
            obj.getSprite().draw(batch);
        }
    }
    public static Map<String,String[]> getStateFrameDetails(String key){
        return stateFrameDetailsMap.get(key);
    }
    public static void setStateFrameDetails(String str,Map<String,String[]> map){
        stateFrameDetailsMap.put(str,map);
    }

    public static void fireBasicBullet(float x, float y){
        gameScreen.gameObjects.add(new BasicBullet(id++,null,world,atlas,x,y));
    }
    public static void addEnemyBlast(float x, float y){
        gameScreen.gameObjects.add(new EnemyBlast(id++,null,world,blastAtlas,x,y));
    }
}
