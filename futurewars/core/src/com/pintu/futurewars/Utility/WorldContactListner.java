package com.pintu.futurewars.Utility;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Pusher;
import com.pintu.futurewars.Casts.Sucker;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;
import com.pintu.futurewars.com.pintu.futurewars.armory.Bomb;

/**
 * Created by hsahu on 7/4/2017.
 */

public class WorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a.getUserData() instanceof Player && b.getUserData() instanceof Sucker){
           ((Sucker) b.getUserData()).canSuck=true;
        }
        else if(a.getUserData() instanceof Sucker && b.getUserData() instanceof Player){
            ((Sucker) a.getUserData()).canSuck=true;
        }

        if(a.getUserData() instanceof Player && b.getUserData() instanceof Pusher){
            ((Player) a.getUserData()).removeSuckers =true;
        }
        else if(a.getUserData() instanceof Pusher && b.getUserData() instanceof Player){
            ((Player) b.getUserData()).removeSuckers =true;
        }

        if(a.getUserData() instanceof BasicBullet || b.getUserData() instanceof BasicBullet){
            BasicBullet bullet = (BasicBullet)( a.getUserData() instanceof BasicBullet ? a.getUserData() : b.getUserData());
            bullet.toBeDestroyed = true;

            Fixture f  = bullet == a.getUserData() ? b:a;
            Object obj =  f.getUserData();
            if(obj instanceof FutureWarsCast && !(obj instanceof Player)){
                FutureWarsCast cast = (FutureWarsCast)f.getUserData();
                cast.takeDamage(bullet);
            }
        }
        if(a.getUserData() instanceof Bomb || b.getUserData() instanceof Bomb){
            Bomb bullet = (Bomb)( a.getUserData() instanceof Bomb ? a.getUserData() : b.getUserData());
            bullet.toBeDestroyed = true;

            Fixture f  = bullet == a.getUserData() ? b:a;
            Object obj =  f.getUserData();
            if(obj instanceof FutureWarsCast && !(obj instanceof Player)){
                FutureWarsCast cast = (FutureWarsCast)f.getUserData();
                cast.takeDamage(bullet);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
