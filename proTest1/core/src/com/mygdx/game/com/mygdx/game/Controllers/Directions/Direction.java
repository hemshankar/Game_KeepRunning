package com.mygdx.game.com.mygdx.game.Controllers.Directions;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.com.mygdx.game.Controllers.Controller;
import com.mygdx.game.com.mygdx.game.Controllers.InputListner.ControllerInputListner;

/**
 * Created by hsahu on 7/5/2017.
 */

abstract public class Direction {

    public Image image;// = new Image();
    public ControllerInputListner inputListner = new ControllerInputListner(this);
    public int id=-1;
    public Controller controller;


    public Direction(Controller controller){
        this.controller = controller;
    }
}
