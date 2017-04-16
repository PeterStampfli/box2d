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

    // has to be able to remain visible (knows its camera
    void keepVisible();

    // begin-touch action, return true if something changed, call requestRendering, this is safer
    boolean touchBegin(Vector2 position);

    // do drag action, return true if something changed
    boolean touchDrag(Vector2 position,Vector2 deltaPosition);

    // end of touch
    boolean touchEnd();
}
