package test.rekovsky.marvin.game.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import test.rekovsky.marvin.game.SpaceShooter;

/**
 * {@link ScrollingBackground} implements the screen background that scrolls down the whole time.
 */
public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 50;
    public static final int GOAL_REACH_ACCELERATION = 200;

    Texture image;
    float y1, y2;
    int speed; //In pixels / second
    int goalSpeed;
    float imageScale;
    boolean speedFixed;

    /**
     * Initializes the activity.
     */
    public ScrollingBackground() {
        image = new Texture("stars_background.png");

        y1 = 0;
        y2 = image.getHeight();
        speed = 0;
        goalSpeed = DEFAULT_SPEED;
        imageScale = SpaceShooter.WIDTH / image.getWidth();
        speedFixed = true;
    }

    /**
     * Called when the screen should render itself.
     *
     * @param deltaTime time in seconds since the last render.
     * @param batch     draws the sprites. It must not be {@code null}.
     */
    public void updateAndRender(float deltaTime, SpriteBatch batch) {
        //Speed adjustment to reach goal
        if (speed < goalSpeed) {
            speed += GOAL_REACH_ACCELERATION * deltaTime;
            if (speed > goalSpeed)
                speed = goalSpeed;
        } else if (speed > goalSpeed) {
            speed -= GOAL_REACH_ACCELERATION * deltaTime;
            if (speed < goalSpeed)
                speed = goalSpeed;
        }

        if (!speedFixed)
            speed += ACCELERATION * deltaTime;

        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        //if image reached the bottom of screen and is not visible, put it back on top
        if (y1 + image.getHeight() * imageScale <= 0)
            y1 = y2 + image.getHeight() * imageScale;

        if (y2 + image.getHeight() * imageScale <= 0)
            y2 = y1 + image.getHeight() * imageScale;

        //Render
        batch.draw(image, 0, y1, SpaceShooter.WIDTH, image.getHeight() * imageScale);
        batch.draw(image, 0, y2, SpaceShooter.WIDTH, image.getHeight() * imageScale);
    }

    /**
     * @param goalSpeed the goal speed the stars in the background should be scrolling at.
     */
    public void setSpeed(int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    /**
     * @param speedFixed the speed is either a fixed number (and doesn't accelerate) or not.
     */
    public void setSpeedFixed(boolean speedFixed) {
        this.speedFixed = speedFixed;
    }

}