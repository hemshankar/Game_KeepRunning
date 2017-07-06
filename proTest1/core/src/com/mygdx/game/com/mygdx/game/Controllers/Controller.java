package com.mygdx.game.com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.com.mygdx.game.Constants.GameConstants;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Direction;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Down;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Left;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Right;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Up;

/**
 * Created by hsahu on 7/5/2017.
 */

public class Controller {
    Viewport cViewPort;
    Stage stage;
    public boolean [] direction = {false,false,false,false};//upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera ctrlCam;

    public Controller(SpriteBatch batch){
        ctrlCam = new OrthographicCamera();
        cViewPort = new FitViewport(GameConstants.VIEW_PORT_WIDTH/ GameConstants.PPM,
                GameConstants.VIEW_PORT_HIGHT/GameConstants.PPM,ctrlCam);
        stage = new Stage(cViewPort, batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();

        Direction up = new Up(this);
        Direction down = new Down(this);
        Direction left = new Left(this);
        Direction right = new Right(this);

        float w = 200/GameConstants.PPM;
        float h = 200/GameConstants.PPM;

        table.add();
        table.add(up.image).size(w,h);
        table.add();

        table.row().pad(5,5,5,5);

        table.add(left.image).size(w,h);
        table.add();
        table.add(right.image).size(w,h);

        table.row().padBottom(5);

        table.add();
        table.add(down.image).size(w,h);
        table.add();

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int w,int h){
        cViewPort.update(w,h);
    }

}
