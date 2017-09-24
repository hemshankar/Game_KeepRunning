package com.pintu.futurewars.Utility;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.pintu.futurewars.commons.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsahu on 9/24/2017.
 */

public class ShapeHelper {

    List<GameShape> shapes = new ArrayList<GameShape>();

    public final String SHAPE_SYNC_CONST = "SHAPE_SYNC_CONST";

    public GameShape drawLine(float x1, float y1,float x2, float y2,Float width,Color c1,Color c2){
        synchronized (SHAPE_SYNC_CONST) {
            GameShape shape = new GameShape();
            shape.type = ShapeType.Line;
            shape.x1 = x1;
            shape.y1 = y1;
            shape.x2 = x2;
            shape.y2 = y2;
            shape.c1 = c1;
            shape.c2 = c2;
            if (width != null) {
                shape.width = width;
            }
            shapes.add(shape);
            shape.drawType = GameShape.DrawType.BETWEEN_POINTS;
            return shape;
        }
    }

    public GameShape drawLine(GameObject a, GameObject b,Float width,Color c1,Color c2){
        GameShape shape = new GameShape();
        shape.type = ShapeType.Filled;
        shape.a = a;
        shape.b = b;
        shape.c1 = c1;
        shape.c2 = c2;
        shape.width = width;
        shape.drawType = GameShape.DrawType.BETWEEN_OBJECTS;
        shapes.add(shape);
        return shape;
    }

    public void removeShape(GameShape s){
        if(s==null)
            return;
        synchronized (SHAPE_SYNC_CONST) {
            s.a = null;
            s.b = null;
            shapes.remove(s);
        }
    }

    public void drawShapes(ShapeRenderer shapeRenderer, Camera camera){
        synchronized (SHAPE_SYNC_CONST) {
            for (GameShape shape : shapes) {
                shape.draw(shapeRenderer, camera);
            }
        }
    }

    public class GameShape{

        public class DrawType{
            public static final String BETWEEN_OBJECTS = "BETWEEN_OBJECTS";
            public static final String BETWEEN_POINTS = "BETWEEN_POINTS";
        }

        float x1, y1, x2, y2, width=.1f, radius;
        Color c1;
        Color c2;
        ShapeType type;
        String drawType;
        GameObject a,b;

        public void draw(ShapeRenderer shapeRenderer, Camera camera){
            if(drawType.equals(DrawType.BETWEEN_OBJECTS)){
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(type);
                shapeRenderer.setColor(1, 1, 1, 1);
                shapeRenderer.rectLine(a.getBody().getPosition().x, a.getBody().getPosition().y
                            , b.getBody().getPosition().x, b.getBody().getPosition().y,width);
                /*if(type == ShapeType.Line)
                    shapeRenderer.rectLine(x1,y1,x2,y2,width,c1,c2);*/

                //shapeRenderer.rect(x, y, width, height);
                //shapeRenderer.circle(x, y, radius);
                shapeRenderer.end();

            /*shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.circle(x, y, radius);
            shapeRenderer.end();*/
            }
        }
    }

}
