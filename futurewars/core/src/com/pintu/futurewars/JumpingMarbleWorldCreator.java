package com.pintu.futurewars;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Brick;
import com.pintu.futurewars.Casts.Healer;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Casts.Sucker;
import com.pintu.futurewars.Casts.SuckerCreator;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.MapBodyBuilder;
import com.pintu.futurewars.Utility.Utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hsahu on 7/2/2017.
 */

public class JumpingMarbleWorldCreator {

    public List<Pusher> pushers;
    public List<Sucker> suckers;
    public List<SuckerCreator> suckersCreator;
    public List<Healer> healers;
    public List<Brick> bricks;
    public Player player;
    public World world;
    public List<FutureWarsCast> casts = new ArrayList<FutureWarsCast>();
    public Set<FutureWarsCast> toBeDestroyed = new HashSet<FutureWarsCast>();
    public List<Joint> joints = new ArrayList<Joint>();
    public float boundaryLeft;
    public float boundaryRight;
    public float boundaryTop;
    public float boundaryBottom;

    public JumpingMarbleWorldCreator(World world, Map map){

        //initiate all arrays
        this.world = world;
        pushers = new ArrayList<Pusher>();
        suckers = new ArrayList<Sucker>();
        suckersCreator = new ArrayList<SuckerCreator>();
        healers = new ArrayList<Healer>();
        bricks = new ArrayList<Brick>();

        //Create pushers

        for(MapObject object: map.getLayers().get("pushers").getObjects().getByType(EllipseMapObject.class)){
            pushers.add(new Pusher(world,object));
        }

        //Create suckers
       /* for(MapObject object: map.getLayers().get("suckers").getObjects().getByType(EllipseMapObject.class)){
            suckers.add(new Sucker(world,object));
        }
*/
        //Create suckerCreator
       /* for(MapObject object: map.getLayers().get("suckerCreator").getObjects().getByType(EllipseMapObject.class)){
            suckersCreator.add(new SuckerCreator(world,object));
        }*/

        //Create healer
       /* for(MapObject object: map.getLayers().get("healers").getObjects().getByType(EllipseMapObject.class)){
            healers.add(new Healer(world,object));
        }*/

        //Create bricks
        for(MapObject object: map.getLayers().get("bricks").getObjects().getByType(RectangleMapObject.class)){
            bricks.add(new Brick(world,object));
        }

        //create player
        MapObject object = map.getLayers().get("player").getObjects().getByType(EllipseMapObject.class).get(0);
        player = new Player(world,object);

        //create obstacles
        MapBodyBuilder.buildShapes(map, GameConstants.PPM,world,"staticObjects");
        addAllCasts();

        //Get boundaries
        MapObject left = map.getLayers().get("leftBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
        boundaryLeft = ((RectangleMapObject)left).getRectangle().getX();
        MapObject right = map.getLayers().get("rightBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
        boundaryRight = ((RectangleMapObject)right).getRectangle().getX();
        MapObject top = map.getLayers().get("topBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
        boundaryTop = ((RectangleMapObject)top).getRectangle().getY();
        MapObject bottom = map.getLayers().get("bottomBoundary").getObjects().getByType(RectangleMapObject.class).get(0);
        boundaryBottom = ((RectangleMapObject)bottom).getRectangle().getY();
    }

    public void createSucker(float x, float y){
        EllipseMapObject mapObject = new EllipseMapObject();
        Ellipse ellipse = new Ellipse();
        System.out.println("Creating at " + x + "," + y);
        ellipse.x = x * GameConstants.PPM;
        ellipse.y = y * GameConstants.PPM;
        ellipse.width = 0.1f*GameConstants.PPM;
        ellipse.height = 0.1f*GameConstants.PPM;
        mapObject.getEllipse().set(ellipse);
        Sucker sucker = new Sucker(world,mapObject);
        suckers.add(sucker);
        casts.add(sucker);
    }

   /* public void removeSucker(Sucker sucker){
        System.out.println("Removing from lists " + sucker.suckerId);
        suckers.remove(sucker);
        casts.remove(sucker);
        toBeDestroyed.add(sucker);
        //sucker.destroy();
    }
*/
    public void removeBody(FutureWarsCast cast){
        synchronized (toBeDestroyed) {
            System.out.println("Removing from lists " + cast);
            if (cast instanceof Sucker)
                suckers.remove(cast);
            if (cast instanceof Pusher)
                pushers.remove(cast);
            if (cast instanceof SuckerCreator)
                suckersCreator.remove(cast);
            if (cast instanceof Brick)
                bricks.remove(cast);
            if (cast instanceof Healer)
                healers.remove(cast);

            casts.remove(cast);
            toBeDestroyed.add(cast);
            //sucker.destroy();
        }
    }


    public List<FutureWarsCast> addAllCasts(){
        casts.addAll(pushers);
        casts.addAll(suckers);
        casts.addAll(suckersCreator);
        casts.addAll(bricks);
        casts.addAll(healers);
        casts.add(player);
        return casts;
    }

    public void destroyBodies(){
        try {
            synchronized ("EDITING_BODY_LIST") {
                for (FutureWarsCast cast : toBeDestroyed) {
                    Utility.world.destroyBody(cast.getBody());
                    //setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
                    System.out.println("Destroyed ");
                    cast.destroyed = true;
                }
            }
            toBeDestroyed.clear();
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
