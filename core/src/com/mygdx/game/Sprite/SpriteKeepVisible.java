package com.mygdx.game.Sprite;

/**
 * A method to keep the sprite visible.
 */

public interface SpriteKeepVisible {

    /**
     * Makes that the sprite stays visible. Uses the camera of the device reference in the sprite.
     *
     *  @param sprite ExtensibleSprite
     */
    void keepVisible(ExtensibleSprite sprite);
}
