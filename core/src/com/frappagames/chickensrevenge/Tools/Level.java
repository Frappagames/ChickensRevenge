package com.frappagames.chickensrevenge.Tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.frappagames.chickensrevenge.ChickensRevenge;

import java.awt.Point;
import java.util.ArrayList;

public class Level {
    private char[] map;

    public static final char WALL_VALUE = '1';
    public static final char BOX_VALUE = '2';
    public static final char EMPTY_VALUE = '0';

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
            int y = (ChickensRevenge.MAP_SIZE - 1 - ((int) Math.ceil(i / ChickensRevenge.MAP_SIZE))) * ChickensRevenge.TILE_SIZE;

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

    public Point getChickenStart() {
        int i = 0;

        for (char c : map) {
            if (c == 'C') {
                return new Point(i % ChickensRevenge.MAP_SIZE, (int) Math.ceil(i / ChickensRevenge.MAP_SIZE));
            }

            i++;
        }

        return null;
    }

    public ArrayList<Point> getFoxesStart() {
        int i = 0;
        ArrayList<Point> foxesStarts = new ArrayList<Point>();

        for (char c : map) {
            if (c == 'F') {
                foxesStarts.add(new Point(i % ChickensRevenge.MAP_SIZE, (int) Math.ceil(i / ChickensRevenge.MAP_SIZE)));
            }

            i++;
        }

        return foxesStarts;
    }

    public char getValueAt(int x, int y) {
        return map[y * ChickensRevenge.MAP_SIZE + x];
    }

    private void setValueAt(int x, int y, char value) {
        map[y * ChickensRevenge.MAP_SIZE + x] = value;
    }

    public void pushBox(int x, int y, Direction direction) {
        switch (direction) {
            case LEFT:
                if (getValueAt(x - 1, y) == BOX_VALUE) pushBox(x - 1, y, Direction.LEFT);
                setValueAt(x - 1, y, BOX_VALUE);
                setValueAt(x, y, EMPTY_VALUE);
                break;
            case RIGHT:
                if (getValueAt(x + 1, y) == BOX_VALUE) pushBox(x + 1, y, Direction.RIGHT);
                setValueAt(x + 1, y, BOX_VALUE);
                setValueAt(x, y, EMPTY_VALUE);
                break;
            case DOWN:
                if (getValueAt(x, y + 1) == BOX_VALUE) pushBox(x, y + 1, Direction.DOWN);
                setValueAt(x, y + 1, BOX_VALUE);
                setValueAt(x, y, EMPTY_VALUE);
                break;
            case UP:
                if (getValueAt(x, y - 1) == BOX_VALUE) pushBox(x, y - 1, Direction.UP);
                setValueAt(x, y - 1, BOX_VALUE);
                setValueAt(x, y, EMPTY_VALUE);
                break;
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
