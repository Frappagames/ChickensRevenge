package com.frappagames.chickensrevenge.Tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.frappagames.chickensrevenge.ChickensRevenge;

public class Level {
    private char[] map;
    public enum Direction { LEFT, RIGHT, UP, DOWN }

    private Direction direction;

    public Level(int level) {
        this.map = Assets.maps[level - 1].toCharArray();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void draw(Batch batch) {
        int i = 0;
        for (char c : map) {
            int x = (i % ChickensRevenge.MAP_SIZE) * ChickensRevenge.TILE_SIZE + ChickensRevenge.DRAW_OFFSET;
            int y = ((int) Math.ceil(i / ChickensRevenge.MAP_SIZE)) * ChickensRevenge.TILE_SIZE;

            switch (c) {
                case '1' :
                    batch.draw(Assets.wall.getRegion(), x, y);
                    break;
                case '2' :
                    batch.draw(Assets.box.getRegion(), x, y);
                    break;
                default :
                    batch.draw(Assets.grass.getRegion(), x, y);
                    break;
            }

            i++;
        }
    }

    /**
     * Return the number of registered levels
     *
     * @return the number of registered levels
     */
    public static int getLevelCount() {
        return Assets.maps.length;
    }
}
