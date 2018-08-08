package com.softcube.spaceshooter.logic.engine.particles.initializers;

import com.softcube.spaceshooter.entities.visual.Particle;

import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public interface ParticleInitializer {
    public void initParticle(Particle Particle, Random random);
}
