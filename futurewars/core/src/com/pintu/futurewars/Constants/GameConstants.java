package com.pintu.futurewars.Constants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameConstants {
    //setting the pixel per meter constant, to be used for scaling all the objects (in size or weight?)
    public static final float PPM = 100f;

    public static final String ATLUS_FILE = "pack/JumpingMarblesPack.atlas";
    public static final String BLAST_ATLUS_FILE = "pack/blastPack/EnemyBlast.atlas";
    public static final String NINJA_ATLUS_FILE = "pack/ninjaPack/ninja.atlas";
    public static final String STICK_ATLUS_FILE = "pack/stickPack/stick.atlas";

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
    public static final float PUSHER_DAMAGE = .1f;

    //Sucker Creator related
    public static final float SUCKER_CREATOR_RECOIL_TIME=30f;

    //----------------------
    public static final float VIEW_PORT_WIDTH = 1600;
    public static final float VIEW_PORT_HIGHT = 900;

    //------------------Widgets
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
    public static final float BASIC_BULLET_SPEED = 10f;
    public static final int BASIC_BULLET_DAMAGE = 50;
    public static final float BASIC_BULLET_RECOIL_TIME = .2f;
    public static final float BASIC_BULLET_TIME_TO_LIVE = .4f;
    public static final float BASIC_BULLET_RETITUTION= 0f;
    public static final float BASIC_BULLET_DENSITY = 10f;

    public final static String BASIC_BULLET = "BASIC_BULLET";
    public final static String BURST_BULLET = "BURST_BULLET";
    public final static String BOMB = "BOMB";

    public static final float BOMB_SIZE = 20;
    public static final float BOMB_SPEED = 20f;
    public static final int BOMB_DAMAGE = 1000;
    public static final float BOMB_RECOIL_TIME = .4f;
    public static final float BOMB_TIME_TO_LIVE = 4f;
    public static final String BOMB_REGION_NAME = "basic_bullet";

    //====================Map Related
    public static final String JUMPING_MARBAL_MAP = "tiles/JumpingMarblesMap2.tmx";
    public static final String FUTURE_WARS_MAP = "tiles/supersky.tmx";

    //======================Property Files============================
    public static final String PLAYER_PROPERTY_FILE = "propertyFiles/player.txt";
    public static final String PUSHER_PROPERTY_FILE = "propertyFiles/pusher.txt";
    public static final String KALEEN_PROPERTY_FILE = "propertyFiles/kaleen.txt";
    public static final String BRICK_PROPERTY_FILE = "propertyFiles/brick.txt";
    public static final String GROUND_PROPERTY_FILE = "propertyFiles/ground.txt";
    public static final String SPEED_BOMB_PROPERTY_FILE = "propertyFiles/speedBomb.txt";
    public static final String JUMPING_KIT_PROPERTY_FILE = "propertyFiles/jumpingKit.txt";
    public static final String POWER_DRINK_PROPERTY_FILE = "propertyFiles/powerDrink.txt";
    public static final String BASIC_BULLET_PROPERTY_FILE = "propertyFiles/armory/basicBullet.txt";
    public static final String BURST_BULLET_PROPERTY_FILE = "propertyFiles/armory/burstBullet.txt";
    public static final String BOMB_PROPERTY_FILE = "propertyFiles/armory/bomb.txt";
    public static final String BOMB_AMO_PROPERTY_FILE = "propertyFiles/bombAmo.txt";
    public static final String ENEMY_BLAST_PROPERTY_FILE = "propertyFiles/blasts/enemyBlast.txt";
    public static final String POWER_BLAST_PROPERTY_FILE = "propertyFiles/blasts/powerBlast.txt";
    public static final String COWBOY_HAT_PROPERTY_FILE = "propertyFiles/cowboyHat.txt";
    public static final String COIN_PROPERTY_FILE = "propertyFiles/coin.txt";
    public static final String STICKY_BOMB_PROPERTY_FILE = "propertyFiles/stickyBomb.txt";
    public static final String WATER_BALLOON_PROPERTY_FILE = "propertyFiles/waterBalloon.txt";

    //============================Joint Constants================================
    public static final String REVOLUTE = "revolute";
    public static final String PRISMATIC = "presmatic";
    public static final String DISTANT = "distant";
    public static final String PULLY = "pully";
    public static final String GEAR = "gear";
    public static final String WHEEL = "wheel";
    public static final String ROPE = "rope";
    public static final String WELD = "weld";

    //===================================Stages====================================
    public static final String STAGE1 = "STAGE1";




}
