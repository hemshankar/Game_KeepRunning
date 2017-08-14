package com.pintu.futurewars.Constants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameConstants {
    //setting the pixel per meter constant, to be used for scaling all the objects (in size or weight?)
    public static final float PPM = 100f;

    public static final String ATLUS_FILE = "pack/JumpingMarblesPack.atlas";
    public static final String BLAST_ATLUS_FILE = "pack/blastPack/EnemyBlast.atlas";

    //cast names
    public static final String PLAYER_ATLAS_NAME = "player";
    public static final String HEALER_ATLAS_NAME = "Healer";
    public static final String PUSHER_ATLAS_NAME = "pusher";
    public static final String SUCKER_ATLAS_NAME = "sucker";
    public static final String SUCKER_CREATOR_ATLAS_NAME = "suckerCreator1";
    public static final String BRICK_ATLAS_NAME = "brick";

    //cast Dimensions
    public static final float DESIRED_SIZE = 35;
    public static final float SIZE_SCALE =DESIRED_SIZE;
    public static final float PLAYER_SIZE = DESIRED_SIZE;
    public static final float HEALER_SIZE = DESIRED_SIZE;
    public static final float PUSHER_SIZE = DESIRED_SIZE;
    public static final float SUCKER_SIZE = DESIRED_SIZE;
    public static final float SUCKER_CREATOR_SIZE = DESIRED_SIZE;
    public static final float BRICK_SIZE =45;

    //Sucker Creator related
    public static final float SUCKER_CREATOR_RECOIL_TIME=30f;

    //----------------------
    public static final float VIEW_PORT_WIDTH = 1600;
    public static final float VIEW_PORT_HIGHT = 900;

    //------------------Controller
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int FIRE_BASIC_BULLET = 4;
    public static final int CIRCLE_CONTROLLER = 5;

    public static final String LEFT_IMAGE = "controls/left.png";
    public static final String RIGHT_IMAGE = "controls/right.png";
    public static final String UP_IMAGE = "controls/up.png";
    public static final String DOWN_IMAGE = "controls/down.png";
    public static final String CIRCLE_IMAGE = "controls/circle.png";
    public static final float CONTROL_BUTTON_SIZE = 130;
    public static final float FIRE_BUTTON_SIZE = 200;
    public static final float CIRCLE_BUTTON_SIZE = 300;

    public static final String POWER_IMAGE = "controls/throw.png";//"controls/throwSucker.png";

    //----------------------Bullets
    public static final float BASIC_BULLET_SIZE = 8;
    public static final String BASIC_BULLET_REGION_NAME = "basic_bullet";
    public static final float BASIC_BULLET_SPEED = 1f;
    public static final int BASIC_BULLET_DAMAGE = 50;
    public static final float BASIC_BULLET_RECOIL_TIME = .2f;
    public static final float BASIC_BULLET_TIME_TO_LIVE = 1f;

    public final static String BASIC_BULLET = "BASIC_BULLET";
    public final static String BURST_BULLET = "BURST_BULLET";
    public final static String BOMB = "BOMB";

    public static final float BOMB_SIZE = 20;
    public static final float BOMB_SPEED = 3f;
    public static final int BOMB_DAMAGE = 1000;
    public static final float BOMB_RECOIL_TIME = .4f;
    public static final float BOMB_TIME_TO_LIVE = 4f;
    public static final String BOMB_REGION_NAME = "basic_bullet";

    //====================Map Related
    public static final String JUMPING_MARBAL_MAP = "tiles/JumpingMarblesMap2.tmx";
    public static final String FUTURE_WARS_MAP = "tiles/supersky.tmx";

}
