package com.github.itsmichaelwang;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.github.itsmichaelwang.characters.*;
import com.github.itsmichaelwang.characters.SquareMan.State;

public class GameRenderer {
	private final float CAMERA_WIDTH = 20f;
	private final float CAMERA_HEIGHT = 15f;
	
	private World world;
	private OrthographicCamera cam;
	public OrthographicCamera getCamera() { return cam; } 
	
	/** Draw rectangles **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	// Create the world when the rendering starts
	public GameRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
	}
	
	public void render() {
		debugRenderer.setProjectionMatrix(cam.combined);
		drawSquareMan();
		drawDeathBoxes();
	}
	
	private void drawSquareMan() {
		SquareMan squareMan = world.getSquareMan();
		if (squareMan.getState() == State.DEAD) {
			// draw dead SquareMan
		} else {
			debugRenderer.begin(ShapeType.Rectangle);
			// draw SquareMan
			Rectangle rect = squareMan.getBounds();
			float x1 = squareMan.getPosition().x;
			float y1 = squareMan.getPosition().y;
			debugRenderer.setColor(new Color(0, 0, 1, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			
			debugRenderer.end();
		}
	}
	
	private void drawDeathBoxes() {
		debugRenderer.begin(ShapeType.Rectangle);
		
		// draw DeathBoxes
		debugRenderer.setColor(1, 0, 0, 1);
		Array<DeathBox> activeDeathBoxes = world.getActiveBoxes();
		for (DeathBox db : activeDeathBoxes) {
			float x2 = db.getPosition().x;
			float y2 = db.getPosition().y;
			debugRenderer.rect(x2, y2, db.getBounds().x, db.getBounds().y);
		}
		
		debugRenderer.end();
	}
}
