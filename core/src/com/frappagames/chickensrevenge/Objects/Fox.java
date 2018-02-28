package com.frappagames.chickensrevenge.Objects;

import com.frappagames.chickensrevenge.ChickensRevenge;
import com.frappagames.chickensrevenge.Tools.Assets;
import com.frappagames.chickensrevenge.Tools.Level;

import java.awt.Point;

public class Fox extends AbstractCharacter {
    public enum GameState { LOST, WIN, MOVE }

    public Fox(Point position) {
        super(position);
        this.alivePicture = Assets.fox.getRegion();
        this.diedPicture = Assets.egg.getRegion();
    }

    // Déplacement du renard
    public GameState moveFox(Point chickenLocation, Level map) {
        Point newLocation = getFoxPath(chickenLocation, map);

        // Le renard mange la poule ?
        if (newLocation == chickenLocation) {
            return GameState.LOST;

        // Le renard est bloquer ?
        } else if (newLocation == getPosition()) {
            return GameState.WIN;

        // Si le renard bouge
        } else {
            setPosition(newLocation);

            return GameState.MOVE;
        }
    }

    // Calcul du chemin à prendre (cacul basique regardant uniquement les cases autour du renard)
    private Point getFoxPath(Point chickenPosition, Level map)
    {
        int bestValue = 1000;
        int currentValue;
        Point bestPosition = position;

        // N
        if (position.y > 0 && map.getValueAt(position.x, position.y - 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x, position.y - 1));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x, position.y - 1);
            }
        }

        // E
        if (position.x < ChickensRevenge.MAP_SIZE && map.getValueAt(position.x + 1, position.y) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x + 1, position.y));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x + 1, position.y);
            }
        }

        // S
        if (position.y < ChickensRevenge.MAP_SIZE && map.getValueAt(position.x, position.y + 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x, position.y + 1));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x, position.y + 1);
            }
        }

        // O
        if (position.x > 0 && map.getValueAt(position.x - 1, position.y) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x - 1, position.y));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x - 1, position.y);
            }
        }

        // NE
        if (position.x < ChickensRevenge.MAP_SIZE && position.y > 0 && map.getValueAt(position.x + 1, position.y - 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x + 1, position.y - 1));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x + 1, position.y - 1);
            }
        }

        // SE
        if (position.x < ChickensRevenge.MAP_SIZE && position.y < ChickensRevenge.MAP_SIZE && map.getValueAt(position.x + 1, position.y + 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x + 1, position.y + 1));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x + 1, position.y + 1);
            }
        }

        // SO
        if (position.x > 0 && position.y < ChickensRevenge.MAP_SIZE && map.getValueAt(position.x - 1, position.y + 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x - 1, position.y + 1));

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(position.x - 1, position.y + 1);
            }
        }

        // NO
        if (position.x > 0 && position.y > 0 && map.getValueAt(position.x - 1, position.y - 1) == Level.EMPTY_VALUE) {
            currentValue = (int) Math.round(chickenPosition.distance(position.x - 1, position.y - 1));

            if (currentValue < bestValue) {
                bestPosition = new Point(position.x - 1, position.y - 1);
            }
        }

        return bestPosition;
    }
}
