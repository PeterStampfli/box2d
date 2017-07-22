package com.mygdx.game.Buttons;

/**
 * Created by peter on 5/9/17.
 */

public interface ButtonTouchEnd {

    /**
     * TouchEnd action on the sprite. Does the action..
     *
     * @param buttonExtension ButtonExtension, calling this method
     * @return boolean, true if something changed
     */
    void touchEnd(ButtonExtension buttonExtension);
}
