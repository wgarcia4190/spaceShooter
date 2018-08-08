package com.softcube.spaceshooter.logic.engine;

import android.util.Log;

/**
 * Created by Wilson on 6/8/16.
 */
public class DrawThread extends GameEngineThread {

    private final GameEngine gameEngine;

    public DrawThread(GameEngine gameEngine){
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

            if(elapsedTimeMillis < 20){
                try{
                    Thread.sleep(20-elapsedTimeMillis);
                }catch (InterruptedException ex){
                    Log.e("TAG", ex.getMessage());
                }
            }
            gameEngine.onDraw();
            previousTimeMillis = currentTimeMillis;
        }

    }
}
