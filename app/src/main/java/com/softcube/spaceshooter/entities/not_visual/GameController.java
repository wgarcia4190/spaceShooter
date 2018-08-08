package com.softcube.spaceshooter.entities.not_visual;

import android.graphics.Canvas;

import com.softcube.spaceshooter.entities.GameObject;
import com.softcube.spaceshooter.entities.visual.Asteroid;
import com.softcube.spaceshooter.entities.visual.Planetoid;
import com.softcube.spaceshooter.entities.visual.SpaceShip;
import com.softcube.spaceshooter.logic.engine.GameEngine;
import com.softcube.spaceshooter.utils.Configurations;
import com.softcube.spaceshooter.utils.GameControllerState;
import com.softcube.spaceshooter.utils.GameEvent;
import com.softcube.spaceshooter.view.main.GameFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 6/11/16.
 */
public class GameController extends GameObject {

    private final GameFragment parent;

    private long currentMillis;
    private int asteroidsSpawned;
    private int planetoidsSpawned;
    private int waitingTime;
    private int numOfLives;

    private GameControllerState gameControllerState;
    private List<Asteroid> asteroids = new ArrayList<>();
    private List<Planetoid> planetoids = new ArrayList<>();

    public GameController(GameEngine gameEngine, GameFragment parent) {
        this.parent = parent;

        for (int index = 0; index < Configurations.ASTEROID_POOL_SIZE; index++) {
            asteroids.add(new Asteroid(this, gameEngine));
        }

        for (int index = 0; index < Configurations.PLANETOID_POOL_SIZE; index++) {
            planetoids.add(new Planetoid(this, gameEngine));
        }
    }


    @Override
    public void startGame(GameEngine gameEngine) {
        currentMillis = 0;
        asteroidsSpawned = 0;
        planetoidsSpawned = 0;
        waitingTime = 0;

        for (int index = 0; index < Configurations.INITIAL_LIFES; index++) {
            gameEngine.onGameEvent(GameEvent.LifeAdded);
        }

        gameControllerState = GameControllerState.PlacingSpaceship;
    }

    @Override
    public void onUpdate(long elapseTimeMillis, GameEngine gameEngine) {
        switch (gameControllerState) {
            case SpawningEnemies:
                currentMillis += elapseTimeMillis;
                long waveAsteroidsTimestamp = asteroidsSpawned * Configurations.TIME_BETWEEN_ASTEROIDS;
                long wavePlanetoidsTimestamp = planetoidsSpawned * Configurations.TIME_BETWEEN_PLANETOIDS;
                if (currentMillis > waveAsteroidsTimestamp) {
                    Asteroid asteroid = asteroids.remove(0);
                    asteroid.init(gameEngine);
                    asteroid.addToGameEngine(gameEngine, layer);
                    asteroidsSpawned++;
                }
                if(currentMillis > wavePlanetoidsTimestamp){

                    if(planetoidsSpawned > 0) {
                        Planetoid planetoid = planetoids.remove(0);
                        planetoid.init(gameEngine);
                        planetoid.addToGameEngine(gameEngine, layer);
                    }
                    planetoidsSpawned++;
                }
                break;
            case StoppingWave:
                waitingTime += elapseTimeMillis;
                if (waitingTime > Configurations.STOPPING_WAVE_WAITING_TIME) {
                    gameControllerState = GameControllerState.PlacingSpaceship;
                    return;
                }
                break;
            case PlacingSpaceship:
                if (numOfLives == 0) {
                    gameEngine.onGameEvent(GameEvent.GameOver);
                } else {
                    numOfLives--;
                    gameEngine.onGameEvent(GameEvent.LifeLost);
                    SpaceShip spaceShip = new SpaceShip(gameEngine);

                    spaceShip.addToGameEngine(gameEngine, 3);
                    spaceShip.startGame(gameEngine);

                    gameControllerState = GameControllerState.Waiting;
                    waitingTime = 0;
                    return;
                }
                break;
            case Waiting:
                waitingTime += elapseTimeMillis;
                if (waitingTime > Configurations.NEXT_WAVE_WAITING_TIME) {
                    gameControllerState = GameControllerState.SpawningEnemies;
                }
                break;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        switch (gameEvent){
            case SpaceshipHit:
                gameControllerState = GameControllerState.StoppingWave;
                waitingTime = 0;
                break;
            case GameOver:
                gameControllerState = GameControllerState.GameOver;

                break;
            case LifeAdded:
                numOfLives++;
                break;

        }
    }

    public void returnAsteroidToPool(Asteroid asteroid) {
        asteroids.add(asteroid);
    }
    public void returnPlanetoidToPool(Planetoid planetoid) {
        planetoids.add(planetoid);
    }
}
