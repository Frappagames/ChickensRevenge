package com.frappagames.chickensrevenge.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frappagames.chickensrevenge.ChickensRevenge;

import java.awt.Point;

/**
 * Created by gfp on 18/01/18.
 */

public abstract class AbstractCharacter {
    Point   position;
    boolean alive;
    TextureRegion alivePicture, diedPicture, picture;

    AbstractCharacter(Point position) {
        this.setPosition(position);
        this.setAlive(true);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void draw(Batch batch) {
        batch.draw(
            alive ? alivePicture : diedPicture,
            position.x * ChickensRevenge.TILE_SIZE,
            (ChickensRevenge.MAP_SIZE - 1 - position.y) * ChickensRevenge.TILE_SIZE
        );
    }
}
