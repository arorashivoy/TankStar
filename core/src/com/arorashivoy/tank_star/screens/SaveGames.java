package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.arorashivoy.tank_star.Helper.CustomConstants.V_HEIGHT;
import static com.arorashivoy.tank_star.Helper.CustomConstants.V_WIDTH;

public class SaveGames implements Screen {
	private final Main app;
	private TextButton save1Button;
	private TextButton save2Button;
	private TextButton save3Button;
	private final TextButton.TextButtonStyle textButtonStyle;
	private final Stage stage;

	// Public Constants
	public static int BTN_WIDTH = 200;
	public static int BTN_HEIGHT = 100;

	public SaveGames(Main app) {
		this.app = app;
		textButtonStyle = new TextButton.TextButtonStyle();
		this.stage = new Stage(app.getViewport());
	}


	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

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


			save2Button = new TextButton("2", textButtonStyle);
			save2Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f - 100);

			save1Button = new TextButton("1", textButtonStyle);
			save1Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f);

			save3Button = new TextButton("3", textButtonStyle);
			save3Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f - 200);

			stage.addActor(save1Button);
			stage.addActor(save2Button);
			stage.addActor(save3Button);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		update(delta);

		stage.draw();
	}

	private void update(float delta) {
		save1Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Load game

				app.setScreen(app.getGameScreen());
			}
		});

		save2Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

			}
		});

		save3Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

			}
		});

		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {

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

	}
}
