package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteTouchBegin {

    /**
     * do the touch begin draw on the sprite for touch at given position
     * return true if something changed
     * @param sprite
     * @param position
     * @return
     */
    boolean touchBegin(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position);
}
