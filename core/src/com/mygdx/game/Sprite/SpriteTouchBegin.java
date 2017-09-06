package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * TouchBegin action
 */

public interface SpriteTouchBegin {

    /**
     * TouchBegin action on the sprite.
     *  @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     */
    void touchBegin(ExtensibleSprite sprite, Vector2 position);
}
