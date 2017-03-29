package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.utilities.Accelerometer.FullOrientation.LANDSCAPE;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.PORTRAIT;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.REVERSE_LANDSCAPE;
import static com.mygdx.game.utilities.Accelerometer.FullOrientation.REVERSE_PORTRAIT;


public class Accelerometer {
    private boolean hasAccelerometer;            // variables for mouse simmulation of accelerometer
    private FullOrientation orientation;               // the current orientation of the userInteraction
    private float mouseAccelerometerMax =5f;
    private float mouseAccelerometerScale;
    private Vector2 mouseAccelerometer=new Vector2();
    private int screenWidth2=0;
    private int screenHeight2=0;

    public enum FullOrientation {
        PORTRAIT, LANDSCAPE, REVERSE_PORTRAIT, REVERSE_LANDSCAPE
    }

    /**
     * create and check if there is an accelerometer
     * @param theCamera
     */
    public Accelerometer(Camera theCamera){
        hasAccelerometer= Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    /**
     * set the acceleration for mouse replacement of accelerometer at border of screen
     * smaller values for less sensitivity and more precision
     */
    public void setMouseAccelerometerMax(float a){ mouseAccelerometerMax=a;}

    /**
     * find the full orientation of the device (required to map accelerometer readings)
     *
     * @return the orientation
     */
    public FullOrientation getOrientation(){
        FullOrientation orientation=PORTRAIT;
        // the native orientation is the reference orientation for 0 degrees device rotation
        Input.Orientation nativeOrientation=Gdx.input.getNativeOrientation();
        int rotation=Gdx.input.getRotation();

        if(nativeOrientation==Input.Orientation.Landscape){ // tablets
            switch (rotation){
                case 0:
                    orientation= LANDSCAPE;
                    break;
                case 90:
                    orientation= REVERSE_PORTRAIT;
                    break;
                case 180:
                    orientation= REVERSE_LANDSCAPE;
                    break;
                case 270:
                    orientation= PORTRAIT;
                    break;
            }
        }
        else {                                               // phones: native orientation=portrait
            switch (rotation){
                case 0:
                    orientation= PORTRAIT;
                    break;
                case 90:
                    orientation= LANDSCAPE;
                    break;
                case 180:
                    orientation= REVERSE_PORTRAIT;
                    break;
                case 270:
                    orientation= REVERSE_LANDSCAPE;
                    break;
            }
        }
        return orientation;
    }

    /**
     * update orientation and mouse accelerometer scales at resize
     */
    public void resize(int width,int height){
        mouseAccelerometerScale =2* mouseAccelerometerMax /Math.min(width, height);
        screenWidth2=width/2;
        screenHeight2=height/2;
        orientation=getOrientation();
    }

    /**
     * read the accelerometer if there is one,
     * use current userInteraction orientation to take into account the device rotation
     *
     * if there is no accelerometer, read the touch position if the right mouse button is pressed
     * and use it to simulate an accelerometer
     *
     */
    public void readAccelerometer(Vector2 position){
        if (hasAccelerometer){
            switch (orientation) {
                case LANDSCAPE:
                    position.set(-Gdx.input.getAccelerometerY(),Gdx.input.getAccelerometerX());
                    break;
                case PORTRAIT:
                    position.set(Gdx.input.getAccelerometerX(),Gdx.input.getAccelerometerY());
                    break;
                case REVERSE_LANDSCAPE:
                    position.set(Gdx.input.getAccelerometerY(),-Gdx.input.getAccelerometerX());
                    break;
                case REVERSE_PORTRAIT:
                    position.set(-Gdx.input.getAccelerometerX(),-Gdx.input.getAccelerometerY());
                    break;
            }
        }
        else {
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                // if the right mouse button is pressed update the replacement accelerometer values
                //  screen origin is top left corner
                mouseAccelerometer.x = mouseAccelerometerScale *(Gdx.input.getX()-screenWidth2);
                mouseAccelerometer.y =-mouseAccelerometerScale *(Gdx.input.getY()-screenHeight2);
                mouseAccelerometer.clamp(0.0f,mouseAccelerometerMax);
            }
            position.set(mouseAccelerometer);
        }
    }
}
