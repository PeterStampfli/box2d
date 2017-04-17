package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by peter on 4/16/17.
 * clipping the sprite batch with scissorstack
 * avoid creation of new Rectangles at each call!
 */

public class Clipper {
    static public SpriteBatch spriteBatch;
    static public Camera camera;
    static private Rectangle scissors=new Rectangle();
    static private Rectangle bounds=new Rectangle();

    /**
     * give it the spritebatch
     * @param spriteBatch
     */
    static public void setSpritebatch(SpriteBatch spriteBatch){
        Clipper.spriteBatch=spriteBatch;
    }

    /**
     * set the camera
     * @param camera
     */
    static public void setCamera(Camera camera){
        Clipper.camera=camera;
    }

    /**
     * set the camera from a viewport
     * @param viewport
     */
    static public void setCamera(Viewport viewport){
        Clipper.camera=viewport.getCamera();
    }

    /**
     * limit the drawing region to the inside of the rectangle
     * @param bounds
     */
    static public void start(Rectangle bounds){
        spriteBatch.flush();
        ScissorStack.calculateScissors(camera,spriteBatch.getTransformMatrix(),bounds,scissors);
        ScissorStack.pushScissors(scissors);
    }

    /**
     * limit the drawing region to the inside of a rectangle region
     * @param x
     * @param y
     * @param width
     * @param height
     */
    static public void start(float x,float y,float width,float height){
        bounds.set(x, y, width, height);
        start(bounds);
    }

    /**
     * end the clipping
     */
    static public void end(){
        spriteBatch.flush();
        ScissorStack.popScissors();
    }


}
