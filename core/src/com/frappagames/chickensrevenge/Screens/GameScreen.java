package com.frappagames.chickensrevenge.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.frappagames.chickensrevenge.ChickensRevenge;
import com.frappagames.chickensrevenge.Tools.Assets;
import com.frappagames.chickensrevenge.Tools.Level;
import com.frappagames.chickensrevenge.Tools.abstractGameScreen;

import java.awt.Point;

/**
 * Game class
 *
 * Created by Jérémy MOREAU on 14/08/15.
 */
class GameScreen extends abstractGameScreen {
    private static final int MOVEMENT_DELAY = 1;

    private enum Direction { LEFT, RIGHT, UP, DOWN }
    private enum GameState { PLAYING, GAME_WIN, GAME_LOST }

    private Point foxLocation;
    private Point chickenLocation;
    private float elapsedTime = 0;
    private GameState gameState = GameState.PLAYING;

    // Map initialisation (Level 1)
    private int level;
    private Level map;

    GameScreen(final ChickensRevenge gameApp) {
        super(gameApp);

        initialize(1);

        // Scores ☼

        // Bouton "Menu" ≡
//        btnMenu.addListener(new ChangeListener() {
//            public void changed(ChangeEvent event, Actor actor) {
//                Assets.playSound(Assets.clickSound);
//                game.setScreen(new MenuScreen(gameApp));
//            }
//        });

        // Play Music ♫
//        if (Settings.soundEnabled) Assets.music.play();
    }

    private void initialize(int level) {
        map = new Level(level);

        // Star positions
        foxLocation = new Point(1, 1);
        chickenLocation = new Point(11, 11);
    }

    @Override
    public void update(float delta) {
        // Exit to game menu on ESCAPE
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Assets.playSound(Assets.clickSound);
            game.setScreen(new MenuScreen(game));
        }

        // Boucle de jeu (Jeu en cours)
        if (gameState == GameState.PLAYING)
        {
            // Calcul du temps écouler depuis le dernier déplacement du renard
            elapsedTime += delta;

            // Si le renard n'a pas bougé depuis un temps supérieur au délais voullu (1s), on le fait bouger
            if (elapsedTime >= MOVEMENT_DELAY) {
                elapsedTime = 0;
//                moveFox();
            }

            // Gestion du clavier
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && chickenLocation.y > 0) {
                map.setDirection(Level.Direction.DOWN);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && chickenLocation.y < ChickensRevenge.MAP_SIZE - 1) {
                map.setDirection(Level.Direction.UP);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && chickenLocation.x > 0) {
                map.setDirection(Level.Direction.LEFT);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && chickenLocation.x < ChickensRevenge.MAP_SIZE - 1) {
                map.setDirection(Level.Direction.RIGHT);
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                initialize(level);
                gameState = GameState.PLAYING;
            }
        }
    }
//    // Déplacement du renard
//    private void moveFox() {
//        Point newLocation = getFoxPath(foxLocation, chickenLocation);
//
//        // Le renard mange la poule ?
//        if (newLocation == chickenLocation) {
//            gameState = GameState.GAME_LOST;
//
//        // Le renard est bloquer ?
//        } else if (newLocation == foxLocation) {
//            gameState = GameState.GAME_WIN;
//
//        // Si le renard bouge
//        } else {
//            foxLocation = newLocation;
//        }
//    }

//    // Calcul du chemin à prendre (cacul basique regardant uniquement les cases autour du renard)
//    private Point getFoxPath(Point foxPosition, Point chickenPosition)
//    {
//        int bestValue = 1000;
//        int currentValue;
//        Point bestPosition = foxPosition;
//
//        // N
//        if (foxPosition.y > 0 && map[foxPosition.y - 1][foxPosition.y] == 1)
//        {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x, foxPosition.y - 1));
//            if (currentValue < bestValue)
//            {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x, foxPosition.y - 1);
//            }
//        }
//
//        // E
//        if (foxPosition.x < ChickensRevenge.MAP_SIZE && map[foxPosition.y][foxPosition.x + 1] == 1)
//        {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x + 1, foxPosition.y));
//            if (currentValue < bestValue)
//            {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x + 1, foxPosition.y);
//            }
//        }
//
//        // S
//        if (foxPosition.y < ChickensRevenge.MAP_SIZE && map[foxPosition.y + 1][foxPosition.x] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x, foxPosition.y + 1));
//            if (currentValue < bestValue) {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x, foxPosition.y + 1);
//            }
//        }
//
//        // O
//        if (foxPosition.x > 0 && map[foxPosition.y][foxPosition.x - 1] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x - 1, foxPosition.y));
//            if (currentValue < bestValue) {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x - 1, foxPosition.y);
//            }
//        }
//
//        // NE
//        if (foxPosition.x < ChickensRevenge.MAP_SIZE && foxPosition.y > 0 && map[foxPosition.y - 1][foxPosition.x + 1] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x + 1, foxPosition.y - 1));
//            if (currentValue < bestValue) {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x + 1, foxPosition.y - 1);
//            }
//        }
//
//        // SE
//        if (foxPosition.x < ChickensRevenge.MAP_SIZE && foxPosition.y < ChickensRevenge.MAP_SIZE && map[foxPosition.y + 1][foxPosition.x + 1] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x + 1, foxPosition.y + 1));
//            if (currentValue < bestValue) {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x + 1, foxPosition.y + 1);
//            }
//        }
//
//        // SO
//        if (foxPosition.x > 0 && foxPosition.y < ChickensRevenge.MAP_SIZE && map[foxPosition.y + 1][foxPosition.x - 1] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x - 1, foxPosition.y + 1));
//            if (currentValue < bestValue) {
//                bestValue = currentValue;
//                bestPosition = new Point(foxPosition.x - 1, foxPosition.y + 1);
//            }
//        }
//
//        // NO
//        if (foxPosition.x > 0 && foxPosition.y > 0 && map[foxPosition.y - 1][foxPosition.x - 1] == 1) {
//            currentValue = (int) Math.round(chickenPosition.distance(foxPosition.x - 1, foxPosition.y - 1));
//            if (currentValue < bestValue) {
//                bestPosition = new Point(foxPosition.x - 1, foxPosition.y - 1);
//            }
//        }
//
//
//        return bestPosition;
//    }

    // Déplacement de la poule (si c'est possible) selon la touche pressée
    private void move(Direction direction)
    {
        if (canMove( chickenLocation.x,  chickenLocation.y, direction)) {
            int cellValue;

            switch (direction)
            {
                case LEFT:
                    cellValue = map[chickenLocation.y][chickenLocation.x - 1];
                    if (cellValue == 2) pushBox((chickenLocation.x - 1), chickenLocation.y, Direction.LEFT);
                    chickenLocation.x -= 1;
                    break;
                case RIGHT:
                    cellValue = map[chickenLocation.y][chickenLocation.x + 1];
                    if (cellValue == 2) pushBox((chickenLocation.x + 1), chickenLocation.y, Direction.RIGHT);
                    chickenLocation.x += 1;
                    break;
                case DOWN:
                    cellValue = map[chickenLocation.y - 1][chickenLocation.x];
                    if (cellValue == 2) pushBox(chickenLocation.x, (chickenLocation.y - 1), Direction.DOWN);
                    chickenLocation.y -= 1;
                    break;
                case UP:
                    cellValue = map[chickenLocation.y + 1][chickenLocation.x];
                    if (cellValue == 2) pushBox(chickenLocation.x, (chickenLocation.y + 1), Direction.UP);
                    chickenLocation.y += 1;
                    break;
            }
        }
    }

    // Déplacement des caisses
    private void pushBox(int x, int y, Direction direction)
    {
        switch (direction) {
            case LEFT:
                if (map[y][x - 1] == 2) pushBox(x - 1, y, Direction.LEFT);
                map[y][x - 1] = 2;
                map[y][x] = 1;
                break;
            case RIGHT:
                if (map[y][x + 1] == 2) pushBox(x + 1, y, Direction.RIGHT);
                map[y][x + 1] = 2;
                map[y][x] = 1;
                break;
            case DOWN:
                if (map[y - 1][x] == 2) pushBox(x, y - 1, Direction.DOWN);
                map[y - 1][x] = 2;
                map[y][x] = 1;
                break;
            case UP:
                if (map[y + 1][x] == 2) pushBox(x, y + 1, Direction.UP);
                map[y + 1][x] = 2;
                map[y][x] = 1;
                break;
        }
    }

    // Vérification de la possibilité de déplacer la poule dans la direction souhaitée
    private boolean canMove(int x, int y, Direction direction)
    {
        int cellValue;

        switch (direction) {
            case LEFT:
                cellValue = map[y][x - 1];
                if (cellValue == 0 || (foxLocation.x == (x - 1) && foxLocation.y == y)) return false;
                else if (cellValue == 1) return true;
                else if (cellValue == 2) return canMove(x - 1, y, Direction.LEFT);
                break;
            case RIGHT:
                cellValue = map[y][x + 1];
                if (cellValue == 0 || (foxLocation.x == (x + 1) && foxLocation.y == y)) return false;
                else if (cellValue == 1) return true;
                else if (cellValue == 2) return canMove(x + 1, y, Direction.RIGHT);
                break;
            case DOWN:
                cellValue = map[y - 1][x];
                if (cellValue == 0 || (foxLocation.x == x && foxLocation.y == (y - 1))) return false;
                else if (cellValue == 1) return true;
                else if (cellValue == 2) return canMove(x, y - 1, Direction.DOWN);
                break;
            case UP:
                cellValue = map[y + 1][x];
                if (cellValue == 0 || (foxLocation.x == x && foxLocation.y == (y + 1))) return false;
                else if (cellValue == 1) return true;
                else if (cellValue == 2) return canMove(x, y + 1, Direction.UP);
                break;
        }

        return false;
    }

    @Override
    public void draw(float delta) {
        game.batch.begin();

        map.draw(game.batch);

//        for (int j = 0; j < ChickensRevenge.MAP_SIZE; j++) {
//            for (int i = 0; i < ChickensRevenge.MAP_SIZE; i++) {
//                if (map[i][j] == 1) {
//                    game.batch.draw(Assets.grass.getRegion(), j * TILE_SIZE, i * TILE_SIZE);
//                } else if (map[i][j] == 2) {
//                    game.batch.draw(Assets.box.getRegion(), j * TILE_SIZE, i * TILE_SIZE);
//                } else {
//                    game.batch.draw(Assets.wall.getRegion(), j * TILE_SIZE, i * TILE_SIZE);
//                }
//            }
//        }

        if (gameState == GameState.GAME_WIN) {
            game.batch.draw(Assets.chicken.getRegion(), chickenLocation.x * ChickensRevenge.TILE_SIZE, chickenLocation.y * ChickensRevenge.TILE_SIZE);
            game.batch.draw(Assets.egg.getRegion(), foxLocation.x * ChickensRevenge.TILE_SIZE, foxLocation.y * ChickensRevenge.TILE_SIZE);

            Assets.genericFont.draw(game.batch, "YOU WIN !!!", 80, 100);
            Assets.genericFont.draw(game.batch, "PRESS \"ENTER\" TO RESTART", 50, 150);
            Assets.genericFont.draw(game.batch, "PRESS \"ECHAP\" TO QUIT", 70, 170);
        } else if (gameState == GameState.GAME_LOST) {
            game.batch.draw(Assets.cross.getRegion(), chickenLocation.x * ChickensRevenge.TILE_SIZE, chickenLocation.y * ChickensRevenge.TILE_SIZE);
            game.batch.draw(Assets.fox.getRegion(), foxLocation.x * ChickensRevenge.TILE_SIZE, foxLocation.y * ChickensRevenge.TILE_SIZE);

            Assets.genericFont.draw(game.batch, "GAME OVER !", 80, 100);
            Assets.genericFont.draw(game.batch, "PRESS \"ENTER\" TO RESTART", 50, 150);
            Assets.genericFont.draw(game.batch, "PRESS \"ECHAP\" TO QUIT", 70, 170);
        } else {
            game.batch.draw(Assets.chicken.getRegion(), chickenLocation.x * ChickensRevenge.TILE_SIZE, chickenLocation.y * ChickensRevenge.TILE_SIZE);
            game.batch.draw(Assets.fox.getRegion(), foxLocation.x * ChickensRevenge.TILE_SIZE, foxLocation.y * ChickensRevenge.TILE_SIZE);
        }

        game.batch.end();

        super.draw(delta);
    }

    @Override
    public void hide() {
        // Pause Music ♫
//        if (Settings.soundEnabled) Assets.music.pause();

        super.hide();
    }

    @Override
    public void pause() {
        // Pause Music ♫
//        if (Settings.soundEnabled) Assets.music.pause();

        super.pause();
    }

    @Override
    public void resume() {
        super.resume();

        // Resume Music ♫
//        if (Settings.soundEnabled) Assets.music.play();
    }

    @Override
    public void dispose() {
        // Stop Music ♫
//        if (Settings.soundEnabled) Assets.music.stop();

        super.dispose();
    }
}
