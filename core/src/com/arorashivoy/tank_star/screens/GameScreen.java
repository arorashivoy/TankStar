package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Objects.Tank;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen implements Screen {
	private final Main app;
	private static final float SCALE = 2f;

	private Tank tank;

	private final TextButtonStyle buttonStyle;
	private TextButton inGameButton;




	public GameScreen(Main app) {
		this.app = app;
		tank = new Tank(app,false, app.world, SCALE);

		// Button
		buttonStyle = new TextButtonStyle();
	}


	@Override
	public void show() {
		Gdx.input.setInputProcessor(app.gameStage);

		// Background
		Pixmap bgOriginal = app.assets.get("img/Backgrounds/background.png", Pixmap.class);
		Pixmap bgResized = new Pixmap(CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT, bgOriginal.getFormat());
		bgResized.drawPixmap(bgOriginal, 0, 0, bgOriginal.getWidth(), bgOriginal.getHeight(), 0, 0, bgResized.getWidth(), bgResized.getHeight());
		Texture bgTex = new Texture(bgResized);
		bgResized.dispose();
		Image bg = new Image(bgTex);

		app.gameStage.addActor(bg);


		// Pause Button Texture
		buttonStyle.font = app.font;
		Pixmap original_up = app.assets.get("img/Buttons/In-game-btn.png", Pixmap.class);
		Pixmap resized_up = new Pixmap(CustomConstants.IN_GAME_BTN_SIZE, CustomConstants.IN_GAME_BTN_SIZE, original_up.getFormat());
		resized_up.drawPixmap(original_up, 0, 0, original_up.getWidth(), original_up.getHeight(), 0, 0, resized_up.getWidth(), resized_up.getHeight());
		buttonStyle.up = new TextureRegionDrawable(new Texture(resized_up));

		resized_up.dispose();

		Pixmap original_down = app.assets.get("img/Buttons/In-game-btn-down.png", Pixmap.class);
		Pixmap resized_down = new Pixmap(CustomConstants.IN_GAME_BTN_SIZE, CustomConstants.IN_GAME_BTN_SIZE, original_down.getFormat());
		resized_down.drawPixmap(original_down, 0, 0, original_down.getWidth(), original_down.getHeight(), 0, 0, resized_down.getWidth(), resized_down.getHeight());
		buttonStyle.down = new TextureRegionDrawable(new Texture(resized_down));
		buttonStyle.over = new TextureRegionDrawable(new Texture(resized_down));
		resized_down.dispose();

		// Button
		inGameButton = new TextButton("", buttonStyle);
		inGameButton.setPosition( 0, CustomConstants.V_HEIGHT - CustomConstants.IN_GAME_BTN_SIZE - 10);

		app.gameStage.addActor(inGameButton);

		tank.show();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


		update(delta);

		app.gameStage.draw();

		app.mapRenderer.render();
		app.boxRenderer.render(app.world, app.camera.combined.scl(CustomConstants.PPM));

		app.batch.begin();
		// Draw shit here
		tank.draw(delta);
		app.batch.end();
	}

	public void update(float delta) {
		inGameButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println("Button clicked");
			}
		});

		app.gameStage.act(delta);

		app.world.step(1 / 60f, 6, 2);
		inputUpdate(delta);
		cameraUpdate(delta);

		app.mapRenderer.setView(app.camera);
		app.batch.setProjectionMatrix(app.camera.combined);
	}

	private void inputUpdate(float delta) {
		tank.inputUpdate(delta);
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
		tank.dispose();
	}


	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////

}
