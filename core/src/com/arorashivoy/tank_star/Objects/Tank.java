package com.arorashivoy.tank_star.Objects;

import static com.arorashivoy.tank_star.Helper.CustomConstants.*;
import static java.lang.Math.*;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Tank {
	private final Main app;
	private final boolean player;
	private final Body body;
	private Sprite tankSprite;
	private boolean chance = true;
	private float angle;
	private float power = 50f;
	private float health = INITIAL_HEALTH;
	private float fuel = INITIAL_FUEL;
	private final Bullets bullet;
	private boolean isFiring = false;

	public Tank(Main app, boolean player, World world, float SCALE) {
		this.player = player;
		this.app = app;

		// Bullet
		bullet = Bullets.createBullet(app, this);


		// Box2D Body
		BodyDef def = new BodyDef();

		if (player) {
			angle = 180f;
			def.position.set(V_WIDTH / (2 * PPM), 200 / PPM);
		} else {
			angle = 0f;
			def.position.set(TANK_WIDTH / PPM, 50 / PPM);
		}

		def.type = BodyDef.BodyType.DynamicBody;
		def.fixedRotation = true;
		body = world.createBody(def);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(TANK_WIDTH / (SCALE * PPM), (3 / 4f) * TANK_HEIGHT / (SCALE * PPM));

		body.createFixture(polygonShape, 1f);
		polygonShape.dispose();
	}

	public void show() {
		// Tank Texture
		Texture tankTex = app.assets.get("img/Tanks/Frost.PNG", Texture.class);
		tankSprite = new Sprite(tankTex);

		if (player) {
			tankSprite.flip(true, false);
		}

		tankSprite.setSize(TANK_WIDTH, TANK_HEIGHT);

		bullet.show();

	}

	@SuppressWarnings("unused")
	public void draw(float delta) {
		updateTankSprite();

		// For shooting
		if (chance) {
			app.shapeRenderer.setProjectionMatrix(app.camera.combined);
			app.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			app.shapeRenderer.setColor(Color.GRAY);
			app.shapeRenderer.rect(body.getPosition().x, body.getPosition().y, 0, 0, power / PPM, 2 / PPM, 1f, 1f, angle);
			app.shapeRenderer.end();
		}

		// Health bar
		app.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		app.shapeRenderer.setColor(Color.BLUE);
		app.shapeRenderer.rect((player ? 500 : 150) / PPM, (V_HEIGHT - 50) / PPM, 0, 0, health * 3 / PPM, 20 / PPM, 1f, 1f, 0f);
		app.shapeRenderer.end();


		// fuel bar
		app.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		app.shapeRenderer.setColor(Color.YELLOW);
		app.shapeRenderer.rect((player ? V_WIDTH - 50 - INITIAL_FUEL : 50) / PPM, 10 / PPM, 0, 0, fuel / PPM, 20 / PPM, 1f, 1f, 0f);
		app.shapeRenderer.end();


		app.batch.begin();
		tankSprite.draw(app.batch);
		app.batch.end();
	}


	private void updateTankSprite() {
		tankSprite.setPosition((body.getPosition().x * PPM) - TANK_WIDTH / 2f, (body.getPosition().y * PPM) - 3 * TANK_HEIGHT / 5f);
	}

	@SuppressWarnings("unused")
	public void inputUpdate(float delta) {
		int horizontalForce = 0;

		// Move forward Backward
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && fuel > 0) {
			horizontalForce -= 1;
			fuel -= 2f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && fuel > 0) {
			horizontalForce += 1;
			fuel -= 2f;
		}

		// Set bullet angle
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			angle += (player) ? -1 : 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			angle -= (player) ? -1 : 1;
		}

		// Set bullet power
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			power -= (player) ? -1 : 1;

			if (power < 30) {
				power = 30;
			} else if (power > 100) {
				power = 100;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (power <= 100 && power >= 30) {
				power += (player) ? -1 : 1;
			}
		}


		body.setLinearVelocity(horizontalForce * 5, body.getLinearVelocity().y);
	}

	public void dispose() {
		tankSprite.getTexture().dispose();
		bullet.dispose();
	}

	public void shoot() {
		bullet.fire(body.getPosition().x, body.getPosition().y, power * cos(toRadians(angle)), power * sin(toRadians(angle)));
		isFiring = true;
	}

	public void setFiring(boolean firing) {
		isFiring = firing;
	}

	public boolean isFiring() {
		return isFiring;
	}

	public void drawBullet() {
		bullet.draw();
	}

	////////////////////////////////////////////////// Getter Setter ///////////////////////////////////////////////////
	public void setChance(boolean chance) {
		this.chance = chance;
	}

	public Body getBody() {
		return body;
	}

	public void resetFuel() {
		fuel = INITIAL_FUEL;
	}

	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////
	public void checkDamage(float x, float y) {
		x -= body.getPosition().x;
		y -= body.getPosition().y;
		double damageX = pow(2.71, -0.5f * (x * x));
		double damageY = pow(2.71, -0.5f * (y * y));

		health -= (damageX + damageY) * DAMAGE_CONST;
		System.out.println("Damage " + (damageX + damageY) * DAMAGE_CONST + player);
	}
}
