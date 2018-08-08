package com.softcube.spaceshooter.logic.input;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.softcube.spaceshooter.view.main.SpaceShooterActivity;

/**
 * Created by Wilson on 6/9/16.
 */
public class GamepadInputController extends InputController implements GamepadControllerListener {

    private final SpaceShooterActivity spaceShooterActivity;

    public GamepadInputController(SpaceShooterActivity spaceShooterActivity){
        this.spaceShooterActivity = spaceShooterActivity;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        int source = event.getSource();

        if((source & InputDevice.SOURCE_JOYSTICK) != InputDevice.SOURCE_JOYSTICK){
            return false;
        }
        horizontalFactor = event.getAxisValue(MotionEvent.AXIS_X);
        verticalFactor = event.getAxisValue(MotionEvent.AXIS_Y);

        InputDevice device = event.getDevice();
        InputDevice.MotionRange rangeX = device.getMotionRange(MotionEvent.AXIS_X, source);
        if(Math.abs(horizontalFactor) <= rangeX.getFlat()){
            horizontalFactor = event.getAxisValue(MotionEvent.AXIS_HAT_X);
            InputDevice.MotionRange rangeHatX = device.getMotionRange(MotionEvent.AXIS_HAT_X, source);

            if(Math.abs(horizontalFactor) <= rangeHatX.getFlat()){
                horizontalFactor = 0;
            }
        }

        InputDevice.MotionRange rangeY = device.getMotionRange(MotionEvent.AXIS_Y, source);
        if(Math.abs(verticalFactor) <= rangeY.getFlat()){
            verticalFactor = event.getAxisValue(MotionEvent.AXIS_HAT_Y);
            InputDevice.MotionRange rangeHatY = device.getMotionRange(MotionEvent.AXIS_HAT_Y, source);

            if(Math.abs(verticalFactor) <= rangeHatY.getFlat()){
                verticalFactor = 0;
            }
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        // The keys we want to listen to
        if (action == MotionEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                verticalFactor -= 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                verticalFactor += 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                horizontalFactor -= 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                horizontalFactor += 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_BUTTON_A) {
                isFiring = true;
                return true;
            }
        }
        else if (action == MotionEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                verticalFactor += 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                verticalFactor -= 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                horizontalFactor += 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                horizontalFactor -= 1;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_BUTTON_A) {
                isFiring = false;
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_BUTTON_B) {
                spaceShooterActivity.onBackPressed();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        spaceShooterActivity.setGamepadControllerListener(this);
    }

    @Override
    public void onStop() {
        spaceShooterActivity.setGamepadControllerListener(null);
    }

    @Override
    public void onPause() {
        /* Do nothing in this implementation */
    }

    @Override
    public void onResume() {
        horizontalFactor = 0;
        verticalFactor = 0;
    }

    @Override
    public void onPreUpdate() {
        /* Do nothing in this implementation */
    }
}
