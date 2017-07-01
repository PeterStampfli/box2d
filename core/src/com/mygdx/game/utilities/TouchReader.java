package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Lattice.LatticeVector;

import static com.badlogic.gdx.Gdx.input;

/**
 * Simplifies reading touch or mouse. Unprojects the position.
 * Limits the mouse position to the application window.
 */

public class TouchReader implements Resizable{

    private Vector3 spacePositionOfTouch=new Vector3();    // x,y-components give touch position
    private int screenWidth,screenHeight;

    /**
     * Update screen/window size for limiting the mouse position.
     */
    public void resize(int width,int height){
        screenWidth=width;
        screenHeight=height;
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
     * @param position Vector2 object, will be set to position
     * @param camera for unprojecting into the graphics world
     * @return Vector2 position, unprojected. Same Vector2 instance for every call
     */
    public Vector2 getPositionBasic(Vector2 position,Camera camera){
        spacePositionOfTouch.set(getXLimited(),getYLimited(),0f);
        camera.unproject(spacePositionOfTouch);
        position.set(spacePositionOfTouch.x,spacePositionOfTouch.y);
        return position;
    }

    /**
     * Get the touch position. Unprojected to the world seen by the camera.
     *
     * @param position Vector2 object, will be set to position
     * @param camera for unprojecting into the graphics world
     * @return Vector2 position, unprojected. Same Vector2 instance for every call
     */
    public Vector2 getPosition(Vector2 position,Camera camera){
        return getPositionBasic(position, camera);
    }

    /**
     * Get the touch position. Unprojected to the world seen by the camera of a viewport.
     *
     * @param position Vector2 object, will be set to position
     * @param viewport with the camera for unprojecting
     * @return position, unprojected according to viewport, will be overwritten at next call
     */
    public Vector2 getPosition(Vector2 position, Viewport viewport){
        return getPositionBasic(position, viewport.getCamera());
    }

    /**
     * Get the touch position. Unprojected to the world seen by the camera of a viewport.
     *
     * @param position LatticeVector2 object, will be set to position
     * @param viewport with the camera for unprojecting
     * @return position, unprojected according to viewport, will be overwritten at next call
     */
    public LatticeVector getPosition(LatticeVector position, Viewport viewport){
        getPositionBasic(position, viewport.getCamera());
        return position;
    }

    /**
     * Get the touch position. Unprojected to the world seen by the camera of a viewport.
     *
     * @param position LatticeVector2 object, will be set to position
     * @param camera for unprojecting
     * @return position, unprojected according to viewport, will be overwritten at next call
     */
    public LatticeVector getPosition(LatticeVector position, Camera camera){
        getPositionBasic(position, camera);
        return position;
    }

    /**
     * Get touch event. Using a mouse you have to press the left button to get a touch event.
     * Touch implies left button press.
     *
     * @return boolean, true if the screen/window has been touched
     */
    public boolean isTouching(){
        return input.isTouched() && input.isButtonPressed(Input.Buttons.LEFT);
    }
}
