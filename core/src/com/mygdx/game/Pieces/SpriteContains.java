package com.mygdx.game.Pieces;

/**
 * Created by peter on 4/18/17.
 * test if a supplied Extensible Sprite contains a point
 */

public interface SpriteContains {
    /**
     * return true if the sprite contains the point
     * @param sprite
     * @param x
     * @param y
     * @return
     */
    boolean test(ExtensibleSprite sprite, float x, float y);
}
