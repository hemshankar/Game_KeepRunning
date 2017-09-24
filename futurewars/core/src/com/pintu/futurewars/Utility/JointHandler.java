package com.pintu.futurewars.Utility;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 9/1/2017.
 */

public class JointHandler {

    public static final String JOINT_CREATE_REMOVE_SYNC_CONST = "JOINT_CREATE_REMOVE_SYNC_CONST";


    public List<JointDetails> jointCreateList = new ArrayList<JointDetails>();
    public List<JointDetails> removeJointList = new ArrayList<JointDetails>();

    public Joint createRevoluteJoint(GameObject a, GameObject b, World w){
        RevoluteJointDef jDef = new RevoluteJointDef();
        jDef.bodyA = a.getBody();
        jDef.bodyB = b.getBody();
        jDef.collideConnected = true;
        jDef.localAnchorA.set(50/GameConstants.PPM,0);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        return w.createJoint(jDef);
    }
    public Joint createWeldJoint(GameObject a, GameObject b, World w){
        WeldJointDef jDef = new WeldJointDef();
        jDef.bodyA = a.getBody();
        jDef.bodyB = b.getBody();
        jDef.collideConnected = true;
        //jDef.dampingRatio = 100f;
        //jDef.localAnchorA.set(100/GameConstants.PPM,0);
        GameUtility.log("==============" + a.getSprite().getWidth()," " + -(a.getSprite().getHeight()));
        jDef.localAnchorA.set(a.getSprite().getWidth(),-(a.getSprite().getHeight()));//10,a.getSprite().getHeight()*-1);// 100/GameConstants.PPM);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        return w.createJoint(jDef);
    }
    public Joint createPrismaticJoint(GameObject a, GameObject b, World w){
        return null;
    }
    public Joint createDistantJoint(GameObject a, GameObject b, World w){
        return null;
    }
    public Joint createPullyJoint(GameObject a, GameObject b, World w){
        return null;
    }
    public Joint createGearJoint(GameObject a, GameObject b, World w){
        return null;
    }
    public Joint createWheelJoint(GameObject a, GameObject b, World w){
        return null;
    }
    public Joint createRopeJoint(GameObject a, GameObject b, World w){
        RopeJointDef jDef = new RopeJointDef();
        jDef.bodyA = a.getBody();
        jDef.bodyB = b.getBody();
        jDef.collideConnected = true;

        //jDef.localAnchorA.set(100/GameConstants.PPM,0);
        jDef.localAnchorA.set(0,10/GameConstants.PPM);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        RopeJoint r = (RopeJoint)w.createJoint(jDef);
        //r.setMaxLength(0);
        return r;
    }

    public void createAllJoints(){
        synchronized(JOINT_CREATE_REMOVE_SYNC_CONST) {
            for (JointDetails jd : jointCreateList) {
                jd.createJoint();
            }
            jointCreateList.clear();
        }
    }

    public void removeAllJoints(){
        synchronized(JOINT_CREATE_REMOVE_SYNC_CONST) {
            for (JointDetails jd : removeJointList) {
                jd.removeJoint();
            }
            removeJointList.clear();
        }
    }

    public void createJoint(GameObject aGameObject,GameObject bGameObject, World w, String t){
        synchronized(JOINT_CREATE_REMOVE_SYNC_CONST) {
            jointCreateList.add(new JointDetails(aGameObject,bGameObject,w,t));
        }
    }

    public void removeJoint(Joint j,World w){
        synchronized(JOINT_CREATE_REMOVE_SYNC_CONST) {
            removeJointList.add(new JointDetails(j,w));
        }
    }

    public class JointDetails{
        GameObject a;
        GameObject b;
        World world;
        String type;
        Joint joint;

        public JointDetails(Joint j,World w){
            joint = j;
            world = w;
        }

        public JointDetails(GameObject aBody,GameObject bBody, World w, String t){
            a = aBody;
            b = bBody;
            world = w;
            type = t;
        }

        public void createJoint(){
            Player2 p = getPlayer(a,b);
            GameObject g = getOtherGameObject(a,b);
            if(type.equalsIgnoreCase(GameConstants.REVOLUTE)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.DISTANT)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.ROPE)){
                p.jointMap.put(g,createRopeJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.PULLY)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.GEAR)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.WHEEL)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.PRISMATIC)){
                p.jointMap.put(g,createRevoluteJoint(a,b,world));
            }else if(type.equalsIgnoreCase(GameConstants.WELD)){
                p.jointMap.put(g,createWeldJoint(a,b,world));
            }
        }

        public void removeJoint(){
            world.destroyJoint(joint);
        }
    }

    private Player2 getPlayer(GameObject a, GameObject b){
        return (Player2) (a instanceof Player2?a:b);
    }
    private GameObject getOtherGameObject(GameObject a, GameObject b){
        return a instanceof Player2?b:a;
    }

}
