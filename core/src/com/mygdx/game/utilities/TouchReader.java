package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.Gdx.input;

/**
 * Simplifies reading touch or mouse. Un-projects the position.
 * Limits the mouse position to the application window.
 */

public class TouchReader implements Resizable {

    public Device device;
    private int screenWidth, screenHeight;

    /**
     * TouchReader has access to device for the actual camera.
     *
     * @param device Device, of the game
     */
    public TouchReader(Device device) {
        this.device = device;
    }

    /**
     * Update screen/window size for limiting the mouse position. call in resize.
     */
    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    /**
     * Get the x-coordinate of the touch position on the screen. Limited to the window for mouse.
     *
     * @return float, x-coordinate
     */
    public int getXLimited() {
        return MathUtils.clamp(input.getX(), 0, screenWidth);
    }

    /**
     * Get the y-coordinate of the touch position on the screen. Limited to the window for mouse.
     *
     * @return float, y-coordinate
     */
    public int getYLimited() {
        return MathUtils.clamp(input.getY(), 0, screenHeight);
    }

    /**
     * Get the touch position. Un-projected to the world seen by the camera.
     *
     * @param position Vector2 object, will be set to position.
     */
    public void getPosition(Vector2 position) {
        position.set(getXLimited(), getYLimited());
        device.unproject(position);
    }

    /**
     * Get touch event. Using a mouse you have to press the left button to get a touch event.
     * Touch on touchscreen is always left button press.
     *
     * @return boolean, true if the screen/window has been touched
     */
    public boolean isTouching() {
        return input.isTouched() && input.isButtonPressed(Input.Buttons.LEFT);
    }
}
