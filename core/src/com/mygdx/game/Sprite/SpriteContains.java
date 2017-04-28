package com.mygdx.game.Sprite;

/**
 * Created by peter on 4/18/17.
 * tests if a supplied Extensible Sprite contains a point
 */

public interface SpriteContains {

    /**
     * Check if the sprite contains the point.
     *
     * @param sprite ExtensibleSprite
     * @param x      float, x-coordinate of the point
     * @param y      float, y-coordinate of the point
     * @return boolean, true if the sprite contains the point
     */
    boolean contains(ExtensibleSprite sprite, float x, float y);
}
