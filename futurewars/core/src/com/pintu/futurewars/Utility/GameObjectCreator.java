package com.pintu.futurewars.Utility;

import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.Collections;
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

    public Map<Float,String> creationDetails =  new HashMap<Float, String>();;
    public Map<String,String> stageFileMap = null;
    List<Float> positions = new ArrayList<Float>();
    public int currentPosition = 0;

    public Map<Integer,Object> rootObjectMap = new HashMap<Integer,Object>();

    public void register(String objId,GameObjectDetails objectDetails){
        objectDetailsList.put(objId,objectDetails);
        objectList.add(objId);
        GameUtility.log(this.getClass().getName(),"Registered: " + objId);
    }

    public GameObject createObject(String objectID,float xPos) throws Exception{
        GameObjectDetails details = objectDetailsList.get(objectID);
        return createObject(objectID,xPos,details.yPos,details.flyPos);
    }/*
    public GameObject createObject(GameObjectDetails od,float xPos) throws Exception{
        return createObject(od,xPos);
    }*/
    public GameObject createObject(GameObjectDetails od,float xPos) throws Exception{

        GameObject gameObject = null;
        if(od.objectClass!=null){
            gameObject = (GameObject) od.objectClass.getConstructor().newInstance();
            gameObject.setXpos(xPos);
            gameObject.setYpos(od.yPos);
            gameObject.setFlyPos(od.flyPos);
            gameObject.initialize();
            GameUtility.getGameScreen().gameObjects.add(gameObject);
            //GameUtility.log(this.getClass().getName(),"Created : " + od.objectClass + " at " + xPos + "," + od.yPos);
        }else{
            GameUtility.log(this.getClass().getName(),"No such object: " + od.objectClass);
        }
        return gameObject;
    }

    public GameObject createObject(String objectID,float xPos, float yPos, float flyPosition) throws Exception{
        GameObjectDetails details = objectDetailsList.get(objectID);
        GameObject gameObject = null;
        if(details!=null){
            gameObject = (GameObject) details.objectClass.getConstructor().newInstance();
            gameObject.setXpos(xPos);
            gameObject.setYpos(yPos);
            gameObject.setFlyPos(flyPosition);
            gameObject.initialize();
            GameUtility.getGameScreen().gameObjects.add(gameObject);
            //GameUtility.log(this.getClass().getName(),"Created : " + objectID + " at " + xPos + "," + yPos);
        }else{
            GameUtility.log(this.getClass().getName(),"No such object: " + objectID);
        }
        return gameObject;
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

    public void createNextObjectOld(Player2 player) throws Exception{
        if(positions.isEmpty())
            return;
        float safeDist = player.body.getPosition().x + 1*player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;

        if(safeDist>positions.get(0)){

            createObject(toGameObjectDetails(creationDetails.get(positions.get(0))),positions.get(0));

            creationDetails.remove(positions.remove(0));
        }
    }

    public void createNextObject(Player2 player) throws Exception{
        if(currentPosition >= positions.size())
            return;
        float safeDist = player.body.getPosition().x + 1*player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
        if(currentPosition > 0 && safeDist + 2*GameConstants.DISTANCE_BETWEEN_GAME_OBJ < positions.get(currentPosition)){
            currentPosition--;
            GameUtility.log("Reduced Current Position to ",currentPosition + "");
        }
        float posToCreate = positions.get(currentPosition);
        if(safeDist>posToCreate){
            createObject(toGameObjectDetails(creationDetails.get(posToCreate)),posToCreate);
            currentPosition++;
            //creationDetails.remove(positions.remove(0));
        }
    }


    public void populateStageMapDetails(String fileName) throws Exception{
        stageFileMap = GameUtility.populateConfigurationsFromConfigFile(fileName);
    }

    public void populateObjectDetailsFromFile(String stageName){
        try{
            Map<String,String> creationD = GameUtility.populateConfigurationsFromConfigFile(stageFileMap.get(stageName));
            List<String> posVals =  new ArrayList<String>(creationD.keySet());
            positions.clear();
            for(String key: posVals){
                Float keyF = Float.parseFloat(key);
                positions.add(keyF);
                if(creationDetails.get(keyF)==null) {
                    creationDetails.put(keyF, creationD.get(key));
                }else{
                    positions.remove(positions.size()-1);
                }
            }
            Collections.sort(positions);
            GameUtility.log(this.getClass().getName(),positions.toString());
            GameUtility.log(this.getClass().getName(),rootObjectMap.toString());
        }
        catch (Exception e){e.printStackTrace();}
    }

    public GameObjectDetails toGameObjectDetails(String objDetails){

        GameObjectDetails gd = new GameObjectDetails();

        //Format of the GameObjectDetails
        //distance<-->classname<->[type]<->[yPos]<->[flyPos]

        ClassLoader classLoader = JumpingMarblesGame.class.getClassLoader();

        try {
            String []details = objDetails.split("<->");
            gd.objectClass = classLoader.loadClass(details[0]);
            gd.type = details[1];
            if(!GameUtility.isEmptyString(details[2])){
                gd.yPos = Float.parseFloat(details[2]);
            }
            if(!GameUtility.isEmptyString(details[3])) {
                gd.flyPos = Float.parseFloat(details[3]);
            }
            //GameUtility.log(this.getClass().getName(),"aClass.getName() = " + gd.objectClass.getName());
        } catch (ClassNotFoundException e) {
            GameUtility.log(this.getClass().getName(),e.toString());
        }
        return gd;
    }


    //==========================================Map Processing========================================
    // branchFactor factor by which the tree will be branched
    // 2 - gives a binary tree,
    // 10 - each node can have 10 children
    Integer branchFactor = 10;

    /**
     * More the branchFactor lesser the depth.
     * @param distance
     */
    /*public void insertInMap(Integer distance){
        Integer dist = distance;
        Map<Integer,Object> root = rootObjectMap;
        Map<Integer,Object> child = null;
        while(dist>0){
            Integer pos = dist%branchFactor;

            child = ( Map<Integer,Object>)root.get(pos);
            if(child==null){
                child = new HashMap<Integer,Object>();
                root.put(pos,child);
            }

            root = child;
            dist = dist/branchFactor;
        }

        root.put(distance,null);
    }

    public void insertInMap(Integer distance){
        Integer dist = 1;// distance.length();
        String sDistance = distance.toString();
        Map<Integer,Object> root = rootObjectMap;
        Map<Integer,Object> child = null;
        while(dist<sDistance.length()+1){
            Integer pos = Integer.parseInt(sDistance.substring(0,dist));

            child = (Map<Integer,Object>)root.get(pos);
            if(child==null){
                child = new HashMap<Integer,Object>();
                root.put(pos,child);
            }

            root = child;
            dist++;
        }

        root.put(distance,null);
    }

    public Integer getNextHigherDistance(Integer distance){
        Integer dist = 1;// distance.length();
        String sDistance = distance.toString();
        Map<Integer,Object> root = rootObjectMap;
        Map<Integer,Object> child = null;
        while(dist<sDistance.length()+1){
            Integer pos = Integer.parseInt(sDistance.substring(0,dist));

            while(child==null && pos < 10){
                child = (Map<Integer,Object>)root.get(pos++);
            }
            if(child==null)
                break;

            root = child;
            dist++;
        }
        if(root.equals(rootObjectMap))
            return null;
        List<Integer> l = new ArrayList<Integer>(root.keySet());
        return l.get(0);
    }*/

    //================================================================================================
}
