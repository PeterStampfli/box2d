package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteDraw {

    /**
     * draw the sprite, with extras
     * @param sprite
     * @param camera
     */
    public void draw(ExtensibleSprite sprite, Batch batch, Camera camera);
}
