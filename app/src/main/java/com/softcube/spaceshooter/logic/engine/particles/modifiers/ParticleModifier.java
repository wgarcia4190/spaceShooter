package com.softcube.spaceshooter.logic.engine.particles.modifiers;

import com.softcube.spaceshooter.entities.visual.Particle;

/**
 * Created by Wilson on 6/18/16.
 */
public interface ParticleModifier {
    public void apply(Particle particle, long milliseconds);
}
