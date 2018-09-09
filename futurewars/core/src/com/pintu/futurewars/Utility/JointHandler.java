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

    public Joint createRevoluteJoint(JointDetails jd){
        RevoluteJointDef jDef = new RevoluteJointDef();
        jDef.bodyA = jd.a.getBody();
        jDef.bodyB = jd.b.getBody();
        jDef.collideConnected = true;
        jDef.localAnchorA.set(50/GameConstants.PPM,0);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        return jd.world.createJoint(jDef);
    }
    public Joint createWeldJoint(JointDetails jd){
        WeldJointDef jDef = new WeldJointDef();
        jDef.bodyA = jd.a.getBody();
        jDef.bodyB = jd.b.getBody();
        jDef.collideConnected = false;
        //jDef.dampingRatio = 100f;
        //jDef.localAnchorA.set(100/GameConstants.PPM,0);
        //GameUtility.log("==============" + jd.a.getSprite().getWidth()," " + -(jd.a.getSprite().getHeight()));
        jDef.localAnchorA.set(0/*jd.a.getSprite().getWidth()+1*/,-jd.length);//10,a.getSprite().getHeight()*-1);// 100/GameConstants.PPM);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        return jd.world.createJoint(jDef);
    }
    public Joint createPrismaticJoint(JointDetails jd){
        return null;
    }
    public Joint createDistantJoint(JointDetails jd){
        return null;
    }
    public Joint createPullyJoint(JointDetails jd){
        return null;
    }
    public Joint createGearJoint(JointDetails jd){
        return null;
    }
    public Joint createWheelJoint(JointDetails jd){
        return null;
    }
    public Joint createRopeJoint(JointDetails jd){
        RopeJointDef jDef = new RopeJointDef();
        jDef.bodyA = jd.a.getBody();
        jDef.bodyB = jd.b.getBody();
        jDef.collideConnected = true;
        jDef.maxLength = jd.length;
        //jDef.localAnchorA.set(100/GameConstants.PPM,0);
        jDef.localAnchorA.set(0,10/GameConstants.PPM);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        RopeJoint r = (RopeJoint)jd.world.createJoint(jDef);
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
            jointCreateList.add(new JointDetails(aGameObject,bGameObject,w,t,0));
        }
    }

    public void createJoint(GameObject aGameObject,
                            GameObject bGameObject,
                            World w,
                            String t,
                            float length){
        synchronized(JOINT_CREATE_REMOVE_SYNC_CONST) {
            jointCreateList.add(new JointDetails(aGameObject,bGameObject,w,t,length));
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
        float length=0;

        public JointDetails(Joint j,World w){
            joint = j;
            world = w;
        }

        public JointDetails(GameObject aBody,GameObject bBody, World w, String t,float len){
            a = aBody;
            b = bBody;
            world = w;
            type = t;
            length = len;
        }

        public void createJoint(){
            synchronized (GameConstants.JOINT_MAP_MONITOR) {
                Player2 p = getPlayer(a, b);
                GameObject g = getOtherGameObject(a, b);
                if (type.equalsIgnoreCase(GameConstants.REVOLUTE)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.DISTANT)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.ROPE)) {
                    p.jointMap.put(g, createRopeJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.PULLY)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.GEAR)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.WHEEL)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.PRISMATIC)) {
                    p.jointMap.put(g, createRevoluteJoint(this));
                } else if (type.equalsIgnoreCase(GameConstants.WELD)) {
                    p.jointMap.put(g, createWeldJoint(this));
                }
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
