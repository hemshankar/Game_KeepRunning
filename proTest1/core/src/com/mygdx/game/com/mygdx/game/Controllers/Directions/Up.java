package com.mygdx.game.com.mygdx.game.Controllers.Directions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.com.mygdx.game.Constants.GameConstants;
import com.mygdx.game.com.mygdx.game.Controllers.Controller;

/**
 * Created by hsahu on 7/5/2017.
 */

public class Up extends Direction {
    public Up(Controller controller){
        super(controller);
        id = GameConstants.UP;
        Texture newTexture = new Texture("controls/up.png");
        image = new Image(newTexture);
        image.addListener(inputListner);
        image.setSize(200/ GameConstants.PPM,200/GameConstants.PPM);
    }
}
