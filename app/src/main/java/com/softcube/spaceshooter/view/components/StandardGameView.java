package com.softcube.spaceshooter.view.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.softcube.spaceshooter.entities.GameObject;

import java.util.List;

/**
 * Created by Wilson on 6/10/16.
 */
public class StandardGameView extends View implements GameView {

    private List<List<GameObject>> layers;

    public StandardGameView(Context context) {
        super(context);
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void draw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (layers){
            int numLayers = layers.size();

            for(int index = 0; index < numLayers; index++){
                List<GameObject> currentLayer = layers.get(index);

                int numObjects = currentLayer.size();
                for(int subIndex = 0; subIndex < numObjects; subIndex++){
                    currentLayer.get(subIndex).onDraw(canvas);
                }
            }
        }
    }

    @Override
    public void setGameObjects(List<List<GameObject>> gameObjects) {
        this.layers = gameObjects;
    }
}
