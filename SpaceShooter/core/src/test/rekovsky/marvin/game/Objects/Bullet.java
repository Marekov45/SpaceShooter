package test.rekovsky.marvin.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import test.rekovsky.marvin.game.SpaceShooter;
import test.rekovsky.marvin.game.Tools.CollisionRect;

/**
 * {@link Bullet} handles the bullets for the ship and removes them when they collide with an asteroid
 * or leave the screen.
 */
public class Bullet {

    public static final int SPEED = 500; // speed of the bullet traveling
    public static final int DEFAULT_Y = 40; // bullet always starts at the same y coordinate
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;
    private static Texture texture; // every bullet uses the same texture

    float x, y;
    CollisionRect rect;
    public boolean remove = false; // checks if bullet should be removed from the game screen

    /**
     * Initializes the activity.
     *
     * @param x the x coordinate of the bullet.
     */
    public Bullet(float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("bullet.png");
    }

    /**
     * Updates the position of the bullet and removes the bullet after it traveled through the game screen.
     *
     * @param deltaTime time in seconds since the last render.
     */
    public void update(float deltaTime) {
        y += SPEED * deltaTime;
        // if y coordinate of the bullet is higher than the height of the game screen, it is removed
        if (y > SpaceShooter.HEIGHT)
            remove = true;
        //moves the collsion rectangle of the bullet with every update
        rect.move(x, y);
    }

    /**
     * Renders the bullet.
     *
     * @param batch draws the sprites using indices. It must not be {@code null}.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    /**
     * @return the collsion rectangle of the bullet. It must not be {@code null}.
     */
    public CollisionRect getCollisionRect() {
        return rect;
    }

}
