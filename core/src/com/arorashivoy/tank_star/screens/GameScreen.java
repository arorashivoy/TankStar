package com.arorashivoy.tank_star.screens;

import static com.arorashivoy.tank_star.Helper.CustomConstants.*;
import com.arorashivoy.tank_star.Objects.Tank;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen implements Screen {
	private final Main app;
	private static final float SCALE = 2f;

	private Tank tank1;
	private Tank tank2;
	private boolean player = false;
	private String tank1addr = "img/Tanks/Frost.PNG";
	private String tank2addr = "img/Tanks/Frost.PNG";

	private final TextButtonStyle buttonStyle;
	private TextButton inGameButton;



	public GameScreen(Main app) {
		this.app = app;

		// Button
		buttonStyle = new TextButtonStyle();
	}


	@Override
	public void show() {
		Gdx.input.setInputProcessor(app.getGameStage());

		if (tank1 != null) {
			app.getWorld().destroyBody(tank1.getBody());
		}
		if (tank2 != null) {
			app.getWorld().destroyBody(tank2.getBody());
		}

		tank1 = new Tank(app,false, app.getWorld(), SCALE, tank1addr);
		tank2 = new Tank(app,true, app.getWorld(), SCALE, tank2addr);

		// Background
		Pixmap bgOriginal = app.getAssets().get("img/Backgrounds/background.png", Pixmap.class);
		Pixmap bgResized = new Pixmap(V_WIDTH, V_HEIGHT, bgOriginal.getFormat());
		bgResized.drawPixmap(bgOriginal, 0, 0, bgOriginal.getWidth(), bgOriginal.getHeight(), 0, 0, bgResized.getWidth(), bgResized.getHeight());
		Texture bgTex = new Texture(bgResized);
		bgResized.dispose();
		Image bg = new Image(bgTex);

		app.getGameStage().addActor(bg);


		// Pause Button Texture
		buttonStyle.font = app.getFont();
		Pixmap original_up = app.getAssets().get("img/Buttons/In-game-btn.png", Pixmap.class);
		Pixmap resized_up = new Pixmap(IN_GAME_BTN_SIZE, IN_GAME_BTN_SIZE, original_up.getFormat());
		resized_up.drawPixmap(original_up, 0, 0, original_up.getWidth(), original_up.getHeight(), 0, 0, resized_up.getWidth(), resized_up.getHeight());
		buttonStyle.up = new TextureRegionDrawable(new Texture(resized_up));

		resized_up.dispose();

		Pixmap original_down = app.getAssets().get("img/Buttons/In-game-btn-down.png", Pixmap.class);
		Pixmap resized_down = new Pixmap(IN_GAME_BTN_SIZE, IN_GAME_BTN_SIZE, original_down.getFormat());
		resized_down.drawPixmap(original_down, 0, 0, original_down.getWidth(), original_down.getHeight(), 0, 0, resized_down.getWidth(), resized_down.getHeight());
		buttonStyle.down = new TextureRegionDrawable(new Texture(resized_down));
		buttonStyle.over = new TextureRegionDrawable(new Texture(resized_down));
		resized_down.dispose();

		// Button
		inGameButton = new TextButton("", buttonStyle);
		inGameButton.setPosition( 0, V_HEIGHT - IN_GAME_BTN_SIZE - 10);


		app.getGameStage().addActor(inGameButton);

		tank1.show();
		tank2.show();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


		update(delta);

		app.getGameStage().draw();

		app.getBatch().begin();
		tank1.drawBullet();
		tank2.drawBullet();
		app.getBatch().end();

		app.getMapRenderer().render();
		app.getBoxRenderer().render(app.getWorld(), app.getCamera().combined.scl(PPM));


		tank1.draw(delta);
		tank2.draw(delta);
	}

	public void update(float delta) {
		inGameButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				app.getPauseScreen().gamePause(tank1, tank2);
				app.setScreen(app.getPauseScreen());
			}
		});

		app.getGameStage().act(delta);

		app.getWorld().step(1 / 60f, 6, 2);
		inputUpdate(delta);
		cameraUpdate();

		app.getMapRenderer().setView(app.getCamera());
		app.getBatch().setProjectionMatrix(app.getCamera().combined);


		tank1.setChance(!player);
		tank2.setChance(player);
	}

	private void inputUpdate(float delta) {
		// Setting gravity for tanks
		tank1.getBody().applyForceToCenter(TANK_GRAVITY, true);
		tank2.getBody().applyForceToCenter(TANK_GRAVITY, true);

		// checking if any is firing
		if (tank1.isFiring() || tank2.isFiring()) {
			return;
		}

		// key presses
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (!player) {
				tank1.shoot();
			} else {
				tank2.shoot();
			}
			player = !player;
			tank1.resetFuel();
			tank2.resetFuel();
		}
		if (player) {
			tank2.inputUpdate(delta);
		}
		else {
			tank1.inputUpdate(delta);
		}
	}

	private void cameraUpdate() {
		Vector3 position = app.getCamera().position;
		position.x = (V_WIDTH / SCALE);
		position.y = (V_HEIGHT / SCALE);
		app.getCamera().position.set(position);

		app.getCamera().update();
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
		tank1.dispose();
		tank2.dispose();
	}


	public void setTankAddr(String addr1, String addr2) {
		this.tank1addr = addr1;
		this.tank2addr = addr2;
	}


	///////////////////////////////////////////////////// HELPERS //////////////////////////////////////////////////////

	public void checkCollision(float x, float y) {
		tank1.checkDamage(x, y);
		tank2.checkDamage(x, y);
	}


}
