package com.pintu.futurewars.Utility;

import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public Map<String,String> stageDetailsMap = new HashMap<String, String>();
    public Map<String,String> listOfDefaultClass = null;
    Random randObjID = null;
    List<Float> positions = new ArrayList<Float>();
    public int currentPosition = 0;
    public float posToCreate = 30;

    public Map<Integer,Object> rootObjectMap = new HashMap<Integer,Object>();

    public GameObject lastPivotLocation = null;
    public float PIVOT_GAP = 12;
    public float TOP_LIMIT = 100;

    public void register(String objId,GameObjectDetails objectDetails){
        objectDetailsList.put(objId,objectDetails);
        objectList.add(objId);
        GameUtility.log(this.getClass().getName(),"Registered: " + objId);
    }

    public GameObject createObject(GameObjectDetails od,float xPos) throws Exception{

        GameObject gameObject = null;
        if(od.objectClass!=null){
            gameObject = (GameObject) od.objectClass.getConstructor().newInstance();
            gameObject.setXpos(xPos);
            gameObject.setYpos(od.yPos);
            gameObject.setFlyPos(od.flyPos);
            if(!GameUtility.isEmptyString(od.propertiesFile)){
                ((AbstractGameObject)gameObject).propFilename = od.propertiesFile;
            }
            gameObject.initialize();
            GameUtility.getGameScreen().gameObjects.add(gameObject);
            //GameUtility.log(this.getClass().getName(),"Created : " + od.objectClass + " at " + xPos + "," + od.yPos);
        }else{
            GameUtility.log(this.getClass().getName(),"No such object: " + od.objectClass);
        }
        return gameObject;
    }

    public GameObject createObjectGroup(GameObjectDetails od,float xPos) throws Exception{

        GameObject gameObject = null;
        if(od.objectClass!=null) {
            for (int i = 0; i < od.numberOfInstances; i++){
                gameObject = (GameObject) od.objectClass.getConstructor().newInstance();
                if(od.arrangeOrder.equals(GameConstants.HORIZONTAL))
                    gameObject.setXpos(xPos+=od.gapBetweenObjects);
                else
                    gameObject.setXpos(xPos);
                if(od.arrangeOrder.equals(GameConstants.VERTICAL))
                    gameObject.setYpos(od.yPos+=od.gapBetweenObjects);
                else
                    gameObject.setYpos(od.yPos);
                gameObject.setFlyPos(od.flyPos);
                if (!GameUtility.isEmptyString(od.propertiesFile)) {
                    ((AbstractGameObject) gameObject).propFilename = od.propertiesFile;
                }
                gameObject.initialize();
                GameUtility.getGameScreen().gameObjects.add(gameObject);
                //GameUtility.log(this.getClass().getName(),"Created : " + od.objectClass + " at " + xPos + "," + od.yPos);
            }
        }else{
            GameUtility.log(this.getClass().getName(),"No such object: " + od.objectClass);
        }
        return gameObject;
    }

    public void reset(){
        objIdCounter = 0;
        safeDistance =  GameConstants.DISTANCE_BETWEEN_GAME_OBJ;
    }

    public void createNextObject(Player2 player) throws Exception{

        float flyLocation = (TOP_LIMIT < (3 + player.body.getPosition().y + GameConstants.PIVOT_ROPE_LENGTH)?TOP_LIMIT:
                                                                        (3 + player.body.getPosition().y + GameConstants.PIVOT_ROPE_LENGTH));
        String pivotOD = "com.pintu.futurewars.Casts.Pivot<->PIVOT<->" + flyLocation + "<->" + flyLocation;
        if(lastPivotLocation==null){
            GameObjectDetails od = toGameObjectDetails(pivotOD);
            lastPivotLocation = createObject(od ,(player.body.getPosition().x + PIVOT_GAP/2));
        }
        //create pivot regularly
        else if(player.body.getPosition().x + player.body.getLinearVelocity().x - lastPivotLocation.getBody().getPosition().x > PIVOT_GAP/2){
            GameObjectDetails od = toGameObjectDetails(pivotOD);
            lastPivotLocation = createObject(od ,(lastPivotLocation.getBody().getPosition().x + PIVOT_GAP));
        }

        float safeDist = player.body.getPosition().x + 1*player.body.getLinearVelocity().x + GameConstants.DISTANCE_BETWEEN_GAME_OBJ;

        if(currentPosition >= positions.size() ){
            if(safeDist + 2 * GameConstants.DISTANCE_BETWEEN_GAME_OBJ < posToCreate) {
                posToCreate = safeDist + randObjID.nextInt(70) + 30;
            }

            if(safeDist > posToCreate) {
                //create random objects
                if (listOfDefaultClass == null) {
                    listOfDefaultClass = GameUtility.populateConfigurationsFromConfigFile(GameConstants.DEFAULT_STAGE_FILE);
                    randObjID = new Random();
                    posToCreate = safeDist;
                }
                int nextID = randObjID.nextInt(listOfDefaultClass.size()) + 1;

                String createDetails = listOfDefaultClass.get(nextID + "");
                GameObjectDetails god = toGameObjectDetails(createDetails);
                if(god.isGroup)
                    createObjectGroup(god,posToCreate);
                else
                    createObject(god, posToCreate);

                posToCreate = GameConstants.DISTANCE_BETWEEN_GAME_OBJ + safeDist + randObjID.nextInt((int)player.maxVelocity/2) + (int)player.maxVelocity/2;

                return;
            }
        }else {
            if (currentPosition > 0 && safeDist + 2 * GameConstants.DISTANCE_BETWEEN_GAME_OBJ < positions.get(currentPosition)) {
                currentPosition--;
                GameUtility.log("Reduced Current Position to ", currentPosition + "");
            }

            posToCreate = positions.get(currentPosition);
            if (safeDist > posToCreate) {
                GameObjectDetails god = toGameObjectDetails(creationDetails.get(posToCreate));
                if(god.isGroup)
                    createObjectGroup(god,posToCreate);
                else
                    createObject(god, posToCreate);
                currentPosition++;
                //creationDetails.remove(positions.remove(0));
            }
        }
    }


    public void populateStageMapDetails(String fileName) throws Exception{
        stageFileMap = GameUtility.populateConfigurationsFromConfigFile(fileName);

    }

    public void populateStageDescDetails(String sName) throws Exception{

        String stageFile = stageFileMap.get(sName);
        if(stageFile == null){
            stageDetailsMap.clear();
            return;
        }
        stageDetailsMap = GameUtility.populateConfigurationsFromConfigFile(stageFile+".std");
        lastPivotLocation = null;
    }

    public void populateObjectDetailsFromFile(String stageName){
        try{
            String stageFile = stageFileMap.get(stageName);
            if(stageFile == null){
                return;
            }
            Map<String,String> creationD = GameUtility.populateConfigurationsFromConfigFile(stageFile + ".txt");
            List<String> posVals =  new ArrayList<String>(creationD.keySet());
            positions.clear();
            creationDetails.clear();
            currentPosition = 0;
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
            if(details.length >= 1 && !GameUtility.isEmptyString(details[0])) {
                gd.objectClass = classLoader.loadClass(details[0]);
            }
            if(details.length >= 2 && !GameUtility.isEmptyString(details[1])) {
                gd.type = details[1];
            }
            if(details.length >= 3 && !GameUtility.isEmptyString(details[2])){
                gd.yPos = Float.parseFloat(details[2]);
            }
            if(details.length >= 4 && !GameUtility.isEmptyString(details[3])) {
                gd.flyPos = Float.parseFloat(details[3]);
            }
            if(details.length >= 5 && !GameUtility.isEmptyString(details[4])) {
                gd.propertiesFile = details[4];
            }
            if(details.length >= 6 && !GameUtility.isEmptyString(details[5])) {
                gd.isGroup = details[5].equals(GameObjectConstants.TRUE)?true:false;
            }
            if(details.length >= 7 && !GameUtility.isEmptyString(details[6])) {
                gd.numberOfInstances = Integer.parseInt(details[6]);
            }
            if(details.length >= 8 && !GameUtility.isEmptyString(details[7])) {
                gd.arrangeOrder = (!details[7].equals(""))?details[7]:GameConstants.SAME_PLACE;
            }
            if(details.length >= 9 && !GameUtility.isEmptyString(details[8])) {
                gd.gapBetweenObjects = (!details[8].equals(""))?Float.parseFloat(details[8]):0.5f;
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
