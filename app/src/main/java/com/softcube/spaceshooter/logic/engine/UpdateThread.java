package com.softcube.spaceshooter.logic.engine;

import android.util.Log;

/**
 * Created by Wilson on 02/03/15.
 */
public class UpdateThread extends GameEngineThread{

    private final GameEngine gameEngine;

    public UpdateThread(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void run(){
        long currentTimeMillis;
        long elapsedTimeMillis;
        long previousTimeMillis = System.currentTimeMillis();

        while(gameIsActive){
            currentTimeMillis = System.currentTimeMillis();
            elapsedTimeMillis = currentTimeMillis - previousTimeMillis;

            if(gameIsPaused){
                while (gameIsPaused){
                    try{
                        synchronized (lock){
                            lock.wait();
                        }
                    }catch (InterruptedException ex){
                        Log.e("TAG", ex.getMessage());
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            gameEngine.onUpdate(elapsedTimeMillis);
            previousTimeMillis = currentTimeMillis;
        }

    }
}
