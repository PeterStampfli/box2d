package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.input;

/**
 * Simplifies reading touch or mouse. Unprojects the position.
 * Limits the mouse position to the application window.
 */

public class TouchReader implements Resizable{

    private Vector3 spacePositionOfTouch=new Vector3();    // x,y-components give touch position
    private int screenWidth,screenHeight;
    public Camera camera;

    /**
     * Update screen/window size for limiting the mouse position. call in resize.
     */
    public void resize(int width,int height){
        screenWidth=width;
        screenHeight=height;
    }

    /**
     * Set the camera. Call in show() method of screens. Or render if using more than one camera/viewport.
     *
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Set the camera using the camera of a viewport. Call in show() method of screens.
     *
     * @param viewport
     */
    public void setCamera(Viewport viewport) {
        this.camera = viewport.getCamera();
    }


    /**
     * Get the x-coordinate of the touch position on the screen. Limited to the window for mouse.
     *
     * @return float, x-coordinate
     */
    public int getXLimited(){
        return MathUtils.clamp(input.getX(),0,screenWidth);
    }

    /**
     * Get the y-coordinate of the touch position on the screen. Limited to the window for mouse.
     *
     * @return float, y-coordinate
     */
    public int getYLimited(){
        return MathUtils.clamp(input.getY(),0,screenHeight);
    }

    /**
     * Get the touch position. Unprojected to the world seen by the camera.
     *
     * @param position Vector2 object, will be set to position.
     */
    public void getPosition(Vector2 position){
        spacePositionOfTouch.set(getXLimited(),getYLimited(),0f);
        camera.unproject(spacePositionOfTouch);
        position.set(spacePositionOfTouch.x,spacePositionOfTouch.y);
    }

    /**
     * Get touch event. Using a mouse you have to press the left button to get a touch event.
     * Touch on touchscreen is always left button press.
     *
     * @return boolean, true if the screen/window has been touched
     */
    public boolean isTouching(){
        return input.isTouched() && input.isButtonPressed(Input.Buttons.LEFT);
    }
}
