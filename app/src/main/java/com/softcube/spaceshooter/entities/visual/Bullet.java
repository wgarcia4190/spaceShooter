package com.softcube.spaceshooter.entities.visual;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameEvent;

/**
 * Created by Wilson on 6/11/16.
 */
public class Bullet extends Sprite {

    private double speedFactor;
    private SpaceShip parent;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.laser_green, BodyType.Rectangular);
        speedFactor = gameEngine.pixelFactor * Configurations.BULLET_SPEED / 1000d;
    }

    @Override
    public void onUpdate(long elapseTimeMillis, GameEngine gameEngine) {
        y += speedFactor * elapseTimeMillis;

        if(y < -height){
            removeFromGameEngine(gameEngine);
            gameEngine.onGameEvent(GameEvent.BulletMissed);
        }
    }

    @Override
    public void onRemovedFromGameEngine() {
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject) {
        if(screenGameObject instanceof Asteroid){
            Asteroid asteroid = (Asteroid) screenGameObject;

            asteroid.explode(gameEngine);
            asteroid.removeFromGameEngine(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);

            removeFromGameEngine(gameEngine);
        }else if(screenGameObject instanceof Planetoid){
            Planetoid planetoid = (Planetoid) screenGameObject;
            gameEngine.onGameEvent(GameEvent.PlanetoidHit);
            planetoid.MAX_HIT -= 1;

            if(planetoid.MAX_HIT <= 0){
                planetoid.explode(gameEngine);
                planetoid.removeFromGameEngine(gameEngine);
            }
            removeFromGameEngine(gameEngine);
        }
    }


    public void init(SpaceShip parent, double positionX, double positionY) {
        x = positionX - width /2;
        y = positionY - height /2;
        this.parent = parent;
    }
}
