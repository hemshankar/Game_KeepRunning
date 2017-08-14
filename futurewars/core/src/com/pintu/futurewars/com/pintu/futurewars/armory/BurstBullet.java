package com.pintu.futurewars.com.pintu.futurewars.armory;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pintu.futurewars.Casts.Player;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Utility.Utility;

import java.util.List;

/**
 * Created by hsahu on 7/15/2017.
 */

public class BurstBullet extends GameBullet {


    public BurstBullet(float x, float y, int direction) {
        super(x, y, direction);
    }

    public static void newBurstBullet(Player player, List<GameBullet> bullets){
        bullets.add(new BasicBullet(player.getX() + player.getWidth() + 10 / GameConstants.PPM,
                player.getY() + player.getHeight() / 1, GameConstants.RIGHT));
        bullets.add(new BasicBullet(player.getX() + player.getWidth() + 10 / GameConstants.PPM,
                player.getY() + player.getHeight() / 4, GameConstants.RIGHT));
        Utility.gameScreen.assetManager.get("audio/SHOOT008.mp3",Sound.class).play();
        Utility.gameScreen.assetManager.get("audio/SHOOT008.mp3",Sound.class).play();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void setSpriteRegion() {

    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public void fire() {

    }

    @Override
    public void setBody(Body b) {

    }

    @Override
    public int getDamage() {
        return 0;
    }

}
