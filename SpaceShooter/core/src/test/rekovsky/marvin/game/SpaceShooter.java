package test.rekovsky.marvin.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import test.rekovsky.marvin.game.States.MenuScreen;
import test.rekovsky.marvin.game.Tools.GameCamera;
import test.rekovsky.marvin.game.Tools.ScrollingBackground;

public class SpaceShooter extends Game {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 720;

    public SpriteBatch batch;
    public ScrollingBackground scrollingBackground;
    public GameCamera cam;

    @Override
    public void create () {
        batch = new SpriteBatch();
        cam = new GameCamera(WIDTH, HEIGHT);



        this.scrollingBackground = new ScrollingBackground();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render () {
        batch.setProjectionMatrix(cam.combined());
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        cam.update(width, height);
        super.resize(width, height);
    }

}
