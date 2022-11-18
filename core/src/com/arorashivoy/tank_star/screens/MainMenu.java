package com.arorashivoy.tank_star.screens;

import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu implements Screen {
	private Main app;
	private Stage stage;

	private Image testImage;
	public MainMenu(Main app) {
		this.app = app;
		this.stage = new Stage(new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, app.camera));
		Gdx.input.setInputProcessor(stage);

		Texture testTex = new Texture("badlogic.jpg");
		testImage = new Image(testTex);
		testImage.setPosition(stage.getWidth()/2 - 128, stage.getHeight()/2 - 128);

		stage.addActor(testImage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		// Drawing stage actors
		stage.draw();

		app.batch.begin();
		// draw the stuff in SpriteBatch here
		app.batch.end();
	}

	public void update(float delta) {
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
