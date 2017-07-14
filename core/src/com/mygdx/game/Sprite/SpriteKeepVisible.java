package com.mygdx.game.Sprite;

/**
 * A method to keep the sprite visible.
 */

public interface SpriteKeepVisible {

    /**
     * Makes that the sprite stays visible. Needs a camera.
     *  @param sprite ExtensibleSprite
     *
     */
    boolean keepVisible(ExtensibleSprite sprite);
}
