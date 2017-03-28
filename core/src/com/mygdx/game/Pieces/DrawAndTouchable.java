package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 3/28/17.
 */

public interface DrawAndTouchable{

    // object can draw with a batch
    public void draw(Batch batch);

    // return true if element is selected by given position
    boolean contains(Vector2 position);

    // begin-touch action, return true if something changed, call requestRendering, this is safer
    boolean touchBegin(Vector2 position);

    // do drag action, return true if something changed
    boolean touchDrag(Vector2 position,Vector2 deltaPosition);

    // end of touch
    boolean touchEnd();
}
