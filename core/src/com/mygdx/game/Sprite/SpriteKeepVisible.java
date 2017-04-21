package com.mygdx.game.Sprite;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteKeepVisible {

    /**
     * make that the sprite stays visible
     * to do something needs camera
     * @param sprite
     */
    boolean keepVisible(com.mygdx.game.Sprite.ExtensibleSprite sprite);
}
