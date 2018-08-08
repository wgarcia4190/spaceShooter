package com.softcube.spaceshooter.entities.visual;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.logic.input.InputController;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 6/11/16.
 */
public class SpaceShip extends Sprite {

    private List<Bullet> bullets = new ArrayList<>();

    private int maxX;
    private int maxY;
    private double speedFactor;

    private Bitmap startBitmap;
    private Bitmap leftBitmap;
    private Bitmap rightBitmap;

    private long timeSinceLastFire;

    public SpaceShip(GameEngine gameEngine) {
        super(gameEngine, R.drawable.player, BodyType.Circular);

        Resources resources = gameEngine.getContext().getResources();

        speedFactor = pixelFactor * Configurations.SHIP_SPEED / 1000d;
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        initBulletPool(gameEngine);

        startBitmap = ((BitmapDrawable) resources.getDrawable(R.drawable.player, null)).getBitmap();
        leftBitmap = ((BitmapDrawable) resources.getDrawable(R.drawable.player_left, null)).getBitmap();
        rightBitmap = ((BitmapDrawable) resources.getDrawable(R.drawable.player_right, null)).getBitmap();
    }


    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject) {
        /*if (screenGameObject instanceof Asteroid) {
            removeFromGameEngine(gameEngine);
            Asteroid asteroid = (Asteroid) screenGameObject;
            asteroid.removeFromGameEngine(gameEngine);

            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            gameEngine.stopGame();
        }

        if(screenGameObject instanceof Planetoid){
            removeFromGameEngine(gameEngine);
            Planetoid planetoid = (Planetoid) screenGameObject;
            planetoid.removeFromGameEngine(gameEngine);

            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            gameEngine.stopGame();
        }*/
    }

    @Override
    public void startGame(GameEngine gameEngine) {
        x = maxX / 2;
        y = maxY / 2;
    }

    @Override
    public void addToGameEngine(GameEngine gameEngine, int layer) {
        super.addToGameEngine(gameEngine, layer);
    }

    @Override
    public void removeFromGameEngine(GameEngine gameEngine) {
        super.removeFromGameEngine(gameEngine);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis, gameEngine);

        updatePosition(elapsedMillis, gameEngine.inputController);
        checkFiring(elapsedMillis, gameEngine);


    }

    /* Private Methods */

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < Configurations.INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    public void releaseBullet(Bullet b) {
        bullets.add(b);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        if (inputController.horizontalFactor == 0 && inputController.verticalFactor == 0) {
            updateBitmap(startBitmap);
        }

        x += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (x < 0) {
            x = 0;
        }

        if (x > maxX) {
            x = maxX;
        }

        y += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (y < 0) {
            y = 0;
        }
        if (y > maxY) {
            y = maxY;
        }

        if (inputController.horizontalFactor > 0.40) {
            updateBitmap(rightBitmap);
        } else if (inputController.horizontalFactor < -0.40) {
            updateBitmap(leftBitmap);
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.inputController.isFiring && timeSinceLastFire > Configurations.TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();

            if (bullet == null) {
                return;
            }
            bullet.init(this, x + width / 2, y);
            bullet.addToGameEngine(gameEngine, 1);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);

        }else{
            timeSinceLastFire += elapsedMillis;
        }
    }
    /* End Private Methods */
}
