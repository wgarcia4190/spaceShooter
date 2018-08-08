package com.softcube.spaceshooter.view.main;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.application.SpaceShooterBaseFragment;
import com.softcube.spaceshooter.entities.not_visual.GameController;
import com.softcube.spaceshooter.entities.visual.ParallaxBackground;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.logic.input.CompositeInputController;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.view.components.GameView;
import com.softcube.spaceshooter.view.main.dialogs.PauseDialog;
import com.softcube.spaceshooter.view.main.dialogs.listeners.GameOverDialogListener;
import com.softcube.spaceshooter.view.main.dialogs.listeners.PauseDialogListener;

/**
 * Created by Wilson on 6/10/16.
 */
public class GameFragment extends SpaceShooterBaseFragment implements View.OnClickListener, InputManager.InputDeviceListener, PauseDialogListener, GameOverDialogListener {

    private GameEngine gameEngine;

    public GameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onLayoutCompleted() {
        prepareAndStartGame();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameEngine.stopGame();

        InputManager inputManager = (InputManager) getActivity().getSystemService(Context.INPUT_SERVICE);
        inputManager.unregisterInputDeviceListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onInputDeviceAdded(int deviceId) {

    }

    @Override
    public void onInputDeviceRemoved(int deviceId) {
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onInputDeviceChanged(int deviceId) {

    }

    @Override
    public void resumeGame() {
        gameEngine.resumeGame();
    }

    @Override
    public void exitGame() {
        gameEngine.stopGame();
        getSpaceShooterActivity().navigateBack();
    }

    @Override
    public void startNewGame() {
        gameEngine.stopGame();
        prepareAndStartGame();
    }


    /* Private Methods */

    private void prepareAndStartGame() {
        GameView gameView = (GameView) getView().findViewById(R.id.gameView);
        gameEngine = new GameEngine(getActivity(), gameView, 4);
        gameEngine.setInputController(new CompositeInputController(getView(), getSpaceShooterActivity()));

        new ParallaxBackground(gameEngine, Configurations.BACKGROUND_SPEED, R.drawable.background_start_big).addToGameEngine(gameEngine, 0);
        new GameController(gameEngine, GameFragment.this).addToGameEngine(gameEngine, 2);

        gameEngine.startGame();

        InputManager inputManager = (InputManager) getActivity().getSystemService(Context.INPUT_SERVICE);
        inputManager.registerInputDeviceListener(GameFragment.this, null);

        gameView.postInvalidate();
    }


    private void pauseGameAndShowPauseDialog() {
        if (gameEngine.isPaused()) {
            return;
        }
        gameEngine.pauseGame();
        PauseDialog dialog = new PauseDialog(getSpaceShooterActivity());
        dialog.setListener(this);
        showDialog(dialog);
    }

    /* End Private Methods */
}
