package com.softcube.spaceshooter.logic.engine.particles.modifiers;

import com.softcube.spaceshooter.entities.visual.Particle;

/**
 * Created by Wilson on 6/18/16.
 */
public class ScaleModifier implements ParticleModifier {

    private double initialValue;
    private double finalValue;
    private long endTime;
    private long startTime;
    private long duration;
    private double valueIncrement;

    public ScaleModifier (double initialValue, double finalValue, long startMilis, long endMilis) {
        this.initialValue = initialValue;
        this.finalValue = finalValue;
        startTime = startMilis;
        endTime = endMilis;
        duration = endTime - startTime;
        valueIncrement = this.finalValue-this.initialValue;
    }

    @Override
    public void apply(Particle particle, long milliseconds) {
        if (milliseconds < startTime) {
            particle.scale = this.initialValue;
        }
        else if (milliseconds > endTime) {
            particle.scale = this.finalValue;
        }
        else {
            double percentageValue = (milliseconds- startTime)*1d/duration;
            double newScale = this.initialValue + valueIncrement*percentageValue;
            particle.scale = newScale;
        }
    }
}
