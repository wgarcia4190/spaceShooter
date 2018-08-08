package com.softcube.spaceshooter.entities.visual;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.BodyType;

/**
 * Created by Wilson on 6/11/16.
 */
public abstract class AnimatedSprite extends Sprite {

    private final AnimationDrawable animationDrawable;
    private int totalTime;
    private long currentTime;

    protected AnimatedSprite(GameEngine gameEngine, int drawableRes, BodyType bodyType) {
        super(gameEngine, drawableRes, bodyType);

        animationDrawable = (AnimationDrawable) spriteDrawable;

        totalTime = 0;
        int numberOfFrames = animationDrawable.getNumberOfFrames();

        for (int index = 0; index < numberOfFrames; index++) {
            totalTime += animationDrawable.getDuration(index);
        }
    }

    @Override
    protected Bitmap obtainDefaultBitmap() {
        AnimationDrawable ad = (AnimationDrawable) spriteDrawable;
        return ((BitmapDrawable) ad.getFrame(0)).getBitmap();
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentTime += elapsedMillis;

        if(currentTime > totalTime){
            if(animationDrawable.isOneShot()){
                return;
            }else{
                currentTime = currentTime % totalTime;
            }
        }

        long animationElapsedTime = 0;
        int numberOfFrames = animationDrawable.getNumberOfFrames();

        for(int index = 0; index < numberOfFrames; index++){
            animationElapsedTime += animationDrawable.getDuration(index);
            if(animationElapsedTime > currentTime){
                bitmap = ((BitmapDrawable) animationDrawable.getFrame(index)).getBitmap();
                break;
            }
        }

    }
}
