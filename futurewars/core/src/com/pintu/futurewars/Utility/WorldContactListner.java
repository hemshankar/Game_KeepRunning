package com.pintu.futurewars.Utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pintu.futurewars.Casts.FutureWarsCast;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Casts.SpeedBomb;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.com.pintu.futurewars.armory.BasicBullet;

/**
 * Created by hsahu on 7/4/2017.
 */

public class WorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();


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
        if(a.getUserData() instanceof SpeedBomb || b.getUserData() instanceof SpeedBomb){

            SpeedBomb speedBomb = (SpeedBomb)( a.getUserData() instanceof SpeedBomb ? a.getUserData() : b.getUserData());

            Fixture f  = speedBomb == a.getUserData() ? b:a;
            Object obj =  f.getUserData();
            if(obj instanceof Player2){
                speedBomb.toBeDestroyed = true;
                Body body = ((Player2)obj).getBody();
                body.applyLinearImpulse(new Vector2(100, 0), body.getWorldCenter(), true);
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
