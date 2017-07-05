package com.jumping.marbles;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Casts.Brick;
import com.jumping.marbles.Casts.Healer;
import com.jumping.marbles.Casts.JumpingMarblesCast;
import com.jumping.marbles.Casts.Player;
import com.jumping.marbles.Casts.Pusher;
import com.jumping.marbles.Casts.Sucker;
import com.jumping.marbles.Casts.SuckerCreator;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.Utility.MapBodyBuilder;
import com.jumping.marbles.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.codegen.MapCreator;

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
    public List<JumpingMarblesCast> casts = new ArrayList<JumpingMarblesCast>();
    public List<JumpingMarblesCast> toBeDestroyed = new ArrayList<JumpingMarblesCast>();


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
        for(MapObject object: map.getLayers().get("suckers").getObjects().getByType(EllipseMapObject.class)){
            suckers.add(new Sucker(world,object));
        }

        //Create suckerCreator
        for(MapObject object: map.getLayers().get("suckerCreator").getObjects().getByType(EllipseMapObject.class)){
            suckersCreator.add(new SuckerCreator(world,object));
        }

        //Create healer
        for(MapObject object: map.getLayers().get("healers").getObjects().getByType(EllipseMapObject.class)){
            healers.add(new Healer(world,object));
        }

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
    }

    public void createSucker(float x, float y){
        EllipseMapObject mapObject = new EllipseMapObject();
        Ellipse ellipse = new Ellipse();
        System.out.println("Creating at " + x + "," + y);
        ellipse.x = x * GameConstants.PPM;
        ellipse.y = y * GameConstants.PPM;
        ellipse.width = 1*GameConstants.PPM;
        ellipse.height = 1*GameConstants.PPM;
        mapObject.getEllipse().set(ellipse);
        Sucker sucker = new Sucker(world,mapObject);
        suckers.add(sucker);
        casts.add(sucker);
    }

    public void removeSucker(Sucker sucker){
        System.out.println("Removing from lists " + sucker.suckerId);
        suckers.remove(sucker);
        casts.remove(sucker);
        toBeDestroyed.add(sucker);
        //sucker.destroy();
    }

    public List<JumpingMarblesCast> addAllCasts(){
        casts.addAll(pushers);
        casts.addAll(suckers);
        casts.addAll(suckersCreator);
        casts.addAll(bricks);
        casts.addAll(healers);
        casts.add(player);
        return casts;
    }

    public void destroyBodies(){
        for(JumpingMarblesCast cast : toBeDestroyed){
            Utility.world.destroyBody(cast.getBody());
            //setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
            System.out.println("Destroyed ");
            cast.destroyed = true;
        }
        toBeDestroyed.clear();
    }

}
