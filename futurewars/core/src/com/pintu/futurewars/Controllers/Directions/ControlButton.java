package com.pintu.futurewars.Controllers.Directions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pintu.futurewars.Controllers.Widgets;
import com.pintu.futurewars.Controllers.InputListner.ControllerInputListner;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControlButton {

    public Image image;// = new Image();
    public ControllerInputListner inputListner;
    public int id=-1;

    public ControlButton(Widgets widgets, int buttonId, String imagePath){
        id = buttonId;
        inputListner = new ControllerInputListner(widgets,buttonId);
        Texture newTexture = new Texture(imagePath);//("controls/left.png");
        image = new Image(newTexture);
        image.addListener(inputListner);
    }
}
