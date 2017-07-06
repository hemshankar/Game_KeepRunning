package com.mygdx.game.com.mygdx.game.Controllers.InputListner;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.com.mygdx.game.Controllers.Directions.Direction;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControllerInputListner extends InputListener {

    public Direction dir;
    public ControllerInputListner(Direction direction){
        dir = direction;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        dir.controller.direction[dir.id] = false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        dir.controller.direction[dir.id] = true;
        return true;
    }
}
