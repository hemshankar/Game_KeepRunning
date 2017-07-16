package com.pintu.futurewars.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pintu.futurewars.Constants.GameConstants;
import com.pintu.futurewars.Controllers.Directions.ControlButton;

/**
 * Created by hsahu on 7/5/2017.
 */

public class Controller {
    Viewport cViewPort;
    Stage stage;
    public boolean [] controles = {false,false,false,false,false};
                                //upPressed, downPressed, leftPressed, rightPressed,throwPressed;
    OrthographicCamera ctrlCam;
    SpriteBatch batch;
    public Controller(SpriteBatch batch){
        this.batch = batch;
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH,
                GameConstants.VIEW_PORT_HIGHT,ctrlCam);
        stage = new Stage(cViewPort, this.batch);
        Gdx.input.setInputProcessor(stage);

        //create directions
        Table directions = new Table();

        directions.setFillParent(true);
        directions.left().bottom();
        ControlButton up = new ControlButton(this, GameConstants.UP, GameConstants.UP_IMAGE);
        ControlButton down = new ControlButton(this, GameConstants.DOWN, GameConstants.DOWN_IMAGE);
        ControlButton left = new ControlButton(this, GameConstants.LEFT, GameConstants.LEFT_IMAGE);
        ControlButton right = new ControlButton(this, GameConstants.RIGHT, GameConstants.RIGHT_IMAGE);


        float w = GameConstants.CONTROL_BUTTON_SIZE;
        float h = GameConstants.CONTROL_BUTTON_SIZE;

        directions.add();
        directions.add(up.image).size(w,h);
        directions.add();

        directions.row().pad(5,5,5,5);

        directions.add(left.image).size(w,h);
        directions.add();
        directions.add(right.image).size(w,h);

        directions.row().padBottom(5);

        directions.add();
        directions.add(down.image).size(w,h);
        directions.add();


        //create power controls
        Table powers = new Table();
        powers.setFillParent(true);
        powers.right().bottom();

        ControlButton FireButton = new ControlButton(this, GameConstants.THROW_SUCKER, GameConstants.POWER_IMAGE);
        //powers.add();

        powers.add(FireButton.image).size(GameConstants.FIRE_BUTTON_SIZE,GameConstants.FIRE_BUTTON_SIZE);
        powers.add();

        powers.row().pad(5,5,100,5);

        powers.add();
        powers.add();
        powers.add();



        //add all tables
        stage.addActor(powers);
        stage.addActor(directions);

    }

    public void draw(){
        stage.draw();
    }

    public void resize(int w,int h){
        cViewPort.update(w,h);
    }

}
