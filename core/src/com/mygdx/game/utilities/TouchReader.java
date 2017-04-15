package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by peter on 3/29/17.
 */

public class TouchReader implements Resizable{

    private Vector3 spacePositionOfTouch=new Vector3();    // x,y-components give touch position
    private int screenWidth,screenHeight;
    private Vector2 position=new Vector2();

    /**
     * update screen size (for limiting raw position)
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
     * read touch position,limit to screen if its a mouse and unproject to current viewport
     * @param camera current, for pieces
     * @return position, unprojected according to viewport, will be overwritten at next call
     */
    public Vector2 getPosition(Camera camera){
        spacePositionOfTouch.set(getXLimited(),getYLimited(),0f);
        camera.unproject(spacePositionOfTouch);
        position.set(spacePositionOfTouch.x,spacePositionOfTouch.y);
        return position;
    }

    /**
     * read touch position,limit to screen if its a mouse and unproject to current viewport
     * @param viewport current, for pieces
     * @return position, unprojected according to viewport, will be overwritten at next call
     */
    public Vector2 getPosition(Viewport viewport){
        return getPosition(viewport.getCamera());
    }

    /**
     * get touch event, for mouse only if the left button is pressed
     */
    public boolean isTouching(){
        return input.isTouched() && input.isButtonPressed(Input.Buttons.LEFT);
    }
}
