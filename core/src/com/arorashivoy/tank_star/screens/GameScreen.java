package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Helper.TiledObjectBox;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen implements Screen {
	private final Main app;
	private static final float SCALE = 2f;

	private World world;
	private Body tank;
	private Texture tankTex;
	private Image bg;

	private TextButtonStyle buttonStyle;
	private TextButton inGameButton;

	private Stage stage;
	private Box2DDebugRenderer boxRenderer;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;


	public GameScreen(Main app) {
		this.app = app;
		this.stage = new Stage(app.viewport);

		world = new World(CustomConstants.GRAVITY, true);
		boxRenderer = new Box2DDebugRenderer();
		tank = createTank(CustomConstants.TANK_WIDTH, 50, CustomConstants.TANK_WIDTH, 3 * CustomConstants.TANK_HEIGHT / 4, false);

		// Button
		buttonStyle = new TextButtonStyle();

		// Map
		map = new TmxMapLoader().load("map/test.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		TiledObjectBox.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
	}


	@Override
	public void show() {
		// Tank one
		Pixmap original = app.assets.get("img/Tanks/Frost.PNG", Pixmap.class);
		Pixmap resized = new Pixmap(CustomConstants.TANK_WIDTH, CustomConstants.TANK_HEIGHT, original.getFormat());
		resized.drawPixmap(original, 0, 0, original.getWidth(), original.getHeight(), 0, 0, resized.getWidth(), resized.getHeight());
		tankTex = new Texture(resized);
		resized.dispose();

		// Background
		Pixmap bgOriginal = app.assets.get("img/Backgrounds/background.png", Pixmap.class);
		Pixmap bgResized = new Pixmap(CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT, bgOriginal.getFormat());
		bgResized.drawPixmap(bgOriginal, 0, 0, bgOriginal.getWidth(), bgOriginal.getHeight(), 0, 0, bgResized.getWidth(), bgResized.getHeight());
		Texture bgTex = new Texture(bgResized);
		bgResized.dispose();
		bg = new Image(bgTex);

		stage.addActor(bg);


		// Button Texture
		buttonStyle.font = app.font;
		Pixmap original_up = app.assets.get("img/Buttons/In-game-btn.png", Pixmap.class);
		Pixmap resized_up = new Pixmap(CustomConstants.IN_GAME_BTN_SIZE, CustomConstants.IN_GAME_BTN_SIZE, original_up.getFormat());
		resized_up.drawPixmap(original_up, 0, 0, original_up.getWidth(), original_up.getHeight(), 0, 0, resized_up.getWidth(), resized_up.getHeight());
		buttonStyle.up = new TextureRegionDrawable(new Texture(resized_up));
		buttonStyle.down = new TextureRegionDrawable(new Texture(resized_up));
		resized_up.dispose();

		Pixmap original_hover = app.assets.get("img/Buttons/In-game-btn-hover.png", Pixmap.class);
		Pixmap resized_hover = new Pixmap(CustomConstants.IN_GAME_BTN_SIZE, CustomConstants.IN_GAME_BTN_SIZE, original_hover.getFormat());
		resized_hover.drawPixmap(original_hover, 0, 0, original_hover.getWidth(), original_hover.getHeight(), 0, 0, resized_hover.getWidth(), resized_hover.getHeight());
		buttonStyle.over = new TextureRegionDrawable(new Texture(resized_hover));
		resized_hover.dispose();

		// Button
		inGameButton = new TextButton("", buttonStyle);
		inGameButton.setPosition( 0, CustomConstants.V_HEIGHT - CustomConstants.IN_GAME_BTN_SIZE - 10);

		stage.addActor(inGameButton);




	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


		update(delta);

		mapRenderer.render();
		boxRenderer.render(world, app.camera.combined.scl(CustomConstants.PPM));

		app.batch.begin();
		app.batch.draw(tankTex, (tank.getPosition().x * CustomConstants.PPM) - CustomConstants.TANK_WIDTH / 2f, (tank.getPosition().y * CustomConstants.PPM) - 3 * CustomConstants.TANK_HEIGHT / 5f, CustomConstants.TANK_WIDTH, CustomConstants.TANK_HEIGHT);
		app.batch.end();
		stage.draw();
	}

	public void update(float delta) {
		world.step(1 / 60f, 6, 2);
		stage.act(delta);
		inputUpdate(delta);
		cameraUpdate(delta);
		inGameButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Button clicked");
			}
		});
		mapRenderer.setView(app.camera);
		app.batch.setProjectionMatrix(app.camera.combined);
	}

	private void inputUpdate(float delta) {
		int horizontalForce = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}

		tank.setLinearVelocity(horizontalForce * 5, tank.getLinearVelocity().y);
		tank.applyForceToCenter(CustomConstants.TANK_GRAVITY, true);

	}

	private void cameraUpdate(float delta) {
		Vector3 position = app.camera.position;
		position.x = (CustomConstants.V_WIDTH / SCALE);
		position.y = (CustomConstants.V_HEIGHT / SCALE);
		app.camera.position.set(position);

		app.camera.update();
	}

	@Override
	public void resize(int width, int height) {
		app.viewport.update(width, height, false);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		world.dispose();
		boxRenderer.dispose();
		mapRenderer.dispose();
		map.dispose();
		tankTex.dispose();
		stage.dispose();
	}


	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////
	private Body createTank(int x, int y, int width, int height, Boolean isStatic) {
		Body body;
		BodyDef def = new BodyDef();
		def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
		def.position.set(x / CustomConstants.PPM, y / CustomConstants.PPM);
		def.fixedRotation = true;
		body = world.createBody(def);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width / (SCALE * CustomConstants.PPM), height / (SCALE * CustomConstants.PPM));

		body.createFixture(polygonShape, 1f);
		polygonShape.dispose();

		return body;
	}
}
