package com.softcube.spaceshooter.logic.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import com.softcube.spaceshooter.entities.GameObject;
import com.softcube.spaceshooter.entities.not_visual.Collision;
import com.softcube.spaceshooter.entities.visual.ScreenGameObject;
import com.softcube.spaceshooter.logic.input.InputController;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameEvent;
import com.softcube.spaceshooter.view.components.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Wilson on 6/8/16.
 */
public class GameEngine {

    private final GameView gameView;

    private List<List<GameObject>> layers = new ArrayList<>();
    private List<GameObject> gameObjects = new ArrayList<>();
    private Map<Class<? extends GameObject>, List<GameObject>> pools = new HashMap<>();

    private List<GameObject> gameObjectsToBeAdded = new ArrayList<>();
    private List<GameObject> gameObjectsToBeRemoved = new ArrayList<>();

    private UpdateThread updateThread;
    private DrawThread drawThread;

    private QuadTree quadTreeRoot = new QuadTree();
    private List<Collision> detectedCollisions = new ArrayList<>();

    /*These instances variables are public because we are removing the
    encapsulation principle in order to get a better performance */
    public Activity activity;
    public InputController inputController;

    public Random random = new Random();

    public int width;
    public int height;
    public double pixelFactor;


    public GameEngine(Activity activity, GameView gameView, int numLayers) {
        this.activity = activity;
        this.gameView = gameView;

        gameView.setGameObjects(layers);

        QuadTree.init();

        width = gameView.getWidth() - gameView.getPaddingRight() - gameView.getPaddingLeft();
        height = gameView.getHeight() - gameView.getPaddingTop() - gameView.getPaddingBottom();

        pixelFactor = height / Configurations.PIXEL_FACTOR_CONSTANT;

        quadTreeRoot.setArea(new Rect(0, 0, width, height));

        for (int index = 0; index < numLayers; index++) {
            layers.add(new ArrayList<GameObject>());
        }

    }

    public void startGame() {
        stopGame();

        int numGameObjects = gameObjects.size();
        for (int index = 0; index < numGameObjects; index++) {
            gameObjects.get(index).startGame(this);
        }

        if (inputController != null) {
            inputController.onStart();
        }

        updateThread = new UpdateThread(this);
        updateThread.start();

        drawThread = new DrawThread(this);
        drawThread.start();
    }

    public void stopGame() {

        if (updateThread != null) {
            synchronized (layers) {
                onGameEvent(GameEvent.GameFinished);
            }

            updateThread.stopGame();
            updateThread = null;
        }

        if(drawThread != null) {
            drawThread.stopGame();
            drawThread = null;
        }

        if(inputController != null) {
            inputController.onStop();
        }
    }

    public void pauseGame() {
        updateThread.pauseGame();
        drawThread.pauseGame();
        inputController.onPause();
    }

    public void resumeGame(){
        updateThread.resumeGame();
        drawThread.resumeGame();
        inputController.onResume();
    }

    public void onUpdate(long elapsedTimeMillis) {
        inputController.onPreUpdate();

        int numGameObjects = gameObjects.size();
        for (int index = 0; index < numGameObjects; index++){
            gameObjects.get(index).onUpdate(elapsedTimeMillis, this);
            gameObjects.get(index).onPostUpdate(this);
        }

        checkCollisions();
        synchronized (layers){
            while(!gameObjectsToBeRemoved.isEmpty()){
                GameObject gameObjectToBeRemoved = gameObjectsToBeRemoved.remove(0);
                gameObjects.remove(gameObjectToBeRemoved);
                layers.get(gameObjectToBeRemoved.layer).remove(gameObjectToBeRemoved);

                if(gameObjectToBeRemoved instanceof ScreenGameObject){
                    quadTreeRoot.removeGameObject((ScreenGameObject) gameObjectToBeRemoved);
                }
                gameObjectToBeRemoved.onRemovedFromGameEngine();
            }
            while (!gameObjectsToBeAdded.isEmpty()){
                GameObject gameObjectToBeAdded = gameObjectsToBeAdded.remove(0);
                addGameObjectToLayer(gameObjectToBeAdded);
            }
        }

    }

    public void onDraw() {
        gameView.draw();
    }

    public void addGameObject(GameObject gameObject, int layer) {
        gameObject.layer = layer;

        if(isRunning()){
            gameObjectsToBeAdded.add(gameObject);
        }else{
            addGameObjectToLayer(gameObject);
        }

        activity.runOnUiThread(gameObject.addToGameUiThreadRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjectsToBeRemoved.add(gameObject);
        activity.runOnUiThread(gameObject.removeFromGameUiThreadRunnable);
    }

    public void onGameEvent(GameEvent gameEvent) {
        int numGameObjects = gameObjects.size();
        for (int index = 0; index < numGameObjects; index++) {
            gameObjects.get(index).onGameEvent(gameEvent);
        }
    }


    /* Getters and Setters */

    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    public boolean isRunning() {
        return updateThread != null && updateThread.isGameActive();
    }

    public boolean isPaused() {
        return updateThread != null && updateThread.isGamePaused();
    }

    public Context getContext() {
        return gameView.getContext();
    }

    /* End Getters and Setters */

    /* Private Methods */

    private void addGameObjectToLayer(GameObject gameObject){
        int layer = gameObject.layer;
        while(layers.size() <= layer){
            layers.add(new ArrayList<GameObject>());
        }

        layers.get(layer).add(gameObject);
        gameObjects.add(gameObject);

        if(gameObject instanceof ScreenGameObject){
            ScreenGameObject screenGameObject = (ScreenGameObject) gameObject;

            if(screenGameObject.bodyType != BodyType.None){
                quadTreeRoot.addGameObject(screenGameObject);
            }
        }

        gameObject.onAddedToGameEngine();
    }

    private void checkCollisions(){
        while (!detectedCollisions.isEmpty()){
            Collision.release(detectedCollisions.remove(0));
        }
        quadTreeRoot.checkCollisions(this, detectedCollisions);

    }

    /* End Private Methods */

}
