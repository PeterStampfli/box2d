package com.mygdx.game.Buttons;

/**
 * What the button does really.
 */

public interface ButtonAct {

    /**
     * Action of the sprite.
     *
     * @param buttonExtension ButtonExtension, calling this method
     * @return boolean, true if something changed
     */
    boolean act(ButtonExtension buttonExtension);
}
