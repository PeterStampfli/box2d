package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteDraw {

    /**
     * draw the sprite, with extras
     * @param sprite
     */
    public void draw(com.mygdx.game.Sprite.ExtensibleSprite sprite, Batch batch);
}
