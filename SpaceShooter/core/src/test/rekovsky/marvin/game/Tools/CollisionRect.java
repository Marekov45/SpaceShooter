package test.rekovsky.marvin.game.Tools;

/**
 * {@link CollisionRect} controls the collision detection of the game.
 */
public class CollisionRect {

    float x, y;
    int width, height;

    /**
     * Initializes the activity.
     *
     * @param x the x coordinate of the collision rectangle.
     * @param y the y coordinate of the collision rectangle.
     * @param width the width of the collision rectangle.
     * @param height the height of the collision rectangle.
     */
    public CollisionRect(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Moves the {@link CollisionRect}. The x and y of what the {@link CollisionRect} is going to be colliding with
     * isn't always the same so the {@link CollisionRect} has to be able to move.
     *
     * @param x the x coordinate of the rectangle.
     * @param y the y coordinate of the rectangle.
     */
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if {@link CollisionRect} collides with another {@link CollisionRect}.
     *
     * @param rect it must not be {@code null}.
     * @return true if they collide with each other.
     */
    public boolean collidesWith(CollisionRect rect) {
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }


}
