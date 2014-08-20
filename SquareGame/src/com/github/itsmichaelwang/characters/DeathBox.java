package com.github.itsmichaelwang.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DeathBox {
	private enum State {
		ALIVE, DEAD
	}
	State state;
	public State getState() { return state; }
	public void setState(State state) { this.state = state; }
	
	// The dimensions of DeathBox
	private Rectangle bounds = new Rectangle();
	public Rectangle getBounds() { return bounds; }
	
	// The box moves at constant speed
	private Vector2 position = new Vector2();
	public Vector2 getPosition() { return position; }
	private Vector2 velocity = new Vector2();
	public Vector2 getVelocity() { return velocity; }
	
	public DeathBox(float size) {
		bounds.x = size;
		bounds.y = size;
	}
	
	public DeathBox(float size, Vector2 position, Vector2 velocity) {
		this(size);
		this.position = position;
		this.velocity = velocity;
	}
	
	// Update box position
	public void update(float delta) {
		position = position.add(velocity.cpy().mul(delta));
	}
}
