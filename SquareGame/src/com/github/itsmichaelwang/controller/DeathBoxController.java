package com.github.itsmichaelwang.controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.itsmichaelwang.GameRenderer;
import com.github.itsmichaelwang.characters.DeathBox;
import com.github.itsmichaelwang.characters.World;

public class DeathBoxController {
	// Controls how many seconds between each enemy spawn
	private float spawnInterval = 1;
	private float stopWatch;
	
	// Track on-screen enemies using an array and a 2D grid
	private Array<DeathBox> activeBoxes;
	private DeathBox[][] activeBoxesGrid;
	
	// Camera viewport for box spawn locations
	private float viewportWidth;
	private float viewportHeight;
	
	public DeathBoxController(World world, Camera cam) {
		this.activeBoxes = world.getActiveBoxes();
		this.viewportWidth = cam.viewportWidth;
		this.viewportHeight = cam.viewportHeight;
		stopWatch = 0;
	}
	
	public void update(float delta) {
		stopWatch = stopWatch + delta;
		if (stopWatch >= spawnInterval) {
			stopWatch = stopWatch - spawnInterval;
			createBox();
		}
		
		// Update all the box positions
		for (DeathBox db : activeBoxes) {
			db.update(delta);
			if (db.getPosition().x < -db.getBounds().x || db.getPosition().x > viewportWidth || db.getPosition().y < -db.getBounds().y) {
				activeBoxes.removeValue(db, true);
			}
		}
	}
	
	// In this case, width and height are the dimensions of the game camera
	private void createBox() {
		// Randomly generate a spawning location for the DeathBox
		DeathBox db;
		float SIZE = 1f;	// Temporary constant value
		// Seeds for spawn velocity and position
		float spawnPosSeed = MathUtils.random();
		float spawnVelSeed = MathUtils.random();
		switch ((int) MathUtils.random(0, 3)) {
			case 0:
				// Left side
				db = new DeathBox(
						SIZE,
						new Vector2(-SIZE, spawnPosSeed*(viewportHeight-SIZE)),
						new Vector2(spawnVelSeed + 4, 0));
				break;
			case 1:
				// Top side
				db = new DeathBox(
						SIZE,
						new Vector2(spawnPosSeed*(viewportWidth-SIZE), viewportHeight),
						new Vector2(0, spawnVelSeed - 5));
				break;
			default:
				// Right side
				db = new DeathBox(
						SIZE,
						new Vector2(viewportWidth, spawnPosSeed*(viewportHeight-SIZE)),
						new Vector2(spawnVelSeed - 5, 0));
		}
		activeBoxes.add(db);
	}
}
