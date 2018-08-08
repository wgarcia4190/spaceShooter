package com.softcube.spaceshooter.logic.engine.particles.initializers;

import com.softcube.spaceshooter.entities.visual.Particle;

import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public class RotationSpeedInitializer implements ParticleInitializer {
    private double minRotationSpeed;
    private double maxRotationSpeed;

    public RotationSpeedInitializer(double minRotationSpeed,	double maxRotationSpeed) {
        this.minRotationSpeed = minRotationSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
    }

    @Override
    public void initParticle(Particle particle, Random random) {
        double rotationSpeed = random.nextDouble()*(maxRotationSpeed-minRotationSpeed) + minRotationSpeed;
        particle.rotationSpeed = rotationSpeed;
    }
}
