package com.softcube.spaceshooter.logic.input;

import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Wilson on 16/03/15.
 */
public interface GamepadControllerListener {
    public boolean dispatchGenericMotionEvent(MotionEvent event);
    public boolean dispatchKeyEvent(KeyEvent event);
}
