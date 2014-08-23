package com.github.itsmichaelwang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.itsmichaelwang.characters.SquareMan;
import com.github.itsmichaelwang.characters.World;
import com.github.itsmichaelwang.characters.SquareMan.State;
import com.github.itsmichaelwang.controller.DeathBoxController;
import com.github.itsmichaelwang.controller.SquareManController;

public class GameScreen implements Screen, InputProcessor {
	SquareGame squareGame;
	World world;
	GameRenderer renderer;
	SquareMan squareMan;
	SquareManController squareManController;
	DeathBoxController dbController;
	
	private int width, height;
	private long endGameTimer = 0;
	private enum GameState {
		RUNNING, RESTARTING
	}
	GameState gameState;
	
	// Pull in squareGame so we can switch screens around
	public GameScreen(SquareGame squareGame) {
		this.squareGame = squareGame;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		squareManController.update(delta);
		dbController.update(delta, squareMan);
		if (dbController.isGameOver()) {
			gameOverSequence();
			dbController.setGameOver(false);
			endGameTimer = TimeUtils.millis();
			gameState = GameState.RESTARTING;
		}
		if (gameState == GameState.RESTARTING) {
			if (TimeUtils.millis() - endGameTimer > 5000) {
				show();
			}
		}
		renderer.render();
	}
	
	// End game, lock controls and go to game over screen
	private void gameOverSequence() {
		Gdx.input.setInputProcessor(null);
		squareManController.leftReleased();
		squareManController.rightReleased();
		squareManController.jumpReleased();
		squareManController.killSquareMan(dbController.getKillerBox());
		System.out.println();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		gameState = GameState.RUNNING;
		world = new World();
		renderer = new GameRenderer(world);
		squareMan = world.getSquareMan();
		squareManController = new SquareManController(world, renderer.getCamera());
		dbController = new DeathBoxController(world, renderer.getCamera());
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void resume() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			squareManController.leftPressed();
	    if (keycode == Keys.RIGHT)
	        squareManController.rightPressed();
	    if (keycode == Keys.Z)
	        squareManController.jumpPressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			squareManController.leftReleased();
	    if (keycode == Keys.RIGHT)
	        squareManController.rightReleased();
	    if (keycode == Keys.Z)
	        squareManController.jumpReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
