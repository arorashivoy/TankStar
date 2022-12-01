package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenu implements Screen {
	private Main app;
	private Stage stage;
	private Texture background = null;
	private TextButton playButton;
	private TextButton resumeButton;
	private TextButtonStyle textButtonStyle;

	// Public Constants
	public static int BTN_WIDTH = 200;
	public static int BTN_HEIGHT = 100;

	public MainMenu(Main app) {
		this.app = app;
		this.stage = new Stage(app.viewport);
		textButtonStyle = new TextButtonStyle();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		// Importing the background image
		if (app.assets.isLoaded("img/Backgrounds/Main_menu.png") && background == null) {
			Pixmap original = app.assets.get("img/Backgrounds/Main_menu.png", Pixmap.class);
			Pixmap resized = new Pixmap(CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT, original.getFormat());
			resized.drawPixmap(original, 0, 0, original.getWidth(), original.getHeight(), 0, 0, resized.getWidth(), resized.getHeight());
			background = new Texture(resized);

			// Disposing the pixmap
			resized.dispose();
		}

		// Importing the button textures
		if (app.assets.isLoaded("img/Buttons/button-up.png") && app.assets.isLoaded("img/Buttons/button-down.png") && app.assets.isLoaded("img/Buttons/button-hover.png")) {
			textButtonStyle.font = app.font;

			// UP
			Pixmap original_up = app.assets.get("img/Buttons/button-up.png", Pixmap.class);
			Pixmap resized_up = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_up.getFormat());
			resized_up.drawPixmap(original_up, 0, 0, original_up.getWidth(), original_up.getHeight(), 0, 0, resized_up.getWidth(), resized_up.getHeight());
			textButtonStyle.up = new TextureRegionDrawable(new Texture(resized_up));
			resized_up.dispose();

			// DOWN
			Pixmap original_down = app.assets.get("img/Buttons/button-down.png", Pixmap.class);
			Pixmap resized_down = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_down.getFormat());
			resized_down.drawPixmap(original_down, 0, 0, original_down.getWidth(), original_down.getHeight(), 0, 0, resized_down.getWidth(), resized_down.getHeight());
			textButtonStyle.down = new TextureRegionDrawable(new Texture(resized_down));
//			textButtonStyle.checked = new TextureRegionDrawable(new Texture(resized_down));
			resized_down.dispose();

			// HOVER
			Pixmap original_hover = app.assets.get("img/Buttons/button-hover.png", Pixmap.class);
			Pixmap resized_hover = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_hover.getFormat());
			resized_hover.drawPixmap(original_hover, 0, 0, original_hover.getWidth(), original_hover.getHeight(), 0, 0, resized_hover.getWidth(), resized_hover.getHeight());
			textButtonStyle.over = new TextureRegionDrawable(new Texture(resized_hover));
			resized_hover.dispose();


			playButton = new TextButton("Play", textButtonStyle);
			playButton.setPosition(650,350);

			resumeButton = new TextButton("Resume", textButtonStyle);
			resumeButton.setPosition(650,200);

			stage.addActor(playButton);
			stage.addActor(resumeButton);
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		update(delta);

		app.batch.begin();
		// draw the stuff in SpriteBatch here
		app.batch.draw(background, 0, 0);
		app.batch.end();

		// Drawing stage actors
		stage.draw();
	}

	private void update(float delta) {
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
			}
		});
		stage.act(delta);
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
		background.dispose();
		stage.dispose();
	}
}
