package com.arorashivoy.tank_star.Objects;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Tank {
	private final Main app;
	private boolean player;
	private Body body;
	private Texture tankTex;

	public Tank(Main app, boolean player, World world, float SCALE) {
		this.player = player;
		this.app = app;


		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(CustomConstants.TANK_WIDTH / CustomConstants.PPM, 50 / CustomConstants.PPM);
		def.fixedRotation = true;
		body = world.createBody(def);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(CustomConstants.TANK_WIDTH / (SCALE * CustomConstants.PPM), 3 * CustomConstants.TANK_HEIGHT / 4 / (SCALE * CustomConstants.PPM));

		body.createFixture(polygonShape, 1f);
		polygonShape.dispose();
	}

	public void show() {
		// Tank texture
		Pixmap original = app.assets.get("img/Tanks/Frost.PNG", Pixmap.class);
		Pixmap resized = new Pixmap(CustomConstants.TANK_WIDTH, CustomConstants.TANK_HEIGHT, original.getFormat());
		resized.drawPixmap(original, 0, 0, original.getWidth(), original.getHeight(), 0, 0, resized.getWidth(), resized.getHeight());
		tankTex = new Texture(resized);
		resized.dispose();
	}

	public void draw(float delta) {
		app.batch.draw(tankTex, (body.getPosition().x * CustomConstants.PPM) - CustomConstants.TANK_WIDTH / 2f, (body.getPosition().y * CustomConstants.PPM) - 3 * CustomConstants.TANK_HEIGHT / 5f, CustomConstants.TANK_WIDTH, CustomConstants.TANK_HEIGHT);
	}

	public void inputUpdate(float delta) {
		int horizontalForce = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}

		body.setLinearVelocity(horizontalForce * 5, body.getLinearVelocity().y);
		body.applyForceToCenter(CustomConstants.TANK_GRAVITY, true);

	}

	public void dispose() {
		tankTex.dispose();
	}
}
