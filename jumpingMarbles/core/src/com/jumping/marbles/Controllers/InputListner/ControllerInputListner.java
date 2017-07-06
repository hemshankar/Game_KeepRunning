package com.jumping.marbles.Controllers.InputListner;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.jumping.marbles.Controllers.Controller;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControllerInputListner extends InputListener {

    public Controller controller;
    public int buttonId;
    public ControllerInputListner(Controller ctrl,int id){
        controller = ctrl;
        buttonId = id;

    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        controller.controles[buttonId] = false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        controller.controles[buttonId] = true;
        return true;
    }
}
