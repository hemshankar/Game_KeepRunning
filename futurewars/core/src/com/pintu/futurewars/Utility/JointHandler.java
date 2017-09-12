package com.pintu.futurewars.Utility;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.pintu.futurewars.Constants.GameConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 9/1/2017.
 */

public class JointHandler {

    public static final String JOINT_CREATE_SYNC_CONST = "JOINT_CREATE_SYNC_CONST";

    public List<JointDetails> jointCreateList = new ArrayList<JointDetails>();

    public void createRevoluteJoint(Body a, Body b, World w){
        RevoluteJointDef jDef = new RevoluteJointDef();
        jDef.bodyA = a;
        jDef.bodyB = b;
        jDef.collideConnected = true;
        jDef.localAnchorA.set(50/GameConstants.PPM,0);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        w.createJoint(jDef);
    }
    public void createWeldJoint(Body a, Body b, World w){
        WeldJointDef jDef = new WeldJointDef();
        jDef.bodyA = a;
        jDef.bodyB = b;
        jDef.collideConnected = true;
        //jDef.localAnchorA.set(50/GameConstants.PPM,0);
        //jDef.localAnchorB.set(300/GameConstants.PPM,300/GameConstants.PPM);
        w.createJoint(jDef);
    }
    public void createPrismaticJoint(Body a, Body b, World w){

    }
    public void createDistantJoint(Body a, Body b, World w){

    }
    public void createPullyJoint(Body a, Body b, World w){

    }
    public void createGearJoint(Body a, Body b, World w){

    }
    public void createWheelJoint(Body a, Body b, World w){

    }
    public void createRopeJoint(Body a, Body b, World w){

    }

    public void createAllJoints(){
        synchronized(JOINT_CREATE_SYNC_CONST) {
            for (JointDetails jd : jointCreateList) {
                jd.createJoint();
            }
            jointCreateList.clear();
        }
    }

    public void createJoint(Body aBody,Body bBody, World w, String t){
        synchronized(JOINT_CREATE_SYNC_CONST) {
            jointCreateList.add(new JointDetails(aBody,bBody,w,t));
        }
    }


    public class JointDetails{
        Body a;
        Body b;
        World world;
        String type;

        public JointDetails(Body aBody,Body bBody, World w, String t){
            a = aBody;
            b = bBody;
            world = w;
            type = t;
        }

        public void createJoint(){
            if(type.equalsIgnoreCase(GameConstants.REVOLUTE)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.DISTANT)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.ROPE)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.PULLY)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.GEAR)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.WHEEL)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.PRISMATIC)){
                createRevoluteJoint(a,b,world);
            }else if(type.equalsIgnoreCase(GameConstants.WELD)){
                createWeldJoint(a,b,world);
            }
        }
    }

}
