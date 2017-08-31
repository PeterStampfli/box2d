package com.mygdx.game.Buttons;

/**
 * Button action for touch begin.
 */

public interface ButtonTouchBegin {

    /**
     * TouchBegin action on the sprite. Mainly changes the state of the button.
     *
     * @param buttonExtension ButtonExtension, calling this method
     */
    void touchBegin(ButtonExtension buttonExtension);
}
