package test.rekovsky.marvin.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import test.rekovsky.marvin.game.Screens.MenuScreen;
import test.rekovsky.marvin.game.Tools.GameCamera;
import test.rekovsky.marvin.game.Tools.ScrollingBackground;

/**
 * {@link SpaceShooter} delegates to a {@link Screen}. This allows an application to easily have multiple screens.
 */
public class SpaceShooter extends Game {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 720;

    public SpriteBatch batch;
    public ScrollingBackground scrollingBackground;
    public GameCamera cam;

    /**
     * Called once when the application is created.
     */
    @Override
    public void create() {
        batch = new SpriteBatch(); //Draws sprites using indices
        cam = new GameCamera(WIDTH, HEIGHT);


        this.scrollingBackground = new ScrollingBackground();
        this.setScreen(new MenuScreen(this));
    }

    /**
     * Called by the game loop from the application every time rendering should be performed.
     */
    @Override
    public void render() {
        batch.setProjectionMatrix(cam.combined());
        super.render();
    }

    /**
     * Re-sizes the game screen when the game is not in a paused state.
     *
     * @param width  new width the screen has been resized to. it must not be {@code null}.
     * @param height new height the screen has been resized to. it must not be {@code null}.
     */
    @Override
    public void resize(int width, int height) {
        cam.update(width, height);
        super.resize(width, height);
    }

}
