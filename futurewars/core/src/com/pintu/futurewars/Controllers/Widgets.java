package com.pintu.futurewars.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    Label speedStats = null;
    Label coinsCollected = null;
    Label timeLeft = null;
    Label distance = null;
    Label numberOfRocketsLabel;
    Label numberOfSkatesLabel;
    Label bulletType;

    public Widgets(JumpingMarblesGame game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort, screen.batch);

        numberOfRocketsLabel = addItem("imgs/rocket.png","0",80,850,80,800,new RocketListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        numberOfSkatesLabel = addItem("imgs/skateIcon.png","0",280,850,280,800,new SkatesListner(),60,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        bulletType = addItem("imgs/gun.png","0",380,850,380,800,new FireListner(),150,150,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));

        speedStats = addItem("imgs/welcome.png","0",1080,950,1150,950,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        coinsCollected = addItem("imgs/welcome.png","0",1080,900,1150,900,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        timeLeft = addItem("imgs/welcome.png","0",1080,850,1150,850,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        distance = addItem("imgs/welcome.png","0",1080,800,1150,800,null,50,50,getFonts("fonts/leadcoat.ttf",30,Color.BLACK));
        addItem("imgs/shoot.png","",stage.getWidth()-180,100,0,0,new ThrowListner(),200,200,null);

    }

    public void draw(){
        stage.draw();
    }

    public void update(float dt){

        speedStats.setText (("" + (int)(screen.player2.body.getLinearVelocity().x*(GameConstants.MPS_TO_KPH))) + " kmph");
        coinsCollected.setText ( "" + screen.player2.totalCoin);
        timeLeft.setText (((int)screen.gameTime - (int)screen.timePassed) + " s");
        distance.setText (  ((int)(1000*screen.player2.body.getPosition().x)/(100000.0))
                + " km");
        numberOfRocketsLabel.setText(screen.player2.totalRockets + "");
        numberOfSkatesLabel.setText(screen.player2.totalSkates + "");
        bulletType.setText(screen.player2.selectedBullet);
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

    private Label addItem(String imageLocation, String label,float imageX, float imageY,
                             float labelX, float labelY,InputListener listner,float imageWidth,float imageHeght,
                                BitmapFont font){
        Texture texture = new Texture(Gdx.files.internal(imageLocation));
        Image stageImage = new Image(texture);
        stageImage.setWidth(imageWidth);
        stageImage.setHeight(imageHeght);
        stageImage.setPosition(imageX,imageY);
        if(listner!=null)
            stageImage.addListener(listner);
        stage.addActor(stageImage);
        if(font!=null) {
            Label stagelabel = new Label(label, new Label.LabelStyle(font, Color.BLACK));
            stagelabel.setWidth(40);
            stagelabel.setHeight(40);
            stagelabel.setPosition(labelX, labelY);
            if (listner != null)
                stagelabel.addListener(listner);
            stage.addActor(stagelabel);
            //font.dispose();
            return stagelabel;
        }
        return null;
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
                screen.player2.hasRifle = false;
            } else{
                screen.player2.hasRifle = true;
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
}
