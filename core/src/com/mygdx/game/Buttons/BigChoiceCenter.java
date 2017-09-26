package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.Drawable;
import com.mygdx.game.Pieces.TouchableAdapter;
import com.mygdx.game.utilities.Device;

/**
 * a varying image
 */

public class BigChoiceCenter extends TouchableAdapter implements Drawable {
    private Device device;                                  // for renderer
    public Rectangle region=new Rectangle();                          // the "window"
    public TextureRegion image;

    int nChoices;                                // number of different choices
    int choice=0;
    boolean pressed=false;

    /**
     * create a carouselCenter object with a sample image that defines the size
     *
     * @param device Device
     * @param width float width of image
     * @param height float height of image
     */
    public BigChoiceCenter(Device device, float width, float height){
        this.device=device;
        region.setWidth(width);
        region.setHeight(height);
    }


    /**
     * set position
     *
     * @param x
     * @param y
     */
    public void setPosition(float x, float y){
        region.setX(x);
        region.setY(y);
    }


    public void setNumberChoices(int nChoices) {
        this.nChoices = nChoices;
    }

    // the region is where the image is drawn and if there is touch
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }


    @Override
    public boolean contains(float x, float y) {
        return region.contains(x, y);
    }

    @Override
    public void draw() {
        if (pressed){
            device.spriteBatch.setColor(ButtonExtension.COLOR_PRESSED);
        }
        else {
            device.spriteBatch.setColor(ButtonExtension.COLOR_UP);
        }
        device.spriteBatch.draw(image,region.getX(),region.getY());
        device.spriteBatch.setColor(Color.WHITE);
    }
}
