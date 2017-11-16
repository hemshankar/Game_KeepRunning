package com.pintu.futurewars.Utility;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Constants.GameObjectConstants;
import com.pintu.futurewars.backgrounds.BackGround;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsahu on 10/21/2017.
 */

public class GameSprite {
    public Body body = null;
    public boolean haveBody = false;
    public boolean removeAfterAnimation = false;
    public float xPos =10;
    public float yPos =10;
    public Sprite sprite = new Sprite();
    public TextureAtlas atlas = null;
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
    public BackGround background = null;
    public boolean isBackground = false;
    /**
     * call this method in the initialize() in the child class after the defineBody method
     */

    public GameSprite(String propertiesFile,float x, float y){
        try{gProps = GameUtility.populateConfigurationsFromConfigFile(propertiesFile);}
        catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }
        initiateSpriteDetails();
        xPos = x;
        yPos = y;
    }

    public void initiateSpriteDetails() {
        stateFrameMap = getStateFrameDetails(gProps.get(GameObjectConstants.STATE_FRAMES));
        stateSoundMap = getStateSoundDetails(gProps.get(GameObjectConstants.STATE_SOUNDS));
        Float width = GameUtility.getFloat(gProps.get(GameObjectConstants.SPRITE_WIDTH));
        spriteWidth = (width == 0)? GameConstants.SIZE_SCALE:width;
        Float height = GameUtility.getFloat(gProps.get(GameObjectConstants.SPRITE_HEIGHT));
        String atlasName = gProps.get(GameObjectConstants.TEXTURE_ATLAS_NAME);
        if(!GameUtility.isEmpty(atlasName)){
            atlas = GameUtility.getAtlas(atlasName);
        }else if(atlas == null){
            throw new RuntimeException("Not texture atlas defined");
        }
        spriteHeight = (height == 0)? GameConstants.SIZE_SCALE:height;
        isAnimated = GameUtility.valueEquals(gProps.get(GameObjectConstants.IS_ANIMATED), GameObjectConstants.TRUE) ? true : false;
        if (isAnimated) {
            Float animationInterval = GameUtility.getFloat(gProps.get(GameObjectConstants.ANIMATION_INTERVAL));
            ANIMATION_INTERVAL = animationInterval == 0 ? .1f : animationInterval;
        }
        currentState = gProps.get(GameObjectConstants.CURRENT_STATE);
        //set bounds may also need to be updated properly
        sprite.setBounds(0, 0, spriteWidth, spriteHeight);
    }

    public void updateSprite( float dt) {

        //====================================Set Sprite Region=====================================
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
            //GameUtility.playSound(currentState);
        }

        if (!destroyed && !GameUtility.isEmpty(framesInCurrentState)) {
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
                    } else if (GameUtility.isTrue(gProps.get(GameObjectConstants.LOOP_ANIMATION))) {
                        frameCounter = 0;
                        sprite.setRegion(atlas.findRegion(framesInCurrentState[frameCounter]));
                    } else {animationCompleted = true;
                        if (removeAfterAnimation) {
                            toBeDestroyed = true;
                        }
                    }
                }else{
                    //if animation completed and looping is not set
                    sprite.setRegion(atlas.findRegion(framesInCurrentState[framesInCurrentState.length-1]));
                }
            } else {
                if (stateChanged) {
                    sprite.setRegion(atlas.findRegion(framesInCurrentState[0]));
                }
            }

            //====================================Set Sprite Position=====================================
            if (haveBody && !isBackground) {
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation((body.getAngle()*180*7)/22);
            } else if(!isBackground){
                sprite.setPosition(xPos, yPos);
            }
        } else {
            if(!isBackground)
                sprite.setPosition(xPos, yPos);
        }
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
            if (!GameUtility.isEmpty(spriteDetails)) {
                value = spriteDetails.split("<-->"); //get each state details
                if (!GameUtility.isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateAndFrames = stateDetails.split("<->");
                        if (!GameUtility.isEmpty(stateAndFrames) && stateAndFrames.length > 1) {
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
            if (!GameUtility.isEmpty(soundDetails)) {
                value = soundDetails.split("<-->"); //get each state details
                if (!GameUtility.isEmpty(value)) {
                    for (String stateDetails : value) {
                        String[] stateSound = stateDetails.split("<->");
                        if (!GameUtility.isEmpty(stateSound) && stateSound.length > 1) {
                            tmpStateSoundDetails.put(stateSound[0], stateSound[1]);
                        }
                    }
                }
            }
        }
        GameUtility.setStateSoundDetails(soundDetails,tmpStateSoundDetails);
        return tmpStateSoundDetails;
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }
}
