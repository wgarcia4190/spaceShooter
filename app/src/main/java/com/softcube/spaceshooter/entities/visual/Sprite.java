package com.softcube.spaceshooter.entities.visual;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameEvent;

/**
 * Created by Wilson on 6/11/16.
 */
public abstract class Sprite extends ScreenGameObject {

    protected final Drawable spriteDrawable;
    public double rotation;

    protected final double pixelFactor;

    protected Bitmap bitmap;

    private final Matrix matrix = new Matrix();
    private final Paint paint = new Paint();
    public int alpha = 255;
    public double scale = 1;


    protected Sprite(GameEngine gameEngine, int drawableRes, BodyType bodyType) {
        Resources resources = gameEngine.getContext().getResources();
        spriteDrawable = resources.getDrawable(drawableRes, null);

        pixelFactor = gameEngine.pixelFactor;
        width = (int) (spriteDrawable.getIntrinsicWidth() * pixelFactor);
        height = (int) (spriteDrawable.getIntrinsicHeight() * pixelFactor);

        bitmap = obtainDefaultBitmap();
        radius = Math.max(height, width) / 2;
        this.bodyType = bodyType;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (x > canvas.getWidth() || y > canvas.getHeight() || x < -width || y < -height) {
            return;
        }

        if (Configurations.VISUAL_COLLISION_DEBUG) {
            paint.setColor(Color.YELLOW);
            if (bodyType == BodyType.Circular) {
                canvas.drawCircle((int) (x + width / 2), (int) (y + height / 2), (int) radius, paint);
            }else if(bodyType == BodyType.Rectangular){
                canvas.drawRect(boundingRect, paint);
            }
        }

        float scaleFactor = (float)(pixelFactor * scale);

        matrix.reset();
        matrix.postScale(scaleFactor, scaleFactor);
        matrix.postTranslate((float)x, (float)y);
        matrix.postRotate((float)rotation, (float)(x + width*scale / 2), (float)(y + height*scale / 2));

        paint.setAlpha(alpha);

        canvas.drawBitmap(bitmap, matrix, paint);

    }

    protected Bitmap obtainDefaultBitmap() {
        return ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    protected void updateBitmap(Bitmap otherBitmap){
        if(this.bitmap != otherBitmap){
            bitmap = otherBitmap;
        }
    }

}
