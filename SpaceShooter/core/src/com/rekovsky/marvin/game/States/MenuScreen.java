package com.rekovsky.marvin.game.States;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.rekovsky.marvin.game.SpaceShooter;

public class MenuScreen implements Screen {

    private Texture background;
    private Texture startBtn;
    SpaceShooter shooter;

    public MenuScreen(SpaceShooter shooter) {
        this.shooter=shooter;
        startBtn = new Texture("play_button.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        shooter.batch.begin();
        shooter.scrBackground.updateAndRender(delta,shooter.batch);
        shooter.batch.draw(startBtn,(1080 / 2) - (startBtn.getWidth() / 2), 1920 / 3);
        shooter.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
