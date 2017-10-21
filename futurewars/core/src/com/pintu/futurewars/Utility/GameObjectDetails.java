package com.pintu.futurewars.Utility;

import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 9/23/2017.
 */

public class GameObjectDetails {

    public Class objectClass;
    public float yPos = 7;
    public float flyPos = 8;
    public String type;
    public String propertiesFile = "";
    public boolean isGroup = false;
    public int numberOfInstances = 1;
    public String arrangeOrder = GameConstants.SAME_PLACE;
    public float gapBetweenObjects = .5f;

}
