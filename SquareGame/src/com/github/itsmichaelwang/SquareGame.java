package com.github.itsmichaelwang;

import com.badlogic.gdx.Game;

public class SquareGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
	
	public void toGameOverScreen() {
		setScreen(new GameOverScreen());
	}
}
