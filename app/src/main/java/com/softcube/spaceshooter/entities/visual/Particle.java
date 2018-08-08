package com.softcube.spaceshooter.entities.visual;

import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.logic.engine.particles.ParticleSystem;
import com.softcube.spaceshooter.logic.engine.particles.modifiers.ParticleModifier;
import com.softcube.spaceshooter.utils.BodyType;

import java.util.ArrayList;

/**
 * Created by Wilson on 6/18/16.
 */
public class Particle extends Sprite {

    private final ParticleSystem particleSystem;
    private long timeToLive;

    public double speedX;
    public double speedY;
    public double rotationSpeed;

    private ArrayList<ParticleModifier> particleModifiers;
    private long totalMillis;

    public Particle(ParticleSystem particleSystem, GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes, BodyType.None);
        this.particleSystem = particleSystem;
    }

    @Override
    public void removeFromGameEngine(GameEngine gameEngine) {
        super.removeFromGameEngine(gameEngine);
        particleSystem.returnToPool(this);
    }


    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject) {
        return;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        totalMillis += elapsedMillis;
        if (totalMillis > timeToLive) {
            removeFromGameEngine(gameEngine);
        }
        else {
            x += speedX*elapsedMillis;
            y += speedY*elapsedMillis;
            rotation += rotationSpeed*elapsedMillis/1000d;
            for (int i=0; i<particleModifiers.size(); i++) {
                particleModifiers.get(i).apply(this, totalMillis);
            }
        }
    }

    public void activate(GameEngine gameEngine, long timeToLive, double mX, double mY, ArrayList<ParticleModifier> particleModifiers, int layer) {
        this.timeToLive = timeToLive;

        x = mX - width / 2;
        y = mY - height / 2;
        addToGameEngine(gameEngine, layer);
        this.particleModifiers = particleModifiers;
        totalMillis = 0;
    }
}
