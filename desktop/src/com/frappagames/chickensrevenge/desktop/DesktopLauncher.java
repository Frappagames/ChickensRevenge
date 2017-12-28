package com.frappagames.chickensrevenge.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.frappagames.chickensrevenge.ChickensRevenge;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Chicken's Revenge";
		config.width = ChickensRevenge.WIDTH;
		config.height = ChickensRevenge.HEIGHT;
		new LwjglApplication(new ChickensRevenge(), config);
	}
}
