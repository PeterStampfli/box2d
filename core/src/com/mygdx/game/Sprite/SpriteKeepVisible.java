package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;

/**
 * A method to keep the sprite visible.
 */

public interface SpriteKeepVisible {

    /**
     * Makes that the sprite stays visible. Needs a camera.
     *
     * @param sprite ExtensibleSprite
     * @param camera Camera, used for looking at sprite
     */
    boolean keepVisible(ExtensibleSprite sprite, Camera camera);
}
