package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by peter on 3/29/17.
 */

public class TouchReader {

    private Camera camera;
    private Vector3 spacePositionOfTouch=new Vector3();    // x,y-components give touch position
    private int screenWidth,screenHeight;

    /**
     * creation with a camera for unprojection of touch
     * @param theCamera
     */
    public TouchReader(Camera theCamera){
        camera=theCamera;
    }

    /**
     * set the camera for unprojecting touch position
     * @param theCamera Camera object
     */
    public  void setCamera(Camera theCamera){
        camera=theCamera;
    }

    /**
     * update orientation and mouse accelerometer scales at resize
     */
    public void resize(int width,int height){
        screenWidth=width;
        screenHeight=height;
    }


    /**
     * touch position on screen, limited to screen (for mouse)
     */
    public int getXLimited(){
        return MathUtils.clamp(input.getX(),0,screenWidth);
    }

    public int getYLimited(){
        return MathUtils.clamp(input.getY(),0,screenHeight);
    }

    /**
     * read touch position,limit to screen if its a mouse and unproject
     */
    public void getPosition(Vector2 position){
        spacePositionOfTouch.set(getXLimited(),getYLimited(),0f);
        camera.unproject(spacePositionOfTouch);
        position.set(spacePositionOfTouch.x,spacePositionOfTouch.y);
    }

    /**
     * get touch event, for mouse only if the left button is pressed
     */
    public boolean isTouching(){
        return input.isTouched() && input.isButtonPressed(Input.Buttons.LEFT);
    }
}
