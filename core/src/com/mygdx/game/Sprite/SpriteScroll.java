package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * sSroll action. Only for pc.
 */

public interface SpriteScroll {

    /**
     * Scroll action on the sprite.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of the mouse (PC)
     * @param amount   int, (+/-) 1, depending on scrolling up or down
     * @return boolean, true if something changed
     */
    boolean scroll(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position, int amount);
}
