package com.arorashivoy.tank_star;

import com.arorashivoy.tank_star.Objects.Tank;
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

import java.util.LinkedList;

public class Main extends Game {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private AssetManager assets;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;

	private Stage gameStage;
	private Box2DDebugRenderer boxRenderer;
	private World world;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;

	////////////////////////////////////////////////////// Screens /////////////////////////////////////////////////////
	private MainMenu mainMenu;
	private SplashScreen splashScreen;
	private GameScreen gameScreen;
	private PauseScreen pauseScreen;

	private LinkedList<LinkedList<Tank>> saves = new LinkedList<>();

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
		pauseScreen = new PauseScreen(this);

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

	public void saveGame(Tank tank1, Tank tank2) {
		LinkedList<Tank> tanks = new LinkedList<>();
		tanks.add(tank1);
		tanks.addLast(tank2);
		saves.addFirst(tanks);
	}

	////////////////////////////////////////////////// Getter Setter ///////////////////////////////////////////////////

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public AssetManager getAssets() {
		return assets;
	}

	public BitmapFont getFont() {
		return font;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public Stage getGameStage() {
		return gameStage;
	}

	public Box2DDebugRenderer getBoxRenderer() {
		return boxRenderer;
	}

	public World getWorld() {
		return world;
	}

	public OrthogonalTiledMapRenderer getMapRenderer() {
		return mapRenderer;
	}


	public MainMenu getMainMenu() {
		return mainMenu;
	}


	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}
}
