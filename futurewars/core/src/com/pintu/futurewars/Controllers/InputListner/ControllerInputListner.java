package com.pintu.futurewars.Controllers.InputListner;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pintu.futurewars.Controllers.Widgets_old;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControllerInputListner extends InputListener {

    public Widgets_old widgets;
    public int buttonId;
    public ControllerInputListner(Widgets_old ctrl, int id){
        widgets = ctrl;
        buttonId = id;

    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        widgets.x = x;
        widgets.y = y;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        widgets.controles[buttonId] = false;
        widgets.x = 0;
        widgets.y = 0;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        widgets.controles[buttonId] = true;
        widgets.x = x;
        widgets.y = y;
        return true;
    }
}
