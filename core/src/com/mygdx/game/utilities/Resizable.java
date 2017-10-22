package com.mygdx.game.utilities;

/**
 * An interface for objects that have to be resized at startup and resize events . For collecting and automatic resizing.
 */

public interface Resizable {

    /**
     * Call in the resize method of ApplicationAdapter or Screen.
     *
     * @param width int, width of screen
     * @param height int, height of screen
     */
    void resize(int width, int height);
}
