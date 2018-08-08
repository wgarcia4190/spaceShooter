package com.softcube.spaceshooter.logic.engine.particles.modifiers;

import com.softcube.spaceshooter.entities.visual.Particle;

/**
 * Created by Wilson on 6/18/16.
 */
public class AlphaModifier implements ParticleModifier {

    private int initialValue;
    private int finalValue;
    private long startTime;
    private long endTime;
    private float duration;
    private float valueIncrement;

    public AlphaModifier(int initialValue, int finalValue, long startMilis, long endMilis) {
        this.initialValue = initialValue;
        this.finalValue = finalValue;
        this.startTime = startMilis;
        this.endTime = endMilis;
        this.duration = endTime - startTime;
        this.valueIncrement = finalValue-initialValue;
    }

    @Override
    public void apply(Particle particle, long milliseconds) {
        if (milliseconds < startTime) {
            particle.alpha = initialValue;
        }
        else if (milliseconds > endTime) {
            particle.alpha = finalValue;
        }
        else {
            double percentageValue = (milliseconds- startTime)*1d/duration;
            int newAlphaValue = (int) (initialValue + valueIncrement*percentageValue);
            particle.alpha = newAlphaValue;
        }
    }
}
