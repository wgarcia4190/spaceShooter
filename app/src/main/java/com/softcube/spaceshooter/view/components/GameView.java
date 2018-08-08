package com.softcube.spaceshooter.view.components;

import android.content.Context;

import com.softcube.spaceshooter.entities.GameObject;

import java.util.List;

/**
 * Created by Wilson on 6/8/16.
 */
public interface GameView {

    public void draw();

    public void setGameObjects(List<List<GameObject>> gameObjects);

    public int getWidth();

    public int getHeight();

    public int getPaddingLeft();

    public int getPaddingRight();

    public int getPaddingTop();

    public int getPaddingBottom();

    public Context getContext();

    public void postInvalidate();
}
