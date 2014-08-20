package com.github.itsmichaelwang.controller;

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
	
	// Track on-screen enemies using an array
	private Array<DeathBox> activeBoxes;
	
	public DeathBoxController(World world) {
		this.activeBoxes = world.getActiveBoxes();
		stopWatch = 0;
	}
	
	public void update(float delta) {
		float width = GameRenderer.getCameraWidth();
		float height = GameRenderer.getCameraHeight();
		stopWatch = stopWatch + delta;
		if (stopWatch >= spawnInterval) {
			stopWatch = stopWatch - spawnInterval;
			createBox(width, height);
		}
		
		// Update all the box positions
		for (DeathBox db : activeBoxes) {
			db.update(delta);
			if (db.getPosition().x < -db.getBounds().x || db.getPosition().x > width || db.getPosition().y < -db.getBounds().y) {
				activeBoxes.removeValue(db, true);
			}
		}
	}
	
	// In this case, width and height are the dimensions of the game camera
	private void createBox(float width, float height) {
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
						new Vector2(-SIZE, spawnPosSeed*(height-SIZE)),
						new Vector2(spawnVelSeed + 4, 0));
				break;
			case 1:
				// Top side
				db = new DeathBox(
						SIZE,
						new Vector2(spawnPosSeed*(width-SIZE), height),
						new Vector2(0, spawnVelSeed - 5));
				break;
			default:
				// Right side
				db = new DeathBox(
						SIZE,
						new Vector2(width, spawnPosSeed*(height-SIZE)),
						new Vector2(spawnVelSeed - 5, 0));
		}
		activeBoxes.add(db);
	}
}
