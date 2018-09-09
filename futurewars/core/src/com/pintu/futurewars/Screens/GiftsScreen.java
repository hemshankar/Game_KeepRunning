package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameSprite;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class GiftsScreen extends BaseUIScreen {

    public Integer numOfGifts;
    final Table scrollTable = new Table();
    ScrollPane scroller = null;
    Table table = null;
    GameSprite gs;
    boolean isGiftsList = true;
    Group gfitDetails = null;
    public GiftsScreen(JumpingMarblesGame game){
        super(game,GameConstants.COMIC_SKIN);
    }

    public void addItems(){
        numOfGifts = game.preferences.getInteger(GameConstants.TOTAL_GIFTS);
        if(numOfGifts==0){//to test.
            numOfGifts = 1;
        }

        scrollTable.pad(100,0,100,0);

        for(int i=0;i<numOfGifts;i++) {
            Texture texture = new Texture(Gdx.files.internal("imgs/giftsMedium.png"));
            Image image = new Image(texture);
            image.setSize(200,200);

            image.addListener(new ItemDialogListner(null));
            scrollTable.row().pad(50,50,50,50);
            scrollTable.add(image);
            scrollTable.row().pad(50,50,50,50);

        }

        scroller = new ScrollPane(scrollTable);

        table = new Table();
        table.setFillParent(true);
        table.add(scroller);//.fill().expand();
        //table.setBounds(0,0,500,700);
        table.setSize(500,500);
        Drawable d = new TextureRegionDrawable(new TextureRegion(new Texture("imgs/sky1.png")));
        table.setBackground(d);
        //table.setPosition(900,100);
        this.stage.addActor(table);

        Texture texture = new Texture(Gdx.files.internal("imgs/backButton.png"));
        Image backButton = new Image(texture);
        backButton.setSize(200,100);
        backButton.setPosition(50,50);
        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if((event + "").equals("touchDown")) {
                    if(isGiftsList) {
                        game.setScreen(game.getStagesScreen());
                    }else{
                        stage.getActors().removeValue(gfitDetails,false);
                        table.setVisible(true);
                        gs.hide();
                    }
                }
                return true;
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        super.show();
        stage.clear();
        addItems();
    }

    public class StageListner extends InputListener{

        String stage=null;
        public StageListner(String stage){
            this.stage = stage;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(GameConstants.HOME_SCREEN.equals(stage)) {
                game.setScreen(game.getStagesScreen());
            }else{
                game.setScreen(game.getNewGameScreen(stage));
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
    }

    public class ItemDialogListner extends InputListener{
        Dialog diaglog =null;
        public ItemDialogListner(Dialog d){
            this.diaglog = d;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            stage.addActor(showGift());
            //stage.getActors().removeValue(table,false);
            table.setVisible(false);
            isGiftsList = false;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
    }

   /* public Dialog giftsDialog(){
        Dialog dialog = new Dialog("", skin, "default") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        //dialog.text("Are you sure you want to quit?");
        //dialog.button("Yes", true); //sends "true" as the result
        //dialog.button("No", false);  //sends "false" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        Texture t = new Texture("imgs/giftsBig.png");
        Image i = new Image(t);
        dialog.add(i);

        //Time remaining
        Label timeRemaining = new Label("Time Remaining",skin,"big");
        dialog.add(timeRemaining);

        //Open

        Image openImage = new Image( new Texture("imgs/replay.png"));
        openImage.setSize(100,100);
        openImage.setBounds(0,0,100,100);
        dialog.add(openImage);

        Image adsImage = new Image( new Texture("imgs/next.png"));
        adsImage.setSize(100,100);
        adsImage.setBounds(0,0,100,100);
        dialog.add(adsImage);


        return dialog;
    }
*/
    public Group showGift(){
        gfitDetails = new Group();
        float giftX = 600;
        float giftY = 350;

        float giftBackX = giftX - 220;
        float giftBackY = giftY - 220;

        Texture t = new Texture("imgs/giftsBig.png");
        Image i = new Image(t);
        i.setPosition(giftX,giftY);
        addBackground(addBackGround(GameConstants.BACKGROUND5_PROPERTY_FILE,giftBackX,giftBackY));
        gfitDetails.addActor(i);

        //Time remaining
        Label timeRemaining = new Label("Opens in 1 minute 40 sec",skin,"big");
        timeRemaining.setPosition(100,900);
        gfitDetails.addActor(timeRemaining);

        //Open
        Image openImage = new Image( new Texture("imgs/replay.png"));
        openImage.setSize(100,100);
        openImage.setPosition(600,100);

        gfitDetails.addActor(openImage);

        Image adsImage = new Image( new Texture("imgs/next.png"));
        adsImage.setSize(100,100);
        adsImage.setPosition(800,100);


        gfitDetails.addActor(adsImage);

        return gfitDetails;
    }

    public GameSprite addBackGround(String porpertiesFile, float x, float y){
        try {
            if(gs == null) {
                gs = new GameSprite(porpertiesFile, x, y);
            }
            gs.show();
        }catch(Exception e){
            GameUtility.log(this.getClass().getName(),e.getMessage());
        }
        return gs;
    }

}
