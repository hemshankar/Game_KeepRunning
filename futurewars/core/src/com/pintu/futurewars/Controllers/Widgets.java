package com.pintu.futurewars.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Screens.GameScreen;
import com.pintu.futurewars.Utility.GameUtility;
import com.pintu.futurewars.commons.AbstractGameObject;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 7/5/2017.
 */

public class Widgets {
    Viewport cViewPort;
    public Stage stage;
    public JumpingMarblesGame game;
    GameScreen screen = null;
    List<BitmapFont> fontList=new ArrayList<BitmapFont>();
    OrthographicCamera ctrlCam;

    CustomButton speedStats = null;
    CustomButton coinsCollected = null;
    CustomButton timeLeft = null;
    CustomButton distance = null;
    CustomButton rockets;
    CustomButton skates;
    CustomButton bulletType;
    CustomButton magnetsLeft = null;
    CustomButton parachute = null;

    public Widgets(JumpingMarblesGame game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort, screen.batch);

        rockets = addItem("imgs/rocketOff.png","imgs/rocketOn.png","0",80,850,80,800,new RocketListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        skates = addItem("imgs/skatesOff.png","imgs/skatesOn.png","0",280,850,280,800,new SkatesListner(),60,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        bulletType = addItem("imgs/offGun.png","imgs/onGun.png","0",390,850,390,800,new FireListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        magnetsLeft = addItem("imgs/offMagnet.png","imgs/onMagnet.png","0",560,850,580,800,new MagnetListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        parachute = addItem("imgs/parachuteOff.png","imgs/parachuteOn.png","0",750,850,775,800,new ParacheuteListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));

        speedStats = addItem("imgs/speed.png",null,"0",1380,950,1450,950,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        coinsCollected = addItem("imgs/coin.png",null,"0",1380,900,1450,900,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        timeLeft = addItem("imgs/watch.png",null,"0",1380,850,1450,850,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        //distance = addItem("imgs/welcome.png",null,"0",1380,800,1450,800,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));

        addItem("imgs/shoot.png",null,"",stage.getWidth()-180,100,0,0,new ThrowListner(),200,200,null);


    }

    public void draw(){
        stage.draw();
    }

    public void update(float dt){

        speedStats.label.setText (("" + (int)(screen.player2.body.getLinearVelocity().x*(GameConstants.MPS_TO_KPH))) + " kmph");
        coinsCollected.label.setText ( "" + screen.player2.totalCoin);
        timeLeft.label.setText (((int)screen.gameTime - (int)screen.timePassed) + " s");
        //distance.label.setText (  ((int)(1000*screen.player2.body.getPosition().x)/(100000.0)) + " km");
        rockets.label.setText(screen.player2.totalRockets + "");
        skates.label.setText(screen.player2.totalSkates + "");
        bulletType.label.setText(screen.player2.selectedBullet);
        magnetsLeft.label.setText(screen.player2.numberOfMagnet + "");
        parachute.label.setText(screen.player2.totalParachute + "");

        if(screen.player2.hasMagnet){
            magnetsLeft.m_offImage.setVisible(false);
            magnetsLeft.m_onImage.setVisible(true);
        }else{
            magnetsLeft.m_offImage.setVisible(true);
            magnetsLeft.m_onImage.setVisible(false);
        }
        if(screen.player2.hasRocket){
            rockets.m_offImage.setVisible(false);
            rockets.m_onImage.setVisible(true);
        }else{
            rockets.m_offImage.setVisible(true);
            rockets.m_onImage.setVisible(false);
        }
        if(screen.player2.hasSkates){
            skates.m_offImage.setVisible(false);
            skates.m_onImage.setVisible(true);
        }else{
            skates.m_offImage.setVisible(true);
            skates.m_onImage.setVisible(false);
        }

        if(screen.player2.hasParachute){
            parachute.m_offImage.setVisible(false);
            parachute.m_onImage.setVisible(true);
        }else{
            parachute.m_offImage.setVisible(true);
            parachute.m_onImage.setVisible(false);
        }

        stage.act();
    }

    public void resize(int w,int h){

        cViewPort.update(w,h);
    }

    public void dispose(){
        for(BitmapFont f: fontList){
            f.dispose();
        }
    }

    private BitmapFont getFonts(String fontTypeTTFFile,int size, Color color){
        BitmapFont f = null;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontTypeTTFFile));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        params.color = color;
        f = generator.generateFont(params);
        fontList.add(f);
        return f;
    }

    private CustomButton addItem(String offImage, String onImage, String label,float imageX, float imageY,
                             float labelX, float labelY,InputListener listner,float imageWidth,float imageHeght,
                                BitmapFont font){
        CustomButton custBut = new CustomButton();

        Texture texture = new Texture(Gdx.files.internal(offImage));
        Image stageImage = new Image(texture);
        stageImage.setWidth(imageWidth);
        stageImage.setHeight(imageHeght);
        stageImage.setPosition(imageX,imageY);
        if(listner!=null)
            stageImage.addListener(listner);
        custBut.m_offImage = stageImage;
        stage.addActor(custBut.m_offImage);

        if(!GameUtility.isEmptyString(onImage)) {
            Texture texture2 = new Texture(Gdx.files.internal(onImage));
            Image stageImage2 = new Image(texture2);
            stageImage2.setWidth(imageWidth);
            stageImage2.setHeight(imageHeght);
            stageImage2.setPosition(imageX, imageY);
            stageImage2.setVisible(false);
            if (listner != null)
                stageImage2.addListener(listner);
            custBut.m_onImage = stageImage2;
            stage.addActor(custBut.m_onImage);
        }



        if(font!=null) {
            Label stagelabel = new Label(label, new Label.LabelStyle(font, Color.BLACK));
            stagelabel.setWidth(40);
            stagelabel.setHeight(40);
            stagelabel.setPosition(labelX, labelY);
            if (listner != null)
                stagelabel.addListener(listner);
            stage.addActor(stagelabel);
            //font.dispose();
            custBut.label = stagelabel;

        }
        return custBut;
    }

    public class CustomButton{
        public Image m_offImage;
        public Image m_onImage;
        public Label label;
    }

    public class RocketListner extends InputListener {

        public RocketListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            /*game.pauseScreen.stageScreen = screen;
            game.setScreen(game.pauseScreen);*/
            if(screen.player2.totalRockets>0 && !screen.player2.hasRocket) {
                screen.player2.totalRockets--;
                screen.player2.hasRocket = true;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
    public class SkatesListner extends InputListener {

        public SkatesListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            /*game.pauseScreen.stageScreen = screen;
            game.setScreen(game.pauseScreen);*/
            if(screen.player2.totalSkates>0 && !screen.player2.hasSkates) {
                screen.player2.totalSkates--;
                screen.player2.hasSkates = true;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
    public class ThrowListner extends InputListener {

        public ThrowListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (screen.player2.jointMap.size() != 0) {
                List<GameObject> goList = new ArrayList<GameObject>();
                for (GameObject gObj : screen.player2.jointMap.keySet()) {
                    AbstractGameObject g = (AbstractGameObject) gObj;
                    Joint j = screen.player2.jointMap.get(g);

                    if (j instanceof WeldJoint && g.isThrowable) {
                        goList.add(g);
                        GameUtility.jointHandler.removeJoint(j, screen.world);
                    }
                }

                for (GameObject g : goList) {
                    screen.player2.jointMap.remove(g);
                }

                GameUtility.jointHandler.removeAllJoints();
                for (GameObject g : goList) {
                    screen.player2.jointMap.remove(g);
                    g.getBody().applyLinearImpulse((screen.player2.getBody().getLinearVelocity().x + 1) * 1.5f, 0, g.getBody().getWorldCenter().x, g.getBody().getWorldCenter().y, true);
                }
                goList.clear();
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
    public class FireListner extends InputListener {

        public FireListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(screen.player2.hasRifle){
                bulletType.m_offImage.setVisible(true);
                bulletType.m_onImage.setVisible(false);
                screen.player2.hasRifle = false;
            } else{
                //stage.getActors().removeValue(bulletType.m_offImage,false);
                bulletType.m_offImage.setVisible(false);
                bulletType.m_onImage.setVisible(true);
                screen.player2.hasRifle = true;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }

    public class MagnetListner extends InputListener {

        public MagnetListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!screen.player2.hasMagnet && screen.player2.numberOfMagnet>0){
                screen.player2.hasMagnet = true;
                screen.player2.numberOfMagnet--;
                screen.player2.magnetEffectRemainig = screen.player2.MAGNET_EFFECT_TIME;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }

    public class ParacheuteListner extends InputListener {

        public ParacheuteListner(){
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!screen.player2.hasParachute && screen.player2.totalParachute>0){
                screen.player2.hasParachute = true;
                screen.player2.totalParachute--;
                screen.player2.parachuteEffectRemainig = screen.player2.PARACHUTE_EFFECT_TIME;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
}
