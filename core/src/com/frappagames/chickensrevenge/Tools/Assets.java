package com.frappagames.chickensrevenge.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Assets management class
 *
 * Created by Jérémy MOREAU on 19/08/15.
 */
public class Assets {
    public static TextureRegionDrawable title, box, cross, egg, grass, chicken, fox, wall,
            btnPlay, btnPlayOver, btnSoundOn, btnSoundOff, btnExit, btnExitOver;

//    public static Animation standAnimation, walkAnimation, jumpAnimation;

    public static  Sound        clickSound;
    public static  Music        music;
    private static TextureAtlas itemsAtlas;

    public static Label.LabelStyle font;
    public static BitmapFont genericFont;

    public static String maps[];

    public static void load() {
        // Fonts
        genericFont = new BitmapFont();
        genericFont.setColor(Color.WHITE);
//        BitmapFont genericFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
//        font = new Label.LabelStyle(genericFont, Color.WHITE);

        // Load Textures
        itemsAtlas  = new TextureAtlas(Gdx.files.internal("chickens-revenge.pack"));

        title   = new TextureRegionDrawable(itemsAtlas.findRegion("title"));
        box     = new TextureRegionDrawable(itemsAtlas.findRegion("box"));
        cross   = new TextureRegionDrawable(itemsAtlas.findRegion("cross"));
        egg     = new TextureRegionDrawable(itemsAtlas.findRegion("egg"));
        grass   = new TextureRegionDrawable(itemsAtlas.findRegion("grass"));
        chicken = new TextureRegionDrawable(itemsAtlas.findRegion("chicken"));
        fox     = new TextureRegionDrawable(itemsAtlas.findRegion("fox"));
        wall    = new TextureRegionDrawable(itemsAtlas.findRegion("wall"));

        btnPlay     = new TextureRegionDrawable(itemsAtlas.findRegion("btnPlay"));
        btnPlayOver = new TextureRegionDrawable(itemsAtlas.findRegion("btnPlayOver"));
        btnSoundOn  = new TextureRegionDrawable(itemsAtlas.findRegion("btnSoundOn"));
        btnSoundOff = new TextureRegionDrawable(itemsAtlas.findRegion("btnSoundOff"));
        btnExit     = new TextureRegionDrawable(itemsAtlas.findRegion("btnExit"));
        btnExitOver = new TextureRegionDrawable(itemsAtlas.findRegion("btnExitOver"));

        // Load Music and sounds
        // Music ♫
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
//        music.setLooping(true);
//        music.setVolume(0.5f);

        // Sounds ♪
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound-click.mp3"));

        // Load animations

//        // Stand animation
//        Texture sheet;
//        sheet = new Texture(Gdx.files.internal("stand.png"));
//        TextureRegion[][] tmp    = TextureRegion.split(sheet, sheet.getWidth()/4, sheet.getHeight()/2);
//        TextureRegion[]   frames = new TextureRegion[8];
//        int               index  = 0;
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 4; j++) {
//                frames[index++] = tmp[i][j];
//            }
//        }
//        standAnimation = new Animation(0.15f, frames);


        // Maps
        FileHandle mapFile = Gdx.files.internal("levels");
        maps = mapFile.readString().split("\n");
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }

    public static void dispose() {
        itemsAtlas.dispose();
        clickSound.dispose();
//        music.dispose();
    }
}
