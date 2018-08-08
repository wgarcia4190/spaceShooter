package com.softcube.spaceshooter.entities.visual;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.softcube.spaceshooter.entities.GameObject;
import com.softcube.spaceshooter.logic.engine.GameEngine;

/**
 * Created by Wilson on 6/11/16.
 */
public class ParallaxBackground extends GameObject {

    private final double imageHeight;
    private final double imageWidth;
    private final Bitmap bitmap;
    private final double speedY;
    private final double screenWidth;
    private final double screenHeight;
    private final double pixelFactor;
    private final double targetWidth;

    private final Matrix matrix = new Matrix();

    protected double positionY;
    protected Paint paint = new Paint();

    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableRes) {
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableRes, null);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        pixelFactor = gameEngine.pixelFactor;
        speedY = speed * pixelFactor / 1000d;

        imageHeight = spriteDrawable.getIntrinsicHeight() * pixelFactor;
        imageWidth = spriteDrawable.getIntrinsicWidth() * pixelFactor;

        screenHeight = gameEngine.height;
        screenWidth = gameEngine.width;

        targetWidth = Math.min(imageWidth, screenWidth);
    }

    @Override
    public void onUpdate(long elapseTimeMillis, GameEngine gameEngine) {
        positionY += speedY * elapseTimeMillis;
    }

    @Override
    public void onDraw(Canvas canvas) {
        draw(canvas);
    }

    /* Private Methods */

    private void draw(Canvas canvas) {
        if (positionY > 0) {
            matrix.reset();
            matrix.postScale((float) (pixelFactor), (float) (pixelFactor));
            matrix.postTranslate(0, (float) (positionY - imageHeight));
            canvas.drawBitmap(bitmap, matrix, null);
        }
        matrix.reset();
        matrix.postScale((float) (pixelFactor), (float) (pixelFactor));
        matrix.postTranslate(0, (float) positionY);
        canvas.drawBitmap(bitmap, matrix, null);

        if (positionY > screenHeight) {
            positionY -= imageHeight;
        }
    }


    /* End Private Methods */
}
