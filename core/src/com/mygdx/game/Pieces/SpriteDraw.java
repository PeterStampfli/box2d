package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteDraw {

    /**
     * draw the sprite, with extras
     * @param sprite
     */
    public void draw(ExtensibleSprite sprite, Batch batch);
}
