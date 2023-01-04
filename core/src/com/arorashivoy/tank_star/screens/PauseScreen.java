package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Main;
import com.arorashivoy.tank_star.Objects.Tank;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.arorashivoy.tank_star.Helper.CustomConstants.*;

public class PauseScreen implements Screen {
	private final Main app;
	private TextButton resumeButton;
	private TextButton quitButton;
	private TextButton saveButton;
	private Sprite backgroundSprite = null;
	private final TextButton.TextButtonStyle textButtonStyle;
	private final Stage stage;

	private Tank tank1 = null;
	private Tank tank2 = null;

	// Public Constants
	public static int BTN_WIDTH = 200;
	public static int BTN_HEIGHT = 100;

	public PauseScreen(Main app) {
		this.app = app;
		textButtonStyle = new TextButton.TextButtonStyle();
		this.stage = new Stage(app.getViewport());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		if (app.getAssets().isLoaded("img/Backgrounds/Pause.png")) {
			Texture background = app.getAssets().get("img/Backgrounds/Pause.png", Texture.class);
			backgroundSprite = new Sprite(background);
			backgroundSprite.setSize(V_WIDTH, V_HEIGHT);

		}

		// Importing the button textures
		if (app.getAssets().isLoaded("img/Buttons/button-up.png") && app.getAssets().isLoaded("img/Buttons/button-down.png") && app.getAssets().isLoaded("img/Buttons/button-hover.png")) {
			textButtonStyle.font = app.getFont();

			// UP
			Pixmap original_up = app.getAssets().get("img/Buttons/button-up.png", Pixmap.class);
			Pixmap resized_up = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_up.getFormat());
			resized_up.drawPixmap(original_up, 0, 0, original_up.getWidth(), original_up.getHeight(), 0, 0, resized_up.getWidth(), resized_up.getHeight());
			textButtonStyle.up = new TextureRegionDrawable(new Texture(resized_up));
			resized_up.dispose();

			// DOWN
			Pixmap original_down = app.getAssets().get("img/Buttons/button-down.png", Pixmap.class);
			Pixmap resized_down = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_down.getFormat());
			resized_down.drawPixmap(original_down, 0, 0, original_down.getWidth(), original_down.getHeight(), 0, 0, resized_down.getWidth(), resized_down.getHeight());
			textButtonStyle.down = new TextureRegionDrawable(new Texture(resized_down));
//			textButtonStyle.checked = new TextureRegionDrawable(new Texture(resized_down));
			resized_down.dispose();

			// HOVER
			Pixmap original_hover = app.getAssets().get("img/Buttons/button-hover.png", Pixmap.class);
			Pixmap resized_hover = new Pixmap(BTN_WIDTH, BTN_HEIGHT, original_hover.getFormat());
			resized_hover.drawPixmap(original_hover, 0, 0, original_hover.getWidth(), original_hover.getHeight(), 0, 0, resized_hover.getWidth(), resized_hover.getHeight());
			textButtonStyle.over = new TextureRegionDrawable(new Texture(resized_hover));
			resized_hover.dispose();


			saveButton = new TextButton("Save", textButtonStyle);
			saveButton.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f - 100);

			resumeButton = new TextButton("Resume", textButtonStyle);
			resumeButton.setPosition(V_WIDTH /2f, V_HEIGHT / 2f);

			quitButton = new TextButton("Quit", textButtonStyle);
			quitButton.setPosition(V_WIDTH /2f, V_HEIGHT / 2f - 200);

			stage.addActor(saveButton);
			stage.addActor(resumeButton);
			stage.addActor(quitButton);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		update(delta);

		app.getBatch().begin();
		if (backgroundSprite != null) {
			backgroundSprite.draw(app.getBatch());
		}
		app.getBatch().end();

		stage.draw();
	}

	private void update(float delta) {
		resumeButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				app.setScreen(app.getGameScreen());
			}
		});

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				app.setScreen(app.getMainMenu());
			}
		});

		saveButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				if (tank1 != null && tank2 != null) {
					app.saveGame(tank1, tank2);
				}
				app.setScreen(app.getMainMenu());
			}
		});

		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		app.getViewport().update(width, height, false);
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
		backgroundSprite.getTexture().dispose();
		stage.dispose();
	}

	public void gamePause(Tank tank1, Tank tank2) {
		this.tank1 = tank1;
		this.tank2 = tank2;
	}
}
