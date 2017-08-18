package com.pintu.futurewars.Utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.JumpingKit;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.commons.GameObject;

/**
 * Created by hsahu on 7/4/2017.
 */

public class WorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        GameObject obj1 = (GameObject) contact.getFixtureA().getUserData();
        GameObject obj2 = (GameObject) contact.getFixtureB().getUserData();
        if(obj1 !=null && obj2 !=null)
            obj2.handleContact(obj1);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() instanceof Ground || b.getUserData() instanceof Ground){
            //System.out.println("=== something is ground");
            Ground ground = (Ground)( a.getUserData() instanceof Ground ? a.getUserData() : b.getUserData());
            Fixture f  = ground == a.getUserData() ? b:a;
            Object obj =  f.getUserData();
            //System.out.println("=== other is " + obj);
            if(obj instanceof Player2){
                Player2 player = (Player2)obj;
                //System.out.println("===" + player.hasJumpingKit);
                if(player.hasJumpingKit){
                    player.body.applyLinearImpulse(new Vector2(0, 6), player.body.getWorldCenter(), true);
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
