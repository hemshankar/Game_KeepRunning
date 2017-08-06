package com.jumping.marbles.Constants;

/**
 * Created by hsahu on 7/2/2017.
 */

public class GameConstants {
    //setting the pixel per meter constant, to be used for scaling all the objects (in size or weight?)
    public static final float PPM = 100f;

    public static final String ATLUS_FILE = "pack/JumpingMarblesPack.atlas";

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
    public static final float VIEW_PORT_HIGHT = 1200;

    //------------------Controller
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int THROW_SUCKER = 4;

    public static final String LEFT_IMAGE = "controls/left.png";
    public static final String RIGHT_IMAGE = "controls/right.png";
    public static final String UP_IMAGE = "controls/up.png";
    public static final String DOWN_IMAGE = "controls/down.png";

    public static final String THROW_SUCKER_IMAGE = "controls/throw.png";//"controls/throwSucker.png";
}
