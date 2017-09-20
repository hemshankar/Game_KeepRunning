package com.pintu.futurewars.commons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Casts.Ground;
import com.pintu.futurewars.Casts.Player2;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.GameUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/14/2017.
 */

public abstract class AbstractGameObject implements GameObject{

    public int objectId;
    public Body body = null;
    boolean haveBody = false;
    boolean removeAfterAnimation = false;
    public float xPos =10;
    public float yPos =10;
    public Sprite sprite = null;
    public World world;
    public TextureAtlas atlas = null;
    public MapObject mapObject=null; //needs to be set by child... later to be handled by This class
    public Map<String,String> gProps = null;
    public boolean isAnimated = false;
    public Map<String,String[]> stateFrameMap = null;
    public Map<String,String> stateSoundMap = null;
    public float ANIMATION_INTERVAL = .1f;
    public float timeInCurrentState = 0f;
    public String currentState="";
    public String previousState="";
    boolean stateChanged = false;
    public String []framesInCurrentState;
    public int frameCounter = 0;
    public boolean animationCompleted = false;
    public Float spriteWidth = 1f;
    public Float spriteHeight = 1f;

    public boolean destroyed = false;
    public boolean toBeDestroyed = false;
    //these can be a bit of overHead, since these needs to be maitained
    public float timeToLive = 0;
    public float timeLived = 0;

    public static final float FLY_INTERVAL = 0f;
    public float flyTimer = 0f;
    public boolean flying = true;
    public boolean canFly = true;
    public float flyPosition = 10;
    //public float flyLimitLow = 5;
    //public float stickToPositionFactor = 1f;


    public AbstractGameObject(int id, Map<String,String> props, World w, TextureAtlas a){
        objectId = id;
        gProps = props;
        world = w;
        atlas = a;
        sprite = new Sprite();
    }
    public AbstractGameObject(int id, String propFile, World w, TextureAtlas a){
        objectId = id;
        try{gProps = GameUtility.populateConfigurationsFromConfigFile(propFile);}
        catch (Exception e){e.printStackTrace();}
        world = w;
        atlas = a;
        sprite = new Sprite();
    }

    public void initialize(){
        defineBody();
        initiateSpriteDetails();
    }

    /**
     * call this method in the initialize() in the child class
     */
    public void defineBody(){

        //======================================NO BODY?===========================================
        haveBody= !(isTrue(gProps.get(GameObjectConstants.NO_BODY)));

        //====================================TIME TO LIVE=========================================
        timeToLive = getFloat(gProps.get(GameObjectConstants.TIME_TO_LIVE));//0=infinity

        //==============================REMOVE AFTER ANIMATION=====================================
        removeAfterAnimation = isTrue(gProps.get(GameObjectConstants.REMOVE_AFTER_ANIMATION));
        if(haveBody) {
            Shape bShape = new CircleShape(); //default shape of abn object is circle
            BodyDef bdef = new BodyDef();

            FixtureDef fixtureDef = new FixtureDef();
            Ellipse eclipse = null;
            Rectangle rect = null;

            //===================================MAP POSITION======================================
            if (mapObject != null) {
                if (mapObject instanceof RectangleMapObject) {
                    rect = ((RectangleMapObject) mapObject).getRectangle();
                    bdef.position.set(rect.x / GameConstants.PPM, rect.y  / GameConstants.PPM);
                } else if (mapObject instanceof EllipseMapObject) {
                    eclipse = ((EllipseMapObject) mapObject).getEllipse();
                    bdef.position.set((eclipse.x + eclipse.width / 2) / GameConstants.PPM, (eclipse.y + eclipse.height / 2) / GameConstants.PPM);
                }//Todo: Implement for rest of the shapes
            } else {
                bdef.position.set(xPos, yPos);
            }

            //===============================Shape definition======================================
            if (valueEquals(gProps.get(GameObjectConstants.BODY_SHAPE), GameObjectConstants.CIRCLE)) {
                bShape = new CircleShape();
                float objectSize = getFloat(gProps.get(GameObjectConstants.OBJECT_RADIUS));
                objectSize = objectSize == 0 ? GameConstants.PLAYER_SIZE : objectSize;
                bShape.setRadius(objectSize / GameConstants.PPM);

            } else if (valueEquals(gProps.get(GameObjectConstants.BODY_SHAPE), GameObjectConstants.POLYGON)) {
                bShape = new PolygonShape();
                float width = getFloat(gProps.get(GameObjectConstants.OBJECT_WIDTH));
                width = width == 0 ? GameConstants.PLAYER_SIZE : width;
                float height = getFloat(gProps.get(GameObjectConstants.OBJECT_HEIGHT));
                height = height == 0 ? GameConstants.PLAYER_SIZE : height;
                ((PolygonShape) bShape).setAsBox(width / GameConstants.PPM, height / GameConstants.PPM);
            }//Todo: Implementation for other shapes
            fixtureDef.shape = bShape;

            //===================================BODY TYPE=========================================
            if (valueEquals(gProps.get(GameObjectConstants.BODY_TYPE), GameObjectConstants.DYNAMIC)) {
                bdef.type = BodyDef.BodyType.DynamicBody;
            } else {
                bdef.type = BodyDef.BodyType.StaticBody;
            }

            //====================================BULLET===========================================
            if (isTrue(gProps.get(GameObjectConstants.IS_BULLET))) {
                bdef.bullet = true;
            }
            //====================================RESTITUTION======================================
            fixtureDef.restitution = gProps.get(GameObjectConstants.RESTITUTION) != null ?
                    Float.parseFloat(gProps.get(GameObjectConstants.RESTITUTION)) : .5f;
            //=====================================FRICTION========================================
            fixtureDef.friction = gProps.get(GameObjectConstants.FRICTION) != null ?
                    Float.parseFloat(gProps.get(GameObjectConstants.FRICTION)) : 0f;
            //=====================================FRICTION========================================
            if (gProps.get(GameObjectConstants.DENSITY) != null)
                fixtureDef.density = Float.parseFloat(gProps.get(GameObjectConstants.DENSITY));
            //=================================BODY IS SENSOR======================================
            if (isTrue(gProps.get(GameObjectConstants.IS_SENSOR))) {
                fixtureDef.isSensor = true;
            }
            //==================================CREATE BODY========================================
            body = world.createBody(bdef);
            //=====================================DAMPING========================================
            body.setLinearDamping(gProps.get(GameObjectConstants.DAMPING) != null ?
                    Float.parseFloat(gProps.get(GameObjectConstants.DAMPING)) : 0f);
            //================================CREATE FIXTURE=======================================
            Fixture f = body.createFixture(fixtureDef);

            //====================set the user data to be used in collision========================
            f.setUserData(this);

            //==================================CREATION SOUND=====================================

        }
    }

    /**
     * call this method in the initialize() in the child class after the defineBody method
     */
    public void initiateSpriteDetails() {
        stateFrameMap = getStateFrameDetails(gProps.get(GameObjectConstants.STATE_FRAMES));
        stateSoundMap = getStateSoundDetails(gProps.get(GameObjectConstants.STATE_SOUNDS));
        Float width = getFloat(gProps.get(GameObjectConstants.SPRITE_WIDTH));
        spriteWidth = (width == 0)? GameConstants.SIZE_SCALE:width;
        Float height = getFloat(gProps.get(GameObjectConstants.SPRITE_HEIGHT));
        String atlasName = gProps.get(GameObjectConstants.TEXTURE_ATLAS_NAME);
        if(!isEmpty(atlasName)){
            atlas = GameUtility.getAtlas(atlasName);
        }else if(atlas == null){
            throw new RuntimeException("Not texture atlas defined");
        }
        spriteHeight = (height == 0)? GameConstants.SIZE_SCALE:height;
        isAnimated = valueEquals(gProps.get(GameObjectConstants.IS_ANIMATED), GameObjectConstants.TRUE) ? true : false;
        if (isAnimated) {
            Float animationInterval = getFloat(gProps.get(GameObjectConstants.ANIMATION_INTERVAL));
            ANIMATION_INTERVAL = animationInterval == 0 ? .1f : animationInterval;
        }
        currentState = gProps.get(GameObjectConstants.CURRENT_STATE);
        //set bounds may also need to be updated properly
        sprite.setBounds(0, 0, spriteWidth * 2 / GameConstants.PPM, spriteHeight * 2 / GameConstants.PPM);

    }

    public void updateSprite() {

    }
    public void update(float dt){
        try {
            timeLived += dt;
            if (destroyed) {
                return;
            }
            //===================Special cases for bullets and short lived game objects================
            if (timeToLive != 0 && timeLived > timeToLive) {
                toBeDestroyed = true;
                return;
            }
            updateSprite();

            if (previousState.equals(currentState)) {
                timeInCurrentState += dt;
                stateChanged = false;
            } else {
                timeInCurrentState = 0;
                framesInCurrentState = stateFrameMap.get(currentState);
                frameCounter = 0;
                animationCompleted = false;
                stateChanged = true;
                previousState = currentState;
                playSound(currentState);
            }

            if (!destroyed && !isEmpty(framesInCurrentState)) {
                if (isAnimated) {
                    if (!animationCompleted) {
                        if (timeInCurrentState > ANIMATION_INTERVAL) {
                            timeInCurrentState = 0;
                            frameCounter++;
                        }
                        if (frameCounter < framesInCurrentState.length) {
                            //System.out.println(frameCounter);
                            //System.out.println(Arrays.asList(framesInCurrentState));
                            sprite.setRegion(atlas.findRegion(framesInCurrentState[frameCounter]));
                        } else if (isTrue(gProps.get(GameObjectConstants.LOOP_ANIMATION))) {
                            frameCounter = 0;
                            sprite.setRegion(atlas.findRegion(framesInCurrentState[frameCounter]));
                        } else {
                            animationCompleted = true;
                            if (removeAfterAnimation) {
                                toBeDestroyed = true;
                            }
                        }
                    }
                } else {
                    if (stateChanged) {
                        sprite.setRegion(atlas.findRegion(framesInCurrentState[0]));
                    }
                }
                if (haveBody) {
                    sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                } else {
                    sprite.setPosition(xPos, yPos);
                }
            } else {
                sprite.setPosition(xPos, yPos);
            }

           /* if(canFly && itsFlyTime(dt) && body.getPosition().y < flyLimitLow) {
                body.applyLinearImpulse(
                        new Vector2(0, world.getGravity().y + (-1 * body.getLinearVelocity().y) * 1.5f),
                        this.body.getWorldCenter(), true);
            }
            if(canFly && itsFlyTime(dt) && body.getPosition().y > flyPosition) {
                body.applyLinearImpulse(
                        new Vector2(0, -1 * body.getLinearVelocity().y*1.5f),
                        this.body.getWorldCenter(), true);
            }*/

            if(haveBody && canFly && itsFlyTime(dt)
                    && body.getPosition().y < flyPosition){
                    //&& body.getLinearVelocity().y <0) {
                body.applyLinearImpulse(
                        new Vector2(0,flyPosition - body.getPosition().y),
                                    this.body.getWorldCenter(), true);
            }
            /*else if(canFly && itsFlyTime(dt) && body.getPosition().y > flyPosition) {
                body.applyLinearImpulse(
                        new Vector2(0, .5f*(flyPosition - body.getPosition().y)
                                             * stickToPositionFactor),
                        this.body.getWorldCenter(), true);
            }*/

            Player2 player = GameUtility.getGameScreen().player2;

            //move with player
            if(!(this instanceof Player2) && haveBody){

                if(player.body.getLinearVelocity().x - 10 <= body.getLinearVelocity().x) {
                    body.setLinearDamping(1);
                }else {
                    body.setLinearDamping(0f);
                    float diff = player.body.getLinearVelocity().x - body.getLinearVelocity().x;
                    if (diff > 10) {
                        body.applyLinearImpulse(
                                new Vector2(diff * 0.01f, 0),
                                this.body.getWorldCenter(), true);
                    }
                }
            }

            //Destroy Non Seen casts
            if(!(this instanceof Player2)
                    && !(this instanceof Ground)
                    && Math.abs(player.body.getPosition().x - body.getPosition().x)>500){
                toBeDestroyed = true;
            }
        }catch(Exception e){
            System.out.println("Error in frame ======" + frameCounter + "--"
                    + gProps.get(GameObjectConstants.TEXTURE_ATLAS_NAME) + " :" + e.getMessage());
        }
    }

    public void updateProp(String key, String value){
        gProps.put(key,value);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Body getBody(){
        return body;
    }

    public void setToDestroyed(){
        toBeDestroyed = true;
    }
    public boolean toBeDestroyed(){
        return toBeDestroyed;
    }
    public void destroy(){
        if(toBeDestroyed){
            if(haveBody)
                world.destroyBody(body);
            destroyed=true;
        }
    }

    public boolean valueEquals(String x, String y){
        if(x !=null && y !=null){
            return x.equalsIgnoreCase(y);
        }
        return false;
    }

    public boolean isEmpty(String str){
        if(str !=null && !"".equals(str))
            return false;
        return true;
    }
    public boolean isEmpty(String[] str){
        if(str !=null && str.length>0)
            return false;
        return true;
    }

    public Integer getInt(String str){
        if(str!=null)
            return Integer.parseInt(str);
        return 0;
    }

    public Float getFloat(String str){
        if(str!=null)
            return Float.parseFloat(str);
        return 0f;
    }

    public boolean isTrue(String str){
        if(str!=null && str.equalsIgnoreCase("TRUE"))
            return true;
        return false;
    }

    public Map<String,String[]> getStateFrameDetails(String spriteDetails) {
        Map<String,String[]> tmpStateFrameDetails = null;
        if (spriteDetails != null) {
                tmpStateFrameDetails = GameUtility.getStateFrameDetails(spriteDetails);
            if(tmpStateFrameDetails!=null)
                return tmpStateFrameDetails;
            else
                tmpStateFrameDetails = new HashMap<String, String[]>();

            String[] value;
            if (!isEmpty(spriteDetails)) {
                value = spriteDetails.split("<-->"); //get each state details
                if (!isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateAndFrames = stateDetails.split("<->");
                        if (!isEmpty(stateAndFrames) && stateAndFrames.length > 1) {
                            String[] frames = stateAndFrames[1].split(",");
                            tmpStateFrameDetails.put(stateAndFrames[0], frames);
                        }
                    }
                }
            }
        }
        GameUtility.setStateFrameDetails(spriteDetails,tmpStateFrameDetails);
        return tmpStateFrameDetails;
    }
    public Map<String,String> getStateSoundDetails(String soundDetails) {
        Map<String,String> tmpStateSoundDetails = null;
        if (soundDetails != null) {
            tmpStateSoundDetails = GameUtility.getStateSoundDetails(soundDetails);
            if(tmpStateSoundDetails!=null)
                return tmpStateSoundDetails;
            else
                tmpStateSoundDetails = new HashMap<String, String>();

            String[] value;
            if (!isEmpty(soundDetails)) {
                value = soundDetails.split("<-->"); //get each state details
                if (!isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateSound = stateDetails.split("<->");
                        if (!isEmpty(stateSound) && stateSound.length > 1) {
                            tmpStateSoundDetails.put(stateSound[0], stateSound[1]);
                        }
                    }
                }
            }
        }
        GameUtility.setStateSoundDetails(soundDetails,tmpStateSoundDetails);
        return tmpStateSoundDetails;
    }

    public void handleContact(GameObject gObj){
        //To be implemented by Specific game object
    }

    public void handleEndContact(GameObject gObj) {
        //To be implemented by the specific game object extending this class
    }

    private void playSound(String sound){
        if(stateSoundMap !=null && stateSoundMap.size()>0 && stateSoundMap.get(sound) !=null) {
            GameUtility.getGameScreen().assetManager.get(stateSoundMap.get(sound), Sound.class).play();
        }
    }

    public boolean itsFlyTime(float dt){
        flyTimer += dt;
        if(flyTimer > FLY_INTERVAL){
            flyTimer = 0;
            return true;
        }
        return false;
    }
}
