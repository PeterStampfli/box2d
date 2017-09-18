package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.utilities.Accelerometer.FullOrientation.LANDSCAPE;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.PORTRAIT;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.REVERSE_LANDSCAPE;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.REVERSE_PORTRAIT;

/**
 * Read the accelerometer of a mobile device or replace it on a PC by the mouse position.
 */

public class Accelerometer implements Resizable{
    private boolean hasAccelerometer;            // variables for mouse simulation of accelerometer
    private FullOrientation orientation;               // the current orientation of the userInteraction
    private float mouseAccelerometerMax = 5f;
    private float mouseAccelerometerScale;
    private Vector2 acceleration = new Vector2();
    private Vector2 mouseAcceleration=new Vector2();
    private int screenWidth2 = 0;
    private int screenHeight2 = 0;

    /**
     * Create an instance and check if there is an accelerometer.
     *
     */
    public Accelerometer() {
        hasAccelerometer = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    /**
     * Set the maximum acceleration for a mouse replacement of the accelerometer.
     * This is the absolute value of the acceleration at the border of the display or window.
     * Use smaller values for less sensitivity and more precision.
     *
     * @param a float, maximum absolute value for acceleration
     */
    public void setMouseAccelerometerMax(float a) {
        mouseAccelerometerMax = a;
    }

    /**
     * Find the full orientation of the mobile device.
     * Required to map accelerometer readings to display (x,y)-axis values.
     *
     * @return FullOrientation enum, the orientation
     */
    public FullOrientation getOrientation() {
        FullOrientation orientation = PORTRAIT;
        // the native orientation is the reference orientation for 0 degrees device rotation
        Input.Orientation nativeOrientation = Gdx.input.getNativeOrientation();
        int rotation = Gdx.input.getRotation();
        if (nativeOrientation == Input.Orientation.Landscape) { // tablets
            switch (rotation) {
                case 0:
                    orientation = LANDSCAPE;
                    break;
                case 90:
                    orientation = REVERSE_PORTRAIT;
                    break;
                case 180:
                    orientation = REVERSE_LANDSCAPE;
                    break;
                case 270:
                    orientation = PORTRAIT;
                    break;
            }
        } else {                                               // phones: native orientation=portrait
            switch (rotation) {
                case 0:
                    orientation = PORTRAIT;
                    break;
                case 90:
                    orientation = LANDSCAPE;
                    break;
                case 180:
                    orientation = REVERSE_PORTRAIT;
                    break;
                case 270:
                    orientation = REVERSE_LANDSCAPE;
                    break;
            }
        }
        return orientation;
    }

    /**
     * Update the orientation and the mouse accelerometer scales at resize events.
     *
     * @param width int, width of screen
     * @param height int, height of screen
     */
    public void resize(int width, int height) {
        mouseAccelerometerScale = 2 * mouseAccelerometerMax / Math.min(width, height);
        screenWidth2 = width / 2;
        screenHeight2 = height / 2;
        orientation = getOrientation();
    }

    /**
     * Read the accelerometer if there is one and transform to (x,y) display axis values.
     * Use the current orientation to take into account the device rotation.
     * <p>
     * If there is no accelerometer use the mouse to simulate the accelerometer.
     * If the right mouse button is pressed, then read the touch position.
     * else use the last reading
     *
     * @return Vector2, acceleration read from the accelerometer or mouse substitute. (Reuses a single Vector2 instance.)
     */
    public Vector2 read() {
        if (hasAccelerometer) {
            switch (orientation) {
                case LANDSCAPE:
                    acceleration.set(-Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX());
                    break;
                case PORTRAIT:
                    acceleration.set(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
                    break;
                case REVERSE_LANDSCAPE:
                    acceleration.set(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX());
                    break;
                case REVERSE_PORTRAIT:
                    acceleration.set(-Gdx.input.getAccelerometerX(), -Gdx.input.getAccelerometerY());
                    break;
            }
        } else {
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                // if the right mouse button is pressed update the replacement accelerometer values
                //  screen origin is top left corner
                mouseAcceleration.x = mouseAccelerometerScale * (Gdx.input.getX() - screenWidth2);
                mouseAcceleration.y = -mouseAccelerometerScale * (Gdx.input.getY() - screenHeight2);
                mouseAcceleration.clamp(0.0f, mouseAccelerometerMax);
            }
            acceleration.set(mouseAcceleration);
        }
        return acceleration;
    }

    /**
     * enum for the device orientation.
     */
    public enum FullOrientation {
        PORTRAIT, LANDSCAPE, REVERSE_PORTRAIT, REVERSE_LANDSCAPE
    }
}
