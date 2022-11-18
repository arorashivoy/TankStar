package com.arorashivoy.tank_star;

import com.arorashivoy.tank_star.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
//	public BitmapFont font;

	public static final int V_WIDTH = 960;
	public static final int V_HEIGHT = 540;

	////////////////////////////////////////////////////// Screens /////////////////////////////////////////////////////
	public MainMenu mainMenu;
	public SplashScreen splashScreen;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
//		font = new BitmapFont();

		mainMenu = new MainMenu(this);
		splashScreen = new SplashScreen(this);

		this.setScreen(splashScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
