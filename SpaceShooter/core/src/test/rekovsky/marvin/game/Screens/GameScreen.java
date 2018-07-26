package test.rekovsky.marvin.game.Screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import test.rekovsky.marvin.game.Objects.Asteroid;
import test.rekovsky.marvin.game.Objects.Bullet;
import test.rekovsky.marvin.game.Objects.Explosion;
import test.rekovsky.marvin.game.Tools.CollisionRect;
import test.rekovsky.marvin.game.SpaceShooter;

/**
 * {@link GameScreen} handles the game logic.
 */
public class GameScreen implements Screen {

    public static final float SPEED = 300;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    public static final float SHOOT_WAIT_TIME = 0.3f;
    public static final float MIN_ASTEROID_SPAWN_TIME = 0.1f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Explosion> explosions;
    private Animation[] anim;
    private Random random;
    private SpaceShooter game;
    private Texture blank;
    private float x;
    private float y;
    private BitmapFont scoreFont;
    private CollisionRect playerRect;
    private float stateTime;
    private float shootTimer;
    private float asteroidSpawnTimer;
    private float health = 1;//0 = dead, 1 = full health
    private int score;

    /**
     * Initializes the activity.
     *
     * @param game is not allowed to be {@code null}.
     */
    public GameScreen(SpaceShooter game) {
        this.game = game;
        y = 0;
        x = SpaceShooter.WIDTH / 2 - SHIP_WIDTH / 2;
        score = 0;
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        explosions = new ArrayList<Explosion>();
        scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        blank = new Texture("blank.png");
        playerRect = new CollisionRect(0, 0, SHIP_WIDTH, SHIP_HEIGHT);
        shootTimer = 0;
        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
        anim = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
        anim[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);//No tilt
        game.scrollingBackground.setSpeedFixed(false);
    }

    @Override
    public void show() {
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //Shooting code
        shootTimer += delta;
        if ((isRight() || isLeft()) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;
            int offset = 4;
            bullets.add(new Bullet(x + offset));
            bullets.add(new Bullet(x + SHIP_WIDTH - offset));
        }

        //Random Asteroid spawn code
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt(SpaceShooter.WIDTH - Asteroid.WIDTH)));
        }

        //Update asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove)
                asteroidsToRemove.add(asteroid);
        }

        //Update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove)
                bulletsToRemove.add(bullet);
        }

        //Update explosions
        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for (Explosion explosion : explosions) {
            explosion.update(delta);
            if (explosion.remove)
                explosionsToRemove.add(explosion);
        }
        explosions.removeAll(explosionsToRemove);

        //Movement code
        if (isLeft()) {//Left
            x -= SPEED * Gdx.graphics.getDeltaTime();
            if (x < 0)
                x = 0;
        }
        if (isRight()) {//Right
            x += SPEED * Gdx.graphics.getDeltaTime();

            if (x + SHIP_WIDTH > SpaceShooter.WIDTH)
                x = SpaceShooter.WIDTH - SHIP_WIDTH;
        }

        //After player moves, update collision rectangle
        playerRect.move(x, y);

        //After all updates, check for collisions
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {//Collision occured
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
                    score += 100;
                }
            }
        }
        bullets.removeAll(bulletsToRemove);

        for (Asteroid asteroid : asteroids) {
            if (asteroid.getCollisionRect().collidesWith(playerRect)) {
                asteroidsToRemove.add(asteroid);
                health -= 0.1;

                //If health is depleted, go to game over screen
                if (health <= 0) {
                    this.dispose();
                    game.setScreen(new GameOverScreen(game, score));
                    return;
                }
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        // font for the score
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(game.batch, scoreLayout, SpaceShooter.WIDTH / 2 - scoreLayout.width / 2, SpaceShooter.HEIGHT - scoreLayout.height - 10);

        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }

        //Draw health
        if (health > 0.6f)
            game.batch.setColor(Color.GREEN);
        else if (health > 0.2f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);

        game.batch.draw(blank, 0, 0, SpaceShooter.WIDTH * health, 5);
        game.batch.setColor(Color.WHITE);

        game.batch.draw((TextureRegion) anim[0].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
        game.batch.end();
    }

    /**
     * Checks if user touched the screen on the right side.
     *
     * @return true if the x coordinate where the user touched the screen is bigger or equal to half of screen width.
     */
    private boolean isRight() {
        return (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x >= SpaceShooter.WIDTH / 2);
    }

    /**
     * Checks if user touched the screen on the left side.
     *
     * @return true if the x coordinate where the user touched the screen is smaller than half of screen width.
     */
    private boolean isLeft() {
        return (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x < SpaceShooter.WIDTH / 2);
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