package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Splash Screen to load the assets
 */
public class SplashScreen implements Screen {
	private final Main app;
	private Stage stage;
	private int frame = 0;

	private Image splashImage;

	public SplashScreen(Main app) {
		this.app = app;
		this.stage = new Stage(app.viewport);
		Gdx.input.setInputProcessor(stage);

		// Importing the image
		Pixmap Original = new Pixmap(Gdx.files.internal("img/Backgrounds/SplashScreen.png"));
		Pixmap resized = new Pixmap(CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT, Original.getFormat());
		resized.drawPixmap(Original, 0, 0, Original.getWidth(), Original.getHeight(), 0, 0, resized.getWidth(), resized.getHeight());
		Texture splashTex = new Texture(resized);
		splashImage = new Image(splashTex);

		// Disposing the pix-maps and textures
		Original.dispose();
		resized.dispose();

		// Adding the actor
		stage.addActor(splashImage);
	}

	@Override
	public void show() {
		splashImage.setPosition(0, 0);
		splashImage.addAction(sequence(alpha(0f), fadeIn(1f)));

		// Queueing the assets
		queueAssets();
	}

	@Override
	public void render(float delta) {
		frame += 1;
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		update(delta);

		// Drawing stage actors
		stage.draw();

//		app.batch.begin();
//		// draw the stuff in SpriteBatch here
//		app.batch.end();
	}

	public void update(float delta) {
		if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || frame > 150) && app.assets.update() && app.font != null) {
			app.setScreen(app.mainMenu);
		}
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
		stage.dispose();
	}

	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////

	/**
	 * Add all the assets to be preloaded to the asset manager
	 */
	private void queueAssets() {
		app.assets.load("img/Backgrounds/Main_menu.png", Pixmap.class);
		app.assets.load("img/Buttons/button-up.png", Pixmap.class);
		app.assets.load("img/Buttons/button-down.png", Pixmap.class);
		app.assets.load("img/Buttons/button-hover.png", Pixmap.class);
		app.assets.load("img/Buttons/In_Game_Button.png", Pixmap.class);
		app.assets.load("img/Buttons/In_Game_Button_hover.png", Pixmap.class);
		app.assets.load("img/Buttons/In-game-btn.png", Pixmap.class);
		app.assets.load("img/Buttons/In-game-btn-down.png", Pixmap.class);
		app.assets.load("img/Tanks/Frost.PNG", Pixmap.class);
		app.assets.load("img/Backgrounds/background.png", Pixmap.class);
	}
}
