package test.rekovsky.marvin.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * {@link GameCamera} controls what the user sees in the game world and adjusts the image to a certain
 * width and height depending on the screen size.
 */
public class GameCamera {

    private OrthographicCamera cam;
    private StretchViewport viewport;

    /**
     * Initializes the activity.
     *
     * @param width the width of the camera.
     * @param height the height of the camera.
     */
    public GameCamera(int width, int height) {
        cam = new OrthographicCamera();
        viewport = new StretchViewport(width, height, cam);
        viewport.apply();
        cam.position.set(width / 2, height / 2, 0);
        cam.update();
    }

    /**
     * Returns the combined projection and view matrix.
     *
     * @return a column major 4x4 matrix. It must not be {@code null}.
     */
    public Matrix4 combined() {
        return cam.combined;
    }

    /**
     * Updates the viewport when the screen resizes.
     *
     * @param width the width of the camera.
     * @param height the height of the camera.
     */
    public void update(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Returns where the user is touching the screen in the game world.
     *
     * @return the location of the input in the game world. It must not be {@code null}.
     */
    public Vector2 getInputInGameWorld() {
        Vector3 inputScreen = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        Vector3 unprojected = cam.unproject(inputScreen);
        return new Vector2(unprojected.x, unprojected.y);
    }

}
