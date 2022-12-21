package com.arorashivoy.tank_star.Helper;

import com.badlogic.gdx.math.Vector2;

public final class CustomConstants {
	// Screen dimensions
	public static final int V_WIDTH = 960;
	public static final int V_HEIGHT = 540;


	// World constants
	public static final Vector2 GRAVITY = new Vector2(0, -9.8f);
	public static final float PPM = 32f;

	// Tank
	public static final int TANK_WIDTH = 50;
	public static final int TANK_HEIGHT = 50;
	public static final Vector2 TANK_GRAVITY = new Vector2(0, -75f);
	public static final float INITIAL_HEALTH = 100f;
	public static final float INITIAL_FUEL = 100f;

	// Bullet
	public static final int BULLET_WIDTH = 10;
	public static final int BULLET_HEIGHT = 10;
	public static final float BULLET_UNIT_TIME = 0.005f;

	// Game Screen
	public static final int IN_GAME_BTN_SIZE = 70;
}
