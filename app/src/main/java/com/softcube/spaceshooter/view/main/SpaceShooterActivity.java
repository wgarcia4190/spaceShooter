package com.softcube.spaceshooter.view.main;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.application.SpaceShooterBaseFragment;
import com.softcube.spaceshooter.logic.input.GamepadControllerListener;

public class SpaceShooterActivity extends FragmentActivity {

    private static final String TAG_FRAGMENT = "content";

    private GamepadControllerListener gamepadControllerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_shooter);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        final SpaceShooterBaseFragment fragment = (SpaceShooterBaseFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if(gamepadControllerListener != null){
            if(gamepadControllerListener.dispatchGenericMotionEvent(event)){
                return true;
            }
        }
        return super.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent (KeyEvent event) {
        if(gamepadControllerListener != null){
            if(gamepadControllerListener.dispatchKeyEvent(event)){
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /* Public Methods */

    public void navigateBack() {
        getFragmentManager().popBackStack();
    }

    public void startGame(){
        navigateToFragment( new GameFragment());
    }

    /* End Public Methods */

    /* Private Methods */

    private void navigateToFragment(SpaceShooterBaseFragment dst) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, dst, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    /* End Private Methods */

    /* Getters and Setter */

    public void setGamepadControllerListener(GamepadControllerListener gamepadControllerListener) {
        this.gamepadControllerListener = gamepadControllerListener;
    }

    /* End Getters and Setter */
}
