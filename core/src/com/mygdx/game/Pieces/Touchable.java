package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Objects that can draw themselves, tell if they contain a point and implement touch and scroll methods.
 */

public interface Touchable extends Shape2D {

    /**
     * make that the sprite stays visible.
     *
     */
    void keepVisible();

    /**
     *
     * Action for touch begin.
     *
     * @param position Vector2, position of touch.
     */
    void touchBegin(Vector2 position);

    /**
     * action for touch drag.
     *  @param position      Vector2, position of touch.
     * @param deltaPosition Vector2, change in the position of touch.
     */
    void touchDrag(Vector2 position, Vector2 deltaPosition);

    /**
     * Action for touch end.
     *
     */
    void touchEnd();

    /**
     * Action for turning the scroll wheel. Test if touch position is inside the sprite. Then maybe do something.
     *  @param position Vector2, position of touch.
     * @param amount   int, tells if the wheel turns up or down.
     */
    void scroll(Vector2 position, int amount);
}
