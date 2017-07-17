package com.pintu.futurewars.Controllers.InputListner;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pintu.futurewars.Controllers.Controller;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControllerInputListner extends InputListener {

    public Controller controller;
    public int buttonId;
    public ControllerInputListner(Controller ctrl, int id){
        controller = ctrl;
        buttonId = id;

    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        controller.x = x;
        controller.y = y;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        controller.controles[buttonId] = false;
        controller.x = 0;
        controller.y = 0;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        controller.controles[buttonId] = true;
        controller.x = x;
        controller.y = y;
        return true;
    }
}
