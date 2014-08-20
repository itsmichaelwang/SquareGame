package com.github.itsmichaelwang;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class SquareGameDesktop {
	public static void main(String[] args) {
		new LwjglApplication(new SquareGame(), "Square Game", 480, 320, true);
	}
}
