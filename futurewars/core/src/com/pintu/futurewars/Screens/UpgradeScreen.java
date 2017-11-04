package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameSprite;
import com.pintu.futurewars.Utility.GameUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hsahu on 9/6/2017.
 */

public class UpgradeScreen implements Screen {

    JumpingMarblesGame game = null;
    MenuStage menuStage = null;
    BitmapFont font = null;
    Skin skin;
    Stage stage = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    GameSprite gs = null;
    ImageButton previous = null;
    ImageButton previousPressed = null;
    ImageButton next = null;
    ImageButton nextPressed = null;
    ImageButton upgradeButton = null;
    ImageButton upgradeButtonPressed = null;

    ImageButton backButton = null;
    ImageButton backButtonPressed = null;

    TextButton upgradeTextButton = null;
    Map<String,String> allUpgradableGameObject = null;
    List<String> upgradableGameObjectList = null;
    Label maxQuality;
    int counter = 0;
    ProgressBar capacity;
    int totalCoins = 0;
    public UpgradeScreen(final JumpingMarblesGame game){
        this.game = game;
        menuStage = new MenuStage(game);
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);
        skin = new Skin(Gdx.files.internal("skins/comic/skin/comic-ui.json"));
        totalCoins = game.preferences.getInteger(GameConstants.PERF_COIN);
        try {
            allUpgradableGameObject = GameUtility.populateConfigurationsFromConfigFile("UpgradeRelatedResources/allUpgradableGameObject.txt");
            //upgradableGameObjectList = Utility.listFileInFolder("allUpgradableGameObject/allUpgradableGameObject");
            upgradableGameObjectList = new ArrayList<String>();
            upgradableGameObjectList.addAll(allUpgradableGameObject.keySet());
        }catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }

        EventListener el = getEvenListner(new ProcessDefinition<Boolean,Event>(){
            @Override
            public Boolean definition(Event event) {
                if(event.toString().equals("touchDown")){
                    previous.setVisible(false);
                    previousPressed.setVisible(true);
                }else if(event.toString().equals("touchUp")){
                    previous.setVisible(true);
                    previousPressed.setVisible(false);
                    counter--;
                    counter = counter==-1?upgradableGameObjectList.size()-1:counter;
                    addAnimation(allUpgradableGameObject.get(upgradableGameObjectList.get(counter)),stage.getWidth()/2-200,stage.getHeight()-400);

                }
                return true;
            }
        });

        EventListener elNext = getEvenListner(new ProcessDefinition<Boolean,Event>(){
            @Override
            public Boolean definition(Event event) {
                if(event.toString().equals("touchDown")){
                    next.setVisible(false);
                    nextPressed.setVisible(true);
                }else if(event.toString().equals("touchUp")){
                    next.setVisible(true);
                    nextPressed.setVisible(false);
                    counter++;
                    counter = counter%upgradableGameObjectList.size();
                    addAnimation(allUpgradableGameObject.get(upgradableGameObjectList.get(counter)),stage.getWidth()/2-200,stage.getHeight()-400);

                }
                return true;
            }
        });

        EventListener upgradeLister = getEvenListner(new ProcessDefinition<Boolean,Event>(){
            @Override
            public Boolean definition(Event event) {
                if(event.toString().equals("touchDown")){
                    upgradeButton.setVisible(false);
                    upgradeButtonPressed.setVisible(true);
                    //menuStage.totalCoins = totalCoins
                }else if(event.toString().equals("touchUp")){
                    upgradeButton.setVisible(true);
                    upgradeButtonPressed.setVisible(false);
                }
                return true;
            }
        });
        EventListener backLister = getEvenListner(new ProcessDefinition<Boolean,Event>(){
            @Override
            public Boolean definition(Event event) {
                if(event.toString().equals("touchDown")){
                    backButton.setVisible(false);
                    backButtonPressed.setVisible(true);
                }else if(event.toString().equals("touchUp")){
                    backButton.setVisible(true);
                    backButtonPressed.setVisible(false);
                    game.setScreen(game.getStagesScreen());
                }
                return true;
            }
        });

        previous = addImageButton("previous","imgs/previous.png",el,200,stage.getHeight()-200,100,100);
        previousPressed = addImageButton("previousPressed","imgs/previousPressed.png",el,200,stage.getHeight()-200,100,100);
        next = addImageButton("previous","imgs/next.png",elNext,stage.getWidth() - 200 - 100,stage.getHeight()-200,100,100);
        nextPressed = addImageButton("previousPressed","imgs/nextPressed.png",elNext,stage.getWidth() - 200 - 100,stage.getHeight()-200,100,100);

        previousPressed.setVisible(false);
        nextPressed.setVisible(false);

        upgradeButton = addImageButton("Upgrade","imgs/upgrade.png",
                                        upgradeLister,stage.getWidth()-200-200,stage.getHeight()-500,200,200);
        upgradeButtonPressed = addImageButton("UpgradePressed","imgs/upgradePressed.png",
                                        upgradeLister,stage.getWidth()-200-200,stage.getHeight()-500,200,200);
        upgradeButtonPressed.setVisible(false);


        upgradeTextButton = new TextButton("Upgrade",skin,"default");
        upgradeTextButton.setPosition(stage.getWidth()-200-200,stage.getHeight()-500);
        /*upgradeTextButton.setWidth(300);
        upgradeTextButton.setHeight(300);*/
        upgradeTextButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(event.toString().equals("touchUp")){
                    totalCoins -=100;
                    game.preferences.putInteger(GameConstants.PERF_COIN,totalCoins);
                    game.preferences.flush();
                    menuStage.totalCoins = totalCoins;
                }
                return true;
            }
        });


        backButton = addImageButton("BackButton","imgs/backButton.png",
                backLister,200,200,800,200);
        backButtonPressed = addImageButton("BackButtonPressed","imgs/backButtonPressed.png",
                backLister,200,200,800,200);
        backButtonPressed.setVisible(false);

        maxQuality = new Label("Max Quality",skin,"title");
        maxQuality.setPosition(200,stage.getHeight()-500);

    }

    public ImageButton addImageButton(String name,
                                      String imageURL,
                                      EventListener listner,
                                      float xPos, float yPos,
                                      float width,float height){
        ImageButton button = null;
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(imageURL)));

        button = new ImageButton(drawable);
        button.setName(name);
        button.getImage().setName(name);
        button.addListener(listner);
        button.setSize(width,height);
        button.setPosition(xPos, yPos);
        return button;
    }

    public EventListener getEvenListner(final ProcessDefinition pd){
        EventListener previousEL = new EventListener() {
            @Override
            public boolean handle(Event event) {
                pd.definition(event);
                return true;
            }
        };
        return previousEL;
    }

    public abstract class ProcessDefinition <T,K>{
        abstract public T definition(K k);
    }

    @Override
    public void show() {

        stage.clear();
        Gdx.input.setInputProcessor(stage);
        counter = 0;
        stage.addActor(previous);
        stage.addActor(previousPressed);
        stage.addActor(next);
        stage.addActor(nextPressed);
        addAnimation(allUpgradableGameObject.get(upgradableGameObjectList.get(counter)),stage.getWidth()/2-200,stage.getHeight()-400);
        stage.addActor(maxQuality);
        //stage.addActor(upgradeButton);
        //stage.addActor(upgradeButtonPressed);
        stage.addActor(upgradeTextButton);
        stage.addActor(backButton);
        stage.addActor(backButtonPressed);

        addCapacityBar();
        menuStage.show();
    }

    public void update(float dt){
        if(gs !=null) {
            gs.updateSprite(dt);
        }
        capacity.setValue(.2f);
        menuStage.update();
        menuStage.act();
        stage.act();
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(.4f,.6f,.5f,1);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        if(gs!=null) {
            gs.draw(game.batch);
        }
        menuStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        menuStage.dispose();
        GameUtility.log(this.getClass().getName(), "Disposed");
    }


    public void addAnimation(String porpertiesFile,float x,float y){
        try {
            gs = new GameSprite(porpertiesFile, x, y);
            gs.sprite.setBounds(0,0,300,300);
        }catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }
    }


    private Label addCapacityBar(){
       /* //===================Replace the label Live with some m_offImage==============================
        Label capacityLabel = new Label("Life", new Label.LabelStyle(GameUtility.getFonts("fonts/leadcoat.ttf",30,Color.BLACK),Color.BLACK));
        capacityLabel.setWidth(10);
        capacityLabel.setHeight(10);
        capacityLabel.setPosition(10,stage.getHeight()-250);
        stage.addActor(capacityLabel);
        //===================End of Replace the label live with some m_offImage=======================*/

        Pixmap pixmap = new Pixmap(100, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(100, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        capacity = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);

        capacity.setValue(1.0f);
        capacity.setAnimateDuration(0.025f);
        capacity.setBounds(10, 10, 400, 50);
        capacity.setFillParent(true);
        capacity.setPosition(530,stage.getHeight()-500);

        //return capacity;
        stage.addActor(capacity);
        return null;
    }
}
