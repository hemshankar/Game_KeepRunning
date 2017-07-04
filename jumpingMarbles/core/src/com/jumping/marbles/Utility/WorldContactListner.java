package com.jumping.marbles.Utility;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jumping.marbles.Casts.Player;
import com.jumping.marbles.Casts.Pusher;
import com.jumping.marbles.Casts.Sucker;

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
