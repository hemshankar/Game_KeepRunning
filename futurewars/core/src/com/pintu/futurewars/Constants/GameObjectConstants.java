package com.pintu.futurewars.Constants;

/**
 * Created by hsahu on 8/14/2017.
 */

public class GameObjectConstants {

    public final static String BODY_TYPE = "BODY_TYPE";
    public final static String BODY_SHAPE = "BODY_SHAPE";
    public final static String NO_BODY = "NO_BODY";
    public final static String GAME_OBJECT_TYPE = "GAME_OBJECT_TYPE";
    public final static String MAP_OBJECT_SHAPE = "MAP_OBJECT_SHAPE";
    public final static String IS_BULLET = "IS_BULLET";
    public final static String OBJECT_RADIUS = "OBJECT_RADIUS";
    public final static String OBJECT_WIDTH = "OBJECT_WIDTH";
    public final static String OBJECT_HEIGHT = "OBJECT_HEIGHT";

    public final static String SPRITE_WIDTH = "SPRITE_WIDTH";
    public final static String SPRITE_HEIGHT = "SPRITE_HEIGHT";
    public final static String RESTITUTION = "RESTITUTION";
    public final static String FRICTION = "FRICTION";
    public final static String DENSITY = "DENSITY";

    public final static String IS_SENSOR = "IS_SENSOR";
    public final static String HEALTH = "HEALTH";
    public final static String TIME_TO_LIVE = "TIME_TO_LIVE";
    public final static String IS_ANIMATED = "IS_ANIMATED";
    public final static String LOOP_ANIMATION = "LOOP_ANIMATION";
    public final static String ANIMATION_INTERVAL = "ANIMATION_INTERVAL";
    public final static String REMOVE_AFTER_ANIMATION = "REMOVE_AFTER_ANIMATION";
    /**
     * value needs to be STATE_1<->FRAME1,FRAME2<-->STATE_2<->FRAME1
     * If IS_ANIMATED is true then states frame will be switched per second
     * If IS_ANIMATED is false then first frame will be selected for every state
     */
    public final static String STATE_FRAMES = "STATE_FRAMES";
    public final static String CURRENT_STATE = "CURRENT_STATE";

    /**
     * value needs to be STATE_1<->SOUND1<-->STATE_2<->SOUND1
     * If IS_ANIMATED is true then states frame will be switched per second
     * If IS_ANIMATED is false then first frame will be selected for every state
     */
    public final static String STATE_SOUNDS = "STATE_SOUNDS";
    public final static String TEXTURE_ATLAS_NAME = "TEXTURE_ATLAS_NAME"; //to get the name of the texture file, will be set to default
    public final static String CREATION_SOUND = "CREATION_SOUND";
    public final static String DESTROY_SOUND = "DESTROY_SOUND";


    public final static String CIRCLE = "CIRCLE";
    public final static String POLYGON = "POLYGON";
    public final static String TRUE = "TRUE";
    public final static String DYNAMIC = "DYNAMIC";
    public final static String STATIC = "STATIC";

    //========================OTHER STANDARDIZATION===========================
    public final static String STATE_1 = "STATE_1";
    public final static String STATE_2 = "STATE_2";
    public final static String STATE_3 = "STATE_3";
    public final static String STATE_4 = "STATE_4";
    public final static String STATE_5 = "STATE_5";




}
