package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Objects that can draw themselves, tell if they contain a point and implement touch and scroll methods.
 */

public interface Touchable extends Shape2D {

    /**
     * Returns whether a point with the given coordinates is contained within the shape.
     */
    boolean contains(float x, float y);

    /**
     * Returns whether the given point is contained within the shape.
     */
    boolean contains(Vector2 point);

    /**
     * Draws itself
     *
     * @param batch  SpriteBatch, of the Device
     * @param camera Camera, current in use. To decide of sprite is visible.
     */
    void draw(Batch batch, Camera camera);

    /**
     * make that the sprite stays visible.
     *
     * @param camera Camera, current in use. To decide of sprite is visible and to determine repositioning of the sprite.
     * @return boolean, true if something changed and redraw needed.
     */
    boolean keepVisible(Camera camera);

    /**
     * Action for touch begin.
     *
     * @param position Vector2, position of touch.
     * @return boolean, true if something changed and redraw needed.
     */
    boolean touchBegin(Vector2 position);

    /**
     * action for touch drag.
     *
     * @param position      Vector2, position of touch.
     * @param deltaPosition Vector2, change in the position of touch.
     * @param camera        Camera current in use. For keeping the sprite visible.
     * @return boolean, true if something changed and redraw needed.
     */
    boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera);

    /**
     * Action for touch end.
     *
     * @param position Vector2, position of touch.
     * @return boolean, true if something changed and redraw needed..
     */
    boolean touchEnd(Vector2 position);

    /**
     * Action for turning the scroll wheel. Test if touch position is inside the sprite. Then maybe do something.
     *
     * @param position Vector2, position of touch.
     * @param amount   int, tells if the wheel turns up or down.
     * @return boolean, true if position of touch is inside the sprite. Something may have changed and redraw needed.
     */
    boolean scroll(Vector2 position, int amount);
}
