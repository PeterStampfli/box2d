package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Draw method for sprites
 */

public interface SpriteDraw {

    /**
     * Draw the sprite.
     *
     * @param sprite ExtensibleSprite
     * @param batch  Batch
     * @param camera Camera
     */
    public void draw(ExtensibleSprite sprite, Batch batch, Camera camera);
}
