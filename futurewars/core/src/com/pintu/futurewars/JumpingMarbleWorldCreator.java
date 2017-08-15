package com.pintu.futurewars;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Brick;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.MapBodyBuilder;
import com.pintu.futurewars.Utility.Utility;
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

    public float boundaryLeft;
    public float boundaryRight;
    public float boundaryTop;
    public float boundaryBottom;

    public JumpingMarbleWorldCreator(World world, Map map){

        synchronized ("DESTROYING_BODY") {
            //initiate all arrays
            this.world = world;

            //Create pushers
            int i = 0;
            for (MapObject object : map.getLayers().get("pushers").getObjects().getByType(EllipseMapObject.class)) {
                gameObjects.add(new Pusher(2210 + i++, null, world, Utility.getAtlas(), object));
            }

            //Create bricks
            for (MapObject object : map.getLayers().get("bricks").getObjects().getByType(RectangleMapObject.class)) {
                gameObjects.add(new Brick(2110 + i++, null, world, Utility.getAtlas(), object));
            }

            //create player
            MapObject object = map.getLayers().get("player").getObjects().getByType(EllipseMapObject.class).get(0);
            player = new Player2(222, null, world, Utility.getAtlas(), object);
            gameObjects.add(player);

            //create obstacles
            MapBodyBuilder.buildShapes(map, GameConstants.PPM, world, "staticObjects");

            //Get boundaries
            MapObject left = map.getLayers().get("leftBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryLeft = ((RectangleMapObject) left).getRectangle().getX();
            MapObject right = map.getLayers().get("rightBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryRight = ((RectangleMapObject) right).getRectangle().getX();
            MapObject top = map.getLayers().get("topBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryTop = ((RectangleMapObject) top).getRectangle().getY();
            MapObject bottom = map.getLayers().get("bottomBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
            boundaryBottom = ((RectangleMapObject) bottom).getRectangle().getY();
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
            System.out.println("Destroying joint");
            world.destroyJoint(j);
        }
        joints.clear();
    }
}
