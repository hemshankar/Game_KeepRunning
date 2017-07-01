package com.mygdx.game.com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.com.mygdx.game.tiles.screens.sprites.Subject;

/**
 * Created by hsahu on 7/1/2017.
 */

public class MyWorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Begin contact");

        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() instanceof Subject){
            System.out.println(" a is Subject");
            Subject s = (Subject)a.getUserData();
            System.out.println(" at " + s.getX());
        }else if(b.getUserData() instanceof Subject){
            System.out.println(" b is Subject");
            Subject s = (Subject)b.getUserData();
            System.out.println(" at " + s.getX());
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("End contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
