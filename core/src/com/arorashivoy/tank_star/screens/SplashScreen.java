package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SplashScreen implements Screen {
	private Main app;
	private Stage stage;
	int frame = 0;

	private Image SplashImage;
	public SplashScreen(Main app) {
		this.app = app;
		this.stage = new Stage(new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, app.camera));
		Gdx.input.setInputProcessor(stage);


		Pixmap Original = new Pixmap(Gdx.files.internal("img/SplashScreen.png"));
		Pixmap resized = new Pixmap(Main.V_WIDTH, Main.V_HEIGHT, Original.getFormat());
		resized.drawPixmap(Original, 0, 0, Original.getWidth(), Original.getHeight(), 0, 0, resized.getWidth(), resized.getHeight());
		Texture SplashTex = new Texture(resized);
		SplashImage = new Image(SplashTex);
		SplashImage.setPosition(0, 0);

		stage.addActor(SplashImage);
	}

	@Override
	public void show() {

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
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			app.setScreen(new MainMenu(app));
		}
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
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
