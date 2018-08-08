package com.softcube.spaceshooter.entities.visual;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.entities.not_visual.GameController;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.logic.engine.particles.ParticleSystem;
import com.softcube.spaceshooter.logic.engine.particles.modifiers.ScaleModifier;
import com.softcube.spaceshooter.utils.BodyType;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameEvent;

/**
 * Created by Wilson on 6/11/16.
 */
public class Planetoid extends Sprite{

    private final GameController gameController;

    private final double speed;
    private double speedX;
    private double speedY;
    private double rotationSpeed;

    private ParticleSystem trailParticleSystem;
    private ParticleSystem explosionParticleSystem;

    public int MAX_HIT = Configurations.PLANETOID_MAX_HIT;


    public Planetoid(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.meteor_big, BodyType.Circular);

        speed = Configurations.PLANETOID_SPEED * pixelFactor / 1000d;
        this.gameController = gameController;

        this.trailParticleSystem = new ParticleSystem(gameEngine, 50, R.drawable.particle_dust, 1200)
                .addModifier(new ScaleModifier(1, 2, 500, 1200))
                .setFadeOut(200);
        this.explosionParticleSystem = new ParticleSystem(gameEngine,
                Configurations.EXPLOSION_PARTICLES, R.drawable.particle_asteroid_1, 700)
                .setSpeedRange(15, 40)
                .setFadeOut(300)
                .setInitialRotationRange(0, 360)
                .setRotationSpeedRange(-180, 180);
    }

    public void init(GameEngine gameEngine) {
        double angle = gameEngine.random.nextDouble() * Math.PI / 3d - Math.PI / 6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);

        x = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width / 4;
        y = -height;
        rotationSpeed = angle * (180d / Math.PI) / 250d;
        rotation = gameEngine.random.nextInt(360);

        trailParticleSystem.clearInitializers()
                .setInitialRotationRange(0,360)
                .setRotationSpeedRange(rotationSpeed * 800, rotationSpeed * 1000)
                .setSpeedByComponentsRange(-speedY * 100, speedY * 100, speedX * 100, speedX * 100);
    }

    public void explode(GameEngine gameEngine) {
        explosionParticleSystem.isOneShot(gameEngine, x + width / 2, y + height / 2,
                Configurations.EXPLOSION_PARTICLES);
    }

    @Override
    public void onUpdate(long elapseTimeMillis, GameEngine gameEngine) {
        x += speedX * elapseTimeMillis;
        y += speedY * elapseTimeMillis;
        rotation += rotationSpeed * elapseTimeMillis;

        if (rotation > 360) {
            rotation = 0;
        } else if (rotation < 0) {
            rotation = 360;
        }

        trailParticleSystem.setPosition(x + width / 2, y + height / 2);

        if (y > gameEngine.height) {
            gameEngine.onGameEvent(GameEvent.AsteroidMissed);
            removeFromGameEngine(gameEngine);
        }
    }

    @Override
    public void removeFromGameEngine(GameEngine gameEngine) {
        super.removeFromGameEngine(gameEngine);
        MAX_HIT = Configurations.PLANETOID_MAX_HIT;

        trailParticleSystem.stopEmiting();
        trailParticleSystem.removeFromGameEngine(gameEngine);
    }

    @Override
    public void onRemovedFromGameEngine() {
        gameController.returnPlanetoidToPool(this);
    }

    @Override
    public void addToGameEngine (GameEngine gameEngine, int layer) {
        super.addToGameEngine(gameEngine, layer);

        trailParticleSystem.addToGameEngine(gameEngine, layer-1);
        trailParticleSystem.emit(15);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject) {

    }
}
