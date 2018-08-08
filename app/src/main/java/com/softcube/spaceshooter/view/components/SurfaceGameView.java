package com.softcube.spaceshooter.view.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.softcube.spaceshooter.entities.GameObject;

import java.util.List;

/**
 * Created by Wilson on 6/10/16.
 */
public class SurfaceGameView extends SurfaceView implements SurfaceHolder.Callback, GameView {

    private List<List<GameObject>> layers;
    private boolean ready;


    public SurfaceGameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ready = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ready = false;
    }

    @Override
    public void draw() {
        if(!ready){
            return;
        }

        Canvas canvas = getHolder().lockCanvas();
        if(canvas == null){
            return;
        }

        canvas.drawRGB(0, 0, 0);
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
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void setGameObjects(List<List<GameObject>> gameObjects) {
        layers = gameObjects;
    }
}
