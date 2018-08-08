package com.softcube.spaceshooter.logic.input;

import android.view.View;

import com.softcube.spaceshooter.view.main.SpaceShooterActivity;

/**
 * Created by Wilson on 6/10/16.
 */
public class CompositeInputController extends InputController {

    private GamepadInputController gamepadInputController;
    private VirtualJoystickInputController virtualJoystickInputController;

    public CompositeInputController(View view, SpaceShooterActivity activity){
        gamepadInputController = new GamepadInputController(activity);
        virtualJoystickInputController = new VirtualJoystickInputController(view);
    }

    @Override
    public void onStart() {
        gamepadInputController.onStart();
        virtualJoystickInputController.onStart();
    }

    @Override
    public void onStop() {
        gamepadInputController.onStop();
        virtualJoystickInputController.onStop();
    }

    @Override
    public void onPause() {
        gamepadInputController.onPause();
        virtualJoystickInputController.onPause();
    }

    @Override
    public void onResume() {
        gamepadInputController.onResume();
        virtualJoystickInputController.onResume();
    }

    @Override
    public void onPreUpdate() {
        isFiring = gamepadInputController.isFiring || virtualJoystickInputController.isFiring;
        horizontalFactor = gamepadInputController.horizontalFactor + virtualJoystickInputController.horizontalFactor;
        verticalFactor = gamepadInputController.verticalFactor + virtualJoystickInputController.verticalFactor;
    }
}
