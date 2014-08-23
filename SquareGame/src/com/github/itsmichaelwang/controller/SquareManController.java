package com.github.itsmichaelwang.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.github.itsmichaelwang.characters.DeathBox;
import com.github.itsmichaelwang.characters.SquareMan;
import com.github.itsmichaelwang.characters.SquareMan.State;
import com.github.itsmichaelwang.characters.World;

public class SquareManController {
	private SquareMan squareMan;
	private float viewportWidth;
	private float viewportHeight;
	
	public SquareManController(World world, Camera cam) {
		this.squareMan = world.getSquareMan();
		this.viewportWidth = cam.viewportWidth;
		this.viewportHeight = cam.viewportHeight;
	}
	
	private static float DAMP = 0.9f;
	private static final float RUN_ACCEL = 20f;
	private static final float JETPACK_ACCEL = 20f;
	public static final float GRAVITY = -10f;
	private static final float MAX_RUN_SPEED = 20f;
	private static final float TERMINAL_VELOCITY = 20f;
	
	/** This runs constantly as part of GameScreen.render(), its purpose as part of the loop
	 * 	is to check for keys being pressed, and to adjust the characters' variables.
	 */
	public void update(float delta) {
		// Modify squareMan's velocity/acceleration depending on what keys are pressed
		processInput();
		
		// Update his velocity based on acceleration
		squareMan.getAcceleration().mul(delta);
		squareMan.getVelocity().add(squareMan.getAcceleration());
		
		// Dampen velocity for friction/air resistance
		squareMan.getVelocity().x *= DAMP;
		
		if (squareMan.getVelocity().x > MAX_RUN_SPEED) {
			squareMan.getVelocity().x = MAX_RUN_SPEED;
		}
		if (squareMan.getVelocity().x < -MAX_RUN_SPEED) {
			squareMan.getVelocity().x = -MAX_RUN_SPEED;
		}
		if (squareMan.getVelocity().y > TERMINAL_VELOCITY) {
			squareMan.getVelocity().y = TERMINAL_VELOCITY;
		}
		if (squareMan.getVelocity().y < -TERMINAL_VELOCITY) {
			squareMan.getVelocity().y = -TERMINAL_VELOCITY;
		}
		
		// Update squareMan's position based on his velocity
		squareMan.update(delta);
		
		// Make sure squareMan doesn't fall off the screen
		checkBounds(viewportWidth, viewportHeight);
		
		
	}
	
	// Simulate the collision between SquareMan and a DeathBox on the squareMan side
	public void killSquareMan(DeathBox db) {
		squareMan.setState(State.DEAD);
		SquareManController.DAMP = 0.97f;	// Dead things probably fly farther O_o
		final float COLLISION_MAGNITUDE = db.getVelocity().len()*2;
		
		// Collision handling - create a unit vector from the DeathBox to SquareMan
		Vector2 squareManCenterofMass = new Vector2(
				squareMan.getPosition().x + squareMan.getBounds().width / 2,
				squareMan.getPosition().y + squareMan.getBounds().height / 2);
		Vector2 dbCenterofMass = new Vector2(
				db.getPosition().x + db.getBounds().width / 2,
				db.getPosition().y + db.getBounds().height / 2);
		
		Vector2 collisionPath = squareManCenterofMass.tmp().sub(dbCenterofMass);
		float magnitude = squareManCenterofMass.dst(dbCenterofMass);
		collisionPath = collisionPath.mul(1/magnitude);
		squareMan.setVelocity(collisionPath.mul(COLLISION_MAGNITUDE));
		squareMan.setAcceleration(new Vector2(0f, GRAVITY));
	}
	
	private void processInput() {
		if (keys.get(Keys.JUMP)) {
			squareMan.getAcceleration().y = JETPACK_ACCEL;
		} else {
			squareMan.getAcceleration().y = GRAVITY;
		}
		if (keys.get(Keys.LEFT)) {
			squareMan.getAcceleration().x = -RUN_ACCEL;
			if (squareMan.getAcceleration().y > 0) {
				squareMan.setState(State.BOOST_LEFT);
			} else {
				squareMan.setState(State.MOVE_LEFT);
			}
		} else if (keys.get(Keys.RIGHT)) {
			squareMan.getAcceleration().x = RUN_ACCEL;
			if (squareMan.getAcceleration().y > 0) {
				squareMan.setState(State.BOOST_RIGHT);
			} else {
				squareMan.setState(State.MOVE_RIGHT);
			}
		} else {
			squareMan.getAcceleration().x = 0;
			if (squareMan.getAcceleration().y > 0) {
				squareMan.setState(State.BOOST_UP);
			} else {
				if (squareMan.getPosition().y > 0 ||
						squareMan.getVelocity().x != 0) {
					squareMan.setState(State.FALLING);
				} else {
					squareMan.setState(State.IDLE);
				}
			}
			
		}
	}
	
	// Make sure the man does not go out of bounds
	private void checkBounds(float width, float height) {
		if (squareMan.getPosition().x < 0) {
			squareMan.getPosition().x = 0;
		} else if (squareMan.getPosition().x + squareMan.getBounds().width > width) {
			squareMan.getPosition().x = width - squareMan.getBounds().width;
		}
		
		if (squareMan.getPosition().y < 0) {
			squareMan.getPosition().y = 0;
		} else if (squareMan.getPosition().y + squareMan.getBounds().height > height) {
			squareMan.getPosition().y = height - squareMan.getBounds().height;
			squareMan.getVelocity().y = 0;
			squareMan.getAcceleration().y = 0;
		}
	}
	
	enum Keys {
		LEFT, RIGHT, JUMP
	}

	static Map<Keys, Boolean> keys = new HashMap<SquareManController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
	};
	
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}
	
	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}
	
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}
	
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	
	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}
}
