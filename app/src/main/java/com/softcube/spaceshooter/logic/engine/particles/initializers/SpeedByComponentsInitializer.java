package com.softcube.spaceshooter.logic.engine.particles.initializers;

import com.softcube.spaceshooter.entities.visual.Particle;

import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public class SpeedByComponentsInitializer implements ParticleInitializer {
    
    private double minSpeedX;
    private double maxSpeedX;
    private double minSpeedY;
    private double maxSpeedY;

    public SpeedByComponentsInitializer(double speedMinX, double speedMaxX, double speedMinY, double speedMaxY) {
        this.minSpeedX = speedMinX;
        this.maxSpeedX = speedMaxX;
        this.minSpeedY = speedMinY;
        this.maxSpeedY = speedMaxY;
    }

    @Override
    public void initParticle(Particle particle, Random random) {
        particle.speedX = (random.nextDouble()*(maxSpeedX-minSpeedX)+minSpeedX)/1000d;
        particle.speedY = (random.nextDouble()*(maxSpeedY-minSpeedY)+minSpeedY)/1000d;
    }
}
