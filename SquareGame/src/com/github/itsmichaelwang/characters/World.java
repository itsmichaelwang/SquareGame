package com.github.itsmichaelwang.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	// Our main character
	private SquareMan squareMan;
	public SquareMan getSquareMan() { return squareMan; }
	
	// All the DeathBoxes
	private Array<DeathBox> activeBoxes;
	public Array<DeathBox> getActiveBoxes() { return activeBoxes; }
	
	public World() {
		createWorld();
	}
	
	private void createWorld() {
		squareMan = new SquareMan(new Vector2(7.5f, 5));
		activeBoxes = new Array<DeathBox>();
	}
}
