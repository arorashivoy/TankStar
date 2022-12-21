package com.arorashivoy.tank_star.Objects;

import static com.arorashivoy.tank_star.Helper.CustomConstants.*;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullets {
	private final Main app;
	private final Tank tank;
	private boolean isVisible = false;
	private double x, y;
	private double startX, startY;
	private double powerX, powerY;
	private int frame = 0;

	private Sprite bulletSprite;

	public Bullets(Main app, Tank tank) {
		this.app = app;
		this.tank = tank;
	}

	public void show() {
		// Bullet Texture
		Texture bulletTex = app.assets.get("img/Bullets/Bullet.png", Texture.class);
		bulletSprite = new Sprite(bulletTex);
		bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
	}


	public void draw() {
		if (isVisible) {
			update();
			bulletSprite.draw(app.batch);
		}
	}

	private void update() {
		x = (powerX * BULLET_UNIT_TIME * frame) + startX;
		y = (powerY * BULLET_UNIT_TIME * frame) + ((1 / 2f) * GRAVITY.y * BULLET_UNIT_TIME * frame * BULLET_UNIT_TIME * frame) + startY;

		bulletSprite.setPosition((float) x, (float) y);

		checkOut();
		checkCollision();

		frame += 1;

	}

	private void checkCollision() {
		app.gameScreen.checkCollision((float) x, (float) y);
	}

	private void checkOut() {
		if (x > V_WIDTH || x < 0 || y > V_HEIGHT || y < 0) {
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
