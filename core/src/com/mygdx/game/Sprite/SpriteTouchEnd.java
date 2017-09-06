package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * TouchEnd action
 */

public interface SpriteTouchEnd {

    /**
     * TouchEnd action on the sprite.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     * @return boolean, true if something changed
     */
    void touchEnd(ExtensibleSprite sprite, Vector2 position);
}
