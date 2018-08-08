package com.softcube.spaceshooter.entities.visual;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.softcube.spaceshooter.entities.GameObject;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.GameEvent;

/**
 * Created by Wilson on 6/10/16.
 */
public abstract class ScreenGameObject extends GameObject {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    public double radius;
    public BodyType bodyType;

    public Rect boundingRect = new Rect(-1, -1, -1, -1);

    public abstract void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject);

    @Override
    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public boolean checkCollision(ScreenGameObject screenGameObject){
        if(bodyType == BodyType.Circular && screenGameObject.bodyType == BodyType.Circular){
            return checkCircularCollision(screenGameObject);
        }else if(bodyType == BodyType.Rectangular && screenGameObject.bodyType == BodyType.Rectangular){
            return checkRectangularCollision(screenGameObject);
        }else{
            return checkMixedCollisions(screenGameObject);
        }
    }


    /* Private Methods */

    private boolean checkMixedCollisions(ScreenGameObject screenGameObject) {
        ScreenGameObject circularSprite;
        ScreenGameObject rectangularSprite;

        if (bodyType == BodyType.Rectangular) {
            circularSprite = this;
            rectangularSprite = screenGameObject;
        } else {
            circularSprite = screenGameObject;
            rectangularSprite = this;
        }

        double circleCenterX = circularSprite.x + circularSprite.width / 2;
        double positionXToCheck = circleCenterX;

        if (circleCenterX < rectangularSprite.x) {
            positionXToCheck = rectangularSprite.x;
        } else if (circleCenterX > rectangularSprite.x + rectangularSprite.width) {
            positionXToCheck = rectangularSprite.x + rectangularSprite.width;
        }

        double distanceX = circleCenterX - positionXToCheck;

        double circleCenterY = circularSprite.y + circularSprite.height / 2;
        double positionYToCheck = circleCenterY;

        if (circleCenterY < rectangularSprite.y) {
            positionYToCheck = rectangularSprite.y;
        } else if (circleCenterY > rectangularSprite.y + rectangularSprite.height) {
            positionYToCheck = rectangularSprite.y + rectangularSprite.height;
        }

        double distanceY = circleCenterY - positionYToCheck;
        double squareDistance = distanceX * distanceX + distanceY * distanceY;

        if (squareDistance <= circularSprite.radius * circularSprite.radius) {
            return true;
        }

        return false;
    }

    private boolean checkRectangularCollision(ScreenGameObject screenGameObject) {
        return Rect.intersects(boundingRect, screenGameObject.boundingRect);
    }

    private boolean checkCircularCollision(ScreenGameObject screenGameObject) {
        double distanceX = (x + width / 2) - (screenGameObject.x + screenGameObject.width / 2);
        double distanceY = (y + height / 2) - (screenGameObject.y + screenGameObject.height / 2);
        double squareDistance = distanceX*distanceX + distanceY*distanceY;
        double collisionDistance = (radius + screenGameObject.radius);

        return squareDistance <= collisionDistance*collisionDistance;
    }

    /* End Private Methods */

}
