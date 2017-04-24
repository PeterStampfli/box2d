package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/19/17.
 */

public interface SpriteTouchDrag {

    /**
     * do the touch drag draw on the sprite for touch at given position
     * return true if something changed
     * @param sprite
     * @param position
     * @param deltaPosition
     * @param camera
     * @return
     */
    boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition, Camera camera);
}
