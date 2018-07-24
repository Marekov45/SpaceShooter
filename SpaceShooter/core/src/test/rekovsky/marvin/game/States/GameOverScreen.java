package test.rekovsky.marvin.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import test.rekovsky.marvin.game.SpaceShooter;


public class GameOverScreen implements Screen {
    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    SpaceShooter shooter;

    int score, highscore;

    Texture shooterOverBanner;
    BitmapFont scoreFont;

    public GameOverScreen (SpaceShooter shooter, int score) {
        this.shooter = shooter;
        this.score = score;

        //Get highscore from save file
        Preferences prefs = Gdx.app.getPreferences("spaceshooter");
        this.highscore = prefs.getInteger("highscore", 0);

        //Check if score beats highscore
        if (score > highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        //Load textures and fonts
        shooterOverBanner = new Texture("gameover.png");
        scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));

       // shooter.scrBackground.setSpeedFixed(true);
      //  shooter.scrBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
    }

    @Override
    public void show () {}

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shooter.batch.begin();

        shooter.scrollingBackground.updateAndRender(delta, shooter.batch);

        shooter.batch.draw(shooterOverBanner, SpaceShooter.WIDTH / 2 - BANNER_WIDTH / 2, SpaceShooter.HEIGHT - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Highscore: \n" + highscore, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(shooter.batch, scoreLayout, SpaceShooter.WIDTH / 2 - scoreLayout.width / 2, SpaceShooter.HEIGHT - BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(shooter.batch, highscoreLayout, SpaceShooter.WIDTH / 2 - highscoreLayout.width / 2, SpaceShooter.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        float touchX = shooter.cam.getInputInGameWorld().x, touchY = SpaceShooter.HEIGHT - shooter.cam.getInputInGameWorld().y;

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = SpaceShooter.WIDTH / 2 - tryAgainLayout.width /2;
        float tryAgainY = SpaceShooter.HEIGHT / 2 - tryAgainLayout.height / 2;
        float mainMenuX = SpaceShooter.WIDTH / 2 - mainMenuLayout.width /2;
        float mainMenuY = SpaceShooter.HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        //Checks if hovering over try again button
        if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
            tryAgainLayout.setText(scoreFont, "Try Again", Color.YELLOW, 0, Align.left, false);

        //Checks if hovering over main menu button
        if (touchX >= mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY >= mainMenuY - mainMenuLayout.height && touchY < mainMenuY)
            mainMenuLayout.setText(scoreFont, "Main Menu", Color.YELLOW, 0, Align.left, false);

        //If try again and main menu is being pressed
        if (Gdx.input.isTouched()) {
            //Try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                shooter.batch.end();
                shooter.setScreen(new GameScreen(shooter));
                return;
            }

            //main menu
            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                shooter.batch.end();
                shooter.setScreen(new MenuScreen(shooter));
                return;
            }
        }

        //Draw buttons
        scoreFont.draw(shooter.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(shooter.batch, mainMenuLayout, mainMenuX, mainMenuY);

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
