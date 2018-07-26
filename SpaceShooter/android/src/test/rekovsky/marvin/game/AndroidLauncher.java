package test.rekovsky.marvin.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


/**
 * {@link AndroidLauncher} gets called first when the application starts.
 */
public class AndroidLauncher extends AndroidApplication {

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SpaceShooter(), config);
    }
}
