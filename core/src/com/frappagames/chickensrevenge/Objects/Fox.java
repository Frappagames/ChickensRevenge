package com.frappagames.chickensrevenge.Objects;

import com.frappagames.chickensrevenge.ChickensRevenge;
import com.frappagames.chickensrevenge.Tools.Assets;
import com.frappagames.chickensrevenge.Tools.Level;

import java.awt.Point;
import java.util.ArrayList;

public class Fox extends AbstractCharacter {
    public enum GameState { LOST, WIN, MOVE }

    public Fox(Point position) {
        super(position);
        this.alivePicture = Assets.fox.getRegion();
        this.diedPicture = Assets.egg.getRegion();
    }

    // Déplacement du renard
    public GameState moveFox(Point chickenLocation, Level map, ArrayList<Fox> foxes) {
        Point newLocation = getFoxPath(chickenLocation, map, foxes);

        // Le renard mange la poule ?
        if (newLocation.equals(chickenLocation)) {
            return GameState.LOST;

        // Le renard est bloquer ?
        } else if (newLocation.equals(getFoxPosition())) {
            this.setAlive(false);

            return GameState.WIN;

        // Si le renard bouge
        } else {
            setFoxPosition(newLocation);
            this.setAlive(true);

            return GameState.MOVE;
        }
    }

    private boolean hasFoxThere(int x, int y, ArrayList<Fox> foxes) {
        for (Fox fox : foxes) {
            if (fox.getFoxPosition().getX() == x && fox.getFoxPosition().getY() == y) {
                return true;
            }
        }

        return false;
    }

    // Calcul du chemin à prendre (cacul basique regardant uniquement les cases autour du renard)
    private Point getFoxPath(Point chickenPosition, Level map, ArrayList<Fox> foxes)
    {
        double bestValue = 1000;
        double currentValue;
        Point bestPosition = foxPosition;

        // N
        if (foxPosition.y > 0 && map.getValueAt(foxPosition.x, foxPosition.y - 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x, foxPosition.y - 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x, foxPosition.y - 1);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x, foxPosition.y - 1);
            }
        }

        // E
        if (foxPosition.x < ChickensRevenge.MAP_SIZE && map.getValueAt(foxPosition.x + 1, foxPosition.y) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x + 1, foxPosition.y, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x + 1, foxPosition.y);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x + 1, foxPosition.y);
            }
        }

        // S
        if (foxPosition.y < ChickensRevenge.MAP_SIZE && map.getValueAt(foxPosition.x, foxPosition.y + 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x, foxPosition.y + 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x, foxPosition.y + 1);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x, foxPosition.y + 1);
            }
        }

        // O
        if (foxPosition.x > 0 && map.getValueAt(foxPosition.x - 1, foxPosition.y) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x - 1, foxPosition.y, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x - 1, foxPosition.y);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x - 1, foxPosition.y);
            }
        }

        // NE
        if (foxPosition.x < ChickensRevenge.MAP_SIZE && foxPosition.y > 0 && map.getValueAt(foxPosition.x + 1, foxPosition.y - 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x + 1, foxPosition.y - 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x + 1, foxPosition.y - 1);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x + 1, foxPosition.y - 1);
            }
        }

        // SE
        if (foxPosition.x < ChickensRevenge.MAP_SIZE && foxPosition.y < ChickensRevenge.MAP_SIZE && map.getValueAt(foxPosition.x + 1, foxPosition.y + 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x + 1, foxPosition.y + 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x + 1, foxPosition.y + 1);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x + 1, foxPosition.y + 1);
            }
        }

        // SO
        if (foxPosition.x > 0 && foxPosition.y < ChickensRevenge.MAP_SIZE && map.getValueAt(foxPosition.x - 1, foxPosition.y + 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x - 1, foxPosition.y + 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x - 1, foxPosition.y + 1);

            if (currentValue < bestValue) {
                bestValue = currentValue;
                bestPosition = new Point(foxPosition.x - 1, foxPosition.y + 1);
            }
        }

        // NO
        if (foxPosition.x > 0 && foxPosition.y > 0 && map.getValueAt(foxPosition.x - 1, foxPosition.y - 1) == Level.EMPTY_VALUE && !hasFoxThere(foxPosition.x - 1, foxPosition.y - 1, foxes)) {
            currentValue = chickenPosition.distance(foxPosition.x - 1, foxPosition.y - 1);

            if (currentValue < bestValue) {
                bestPosition = new Point(foxPosition.x - 1, foxPosition.y - 1);
            }
        }

        return bestPosition;
    }
}
