package com.github.itsmichaelwang.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SquareMan {
	public enum State {
		BOOST_LEFT, BOOST_UP, BOOST_RIGHT,
		MOVE_LEFT, FALLING, MOVE_RIGHT,
		IDLE, DEAD
	}
	public enum VerticalState {
		IDLE, BOOSTING, FALLING
	}
	public enum HorizontalState {
		IDLE, LEFT, RIGHT
	}
	
	// The dimensions of SquareMan
	static final float SIZE = 0.5f;
	Rectangle bounds = new Rectangle();
	public Rectangle getBounds() { return bounds; }
	
	// SquareMan's location
	Vector2 position = new Vector2();
	public Vector2 getPosition() { return position; }
	
	Vector2 velocity = new Vector2();
	public Vector2 getVelocity() { return velocity; }
	public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
	
	Vector2 acceleration = new Vector2();
	public Vector2 getAcceleration() { return acceleration; }
	public void setAcceleration(Vector2 acceleration) { this.acceleration = acceleration; }
	
	private State state = State.IDLE;
	public void setState(State state) { this.state = state; }
	
	public SquareMan(Vector2 initPosition) {
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.position = initPosition;
	}
	
	// Update SquareMan's location
	public void update(float delta) {
		position.add(velocity.cpy().mul(delta));
	}
}
