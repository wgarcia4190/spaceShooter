package com.softcube.spaceshooter.entities;

import android.graphics.Canvas;

import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.GameEvent;

/**
 * Created by Wilson on 03/03/15.
 */
public abstract class GameObject {

    public int layer;
    public final Runnable addToGameUiThreadRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public final Runnable removeFromGameUiThreadRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void startGame(GameEngine gameEngine){}
    public void onUpdate(long elapseTimeMillis, GameEngine gameEngine){}
    public void onDraw(Canvas canvas){}
    public void onAddedToGameUiThread(){}
    public void onRemovedFromGameUiThread(){}
    public void onAddedToGameEngine(){}
    public void onRemovedFromGameEngine(){}
    public void onPostUpdate(GameEngine gameEngine){}
    public void onGameEvent(GameEvent gameEvent){}

    public void addToGameEngine(GameEngine gameEngine, int layer){
        gameEngine.addGameObject(this, layer);
    }

    public void removeFromGameEngine(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
    }

}
