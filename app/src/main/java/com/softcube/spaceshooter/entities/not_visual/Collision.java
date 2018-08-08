package com.softcube.spaceshooter.entities.not_visual;

import com.softcube.spaceshooter.entities.visual.ScreenGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 6/18/16.
 */
public class Collision {

    private static List<Collision> collisionPool = new ArrayList<>();

    public ScreenGameObject objectA;
    public ScreenGameObject objectB;

    public Collision(ScreenGameObject objectA, ScreenGameObject objectB){
        this.objectA = objectA;
        this.objectB = objectB;
    }

    public static Collision init(ScreenGameObject objectA, ScreenGameObject objectB){
        try {
            if (collisionPool.isEmpty()) {
                return new Collision(objectA, objectB);
            }

            Collision collision = collisionPool.remove(0);
            collision.objectA = objectA;
            collision.objectB = objectB;

            return collision;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void release(Collision collision){
        collision.objectA = null;
        collision.objectB = null;
        collisionPool.add(collision);
    }

    public boolean equals(Collision collision){
        return (this.objectA == collision.objectA && this.objectB == collision.objectB) ||
                (this.objectA == collision.objectB && this.objectB == collision.objectA);
    }
}
