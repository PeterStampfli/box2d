package com.mygdx.game.Sprite;

/**
 * For any effect by a sprite.
 */

public interface SpriteAct {

    /**
     * An action/effect for a sprite. Add it to some method.
     *
     * @param sprite ExtensibleSprite, it gets the effect.
     * @return true, if something changed
     */
    boolean act(ExtensibleSprite sprite);
}
