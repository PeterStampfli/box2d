package com.mygdx.game.utilities;

/**
 * Created by peter on 4/15/17.
 * An interface for objects that have to be resized at startup . For collecting and automatic resizing.
 */

public interface Resizable {

    /**
     * Call in the resize method of ApplicationAdapter or Screen.
     *
     * @param width
     * @param height
     */
    public void resize(int width,int height);
}
