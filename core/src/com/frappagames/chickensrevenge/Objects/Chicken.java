package com.frappagames.chickensrevenge.Objects;

import com.frappagames.chickensrevenge.ChickensRevenge;
import com.frappagames.chickensrevenge.Tools.Assets;

import java.awt.Point;

public class Chicken extends AbstractCharacter {
    public Chicken(Point position) {
        super(position);
        this.alivePicture = Assets.chicken.getRegion();
        this.diedPicture = Assets.cross.getRegion();
    }

    public void move(ChickensRevenge.Direction direction) {
        switch (direction) {
            case LEFT:
                this.position.x -= 1;
                break;
            case RIGHT:
                this.position.x += 1;
                break;
            case UP:
                this.position.y -= 1;
                break;
            case DOWN:
                this.position.y += 1;
                break;
        }
    }
}
