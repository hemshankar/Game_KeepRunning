package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
/**
 * Created by hsahu on 6/21/2017.
 */

public class GdxUtil {

    public static void clearScrean(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void draw(SpriteBatch batch, Sprite sprite){
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public static void draw(SpriteBatch batch, BitmapFont font, String str, int x, int y){
        batch.begin();
        font.draw(batch,str,x,y,500,10,true);
        batch.end();
    }

    public static void pack (String inputDir, String outputDir, String packFileName) throws Exception {
        TexturePacker.process(inputDir, outputDir, packFileName);
    }

}
