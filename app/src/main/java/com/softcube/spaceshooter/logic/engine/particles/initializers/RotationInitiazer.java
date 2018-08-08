package com.softcube.spaceshooter.logic.engine.particles.initializers;

import com.softcube.spaceshooter.entities.visual.Particle;

import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public class RotationInitiazer implements ParticleInitializer {
    private int minAngle;
    private int maxAngle;

    public RotationInitiazer(int minAngle, int maxAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public void initParticle(Particle particle, Random random) {
        int value = random.nextInt(maxAngle-minAngle)+minAngle;
        particle.rotation = value;
    }
}
