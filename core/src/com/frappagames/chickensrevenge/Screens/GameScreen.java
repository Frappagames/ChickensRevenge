package com.frappagames.chickensrevenge.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.frappagames.chickensrevenge.ChickensRevenge;
import com.frappagames.chickensrevenge.Objects.Chicken;
import com.frappagames.chickensrevenge.Objects.Fox;
import com.frappagames.chickensrevenge.Tools.Assets;
import com.frappagames.chickensrevenge.Tools.Level;
import com.frappagames.chickensrevenge.Tools.abstractGameScreen;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Game class
 *
 * Created by Jérémy MOREAU on 14/08/15.
 */
public class GameScreen extends abstractGameScreen {
    private static final int MOVEMENT_DELAY = 1;

    private enum GameState { PLAYING, GAME_WIN, GAME_LOST }

    private float elapsedTime = 0;
    private GameState gameState = GameState.PLAYING;

    // Map initialisation (Level 1)
    private int level = 3;
    private Level map;

    private Chicken        chicken;
    private ArrayList<Fox> foxes;

    public GameScreen(final ChickensRevenge gameApp) {
        super(gameApp);

        initialize(level);

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
        // Initialize map
        map = new Level(level);

        // Add chicken
        chicken = new Chicken(map.getChickenStart());

        // Add foxes
        foxes = new ArrayList<Fox>();
        for (Point position : map.getFoxesStart()) {
            foxes.add(new Fox(position));
        }
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
                for (Fox fox : foxes) {
                    if (fox.moveFox(chicken.getFoxPosition(), map) != Fox.GameState.MOVE) {

                    }
                }
            }

            // Gestion du clavier
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && chicken.getFoxPosition().y > 0) {
                if (canMove(chicken.getFoxPosition().x, chicken.getFoxPosition().y, ChickensRevenge.Direction.DOWN)) {
                    if (map.getValueAt(chicken.getFoxPosition().x, chicken.getFoxPosition().y + 1) == Level.BOX_VALUE) {
                        map.pushBox(chicken.getFoxPosition().x, chicken.getFoxPosition().y + 1, Level.Direction.DOWN);
                    }
                    chicken.move(ChickensRevenge.Direction.DOWN);
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && chicken.getFoxPosition().y < ChickensRevenge.MAP_SIZE - 1) {
                if (canMove(chicken.getFoxPosition().x, chicken.getFoxPosition().y, ChickensRevenge.Direction.UP)) {
                    if (map.getValueAt(chicken.getFoxPosition().x, chicken.getFoxPosition().y - 1) == Level.BOX_VALUE) {
                        map.pushBox(chicken.getFoxPosition().x, chicken.getFoxPosition().y - 1, Level.Direction.UP);
                    }
                    chicken.move(ChickensRevenge.Direction.UP);
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && chicken.getFoxPosition().x > 0) {
                if (canMove(chicken.getFoxPosition().x, chicken.getFoxPosition().y, ChickensRevenge.Direction.LEFT)) {
                    if (map.getValueAt(chicken.getFoxPosition().x - 1, chicken.getFoxPosition().y) == Level.BOX_VALUE) {
                        map.pushBox(chicken.getFoxPosition().x - 1, chicken.getFoxPosition().y, Level.Direction.LEFT);
                    }
                    chicken.move(ChickensRevenge.Direction.LEFT);
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && chicken.getFoxPosition().x < ChickensRevenge.MAP_SIZE - 1) {
                if (canMove(chicken.getFoxPosition().x, chicken.getFoxPosition().y, ChickensRevenge.Direction.RIGHT)) {
                    if (map.getValueAt(chicken.getFoxPosition().x + 1, chicken.getFoxPosition().y) == Level.BOX_VALUE) {
                        map.pushBox(chicken.getFoxPosition().x + 1, chicken.getFoxPosition().y, Level.Direction.RIGHT);
                    }
                    chicken.move(ChickensRevenge.Direction.RIGHT);
                }
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                initialize(level);
                gameState = GameState.PLAYING;
            }
        }
    }

    // Vérification de la possibilité de déplacer la poule dans la direction souhaitée
    private boolean canMove(int x, int y, ChickensRevenge.Direction direction) {
        char cellValue;

        switch (direction) {
            case LEFT:
                cellValue = map.getValueAt(x - 1, y);

                return !(cellValue == Level.WALL_VALUE || isThereAFoxHere(x - 1, y))
                        && (cellValue != Level.BOX_VALUE
                            || canMove(x - 1, y, ChickensRevenge.Direction.LEFT));
            case RIGHT:
                cellValue = map.getValueAt(x + 1, y);

                return !(cellValue == Level.WALL_VALUE || isThereAFoxHere(x + 1, y))
                        && (cellValue != Level.BOX_VALUE
                            || canMove(x + 1, y, ChickensRevenge.Direction.RIGHT));
            case DOWN:
                cellValue = map.getValueAt(x, y + 1);

                return !(cellValue == Level.WALL_VALUE || isThereAFoxHere(x, y + 1))
                        && (cellValue != Level.BOX_VALUE
                            || canMove(x, y + 1, ChickensRevenge.Direction.DOWN));
            case UP:
                cellValue = map.getValueAt(x, y - 1);

                return !(cellValue == Level.WALL_VALUE || isThereAFoxHere(x, y - 1))
                        && (cellValue != Level.BOX_VALUE
                            || canMove(x, y - 1, ChickensRevenge.Direction.UP));
        }

        return false;
    }

    private boolean isThereAFoxHere(int x, int y) {
        for (Fox fox : foxes) {
            if (fox.getFoxPosition().x == x && fox.getFoxPosition().y == y) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void draw(float delta) {
        game.batch.begin();

        // Draw map tiles
        map.draw(game.batch);

        // Draw chicken
        chicken.draw(game.batch);

        // Draw Foxes
        for (Fox fox : foxes) {
            fox.draw(game.batch);
        }

        if (gameState == GameState.GAME_WIN) {
            Assets.genericFont.draw(game.batch, "YOU WIN !!!", 80, 100);
            Assets.genericFont.draw(game.batch, "PRESS \"ENTER\" TO RESTART", 50, 150);
            Assets.genericFont.draw(game.batch, "PRESS \"ECHAP\" TO QUIT", 70, 170);
        } else if (gameState == GameState.GAME_LOST) {
            Assets.genericFont.draw(game.batch, "GAME OVER !", 80, 100);
            Assets.genericFont.draw(game.batch, "PRESS \"ENTER\" TO RESTART", 50, 150);
            Assets.genericFont.draw(game.batch, "PRESS \"ECHAP\" TO QUIT", 70, 170);
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
