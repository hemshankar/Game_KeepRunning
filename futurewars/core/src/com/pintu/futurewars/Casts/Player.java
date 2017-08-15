package com.pintu.futurewars.Casts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameObjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 7/2/2017.
 */

public class Player extends FutureWarsCast {


    public Player(int id, Map<String, String> props, World w, TextureAtlas a, MapObject obj) {
        super(id, props, w, a, obj);
    }

    public void initialize(){
        Map<String, String> props = new HashMap<String, String>();
        props.put(GameObjectConstants.BODY_SHAPE,GameObjectConstants.CIRCLE);
        props.put(GameObjectConstants.BODY_TYPE,GameObjectConstants.DYNAMIC);
        //props.put(GameObjectConstants.IS_SENSOR,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.STATE_FRAMES,"STATE_1<->player");
        //props.put(GameObjectConstants.IS_ANIMATED,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.LOOP_ANIMATION,GameObjectConstants.TRUE);
        //props.put(GameObjectConstants.ANIMATION_INTERVAL,".9");
        //props.put(GameObjectConstants.IS_BULET,GameObjectConstants.TRUE);
        props.put(GameObjectConstants.CURRENT_STATE,GameObjectConstants.STATE_1);
        gProps = props;
        defineBody();
        initiateSpriteDetails();
    }
    /*public World world;
    public Body body;
    public MapObject mapObject;
    public boolean removeSuckers = false;
    public float recoilTimeElapsed = 0;
    public List<Sucker> suckers = new ArrayList<Sucker>();
    public List<Joint> joints = new ArrayList<Joint>();
    public String selectedBullet = null;
    TextureAtlas atlas = Utility.getAtlas();
    float WALKING_ANIMATION_DELAY = .2f;
    float walkingAnimationTime = 0;
    int walkingState = 1;
    String walkingStats[] = {GameConstants.PLAYER_ATLAS_NAME,GameConstants.PUSHER_ATLAS_NAME};
    public Player(World world,MapObject object ){
        this.world = world;
        this.mapObject = object;
        definePlayer();
        Utility.setPlayer(this);
    }

    public void definePlayer(){

        //initiate the objects to create a body
        CircleShape cshape = new CircleShape();
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //get the map object
        Ellipse c = ((EllipseMapObject)mapObject).getEllipse();

        //set the body definition
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set((c.xPos + c.width/2)/ GameConstants.PPM,(c.yPos + c.height/2)/GameConstants.PPM);

        //create the body using body definition
        body = world.createBody(bdef);

        //create shape
        cshape.setRadius(GameConstants.PLAYER_SIZE/GameConstants.PPM);

        //create fixtureDef using shape
        fixtureDef.shape = cshape;
        fixtureDef.restitution=.5f;
        fixtureDef.friction=0f;

        //create the fixture using fixture def
        Fixture f =body.createFixture(fixtureDef);

        //set the user data to be used in collision
        f.setUserData(this);

        selectedBullet = GameConstants.BURST_BULLET;
    }

    public void throwSuckers(){
        for(Sucker s: suckers){
            float xDirection = this.body.getPosition().xPos - s.body.getPosition().xPos;
            xDirection = Math.abs(xDirection)/xDirection;

            float yDirection = this.body.getPosition().yPos - s.body.getPosition().yPos;
            yDirection = Math.abs(yDirection)/yDirection;
            s.body.setLinearVelocity((-xDirection) * 20f,  (-yDirection) * 20f);
            s.canSuck = false;
            s.suckking=false;
            s.justThrown=true;
        }
        suckers.clear();
    }

    public void setFree(){
        Utility.worldCreator.addJointsToRemove(this.joints);
        this.joints.clear();
        Utility.worldCreator.removeJoints();
        throwSuckers();

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        //System.out.println(body.getPosition().xPos + "-----------------" + body.getPosition().yPos);
        *//*if(body.getLinearVelocity().xPos <= 5)
            body.applyLinearImpulse(new Vector2(.5f,0),body.getWorldCenter(),true);*//*
        //set walking animation
        walkingAnimationTime = walkingAnimationTime + dt;
        if(walkingAnimationTime>WALKING_ANIMATION_DELAY){
            walkingAnimationTime=0;
            walkingState = (++walkingState)%2;
            region = atlas.findRegion(walkingStats[walkingState]);
            setRegion(region);
        }
    }

    @Override
    public String getCastName() {
        return GameConstants.PLAYER_ATLAS_NAME;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public void fire(List<GameBullet> bullets,float dt){
        recoilTimeElapsed +=dt;
        if(GameConstants.BASIC_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                BasicBullet.newBasicBullet(this, bullets);
            }
        }else if(GameConstants.BURST_BULLET.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BASIC_BULLET_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                BurstBullet.newBurstBullet(this, bullets);
            }
        } else if(GameConstants.BOMB.equals(selectedBullet)){
            if(recoilTimeElapsed > GameConstants.BOMB_RECOIL_TIME) {
                recoilTimeElapsed = 0;
                //Bomb.newBomb(this, bullets);
            }
        }
    }*/
}
