package com.softcube.spaceshooter.logic.engine;

import android.graphics.Rect;

import com.softcube.spaceshooter.entities.not_visual.Collision;
import com.softcube.spaceshooter.entities.visual.ScreenGameObject;
import com.softcube.spaceshooter.utils.Configurations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 6/18/16.
 */
public class QuadTree {

    private List<ScreenGameObject> gameObjects = new ArrayList<>();
    private Rect area = new Rect();
    private Rect tmpRect = new Rect();

    private QuadTree[] children = new QuadTree[4];
    private static List<QuadTree> quadTreePool = new ArrayList<>();

    public static void init() {
        quadTreePool.clear();

        for (int index = 0; index < Configurations.MAX_QUADTREES; index++) {
            quadTreePool.add(new QuadTree());
        }
    }

    public void setArea(Rect area) {
        this.area.set(area);
    }

    public void checkObjects(List<ScreenGameObject> gameObjects) {
        this.gameObjects.clear();
        int numObjects = gameObjects.size();

        for (int index = 0; index < numObjects; index++) {
            ScreenGameObject gameObject = gameObjects.get(index);
            Rect boundingRect = gameObject.boundingRect;

            if (Rect.intersects(boundingRect, area)) {
                this.gameObjects.add(gameObject);
            }
        }
    }

    public void checkCollisions(GameEngine gameEngine, List<Collision> detectedCollisions) {
        int numObjects = gameObjects.size();

        if (numObjects > Configurations.MAX_OBJECTS_TO_CHECK && quadTreePool.size() >= 4) {
            splitAndCheck(gameEngine, detectedCollisions);
        } else {
            for (int index = 0; index < numObjects; index++) {
                ScreenGameObject objectA = this.gameObjects.get(index);
                for(int subIndex = index + 1; subIndex < numObjects; subIndex++){
                    ScreenGameObject objectB = this.gameObjects.get(subIndex);
                    if(objectA.checkCollision(objectB)){
                        Collision collision = Collision.init(objectA, objectB);
                        if(!hasBeenDetected(detectedCollisions, collision)){
                            detectedCollisions.add(collision);
                            objectA.onCollision(gameEngine, objectB);
                            objectB.onCollision(gameEngine, objectA);
                        }
                    }
                }
            }
        }
    }


    private void splitAndCheck(GameEngine gameEngine, List<Collision> detectedCollisions) {
        for (int index = 0; index < 4; index++) {
            children[index] = quadTreePool.remove(0);

            children[index].setArea(getArea(index));
            children[index].checkObjects(this.gameObjects);
            children[index].checkCollisions(gameEngine, detectedCollisions);
            children[index].gameObjects.clear();

            quadTreePool.add(children[index]);
        }
    }

    private boolean hasBeenDetected(List<Collision> detectedCollisions, Collision collision) {
        int numCollisions = detectedCollisions.size();

        for (int index = 0; index < numCollisions; index++) {
            if (detectedCollisions.get(index).equals(collision)) {
                return true;
            }
        }
        return false;
    }

    private Rect getArea(int area) {
        int startX = this.area.left;
        int startY = this.area.top;
        int width = this.area.width();
        int height = this.area.height();

        switch (area) {
            case 0:
                tmpRect.set(startX, startY, startX + width / 2, startY + height / 2);
                break;
            case 1:
                tmpRect.set(startX + width / 2, startY, startX + width, startY + height / 2);
                break;
            case 2:
                tmpRect.set(startX, startY + height / 2, startX + width / 2, startY + height);
                break;
            case 3:
                tmpRect.set(startX + width / 2, startY + height / 2, startX + width, startY + height);
                break;
        }

        return tmpRect;
    }

    public void addGameObject(ScreenGameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void removeGameObject(ScreenGameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }
}
