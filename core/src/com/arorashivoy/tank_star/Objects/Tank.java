package com.arorashivoy.tank_star.Objects;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Tank {
	private final Main app;
	private boolean player;
	Body body;

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

	public void render(float delta) {
		app.batch.begin();
		app.batch.draw(tankTex, (body.getPosition().x * CustomConstants.PPM) - CustomConstants.TANK_WIDTH / 2f, (body.getPosition().y * CustomConstants.PPM) - 3 * CustomConstants.TANK_HEIGHT / 5f, CustomConstants.TANK_WIDTH, CustomConstants.TANK_HEIGHT);
		app.batch.end();
	}
}
