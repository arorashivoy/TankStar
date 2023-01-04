package com.arorashivoy.tank_star.Objects;

import static com.arorashivoy.tank_star.Helper.CustomConstants.*;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;
import java.util.HashMap;

//@SuppressWarnings("FieldMayBeFinal")
public class Bullets implements Serializable {
	private final Main app;
	private final Tank tank;
	private boolean isVisible = false;
	private double x, y;
	private double startX, startY;
	private double powerX, powerY;
	private int frame = 0;

	private static HashMap<Tank, Bullets> bullets = new HashMap<>();

	private Sprite bulletSprite;

	private Bullets(Main app, Tank tank) {
		this.app = app;
		this.tank = tank;
		bullets.put(tank, this);
	}

	public static Bullets createBullet(Main app, Tank tank) {
		Bullets bullet = bullets.get(tank);
		if (bullet == null) {
			bullet = new Bullets(app, tank);
			bullets.put(tank, bullet);
		}

		return bullet;
	}

	public void show() {
		// Bullet Texture
		Texture bulletTex = app.getAssets().get("img/Bullets/Bullet.png", Texture.class);
		bulletSprite = new Sprite(bulletTex);
		bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
	}


	public void draw() {
		if (isVisible) {
			update();
			bulletSprite.draw(app.getBatch());
		}
	}

	private void update() {
		x = (powerX * BULLET_UNIT_TIME * frame) + startX;
		y = (powerY * BULLET_UNIT_TIME * frame) + ((1 / 2f) * GRAVITY.y * BULLET_UNIT_TIME * frame * BULLET_UNIT_TIME * frame) + startY;

		bulletSprite.setPosition((float) x * PPM, (float) y * PPM);

		checkOut();
		if (frame < FRAME_SKIP) {
			checkCollision();
		}

		frame += 1;

	}

	private void checkCollision() {
		app.getGameScreen().checkCollision((float) x, (float) y);
	}

	private void checkOut() {
		if (x * PPM > V_WIDTH || x * PPM < 0 || y * PPM > V_HEIGHT || y * PPM < 0) {
			isVisible = false;
			tank.setFiring(false);
		}
	}

	public void fire(float startX, float startY, double powerX, double powerY) {
		isVisible = true;
		frame = 0;

		this.startX = startX;
		this.startY = startY;
		this.powerX = powerX;
		this.powerY = powerY;
	}


	public void dispose() {
		bulletSprite.getTexture().dispose();
	}
}
