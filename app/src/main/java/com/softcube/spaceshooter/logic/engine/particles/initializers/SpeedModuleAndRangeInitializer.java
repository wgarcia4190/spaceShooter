package com.softcube.spaceshooter.logic.engine.particles.initializers;

import com.softcube.spaceshooter.entities.visual.Particle;

import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public class SpeedModuleAndRangeInitializer implements ParticleInitializer {
    private double speedMin;
    private double speedMax;
    private int minAngle;
    private int maxAngle;

    public SpeedModuleAndRangeInitializer(double speedMin, double speedMax, int minAngle, int maxAngle) {
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public void initParticle(Particle particle, Random random) {
        double speed = random.nextDouble()*(speedMax-speedMin) + speedMin;
        int angle;
        if (maxAngle == minAngle) {
            angle = minAngle;
        }
        else {
            angle = random.nextInt(maxAngle - minAngle) + minAngle;
        }
        double angleInRads = angle*Math.PI/180f;
        particle.speedX = speed * Math.cos(angleInRads)/1000d;
        particle.speedY = speed * Math.sin(angleInRads)/1000d;
    }
}
