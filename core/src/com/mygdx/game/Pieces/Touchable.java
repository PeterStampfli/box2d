package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 3/28/17.
 *
 * Objects that can draw themselves, tell if they contain a point and can be touched
 */

public interface Touchable extends Shape2D {

    // object can draw with a batch
    void draw(Batch batch);

    // has to be able to remain visible (knows its camera), return true if something changes (and redraw needed)
    boolean keepVisible();

    // begin-touch draw, return true if something changed, call requestRendering, this is safer
    // return true if something happened
    boolean touchBegin(Vector2 position);

    // do drag draw, return true if something changed
    // return true if something happened
    boolean touchDrag(Vector2 position,Vector2 deltaPosition);

    // end of touch
    // return true if something happened
    boolean touchEnd(Vector2 position);

    // scrolling, for pc
    // return true if test point and do scroll (presumably something happened ...)
    boolean scroll(Vector2 position, int amount);
}
