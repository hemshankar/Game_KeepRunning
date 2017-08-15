package com.pintu.futurewars.commons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.Utility.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 8/14/2017.
 */

public abstract class AbstractGameObject implements GameObject{

    protected int objectId;
    protected Body body = null;
    protected float xPos =10;
    protected float yPos =10;
    protected Sprite sprite = null;
    public World world;
    protected TextureAtlas atlas = null;
    protected MapObject mapObject=null; //needs to be set by child... later to be handled by This class
    protected Map<String,String> gProps = null;
    protected boolean isAnimated = false;
    protected Map<String,String[]> stateFrameMap = null;
    protected Map<String,String[]> stateSoundMap = null;
    protected float ANIMATION_INTERVAL = .1f;
    protected float timeInCurrentState = 0f;
    protected String currentState="";
    protected String previousState="";
    boolean stateChanged = false;
    protected String []framesInCurrentState;
    protected int frameCounter = 0;
    protected boolean animationCompleted = false;
    protected Float spriteWidth = 1f;
    protected Float spriteHeight = 1f;

    public boolean destroyed = false;
    public boolean toBeDestroyed = false;
    //these can be a bit of overHead, since these needs to be maitained
    public float timeToLive = 0;
    public float timeLived = 0;

    public AbstractGameObject(int id, Map<String,String> props, World w, TextureAtlas a){
        objectId = id;
        gProps = props;
        world = w;
        atlas = a;
        sprite = new Sprite();
    }

    public abstract void initialize();

    /**
     * call this method in the initialize() in the child class
     */
    public void defineBody(){
        Shape bShape = new CircleShape(); //default shape of abn object is circle
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Ellipse eclipse = null;
        Rectangle rect = null;

        //=====================================MAP POSITION========================================
        if(mapObject!=null) {
            if (mapObject instanceof RectangleMapObject) {
                rect = ((RectangleMapObject)mapObject).getRectangle();
                bdef.position.set((rect.x + rect.width/2)/ GameConstants.PPM,(rect.y + rect.height/2)/GameConstants.PPM);
            } else if(mapObject instanceof EllipseMapObject){
                eclipse = ((EllipseMapObject)mapObject).getEllipse();
                bdef.position.set((eclipse.x + eclipse.width/2)/ GameConstants.PPM,(eclipse.y + eclipse.height/2)/GameConstants.PPM);
            }//Todo: Implement for rest of the shapes
        }else{
            bdef.position.set(xPos, yPos);
        }

        //===============================Shape definition======================================
        if(valueEquals(gProps.get(GameObjectConstants.BODY_SHAPE), GameObjectConstants.CIRCLE)){
            bShape = new CircleShape();
            float objectSize = getFloat(gProps.get(GameObjectConstants.OBJECT_RADIUS));
            objectSize = objectSize==0?GameConstants.PLAYER_SIZE:objectSize;
            bShape.setRadius(objectSize/GameConstants.PPM);

        } else if(valueEquals(gProps.get(GameObjectConstants.BODY_SHAPE), GameObjectConstants.POLYGON)){
            bShape = new PolygonShape();
            float width = getFloat(gProps.get(GameObjectConstants.OBJECT_WIDTH));
            width = width==0?GameConstants.PLAYER_SIZE:width;
            float height = getFloat(gProps.get(GameObjectConstants.OBJECT_HEIGHT));
            height = height==0?GameConstants.PLAYER_SIZE:height;
            ((PolygonShape)bShape).setAsBox(width/GameConstants.PPM,height/GameConstants.PPM);
        }//Todo: Implementation for other shapes
        fixtureDef.shape = bShape;

        //=====================================BODY TYPE===========================================
        if(valueEquals(gProps.get(GameObjectConstants.BODY_TYPE), GameObjectConstants.DYNAMIC)) {
            bdef.type = BodyDef.BodyType.DynamicBody;
        }else{
            bdef.type = BodyDef.BodyType.StaticBody;
        }

        //======================================BULLET=============================================
        if(isTrue(gProps.get(GameObjectConstants.IS_BULET))) {
            bdef.bullet = true;
        }
        //======================================RESTITUTION========================================
        fixtureDef.restitution= gProps.get(GameObjectConstants.RESTITUTION)!=null ?
                    Float.parseFloat(gProps.get(GameObjectConstants.RESTITUTION)): .5f;
        //=======================================FRICTION==========================================
        fixtureDef.friction= gProps.get(GameObjectConstants.FRICTION)!=null ?
                Float.parseFloat(gProps.get(GameObjectConstants.FRICTION)): .2f;
        //=======================================FRICTION==========================================
        if(gProps.get(GameObjectConstants.DENSITY)!=null)
            fixtureDef.density= Float.parseFloat(gProps.get(GameObjectConstants.DENSITY));
        //===================================BODY IS SENSOR========================================
        if(isTrue(gProps.get(GameObjectConstants.IS_SENSOR))) {
            fixtureDef.isSensor = true;
        }
        //===================================TIME TO LIVE========================================
        timeToLive = getFloat(gProps.get(GameObjectConstants.TIME_TO_LIVE));//0=infinity

        //====================================CREATE BODY==========================================
        body = world.createBody(bdef);

        //==================================CREATE FIXTURE=========================================
        Fixture f =body.createFixture(fixtureDef);

        //======================set the user data to be used in collision==========================
        f.setUserData(this);
    }

    /**
     * call this method in the initialize() in the child class after the defineBody method
     */
    public void initiateSpriteDetails() {
        stateFrameMap = getStateFrameDetails(gProps.get(GameObjectConstants.STATE_FRAMES));
        Float width = getFloat(gProps.get(GameObjectConstants.SPRITE_WIDTH));
        spriteWidth = (width == 0)? GameConstants.SIZE_SCALE:width;
        Float height = getFloat(gProps.get(GameObjectConstants.SPRITE_HEIGHT));
        spriteHeight = (height == 0)? GameConstants.SIZE_SCALE:height;

    }

    public void updateSprite() {
        isAnimated = valueEquals(gProps.get(GameObjectConstants.IS_ANIMATED), GameObjectConstants.TRUE) ? true : false;
        if (isAnimated) {
            Float animationInterval = getFloat(gProps.get(GameObjectConstants.ANIMATION_INTERVAL));
            ANIMATION_INTERVAL = animationInterval == 0 ? .1f : animationInterval;
        }
        currentState = gProps.get(GameObjectConstants.CURRENT_STATE);
        //set bounds may also need to be updated properly
        sprite.setBounds(0, 0, spriteWidth * 2 / GameConstants.PPM, spriteHeight * 2 / GameConstants.PPM);
    }
    public void update(float dt){
        timeLived+=dt;
        if(destroyed){
            return;
        }
        //===================Special cases for bullets and short lived game objects================
        if(timeToLive!=0 && timeLived > timeToLive){
            toBeDestroyed = true;
            return;
        }
        updateSprite();

        if(previousState.equals(currentState)) {
            timeInCurrentState += dt;
            stateChanged = false;
        }else{
            timeInCurrentState = 0;
            framesInCurrentState = stateFrameMap.get(currentState);
            frameCounter = 0;
            animationCompleted = false;
            stateChanged = true;
            previousState = currentState;
        }

        if(!destroyed && !isEmpty(framesInCurrentState)) {
            if(isAnimated) {
                if (!animationCompleted) {
                    if (timeInCurrentState > ANIMATION_INTERVAL) {
                        timeInCurrentState = 0;
                        frameCounter++;
                    }
                    if (frameCounter < framesInCurrentState.length) {
                        sprite.setRegion(atlas.findRegion(framesInCurrentState[frameCounter]));
                    } else if (isTrue(gProps.get(GameObjectConstants.LOOP_ANIMATION))) {
                        frameCounter = 0;
                        sprite.setRegion(atlas.findRegion(framesInCurrentState[frameCounter]));
                    } else {
                        animationCompleted = true;
                    }
                }
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2,body.getPosition().y -sprite.getHeight()/2);
            }else{
                if(stateChanged) {
                    sprite.setRegion(atlas.findRegion(framesInCurrentState[0]));
                }
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2,body.getPosition().y -sprite.getHeight()/2);
            }
        }else{
            sprite.setPosition(0, 0);
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
            world.destroyBody(body);
            destroyed=true;
        }
    }

    protected boolean valueEquals(String x, String y){
        if(x !=null && y !=null){
            return x.equalsIgnoreCase(y);
        }
        return false;
    }

    protected boolean isEmpty(String str){
        if(str !=null && !"".equals(str))
            return false;
        return true;
    }
    protected boolean isEmpty(String[] str){
        if(str !=null && str.length>0)
            return false;
        return true;
    }

    protected Integer getInt(String str){
        if(str!=null)
            return Integer.parseInt(str);
        return 0;
    }

    protected Float getFloat(String str){
        if(str!=null)
            return Float.parseFloat(str);
        return 0f;
    }

    protected boolean isTrue(String str){
        if(str!=null && str.equalsIgnoreCase("TRUE"))
            return true;
        return false;
    }

    protected Map<String,String[]> getStateFrameDetails(String spriteDetails) {
        Map<String,String[]> tmpStateFrameDetails = null;
        if (spriteDetails != null) {
                tmpStateFrameDetails = Utility.getStateFrameDetails(spriteDetails);
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
        Utility.setStateFrameDetails(spriteDetails,tmpStateFrameDetails);
        return tmpStateFrameDetails;
    }


}