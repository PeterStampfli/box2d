package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.Drawable;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 9/23/17.
 */

public class CarouselCenter implements Touchable,Drawable {
    private Device device;                                  // for scissors
    private Rectangle region=new Rectangle();                          // the "window"

    /**
     * create a carouselCenter object with a sample image that defines the size
     *
     * @param device Device
     * @param sampleImage
     */
    public CarouselCenter(Device device,TextureRegion sampleImage){
        this.device=device;
        region.setWidth(sampleImage.getRegionWidth());
        region.setHeight(sampleImage.getRegionHeight());
    }

    /**
     * set position of center
     *
     * @param x
     * @param y
     */
    public void setCenter(float x, float y){
        region.setX(x-0.5f*region.getWidth());
        region.setY(y-0.5f*region.getHeight());
    }

    @Override
    public void keepVisible() {

    }

    @Override
    public void touchBegin(Vector2 position) {

    }

    @Override
    public void touchDrag(Vector2 position, Vector2 deltaPosition) {

    }

    @Override
    public void touchEnd() {

    }

    @Override
    public void scroll(Vector2 position, int amount) {

    }

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

    }
}
