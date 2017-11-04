package com.pintu.futurewars.Utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.pintu.futurewars.Casts.Enemy;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.JumpingKit;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.GameBullet;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/4/2017.
 */

public class WorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        GameObject obj1 = (GameObject) contact.getFixtureA().getUserData();
        GameObject obj2 = (GameObject) contact.getFixtureB().getUserData();

        if(obj1 instanceof GameBullet){
            if(obj2 instanceof Enemy){
                obj1.handleContact(obj2);
            }
            return;
        }else if(obj2 instanceof GameBullet){
            if(obj1 instanceof Enemy){
                obj2.handleContact(obj2);
            }
            return;
        }

        if(obj1 !=null && obj2 !=null){
            if(obj2 instanceof Player2){
                obj1.handleContact(obj2);
            }else{
                obj2.handleContact(obj1);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        GameObject obj1 = (GameObject) contact.getFixtureA().getUserData();
        GameObject obj2 = (GameObject) contact.getFixtureB().getUserData();

        if(obj1 !=null && obj2 !=null) //making sure that control goes to Player
            if(obj1 instanceof Player2)
                obj2.handleEndContact(obj1);
            else
                obj1.handleEndContact(obj2);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
