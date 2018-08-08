package com.softcube.spaceshooter.logic.input;

import android.view.MotionEvent;
import android.view.View;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.utils.Configurations;

/**
 * Created by Wilson on 6/10/16.
 */
public class VirtualJoystickInputController extends InputController {

    private float startingPositionX;
    private float startingPositionY;

    private double maxDistance;

    public VirtualJoystickInputController(View view){
        view.findViewById(R.id.vjoystick_main).setOnTouchListener(new VJoystickTouchListener());
        view.findViewById(R.id.vjoystick_touch).setOnTouchListener(new VFireButtonTouchListener());

        double pixelFactor = view.getHeight() / Configurations.PIXEL_FACTOR_CONSTANT;
        maxDistance = 50*pixelFactor;
    }

    @Override
    public void onStart() {
        /*Do nothing */
    }

    @Override
    public void onStop() {
        /*Do nothing */
    }

    @Override
    public void onPause() {
        /*Do nothing */
    }

    @Override
    public void onResume() {
        /*Do nothing */
    }

    @Override
    public void onPreUpdate() {
        /*Do nothing */
    }

    private class VJoystickTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if(action == MotionEvent.ACTION_DOWN){
                startingPositionX = event.getX(0);
                startingPositionY = event.getY(0);
            }else if(action == MotionEvent.ACTION_UP){
                horizontalFactor = 0;
                verticalFactor = 0;
            }else if(action == MotionEvent.ACTION_MOVE){

                horizontalFactor = (event.getX(0) - startingPositionX) / maxDistance;
                if(horizontalFactor > 1){
                    horizontalFactor = 1;
                }else if(horizontalFactor < -1){
                    horizontalFactor = -1;
                }

                verticalFactor = (event.getY(0) - startingPositionY) / maxDistance;
                if(verticalFactor > 1){
                    verticalFactor = 1;
                }else if(verticalFactor < -1){
                    verticalFactor = -1;
                }
            }
            return true;
        }
    }

    private class VFireButtonTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if(action == MotionEvent.ACTION_DOWN){
                isFiring = true;
            }else if(action == MotionEvent.ACTION_UP){
                isFiring = false;
            }
            return true;
        }
    }
}
