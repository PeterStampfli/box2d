package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;

/**
 * Created by peter on 4/19/17.
 * as an interface, can create reusable static methods of the interface
 */

public interface SpriteKeepVisible {

    /**
     * make that the sprite stays visible
     * to do something needs camera
     * @param sprite
     */
    boolean keepVisible(ExtensibleSprite sprite, Camera camera);
}
