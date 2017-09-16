package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.common.exceptions.UtilityException;
import com.pintu.futurewars.Blasts.EnemyBlast;
import com.pintu.futurewars.Blasts.PowerBlast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.Bomb;
import com.pintu.futurewars.commons.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameUtility {
    public static TextureAtlas atlas = new TextureAtlas(GameConstants.ATLUS_FILE);
    public static TextureAtlas blastAtlas = new TextureAtlas(GameConstants.BLAST_ATLUS_FILE);
    //public static TextureAtlas ninjaAtlas = new TextureAtlas(GameConstants.NINJA_ATLUS_FILE);
    public static TextureAtlas stickAtlas = new TextureAtlas(GameConstants.STICK_ATLUS_FILE);
    public static Player player = null;
    public static Player2 player2 = null;
    public static Map<String ,Map<String,String[]>> stateFrameDetailsMap
                                                            = new HashMap<String, Map<String, String[]>>();
    public static Map<String ,Map<String,String>> stateSoundDetailsMap
                                                            = new HashMap<String, Map<String, String>>();

    public static TextureAtlas getAtlas(){
        return atlas;
    }
    public static TextureAtlas getBlastAtlas(){
        return blastAtlas;
    }

    //public static GameScreen gameScreen= null;
    public static WeakReference<GameScreen> gameScreen = null;
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

    public static JointHandler jointHandler = new JointHandler();

    public static void renderGameObjects(SpriteBatch batch, Set<GameObject> gos){
        for(GameObject obj: gos){
            if(obj.getSprite()!=null){
                if(obj.getSprite().getTexture() == null){ //improge this logic
                    //System.out.print("Is NULL==============" + obj);
                }else{
                    obj.getSprite().draw(batch);
                }
            }

        }
    }
    public static Map<String,String[]> getStateFrameDetails(String key){
        return stateFrameDetailsMap.get(key);
    }

    public static Map<String,String> getStateSoundDetails(String key){
        return stateSoundDetailsMap.get(key);
    }
    public static void setStateSoundDetails(String key, Map<String,String> map){
        stateSoundDetailsMap.put(key,map);
    }

    public static void setStateFrameDetails(String str,Map<String,String[]> map){
        stateFrameDetailsMap.put(str,map);
    }

    public static void fireBasicBullet(float x, float y){
        BasicBullet bullet = new BasicBullet(id++,world ,atlas,x,y);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void fireBurstBullet(float x, float y){
        BasicBullet bullet = new BasicBullet(id++,world ,atlas,x,y-1);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);

        bullet = new BasicBullet(id++,world ,atlas,x+1,y+1);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void fireBurstBullet(float x, float y,float distance){
        BasicBullet bullet = new BasicBullet(id++,world ,atlas,x,y-distance);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);

        bullet = new BasicBullet(id++,world ,atlas,x,y+distance);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }
    public static void Bomb(float x, float y){
        Bomb bullet = new Bomb(id++,world ,atlas,x,y);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void addEnemyBlast(float x, float y){
        EnemyBlast blast = new EnemyBlast(id++,world,blastAtlas,x,y);
        blast.initialize();
        getGameScreen().gameObjects.add(blast);
    }
    public static void addPowerBlast(float x, float y){
        PowerBlast blast = new PowerBlast(id++,world,blastAtlas,x,y);
        blast.initialize();
        getGameScreen().gameObjects.add(blast);
    }
    public static HashMap<String, String> populateConfigurationsFromConfigFile(String fileName) throws UtilityException {
        HashMap configFile = new HashMap();
        FileHandle file = Gdx.files.internal(fileName);
        String text = file.readString();

        try {
            String [] lines = text.split("\n");
            for (String str: lines){
                str = str.trim();
                  String[] arr = str.split("=");
                if(arr.length > 1) {
                    configFile.put(arr[0], arr[1]);
                }
            }
        } catch (Exception var8) {
            throw new UtilityException("Error getting lines from file to hashmap. \n Cause by: " + var8.toString());
        } finally {
           //close fineHandle?
        }

        return configFile;
    }

    public static void setGameScreen(GameScreen screen){
        gameScreen = new WeakReference<GameScreen>(screen);
    }

    public static GameScreen getGameScreen(){
        return gameScreen.get();
    }

    public static void log(String className,String str){
        System.out.println( className + ":" + str);
    }
}
