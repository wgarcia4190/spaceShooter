package com.softcube.spaceshooter.logic.engine.particles;

import com.softcube.spaceshooter.entities.visual.Particle;
import com.softcube.spaceshooter.entities.visual.ScreenGameObject;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.logic.engine.particles.initializers.ParticleInitializer;
import com.softcube.spaceshooter.logic.engine.particles.initializers.RotationInitiazer;
import com.softcube.spaceshooter.logic.engine.particles.initializers.RotationSpeedInitializer;
import com.softcube.spaceshooter.logic.engine.particles.initializers.SpeedByComponentsInitializer;
import com.softcube.spaceshooter.logic.engine.particles.initializers.SpeedModuleAndRangeInitializer;
import com.softcube.spaceshooter.logic.engine.particles.modifiers.AlphaModifier;
import com.softcube.spaceshooter.logic.engine.particles.modifiers.ParticleModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Wilson on 6/18/16.
 */
public class ParticleSystem extends ScreenGameObject {

    private final Random random;
    private final ArrayList<ParticleModifier> particleModifiers;
    private final ArrayList<ParticleInitializer> particleInitializers;
    private final long timeToLive;

    private final double pixelFactor;
    private List<Particle> particlePool = new ArrayList<>();

    private long totalMillis;

    private int activatedParticles;
    private double particlesPerMiliscond;
    private boolean isEmiting;

    public ParticleSystem(GameEngine gameEngine, int maxParticles, int drawableRes, long timeToLive) {
        random = new Random();

        particleModifiers = new ArrayList<>();
        particleInitializers = new ArrayList<>();
        this.timeToLive = timeToLive;

        pixelFactor = gameEngine.pixelFactor;

        for (int index = 0; index < maxParticles; index++) {
            particlePool.add(new Particle(this, gameEngine, drawableRes));
        }
    }

    public ParticleSystem setSpeedRange(double speedMin, double speedMax) {
        particleInitializers.add(new SpeedModuleAndRangeInitializer(speedMin * pixelFactor, speedMax * pixelFactor, 0, 360));
        return this;
    }

    public ParticleSystem setSpeedModuleAndAngleRange(double speedMin, double speedMax, int minAngle, int maxAngle) {
        particleInitializers.add(new SpeedModuleAndRangeInitializer(speedMin * pixelFactor, speedMax * pixelFactor, minAngle, maxAngle));
        return this;
    }

    public ParticleSystem setSpeedByComponentsRange(double speedMinX, double speedMaxX, double speedMinY, double speedMaxY) {
        particleInitializers.add(new SpeedByComponentsInitializer(speedMinX * pixelFactor, speedMaxX * pixelFactor,
                speedMinY * pixelFactor, speedMaxY * pixelFactor));
        return this;
    }

    public ParticleSystem setFadeOut(long milisecondsBeforeEnd) {
        particleModifiers.add(new AlphaModifier(255, 0, timeToLive - milisecondsBeforeEnd, timeToLive));
        return this;
    }

    public ParticleSystem addModifier(ParticleModifier modifier) {
        particleModifiers.add(modifier);
        return this;
    }

    public ParticleSystem setInitialRotationRange(int minAngle, int maxAngle) {
        particleInitializers.add(new RotationInitiazer(minAngle, maxAngle));
        return this;
    }

    public ParticleSystem setRotationSpeedRange(double minRotationSpeed, double maxRotationSpeed) {
        particleInitializers.add(new RotationSpeedInitializer(minRotationSpeed, maxRotationSpeed));
        return this;
    }

    public void emit(int particlesPerSecond) {
        activatedParticles = 0;
        totalMillis = 0;
        particlesPerMiliscond = particlesPerSecond / 1000d;
        isEmiting = true;
    }

    public void isOneShot(GameEngine gameEngine, double x, double y, int numParticles) {
        this.x = x;
        this.y = y;
        isEmiting = false;

        for (int index = 0; !particlePool.isEmpty() && index < numParticles; index++) {
            activateParticle(gameEngine);
        }
    }

    public void returnToPool(Particle particle) {
        particlePool.add(particle);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void stopEmiting() {
        isEmiting = false;
    }

    public ParticleSystem clearInitializers() {
        particleInitializers.clear();
        return this;
    }

    private void activateParticle(GameEngine gameEngine) {
        Particle particle = particlePool.remove(0);

        for (int index = 0; index < particleInitializers.size(); index++) {
            particleInitializers.get(index).initParticle(particle, random);
        }

        particle.activate(gameEngine, timeToLive, this.x, this.y, particleModifiers, this.layer);
        activatedParticles++;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if (!isEmiting) {
            return;
        }
        totalMillis += elapsedMillis;
        while (!particlePool.isEmpty() &&
                activatedParticles < particlesPerMiliscond * totalMillis) {
            activateParticle(gameEngine);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject screenGameObject) {
        return;
    }
}
