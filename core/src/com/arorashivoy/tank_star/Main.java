package com.arorashivoy.tank_star;

import com.arorashivoy.tank_star.screens.*;
import com.arorashivoy.tank_star.Helper.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;
	public AssetManager assets;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;

	public Stage gameStage;
	public Box2DDebugRenderer boxRenderer;
	public World world;
	public OrthogonalTiledMapRenderer mapRenderer;
	public TiledMap map;

	////////////////////////////////////////////////////// Screens /////////////////////////////////////////////////////
	public MainMenu mainMenu;
	public SplashScreen splashScreen;
	public GameScreen gameScreen;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT);
		viewport = new FitViewport(CustomConstants.V_WIDTH, CustomConstants.V_HEIGHT, camera);
		batch = new SpriteBatch();
		assets = new AssetManager();
		shapeRenderer = new ShapeRenderer();

		// Rendering the game screen elements
		this.gameStage = new Stage(this.viewport);
		this.boxRenderer = new Box2DDebugRenderer();
		this.world = new World(CustomConstants.GRAVITY, true);
		this.map = new TmxMapLoader().load("map/test.tmx");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map);
		TiledObjectBox.parseTiledObjectLayer(this.world, map.getLayers().get("collision-layer").getObjects());

		// Creating Screens
		splashScreen = SplashScreen.getInstance(this);
		mainMenu = new MainMenu(this);
		gameScreen = new GameScreen(this);

		initFonts();

		this.setScreen(splashScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		splashScreen.dispose();
		mainMenu.dispose();
		gameScreen.dispose();

		batch.dispose();
		assets.dispose();

		boxRenderer.dispose();
		mapRenderer.dispose();
		map.dispose();
		gameStage.dispose();
	}


	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////
	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/AmericanTypewriter.ttc"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();


		params.size = 24;
		params.color = Color.BLACK;
		font = generator.generateFont(params);
	}
}
