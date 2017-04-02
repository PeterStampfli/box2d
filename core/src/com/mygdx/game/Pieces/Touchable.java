package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 3/28/17.
 */

public interface Touchable extends Drawable,Shape2D {

    // begin-touch action, return true if something changed, call requestRendering, this is safer
    boolean touchBegin(Vector2 position);

    // do drag action, return true if something changed
    boolean touchDrag(Vector2 position,Vector2 deltaPosition);

    // end of touch
    boolean touchEnd();
}
