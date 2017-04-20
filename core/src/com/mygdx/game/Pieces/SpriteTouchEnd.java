package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteTouchEnd {

    /**
     * do the touch end draw on the sprite for touch at given position
     * return true if something changed
     * @param sprite
     * @param position
     * @return
     */
    boolean touchEnd(ExtensibleSprite sprite, Vector2 position);
}
