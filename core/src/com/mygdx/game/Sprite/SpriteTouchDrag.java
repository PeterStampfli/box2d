package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * TouchDrag action
 */

public interface SpriteTouchDrag {

    /**
     * TouchDrag action on the sprite.
     *  @param sprite        ExtensibleSprite
     * @param position      Vector2, position of touch
     * @param deltaPosition Vector2, change in the touch position
     */
    void touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition);
}
