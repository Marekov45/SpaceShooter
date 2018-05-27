package com.rekovsky.marvin.game.Tools; 

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 80;
    private Texture img;
    private float y1, y2;
    private int speed; //pixels per second
    private float imageScale;

    public ScrollingBackground() {
        img = new Texture("bigspace.jpg");

        y1 = 0;
        y2 = img.getHeight();
        speed = DEFAULT_SPEED;
        imageScale=0;
    }

    public void updateAndRender(float delta, SpriteBatch batch) {
        y1 -= speed * delta;
        y2 -= speed * delta;

        if(y1 + img.getHeight() * imageScale<=0)
            y1 = y2 + img.getHeight() * imageScale;

        if(y2 + img.getHeight() * imageScale<=0)
            y2 = y1 + img.getHeight() * imageScale;

        batch.draw(img,0,y1,Gdx.graphics.getWidth(),img.getHeight() * imageScale);
        batch.draw(img,0,y2,Gdx.graphics.getWidth(),img.getHeight() * imageScale);
    }

    public void resize (int width, int height) {
        imageScale= width / img.getWidth();
    }


}
