package com.rekovsky.marvin.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; 
import com.rekovsky.marvin.game.States.MenuScreen;
import com.rekovsky.marvin.game.Tools.ScrollingBackground;

public class SpaceShooter extends Game {

    public SpriteBatch batch;
    public ScrollingBackground scrBackground;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.scrBackground = new ScrollingBackground();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        this.scrBackground.resize(width, height);
        super.resize(width, height);
    }

}
