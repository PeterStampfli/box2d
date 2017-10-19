package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.Drawable;
import com.mygdx.game.Pieces.TouchableAdapter;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * a varying image
 */

public class BigChoiceCenter extends TouchableAdapter implements Drawable {
    public Device device;                                  // for spriteBatch
    public Rectangle region=new Rectangle();                          // the "window"
    public TextureRegion image;

    int nChoices;                                // number of different choices
    int choice=0;
    boolean pressed=false;

    /**
     * create a carouselCenter object with a sample image that defines the size
     *
     * @param device Device, for spriteRenderer
     */
    public BigChoiceCenter(Device device){
        this.device=device;
    }

    /**
     * set dimensions
     *
     * @param width float width of image
     * @param height float height of image
     */
    public void setDimensions(float width, float height){
        region.setWidth(width);
        region.setHeight(height);
    }

    /**
     * set position
     *
     * @param x float, x-coordinate
     * @param y float, y-coordinate
     */
    public void setPosition(float x, float y){
        region.setX(x);
        region.setY(y);
    }

    /**
     * set the number of choices
     *
     * @param nChoices int, number of choices
     */
    public void setNumberChoices(int nChoices) {
        this.nChoices = nChoices;
    }

    //depending on actual choices

    /**
     * Create a textureRegion image for the center (default, for overwriting)
     *
     * @param choice int, the choice
     * @return TextureRegion, image
     */
     public TextureRegion createImage(int choice){


return null;


     }

    /**
     * do something depending on choice (default, for overwriting)
     *
     * @param choice int, the choice
     */
    public void action(int choice){
        L.og("do action choice: "+choice);
    }

    // navigation

    /**
     * switch to the next choice, change image
     */
    public void nextChoice(){
        choice++;
        if (choice>=nChoices){
            choice=0;
        }
        image=createImage(choice);
    }

    /**
     * switch to the previous choice, change image
     */
    public void previousChoice(){
        choice--;
        if (choice<0){
            choice=nChoices-1;
        }
        image=createImage(choice);
    }


    // the touchable methods

    // the region is where the image is drawn and if there is touch
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }


    @Override
    public boolean contains(float x, float y) {
        return region.contains(x, y);
    }

    /**
     * draw the center, filling the rectangle region with the texture region
     */
    @Override
    public void draw() {
        if (pressed){
            device.spriteBatch.setColor(ButtonExtension.COLOR_PRESSED);
        }
        else {
            device.spriteBatch.setColor(ButtonExtension.COLOR_UP);
        }
        device.spriteBatch.draw(image,region.getX(),region.getY(),region.getWidth(),region.getHeight());
        device.spriteBatch.setColor(Color.WHITE);
    }


    @Override
    public void touchBegin(Vector2 position){
        pressed=true;
    }
}
