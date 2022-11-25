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
	public static final Vector2 TANK_GRAVITY = new Vector2(0, -50f);
}
