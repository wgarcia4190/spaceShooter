package com.softcube.spaceshooter.logic.input;

/**
 * Created by Wilson on 10/03/15.
 */
public abstract class InputController {

    public double horizontalFactor;
    public double verticalFactor;
    public boolean isFiring;

    public abstract void onStart();
    public abstract void onStop();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onPreUpdate();


}
