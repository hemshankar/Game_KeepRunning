package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class GiftsScreen extends BaseUIScreen {

    public Integer numOfGifts;
    public GiftsScreen(JumpingMarblesGame game){
        super(game,GameConstants.COMIC_SKIN);
    }

    public void addItems(){
        numOfGifts = game.preferences.getInteger(GameConstants.PERF_GIFT);
        if(numOfGifts==0){//to test.
            numOfGifts = 10;
        }
        final Table scrollTable = new Table();
        scrollTable.pad(100,0,100,0);

        for(int i=0;i<numOfGifts;i++) {
            Texture texture = new Texture(Gdx.files.internal("imgs/giftsBig.png"));
            Image image = new Image(texture);
            image.setSize(200,200);
            image.addListener(new ItemDialogListner(giftsDialog()));
            scrollTable.add(image);
            scrollTable.row();

        }

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
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
        backButton.setSize(200,200);
        backButton.setPosition(200,200);
        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //game.setScreen(game.getStagesScreen());
                return true;
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        super.show();
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
            diaglog.show(stage);
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
    }

    public Dialog giftsDialog(){
        Dialog dialog = new Dialog("Warning", skin, "default") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        //dialog.text("Are you sure you want to quit?");
        dialog.button("Yes", true); //sends "true" as the result
        //dialog.button("No", false);  //sends "false" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        Drawable d = new TextureRegionDrawable(new TextureRegion(new Texture("imgs/giftsBig.png")));
        dialog.background(d);

        return dialog;
    }

}
