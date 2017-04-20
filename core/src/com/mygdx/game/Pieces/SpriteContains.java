package com.mygdx.game.Pieces;

/**
 * Created by peter on 4/18/17.
 * contains if a supplied Extensible Sprite contains a point
 */

public interface SpriteContains {
    /**
     * return true if the sprite contains the point
     * @param sprite
     * @param x
     * @param y
     * @return
     */
    boolean contains(ExtensibleSprite sprite, float x, float y);
}
