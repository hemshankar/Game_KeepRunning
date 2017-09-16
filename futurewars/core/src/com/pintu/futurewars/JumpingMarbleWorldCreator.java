package com.pintu.futurewars;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Brick;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.Utility.MapBodyBuilder;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hsahu on 7/2/2017.
 */

public class JumpingMarbleWorldCreator {

    public Player2 player;
    public World world;
    public Set<GameObject> gameObjects = new HashSet<GameObject>();
    public Set<GameObject> toBeDestroyed = new HashSet<GameObject>();

    public List<Joint> joints = new ArrayList<Joint>();

    public float boundaryLeft = 0;
    public float boundaryRight = 9993936;
    public float boundaryTop = 1888;
    public float boundaryBottom=392;

    public JumpingMarbleWorldCreator(World world, Map map){

        synchronized ("DESTROYING_BODY") {
            //initiate all arrays
            this.world = world;

            //Create pushers
            int i = 0;
            /*Pusher p;
            for (MapObject object : map.getLayers().get("pushers").getObjects().getByType(EllipseMapObject.class)) {
                p=new Pusher(2210 + i++, world, GameUtility.getAtlas(), object);
                p.initialize();
                gameObjects.add(p);
                p.body.setUserData(p);
            }*/

            //Create bricks
           /* Brick b;
            for (MapObject object : map.getLayers().get("bricks").getObjects().getByType(RectangleMapObject.class)) {
                b=new Brick(2110 + i++, world, GameUtility.getAtlas(), object);
                b.initialize();
                gameObjects.add(b);
                b.body.setUserData(b);
            }*/

            /*Ground g;
            for (MapObject object : map.getLayers().get("groundSensor").getObjects().getByType(RectangleMapObject.class)) {
                g=new Ground(210 + i++, world, GameUtility.getAtlas(), object);
                g.initialize();
                gameObjects.add(g);
                g.body.setUserData(g);
            }*/

            //create player
            /*MapObject object = map.getLayers().get("player").getObjects().getByType(EllipseMapObject.class).get(0);
            player = new Player2(222, world, GameUtility.stickAtlas, object);
            player.initialize();
            gameObjects.add(player);
            player.body.setUserData(player);*/

            //create obstacles
            //MapBodyBuilder.buildShapes(map, GameConstants.PPM, world, "staticObjects");

            //Get boundaries
            /*MapObject left = map.getLayers().get("leftBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryLeft = ((RectangleMapObject) left).getRectangle().getX();
            MapObject right = map.getLayers().get("rightBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryRight = ((RectangleMapObject) right).getRectangle().getX();
            MapObject top = map.getLayers().get("topBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryTop = ((RectangleMapObject) top).getRectangle().getY();
            MapObject bottom = map.getLayers().get("bottomBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryBottom = ((RectangleMapObject) bottom).getRectangle().getY();*/

            System.out.println("Left : " + boundaryLeft + ", "
            + "Right : " + boundaryRight + ", "
            + "Top : " + boundaryTop + ", "
            + "Bottom : " + boundaryBottom + ", ");

        }
    }

    public void removeBodies(){
        synchronized ("DESTROYING_BODY") {
            for (GameObject obj : gameObjects) {
                if(obj.toBeDestroyed()) {
                    toBeDestroyed.add(obj);
                }
            }
        }
    }

    public void destroyBodies(){
        try {
            synchronized ("DESTROYING_BODY") {
                for (GameObject cast : toBeDestroyed) {
                    gameObjects.remove(cast);
                    cast.destroy();
                }
                toBeDestroyed.clear();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addJointsToRemove(List<Joint> joints){
        this.joints.addAll(joints);
    }

    public void removeJoints(){
        for(Joint j : joints){
            //System.out.println("Destroying joint");
            world.destroyJoint(j);
        }
        joints.clear();
    }
}
