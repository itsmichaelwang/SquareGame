package com.github.itsmichaelwang;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.github.itsmichaelwang.characters.*;

public class GameRenderer {
	private static final float CAMERA_WIDTH = 20f;
	public static float getCameraWidth() { return CAMERA_WIDTH; }
	private static final float CAMERA_HEIGHT = 15f;
	public static float getCameraHeight() { return CAMERA_HEIGHT; }
	
	private World world;
	private OrthographicCamera cam;
	
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
		debugRenderer.begin(ShapeType.Rectangle);
		
		// draw SquareMan
		SquareMan squareMan = world.getSquareMan();
		Rectangle rect = squareMan.getBounds();
		float x1 = squareMan.getPosition().x;
		float y1 = squareMan.getPosition().y;
		debugRenderer.setColor(new Color(0, 0, 1, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		
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
