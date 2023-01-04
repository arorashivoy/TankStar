package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Main;
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

import static com.arorashivoy.tank_star.Helper.CustomConstants.V_HEIGHT;
import static com.arorashivoy.tank_star.Helper.CustomConstants.V_WIDTH;

public class ChooseTank implements Screen {
	private final Main app;
	private TextButton tank1Button;
	private TextButton tank2Button;
	private TextButton tank3Button;
	private Sprite backgroundSprite = null;
	private final TextButton.TextButtonStyle textButtonStyle;
	private final Stage stage;
	private String tank1addr;
	private boolean first = true;

	// Public Constants
	public static int BTN_WIDTH = 200;
	public static int BTN_HEIGHT = 100;

	public ChooseTank(Main app) {
		this.app = app;
		textButtonStyle = new TextButton.TextButtonStyle();
		this.stage = new Stage(app.getViewport());
		first = true;
	}

	public ChooseTank(Main app, String tank1addr) {
		this.app = app;
		textButtonStyle = new TextButton.TextButtonStyle();
		this.stage = new Stage(app.getViewport());
		this.tank1addr = tank1addr;
		first = false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		if (app.getAssets().isLoaded("img/Backgrounds/ChooseTank.png")) {
			Texture background = app.getAssets().get("img/Backgrounds/ChooseTank.png", Texture.class);
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


			tank2Button = new TextButton("2", textButtonStyle);
			tank2Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f - 100);

			tank1Button = new TextButton("1", textButtonStyle);
			tank1Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f);

			tank3Button = new TextButton("3", textButtonStyle);
			tank3Button.setPosition(V_WIDTH / 2f, V_HEIGHT / 2f - 200);

			stage.addActor(tank1Button);
			stage.addActor(tank2Button);
			stage.addActor(tank3Button);
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
		tank1Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (first) {
					app.setScreen(new ChooseTank(app, "img/Tanks/Frost.PNG"));
				} else {
					app.getGameScreen().setTankAddr(tank1addr, "img/Tanks/Frost.PNG");
					app.setScreen(app.getGameScreen());
				}
			}
		});

		tank2Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (first) {
					app.setScreen(new ChooseTank(app, "img/Tanks/Tank5.PNG"));
				} else {
					app.getGameScreen().setTankAddr(tank1addr, "img/Tanks/Tank5.PNG");
					app.setScreen(app.getGameScreen());
				}
			}
		});

		tank3Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (first) {
					app.setScreen(new ChooseTank(app, "img/Tanks/Tank2.png"));
				} else {
					app.getGameScreen().setTankAddr(tank1addr, "img/Tanks/Tank2.png");
					app.setScreen(app.getGameScreen());
				}
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
}
