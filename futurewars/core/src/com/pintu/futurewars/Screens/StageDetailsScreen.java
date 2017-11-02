package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameSprite;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class StageDetailsScreen implements Screen {

    JumpingMarblesGame game = null;
    BitmapFont font = null;
    Stage stage = null;
    OrthographicCamera ctrlCam = null;
    FitViewport cViewPort = null;
    GameSprite gs = null;
    TextButton tb = null;
    ImageButton previous;


    public TmxMapLoader mapLoader = null;
    public TiledMap map = null;
    //public OrthogonalTiledMapRenderer renderer = null;

    public StageDetailsScreen(JumpingMarblesGame game){
        this.game = game;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort,game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(GameConstants.STAGES_DETAILS_MAP);

        Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        //skin.add("logo", new Texture("imgs/welcome.png"));
        tb = new TextButton("Demo",skin,"toggle");
        tb.setSize(200,100);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("imgs/speed.png")));
        previous = new ImageButton(drawable);
        previous.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if ((event + "").equals("touchDown")){
                    Gdx.app.log("33333333", event + "");
                }
                return true;
            }
        });
        previous.setSize(100,100);


        //renderer = new OrthogonalTiledMapRenderer(map,1);
        initFonts();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        try {
            GameUtility.gameObjectCreator.populateStageDescDetails(game.selectedStage);
        }catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }

        for (MapObject object : map.getLayers().get("placeHolders").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse eclipse = ((EllipseMapObject) object).getEllipse();
            if(object.getName().equals(GameConstants.STAGES_DETAILS_TITLE)){
                addText(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_TITLE)
                        ,font,eclipse.x,eclipse.y,100,100,Color.BLACK);
            }else if(object.getName().equals(GameConstants.STAGES_DETAILS_REWARD)){
                addText(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_REWARD)
                        ,font,eclipse.x,eclipse.y,100,100,Color.BLACK);
            }else if(object.getName().equals(GameConstants.STAGES_DETAILS_DIFFICULTY)){
                addText(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_DIFFICULTY)
                        ,font,eclipse.x,eclipse.y,100,100,Color.BLACK);
            }else if(object.getName().equals(GameConstants.STAGES_DETAILS_LOCATION)){
                addText(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_LOCATION)
                        ,font,eclipse.x,eclipse.y,100,100,Color.BLACK);
            }else if(object.getName().equals(GameConstants.STAGES_DETAILS_DESCRIPTION)){
                addText(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_DESCRIPTION)
                        ,font,eclipse.x,eclipse.y,100,100,Color.BLACK);
            } else if(object.getName().equals(GameConstants.STAGES_DETAILS_PLAY)) {
                addButton("Accept",font,eclipse.x,eclipse.y,100,100,
                        new StageListner(this,game.getNewGameScreen(game.selectedStage)),Color.BLACK);
            }else if(object.getName().equals(GameConstants.STAGES_DETAILS_CANCEL)) {
                addButton("Cancel",font,eclipse.x,eclipse.y,100,100,
                        new StageListner(this,game.getStagesScreen()),Color.BLACK);
            }
        }
        addAnimation(GameUtility.gameObjectCreator.stageDetailsMap.get(GameConstants.STAGES_DETAILS_ANIMATION),200,800);
        stage.addActor(previous);
        //stage.addActor(tb);
    }

    public void update(float dt){
        if(gs !=null) {
            gs.updateSprite(dt);
        }
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
        font.dispose();
        stage.dispose();
        map.dispose();

        GameUtility.log(this.getClass().getName(), "Disposed");
    }
    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 50;
        params.color = Color.BLACK;

        font = generator.generateFont(params);
    }

    public void addText(String text, BitmapFont font, float x, float y, float width, float height,
                        Color color){
        Label stagelabel = new Label(text,new Label.LabelStyle(font,color));
        stagelabel.setWidth(width);
        stagelabel.setHeight(height);
        stagelabel.setPosition(x,y);
        stage.addActor(stagelabel);
    }

    public void addAnimation(String porpertiesFile,float x,float y){
        try {
            gs = new GameSprite(porpertiesFile, x, y);
        }catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }
    }

    public void addButton(String buttonText,
                          BitmapFont font,
                          float x,
                          float y,
                          float width,
                          float height,
                          StageListner stageListner,
                          Color color){

        Label stagelabel = new Label(buttonText,new Label.LabelStyle(font,color));
        stagelabel.setWidth(width);
        stagelabel.setHeight(height);
        stagelabel.setPosition(x,y);
        stagelabel.setColor(Color.BLUE);
        stagelabel.addListener(stageListner);
        stage.addActor(stagelabel);

    }

    public class StageListner extends InputListener{

        public Screen selectedScreen = null;
        StageDetailsScreen pauseScreen = null;
        public StageListner(StageDetailsScreen pauseScreen, Screen screen){
            this.pauseScreen = pauseScreen;
            selectedScreen = screen;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //stageScreen.show();
            if(selectedScreen!=null)
                game.setScreen(selectedScreen);
            //pauseScreen.dispose();
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //addLoadingLabel();
            return true;
        }
    }
}
