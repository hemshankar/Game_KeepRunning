package com.jumping.marbles.Casts;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.Utility.Utility;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Healer extends JumpingMarblesCast{

    public World world;
    public Body body;
    MapObject mapObject;
    public Player player;
    public boolean canHeal = false;
    public Healer(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        defineHealer();
    }

    public void defineHealer(){

        //initiate the objects to create a body
        CircleShape shape = new CircleShape();
        BodyDef bdef = new BodyDef();
        FixtureDef cFixtureDef = new FixtureDef();

        //get the map object
        Ellipse c = ((EllipseMapObject)mapObject).getEllipse();

        //set the body definition
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((c.x + c.width/2)/ GameConstants.PPM,(c.y + c.height/2)/GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(bdef);

        //create shape
        shape.setRadius(GameConstants.HEALER_SIZE/GameConstants.PPM);

        //create fixtureDef using shape
        cFixtureDef.shape = shape;
        cFixtureDef.restitution=0.5f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(cFixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);
    }

    @Override
    public String getCastName() {
        return GameConstants.HEALER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        player = Utility.getPlayer();
        if(player !=null) {
            float playerX = player.body.getPosition().x;
            float playerY = player.body.getPosition().y;

            if (Math.abs(body.getPosition().x - playerX) < 50 / GameConstants.PPM
                    && Math.abs(body.getPosition().y - playerY) < 50 / GameConstants.PPM) {
                canHeal = true;
            }
        }
    }
    public void healPlayer(){
        if(canHeal){
            canHeal = false;
            for(Sucker sucker: player.suckers){
                System.out.println("Destroying " + sucker.suckerId);
                Utility.worldCreator.removeSucker(sucker);
            }
            player.suckers.clear();
            player.joints.clear();
        }
    }
}
