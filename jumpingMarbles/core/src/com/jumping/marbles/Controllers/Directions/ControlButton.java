package com.jumping.marbles.Controllers.Directions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumping.marbles.Constants.GameConstants;
import com.jumping.marbles.Controllers.Controller;
import com.jumping.marbles.Controllers.InputListner.ControllerInputListner;

/**
 * Created by hsahu on 7/5/2017.
 */

public class ControlButton {

    public Image image;// = new Image();
    public ControllerInputListner inputListner;
    public int id=-1;

    public ControlButton(Controller controller, int buttonId, String imagePath){
        id = buttonId;
        inputListner = new ControllerInputListner(controller,buttonId);
        Texture newTexture = new Texture(imagePath);//("controls/left.png");
        image = new Image(newTexture);
        image.addListener(inputListner);
    }
}
