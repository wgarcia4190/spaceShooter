package com.softcube.spaceshooter.utils;

/**
 * Created by Wilson on 6/8/16.
 */
public class Configurations {
    public static final boolean VISUAL_COLLISION_DEBUG = false;
    public static final int TITLE_ANIMATION_DURATION = 1600;
    public static final double PIXEL_FACTOR_CONSTANT = 400d;

    public static final double SHIP_SPEED = 450d;
    public static final double BULLET_SPEED = -600d;
    public static final int BACKGROUND_SPEED = 600;
    public static final double ASTEROID_SPEED = 300d;
    public static final double PLANETOID_SPEED = 100d;

    public static final int INITIAL_BULLET_POOL_AMOUNT = 10;
    public static final long TIME_BETWEEN_BULLETS = 150;
    public static final int EXPLOSION_PARTICLES = 20;

    public static final int TIME_BETWEEN_ASTEROIDS = 1000;
    public static final int TIME_BETWEEN_PLANETOIDS = 12500;
    public static final int ASTEROID_POOL_SIZE = 10;
    public static final int PLANETOID_POOL_SIZE = 3;
    public static final int PLANETOID_MAX_HIT = 10;
    public static final int INITIAL_LIFES = 3;
    public static final int STOPPING_WAVE_WAITING_TIME = 2000;
    public static final int NEXT_WAVE_WAITING_TIME = 3000;

    public static final int MAX_QUADTREES = 12;
    public static final int MAX_OBJECTS_TO_CHECK = 8;
}
