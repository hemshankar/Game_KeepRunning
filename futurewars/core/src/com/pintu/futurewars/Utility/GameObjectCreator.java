package com.pintu.futurewars.Utility;

import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hsahu on 9/23/2017.
 */

public class GameObjectCreator {

    public float safeDistance = GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
    public int objIdCounter = 0; //rotats from 0 - objectDetailsList.size();

    public Map<String,GameObjectDetails> objectDetailsList = new HashMap<String, GameObjectDetails>();
    public List<String> objectList = new ArrayList<String>();

    public void register(String objId,GameObjectDetails objectDetails){
        objectDetailsList.put(objId,objectDetails);
        objectList.add(objId);
        GameUtility.log(this.getClass().getName(),"Registered: " + objId);
    }

    public void createObject(String objectID,float xPos) throws Exception{
        GameObjectDetails details = objectDetailsList.get(objectID);
        createObject(objectID,xPos,details.yPos,details.flyPos);
    }

    public void createObject(String objectID,float xPos, float yPos, float flyPosition) throws Exception{
        GameObjectDetails details = objectDetailsList.get(objectID);

        if(details!=null){
            GameObject gameObject = (GameObject) details.objectClass.getConstructor().newInstance();
            gameObject.setXpos(xPos);
            gameObject.setYpos(yPos);
            gameObject.setFlyPos(flyPosition);
            gameObject.initialize();
            GameUtility.getGameScreen().gameObjects.add(gameObject);
            GameUtility.log(this.getClass().getName(),"Created : " + objectID + " at " + xPos + "," + yPos);
        }else{
            GameUtility.log(this.getClass().getName(),"No such object: " + objectID);
        }
    }

    public void createNextObj(Player2 player) throws Exception{
        // if safeDistance is too far away, update the safe distance, objects farther from the safe distance will get automatically distorued
        if(player.body.getPosition().x + 1*player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ < safeDistance) {
            safeDistance = player.body.getPosition().x + 1 * player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
        }
        //if the player has crossed the safe distance update it, and create a game object at the next safeDistance
        if(player.body.getPosition().x >= safeDistance) {
            safeDistance = player.body.getPosition().x + 1 * player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
            createObject(objectList.get(objIdCounter++),safeDistance);
            objIdCounter = objIdCounter%objectList.size();
        }

            /*createObject(objectList.get(objIdCounter++),nextGameObjCreationPos);
            System.out.println("Distance: " + (GameConstants.DISTANCE_BETWEEN_GAME_OBJ + + 2*player.body.getLinearVelocity().x));
            nextGameObjCreationPos += (GameConstants.DISTANCE_BETWEEN_GAME_OBJ + 2*player.body.getLinearVelocity().x);
            objIdCounter = objIdCounter%objectList.size();
        }else if(GameConstants.DISTANCE_BETWEEN_GAME_OBJ/10 > 10 + player.body.getLinearVelocity().x){
            //if the player is not able to cover the minimal distance in half second, we need to improvise
            // the distance in whcih new game object will be created.
            nextGameObjCreationPos = player.body.getPosition().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
        }*/
    }

    public void reset(){
        objIdCounter = 0;
        safeDistance =  GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
    }
}
