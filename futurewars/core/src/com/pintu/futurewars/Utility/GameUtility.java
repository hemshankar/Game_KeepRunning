package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
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
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameUtility {

    public static Map<String,TextureAtlas> atlasMap = new HashMap<String, TextureAtlas>();

    public static Player player = null;
    public static Player2 player2 = null;
    public static Map<String ,Map<String,String[]>> stateFrameDetailsMap
                                                            = new HashMap<String, Map<String, String[]>>();
    public static Map<String ,Map<String,String>> stateSoundDetailsMap
                                                            = new HashMap<String, Map<String, String>>();

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

    public static GameObjectCreator gameObjectCreator = new GameObjectCreator();
    public static ShapeHelper shapeHelper = new ShapeHelper();

    public static void renderGameObjects(SpriteBatch batch, Set<GameObject> gos){
        for(GameObject obj: gos){
            if(obj.getSprite()!=null){
                if(obj.getSprite().getTexture() == null){ //improge this logic
                    //System.out.print("Is NULL==============" + obj);
                }else{
                    try {
                        AbstractGameObject o = (AbstractGameObject) obj;
                        if (!o.isBackground) { //if the object is not a background draw its background first
                            if (o.getBackground() != null && !o.background.toBeDestroyed) {
                                o.getBackground().getSprite().setPosition(o.body.getPosition().x - o.getBackground().getSprite().getWidth() / 2,
                                        o.body.getPosition().y - o.getBackground().getSprite().getHeight() / 2);
                                //o.getBackground().getSprite().setPosition(o.getSprite().getX(),o.sprite.getY());
                                o.getBackground().getSprite().draw(batch);
                            }
                            o.getSprite().draw(batch);
                        }
                    }catch(Exception e){
                        System.out.println("Error in obj: " + obj);
                        e.printStackTrace();
                    }
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
        BasicBullet bullet = new BasicBullet(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x,y);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void fireBurstBullet(float x, float y){
        BasicBullet bullet = new BasicBullet(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x,y-1);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);

        bullet = new BasicBullet(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x+1,y+1);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void fireBurstBullet(float x, float y,float distance){
        BasicBullet bullet = new BasicBullet(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x,y-distance);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);

        bullet = new BasicBullet(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x,y+distance);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }
    public static void Bomb(float x, float y){
        Bomb bullet = new Bomb(id++,world ,getAtlas(GameConstants.ATLAS_FILE),x,y);
        bullet.initialize();
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void addEnemyBlast(float x, float y){
        EnemyBlast blast = new EnemyBlast(id++,world,getAtlas(GameConstants.BLAST_ATLUS_FILE),x,y);
        blast.initialize();
        blast.canFly = false;
        getGameScreen().gameObjects.add(blast);
    }
    public static void addPowerBlast(float x, float y){
        PowerBlast blast = new PowerBlast(id++,world,getAtlas(GameConstants.BLAST_ATLUS_FILE),x,y);
        blast.canFly = false;
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
    public static TextureAtlas getAtlas(String fileName){
        TextureAtlas ta = atlasMap.get(fileName);
        if(ta == null){
            ta = new TextureAtlas(fileName);
            atlasMap.put(fileName,ta);
        }
        return ta;
    }

    public static void disposeAllAtlas(){
        for(TextureAtlas at: atlasMap.values()){
            at.dispose();
            log("GameUtility",at.getClass().getName() + " disposed");
        }
        atlasMap.clear();
    }

    public void reset(){

    }

    public static boolean isEmptyString(String str){
        if(str!=null && str.length()>0)
            return true;
        return false;
    }
}
