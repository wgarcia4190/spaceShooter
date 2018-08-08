package com.softcube.spaceshooter.logic.engine;


/**
 * Created by Wilson on 02/03/15.
 */
public abstract class GameEngineThread extends Thread{

    private static final String TAG = GameEngineThread.class.getSimpleName();

    protected boolean gameIsActive = true;
    protected boolean gameIsPaused = false;

    protected Object lock = new Object();


    @Override
    public void start(){
        gameIsActive = true;
        gameIsPaused = false;

        super.start();
    }

    public void pauseGame(){
        gameIsPaused = true;
    }

    public void resumeGame(){
        if(gameIsPaused){
            gameIsPaused = false;

            synchronized (lock){
                lock.notify();
            }
        }
    }

    public void stopGame(){
        gameIsActive = false;
        resumeGame();
    }

    /* Getters and Setter */

    public boolean isGameActive() {
        return gameIsActive;
    }

    public boolean isGamePaused() {
        return gameIsPaused;
    }

}
