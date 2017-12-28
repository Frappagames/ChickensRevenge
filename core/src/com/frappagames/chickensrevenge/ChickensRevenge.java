package com.frappagames.chickensrevenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frappagames.chickensrevenge.Screens.SplashScreen;
import com.frappagames.chickensrevenge.Tools.Assets;
import com.frappagames.chickensrevenge.Tools.Settings;

public class ChickensRevenge extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 368;
	public static final int HEIGHT = 368;
    public static final int TILE_SIZE = 16;
    public static final int MAP_SIZE = 23;
    public static final int DRAW_OFFSET = 0;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();

		Assets.load();
		Settings.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Assets.dispose();
	}
}
