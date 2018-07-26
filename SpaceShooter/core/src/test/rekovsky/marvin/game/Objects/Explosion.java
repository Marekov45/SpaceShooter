package test.rekovsky.marvin.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * {@link Explosion} keeps track of every single explosion in the game. If the animation of the explosion is done
 * it gets removed.
 */
public class Explosion {
    public static final float FRAME_LENGTH = 0.2f; //time between each frame of the explosion animation
    public static final int OFFSET = 8; // makes sure that the explosion is centered on where the asteroid was
    public static final int SIZE = 64; // size of the explosion
    public static final int IMAGE_SIZE = 32;

    private static Animation anim = null;
    float x, y;
    float statetime;

    public boolean remove = false; // checks if explosion should be removed from the game screen

    /**
     * Initializes the activity.
     *
     * @param x the x  coordinate of the asteroid
     * @param y the y  coordinate of the asteroid
     */
    public Explosion(float x, float y) {
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0;

        //Loads animation and splits explosion file into a 2D-Array of texture arrays for the animation
        if (anim == null)
            anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), IMAGE_SIZE, IMAGE_SIZE)[0]);
    }

    /**
     * Removes the explosion after the asteroid was destroyed.
     *
     * @param deltatime time in seconds since the last render.
     */
    public void update(float deltatime) {
        statetime += deltatime;
        if (anim.isAnimationFinished(statetime))
            remove = true;
    }

    /**
     * Renders the animation.
     *
     * @param batch draws the sprites using indices. It must not be {@code null}.
     */
    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) anim.getKeyFrame(statetime), x, y, SIZE, SIZE);
    }

}
