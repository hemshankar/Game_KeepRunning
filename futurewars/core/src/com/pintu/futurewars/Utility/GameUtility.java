package com.pintu.futurewars.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.common.exceptions.UtilityException;
import com.pintu.futurewars.Blasts.EnemyBlast;
import com.pintu.futurewars.Blasts.PowerBlast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.JumpingMarbleWorldCreator;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.Screens.MenuStage;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.Bomb;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import billing.BillingManager;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameUtility {

    public static Map<String, TextureAtlas> atlasMap = new HashMap<String, TextureAtlas>();

    public static Player player = null;
    public static Player2 player2 = null;
    public static Map<String, Map<String, String[]>> stateFrameDetailsMap
            = new HashMap<String, Map<String, String[]>>();
    public static Map<String, Map<String, String>> stateSoundDetailsMap
            = new HashMap<String, Map<String, String>>();

    //public static GameScreen gameScreen= null;
    public static WeakReference<GameScreen> gameScreen = null;
    public static World world;

    public static void setPlayer(Player pler) {
        player = pler;
    }

    public static void setPlayer(Player2 pler) {
        player2 = pler;
    }

    public static JumpingMarbleWorldCreator worldCreator = null;

    public static Player getPlayer() {
        return player;
    }

    public static void setWorld(World w) {
        world = w;
    }

    public static JumpingMarbleWorldCreator getWorldCreator() {
        return worldCreator;
    }

    public static int id = 0;

    public static JointHandler jointHandler = new JointHandler();

    public static GameObjectCreator gameObjectCreator = new GameObjectCreator();
    public static ShapeHelper shapeHelper = new ShapeHelper();
    public static Random random = new Random();
    public static JumpingMarblesGame game = null;
    public static MenuStage menuStage = null;

    public static Map<String, Skin> skinMap = new HashMap<String, Skin>();

    //Billing related
    public static BillingManager billingManager = new BillingManager();

    public static void renderGameObjects(SpriteBatch batch, Set<GameObject> gos) {
        for (GameObject obj : gos) {
            if (obj.getSprite() != null) {
                if (obj.getSprite().getTexture() == null) { //improge this logic
                    //System.out.print("Is NULL==============" + obj);
                } else {
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
                    } catch (Exception e) {
                        System.out.println("Error in obj: " + obj);
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static Map<String, String[]> getStateFrameDetails(String key) {
        return stateFrameDetailsMap.get(key);
    }

    public static Map<String, String> getStateSoundDetails(String key) {
        return stateSoundDetailsMap.get(key);
    }

    public static void setStateSoundDetails(String key, Map<String, String> map) {
        stateSoundDetailsMap.put(key, map);
    }

    public static void setStateFrameDetails(String str, Map<String, String[]> map) {
        stateFrameDetailsMap.put(str, map);
    }

    public static void fireBasicBullet(float x, float y, GameObject target) {
        BasicBullet bullet = new BasicBullet(id++, world, getAtlas(GameConstants.ATLAS_FILE), x, y);
        bullet.initialize();
        bullet.target = target;
        bullet.fire();

        getGameScreen().gameObjects.add(bullet);
    }

    public static void fireBurstBullet(float x, float y, float distance, GameObject target) {
        BasicBullet bullet = new BasicBullet(id++, world, getAtlas(GameConstants.ATLAS_FILE), x, y - distance);
        bullet.initialize();
        bullet.target = target;
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);

        bullet = new BasicBullet(id++, world, getAtlas(GameConstants.ATLAS_FILE), x, y + distance);
        bullet.initialize();
        bullet.target = target;
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void Bomb(float x, float y, GameObject target) {
        Bomb bullet = new Bomb(id++, world, getAtlas(GameConstants.ATLAS_FILE), x, y);
        bullet.initialize();
        bullet.target = target;
        bullet.fire();
        getGameScreen().gameObjects.add(bullet);
    }

    public static void addEnemyBlast(float x, float y) {
        EnemyBlast blast = new EnemyBlast(id++, world, getAtlas(GameConstants.BLAST_ATLUS_FILE), x, y);
        blast.initialize();
        blast.canFly = false;
        getGameScreen().gameObjects.add(blast);
    }

    public static void addPowerBlast(float x, float y) {
        PowerBlast blast = new PowerBlast(id++, world, getAtlas(GameConstants.BLAST_ATLUS_FILE), x, y);
        blast.canFly = false;
        blast.initialize();
        getGameScreen().gameObjects.add(blast);
    }

    //config cache
    private static Map<String, Map<String, String>> configMap = new HashMap<String, Map<String, String>>();
    public static Map<String, String> populateConfigurationsFromConfigFile(String fileName) throws UtilityException {

        Map<String, String> configFile = configMap.get(fileName);
        if(configFile == null) {
            configFile = new HashMap<String, String>();
            FileHandle file = Gdx.files.internal(fileName.trim());
            String text = file.readString();

            try {
                String[] lines = text.split("\n");
                for (String str : lines) {
                    str = str.trim();
                    String[] arr = str.split("=");
                    if (arr.length > 1) {
                        configFile.put(arr[0], arr[1]);
                        try{
                            if(GameObjectConstants.TEXTURE_ATLAS_NAME.equals(arr[0])){
                                getAtlas(arr[1]);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            log("GameUtility",e.getMessage());
                        }
                    }
                }
            } catch (Exception var8) {
                throw new UtilityException("Error getting lines from file to hashmap. \n Cause by: " + var8.toString());
            } finally {
                //close fineHandle?
            }

            configMap.put(fileName,configFile);
        }
        return configFile;
    }

    public static MenuStage getMenuStage() {
        if (menuStage == null)
            menuStage = new MenuStage(game);
        return menuStage;
    }

    public static void setGameScreen(GameScreen screen) {
        gameScreen = new WeakReference<GameScreen>(screen);
    }

    public static GameScreen getGameScreen() {
        if (gameScreen == null)
            return null;
        return gameScreen.get();
    }

    public static void log(String className, String str) {
        System.out.println(className + ":" + str);
    }

    public static TextureAtlas getAtlas(String fileName) {
        TextureAtlas ta = atlasMap.get(fileName);
        if (ta == null) {
            ta = new TextureAtlas(fileName);
            atlasMap.put(fileName, ta);
        }
        return ta;
    }

    public static void disposeAllAtlas() {
        for (TextureAtlas at : atlasMap.values()) {
            at.dispose();
            log("GameUtility", at.getClass().getName() + " disposed");
        }
        atlasMap.clear();
    }

    public void reset() {

    }

    public static boolean isEmptyString(String str) {
        if (str != null && str.length() > 0)
            return false;
        return true;
    }

    public static Map<String, String[]> getStateFrameDetails2(String spriteDetails) {
        Map<String, String[]> tmpStateFrameDetails = null;
        if (spriteDetails != null) {
            tmpStateFrameDetails = GameUtility.getStateFrameDetails(spriteDetails);
            if (tmpStateFrameDetails != null)
                return tmpStateFrameDetails;
            else
                tmpStateFrameDetails = new HashMap<String, String[]>();

            String[] value;
            if (!isEmpty(spriteDetails)) {
                value = spriteDetails.split("<-->"); //get each state details
                if (!isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateAndFrames = stateDetails.split("<->");
                        if (!isEmpty(stateAndFrames) && stateAndFrames.length > 1) {
                            String[] frames = stateAndFrames[1].split(",");
                            tmpStateFrameDetails.put(stateAndFrames[0], frames);
                        }
                    }
                }
            }
        }
        GameUtility.setStateFrameDetails(spriteDetails, tmpStateFrameDetails);
        return tmpStateFrameDetails;
    }

    public static Map<String, String> getStateSoundDetails2(String soundDetails) {
        Map<String, String> tmpStateSoundDetails = null;
        if (soundDetails != null) {
            tmpStateSoundDetails = getStateSoundDetails(soundDetails);
            if (tmpStateSoundDetails != null)
                return tmpStateSoundDetails;
            else
                tmpStateSoundDetails = new HashMap<String, String>();

            String[] value;
            if (!isEmpty(soundDetails)) {
                value = soundDetails.split("<-->"); //get each state details
                if (!isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateSound = stateDetails.split("<->");
                        if (!isEmpty(stateSound) && stateSound.length > 1) {
                            tmpStateSoundDetails.put(stateSound[0], stateSound[1]);
                        }
                    }
                }
            }
        }
        GameUtility.setStateSoundDetails(soundDetails, tmpStateSoundDetails);
        return tmpStateSoundDetails;
    }


    public static boolean valueEquals(String x, String y) {
        if (x != null && y != null) {
            return x.equalsIgnoreCase(y);
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str))
            return false;
        return true;
    }

    public static boolean isEmpty(String[] str) {
        if (str != null && str.length > 0)
            return false;
        return true;
    }

    public static Integer getInt(String str) {
        if (str != null)
            return Integer.parseInt(str);
        return 0;
    }

    public static Float getFloat(String str) {
        if (str != null)
            return Float.parseFloat(str);
        return 0f;
    }

    public static boolean isTrue(String str) {
        if (str != null && str.equalsIgnoreCase("TRUE"))
            return true;
        return false;
    }

    public static void playSound(String sound) {
        if (!isEmptyString(sound)) {
            GameUtility.getGameScreen().assetManager.get(sound, Sound.class).play();
        }
    }

    public static BitmapFont getFonts(String fontTypeTTFFile, int size, Color color) {
        BitmapFont f = null;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontTypeTTFFile));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        params.color = color;
        f = generator.generateFont(params);

        return f;
    }

    public static Skin getSkin(String id) { //load all the skins at the start of the game
        Skin skn = skinMap.get(id);
        if (skn == null) {
            skn = new Skin(Gdx.files.internal(id));
            skinMap.put(id, skn);
        }
        return skn;
    }

    public static void dispose() {
        for (String key : skinMap.keySet()) {
            if (skinMap.get(key) != null) {
                skinMap.get(key).dispose();
            }
        }
    }

    public static void initiate() throws Exception{
        String propertiesFileList = "initialization/allPropertiesFile";
        FileHandle fHandle = Gdx.files.internal(propertiesFileList);

        for(String file:fHandle.readString().split("\n")) {
            System.out.println("Adding resource : " + file);
            try {
                populateConfigurationsFromConfigFile(file);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /*

    public static List<String> getLines(File file) throws Exception{
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<FileHandle> listFilesForFolder(FileHandle fileH) {
        List<FileHandle> files = new ArrayList<FileHandle>();

        for (final FileHandle f : fileH.list()) {
            if (f.isDirectory()) {
                files.addAll(listFilesForFolder(f));
            } else {
                files.add(fileH);
            }
        }
        return files;
    }

*/}
