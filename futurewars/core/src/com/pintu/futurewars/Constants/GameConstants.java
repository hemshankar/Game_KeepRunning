package com.pintu.futurewars.Constants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameConstants {
    //setting the pixel per meter constant, to be used for scaling all the objects (in size or weight?)
    public static final float PPM = 100f;

    public static final String ATLAS_FILE = "pack/JumpingMarblesPack.atlas";
    public static final String BLAST_ATLUS_FILE = "pack/blastPack/EnemyBlast.atlas";
    public static final String NINJA_ATLUS_FILE = "pack/ninjaPack/ninja2.atlas";
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
    public static final float PUSHER_DAMAGE = 1f;

    //Sucker Creator related
    public static final float SUCKER_CREATOR_RECOIL_TIME=30f;

    //----------------------
    public static final float VIEW_PORT_WIDTH = 1600;
    public static final float VIEW_PORT_HIGHT = 1000;

    //------------------Widgets_old
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
    public static final String JOINT_MAP_MONITOR = "JOINT_MAP_MONITOR";
    public static final String POWER_IMAGE = "controls/throw.png";//"controls/throwSucker.png";

    //----------------------Bullets
    public static final float BASIC_BULLET_SIZE = 8;
    public static final String BASIC_BULLET_REGION_NAME = "basic_bullet";
    public static final float BASIC_BULLET_SPEED = 1f;
    public static final float BUBBLE_BULLET_SPEED = 0.01f;
    public static final int BASIC_BULLET_DAMAGE = 50;
    public static final float BASIC_BULLET_RECOIL_TIME = .2f;
    public static final float BASIC_BULLET_TIME_TO_LIVE = .4f;
    public static final float BASIC_BULLET_RETITUTION= 0f;
    public static final float BASIC_BULLET_DENSITY = 10f;

    public final static String BASIC_BULLET = "BASIC_BULLET";
    public final static String BURST_BULLET = "BURST_BULLET";
    public final static String BOMB = "BOMB";

    public static final float BOMB_SIZE = 20;
    public static final float BOMB_SPEED = 2000f;
    public static final int BOMB_DAMAGE = 1000;
    public static final float BOMB_RECOIL_TIME = .4f;
    public static final float BOMB_TIME_TO_LIVE = 4f;
    public static final String BOMB_REGION_NAME = "basic_bullet";

    //====================Map Related
    public static final String JUMPING_MARBAL_MAP = "tiles/JumpingMarblesMap2.tmx";
    public static final String FUTURE_WARS_MAP = "tiles/supersky.tmx";
    public static final String WORLD_MAP = "tiles/worldMa.tmx";
    public static final String WORLD_MAP_RESIZE = "tiles/worldMap130x63.tmx";
    public static final String STAGES_DETAILS_MAP = "tiles/stageDetails.tmx";

    //======================Property Files============================
    public static final String PLAYER_PROPERTY_FILE = "propertyFiles/player.txt";
    public static final String KITE_PROPERTY_FILE = "propertyFiles/kite.txt";
    public static final String PUSHER_PROPERTY_FILE = "propertyFiles/pusher.txt";
    public static final String HORSE_PROPERTY_FILE = "propertyFiles/horse.txt";
    public static final String CAR_PROPERTY_FILE = "propertyFiles/car.txt";
    public static final String CAT_PROPERTY_FILE = "propertyFiles/cat.txt";
    public static final String SKATES_PROPERTY_FILE = "propertyFiles/skates.txt";
    public static final String KALEEN_PROPERTY_FILE = "propertyFiles/kaleen.txt";
    public static final String BRICK_PROPERTY_FILE = "propertyFiles/brick.txt";
    public static final String GROUND_PROPERTY_FILE = "propertyFiles/ground.txt";
    public static final String PIVOT_PROPERTY_FILE = "propertyFiles/pivot.txt";
    public static final String SPEED_BOMB_PROPERTY_FILE = "propertyFiles/speedBomb.txt";
    public static final String JUMPING_KIT_PROPERTY_FILE = "propertyFiles/jumpingKit.txt";
    public static final String MAGNET_PROPERTY_FILE = "propertyFiles/magnet.txt";
    public static final String FLYING_KIT_PROPERTY_FILE = "propertyFiles/flyingKit.txt";
    public static final String POWER_DRINK_PROPERTY_FILE = "propertyFiles/powerDrink.txt";
    public static final String RIFLE_PROPERTY_FILE = "propertyFiles/rifle.txt";
    public static final String BASIC_BULLET_PROPERTY_FILE = "propertyFiles/armory/basicBullet.txt";
    public static final String BUBBLE_BULLET_PROPERTY_FILE = "propertyFiles/armory/bubbleBullet.txt";
    public static final String BURST_BULLET_PROPERTY_FILE = "propertyFiles/armory/burstBullet.txt";
    public static final String BOMB_PROPERTY_FILE = "propertyFiles/armory/bomb.txt";
    public static final String BOMB_AMO_PROPERTY_FILE = "propertyFiles/bombAmo.txt";
    public static final String ENEMY_BLAST_PROPERTY_FILE = "propertyFiles/blasts/enemyBlast.txt";
    public static final String POWER_BLAST_PROPERTY_FILE = "propertyFiles/blasts/powerBlast.txt";
    public static final String COWBOY_HAT_PROPERTY_FILE = "propertyFiles/cowboyHat.txt";
    public static final String COIN_PROPERTY_FILE = "propertyFiles/coin.txt";
    public static final String GIFT_PROPERTY_FILE = "propertyFiles/gift.txt";
    public static final String STICKY_BOMB_PROPERTY_FILE = "propertyFiles/stickyBomb.txt";
    public static final String WATER_BALLOON_PROPERTY_FILE = "propertyFiles/waterBalloon.txt";
    public static final String BACKGROUND1_PROPERTY_FILE = "propertyFiles/background1.txt";
    public static final String BACKGROUND2_PROPERTY_FILE = "propertyFiles/background2.txt";
    public static final String BACKGROUND3_PROPERTY_FILE = "propertyFiles/background3.txt";
    public static final String BACKGROUND4_PROPERTY_FILE = "propertyFiles/background4.txt";
    public static final String BACKGROUND5_PROPERTY_FILE = "propertyFiles/background5.txt";
    public static final String DEFAULT_STAGE_FILE = "stages/defaultStage.txt";
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
    public static final String STAGE2 = "STAGE2";
    public static final String STAGE3 = "STAGE3";
    public static final String HOME_SCREEN = "HOME_SCREEN";
    public static final String GIFTS_SCREEN = "GIFTS_SCREEN";

    //===============================Various GameObjects===========================
    public static String KALEEN = "KALEEN";
    public static String PLAYER = "PLAYER";
    public static String PUSHER = "PUSHER";
    public static String KITE = "KITE";
    public static String HORSE = "HORSE";
    public static String CAT = "CAT";
    public static String SKATES = "SKATES";
    public static String SPEED_BOMB = "SPEED_BOMB";
    public static String POWER_DRINK = "POWER_DRINK";
    public static String RIFLES_DRINK = "RIFLES_DRINK";
    public static String COIN = "COIN";
    public static String BOMB_AMO = "BOMB_AMO";
    public static String COWBOY_HAT = "COWBOY_HAT";
    public static String FLYING_KIT = "FLYING_KIT";
    public static String JUMPING_KIT = "JUMPING_KIT";
    public static String MAGNET = "MAGNET";
    public static String STICKY_BOMB = "STICKY_BOMB";
    public static String WATER_BALOON = "WATER_BALOON";
    public static String BRICK = "BRICK";

    //==============================For GameObjectCreator==========================
    public static float DISTANCE_BETWEEN_GAME_OBJ =10;

    //=================================SYNC_CONSTANTS==============================
    public static final String CATCH_OBJECT = "CATCH_OBJECT";

    //==================================OTHERS=====================================
    public static final float CATCH_RANGE = 40000;
    public static final float ROPE_WIDTH = .05f;
    public static final String SAME_PLACE = "SAME_PLACE";
    public static final String HORIZONTAL = "SAME_PLACE";
    public static final String VERTICAL = "SAME_PLACE";
    public static final float MPS_TO_KPH = 3.6f;
    public static final float PIVOT_ROPE_LENGTH = 4;

    //====================================STAGE_DETAILS===========================
    public static final String STAGES_DETAILS_ANIMATION = "Animation";
    public static final String STAGES_DETAILS_TITLE = "Title";
    public static final String STAGES_DETAILS_DESCRIPTION = "Description";
    public static final String STAGES_DETAILS_IMAGE = "Image";
    public static final String STAGES_DETAILS_PLAY = "Play";
    public static final String STAGES_DETAILS_CANCEL = "Cancel";
    public static final String STAGES_DETAILS_REWARD = "Reward";
    public static final String STAGES_DETAILS_DIFFICULTY = "Difficulty";
    public static final String STAGES_DETAILS_LOCATION = "Location";


    //===================================PREFERENCES CONSTANTS====================
    public static String PERF_COIN = "COIN";
    public static String PERF_PARACHUTE = "PERF_PARACHUTE";
    public static String PERF_HORSE = "PERF_HORSE";
    public static String PERF_CAR = "PERF_CAR";
    public static String PERF_SKATE = "PERF_SKATE";
    public static String PERF_ROCKET = "PERF_ROCKET";
    public static String PERF_MAGNET = "PERF_MAGNET";
    public static String PERF_RIFLE = "PERF_RIFLE";
    public static String PERF_KITE = "PERF_KITE";
    public static String PERF_KALEEN = "PERF_KALEEN";
    public static String PERF_SPEEDBOMB = "PERF_SPEEDBOMB";
    public static String PREF_JUMPINGKIT = "PREF_JUMPINGKIT";
    public static String PREF_FLYINGKIT = "PREF_FLYINGKIT";
    public static String PREF_POWERDRINK = "PREF_POWERDRINK";
    public static String THIS_STAGE_TOTAL_COIN = "THIS_STAGE_TOTAL_COIN";
    public static String THIS_STAGE_MAX_SPEED = "THIS_STAGE_MAX_SPEED";
    public static String THIS_STAGE_TOTAL_KILLS = "THIS_STAGE_TOTAL_KILLS";
    public static String THIS_STAGE_TOTAL_GIFTS = "THIS_STAGE_TOTAL_GIFTS";
    public static String TOTAL_GIFTS = "TOTAL_GIFTS";



    //====================================SKINS=====================================
    public static String COMIC_SKIN = "skins/comic/skin/comic-ui.json";

    public static String COIN_SOUND = "audio/coin.wav";
    public static String JUMP_SOUND = "audio/jump.wav";
    public static String GIFT_SOUND = "audio/gift.mp3";
    public static String KICK_SOUND = "audio/kick.wav";
    public static String PUSHER_PUSH_SOUND = "audio/birdHit.wav";
    public static String KITE_SOUND = "audio/kite.wav";
    public static String HORSE_RUNNING_SOUND = "audio/horseRunning.wav";
    public static String HORSE_SHOUTING_SOUND = "audio/HorseShouting.mp3";
    public static String CAT_MEOW_SOUND = "audio/catMeow.wav";
    public static String PUSHER_HIT_SOUND = "audio/pusherHit.wav";
    public static String PIVOT_SOUND = "audio/pivotSound.wav";
    public static String TOING_SOUND = "audio/Toing.mp3";
    public static String FUNNY_SPRING_SOUND = "audio/Funny Spring.mp3";
    public static String BOMB_TIMER_SOUND = "audio/BombTimer.mp3";
    public static String GUN_FIRE_SOUND = "audio/gunFire.mp3";
    public static String POWER_DRINK_SOUND = "audio/mario.mp3";
    public static String COW_BOY_SOUND = "audio/cowBoy.mp3";
    public static String TADAA_SOUND = "audio/Tadaa.mp3";
    public static String KALEEN_SOUND = "audio/kaleen.mp3";
    public static String SPEED_BOMB_SOUND = "audio/speedBomb.mp3";
    public static String MAGNET_SOUND = "audio/magnetSound.mp3";

}

