package com.mygdx.game.Sprite;

/**
 * scroll action. Only for pc.
 */

public interface SpriteScroll {

    /**
     * Scroll action on the sprite.
     *  @param sprite   ExtensibleSprite
     * @param amount   int, (+/-) 1, depending on scrolling up or down
     */
    void scroll(ExtensibleSprite sprite, int amount);
}
