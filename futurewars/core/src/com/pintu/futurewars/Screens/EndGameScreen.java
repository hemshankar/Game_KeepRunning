package com.pintu.futurewars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.JumpingMarblesGame;
import com.pintu.futurewars.Utility.GameUtility;

/**
 * Created by hsahu on 9/6/2017.
 */

public class EndGameScreen extends BaseUIScreen {

    public String currentStage;
    
    public EndGameScreen(JumpingMarblesGame game,String curStage){
        super(game,GameConstants.COMIC_SKIN);
        currentStage = curStage;
    }

    public void addItems(){

        StageItem item = new StageItem(0,0,0,0,200,200,650, 820);
        addItem(null,"Game Over",null,item,skin,"title");

        item = new StageItem(80,80,600,700,200,200,800, 650);
        addItem("imgs/coin.png","" + GameUtility.game.preferences.getInteger(GameConstants.THIS_STAGE_TOTAL_COIN) + " coins",null,item,skin,"big");

        item = new StageItem(80,80,600,600,200,200,800, 550);
        addItem("imgs/speed.png","" + GameUtility.game.preferences.getInteger(GameConstants.THIS_STAGE_MAX_SPEED) + " kmph",null,item,skin,"big");

        item = new StageItem(100,100,600,500,200,200,800, 450);
        addItem("imgs/onGun.png","" + GameUtility.game.preferences.getInteger(GameConstants.THIS_STAGE_TOTAL_KILLS) + " kills",null,item,skin,"big");

        item = new StageItem(100,100,600,300,20,20,600,250);
        addItem("imgs/replay.png","Replay",new StageListner(currentStage),item,skin,"big");

        item = new StageItem(100,100,800,300,20,20,800,250);
        addItem("imgs/home.png",GameConstants.HOME_SCREEN,new StageListner(GameConstants.HOME_SCREEN),item,skin,"big");

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
}
