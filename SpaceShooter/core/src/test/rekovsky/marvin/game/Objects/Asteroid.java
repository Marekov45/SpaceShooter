package test.rekovsky.marvin.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import test.rekovsky.marvin.game.SpaceShooter;
import test.rekovsky.marvin.game.Tools.CollisionRect;

/**
 * {@link Asteroid} handles the asteroids traveling through the game screen.
 */
public class Asteroid {
    public static final int SPEED = 250;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private static Texture texture;

    float x, y;
    CollisionRect rect;
    public boolean remove = false;

    /**
     * Initializes the activity.
     *
     * @param x the x coordinate of the asteroid.
     */
    public Asteroid (float x) {
        this.x = x;
        this.y = SpaceShooter.HEIGHT; // asteroids start spawning at the top of the screen
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("asteroid.png");
    }

    /**
     * Updates the position of the asteroid and removes the asteroid after it traveled through the game screen
     * or got shot.
     *
     * @param deltaTime time in seconds since the last render.
     */
    public void update (float deltaTime) {
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    /**
     * Renders the asteroid.
     *
     * @param batch draws the sprites using indices. It must not be {@code null}.
     */
    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    /**
     *
     * @return the collsion rectangle of the asteroid. It must not be {@code null}.
     */
    public CollisionRect getCollisionRect () {
        return rect;
    }

    /**
     *
     * @return the x coordinate of the asteroid.
     */
    public float getX () {
        return x;
    }

    /**
     *
     * @return the y coordinate of the asteroid.
     */
    public float getY () {
        return y;
    }
}
