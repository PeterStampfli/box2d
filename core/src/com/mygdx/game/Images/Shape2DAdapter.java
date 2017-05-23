package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Implements contains method, doing nothing, returning false.
 * Contains(vector) method uses contains(x,y).
 */

public class Shape2DAdapter implements Shape2D {

    /**
     * Implementing the contains-method for components with nothing.
     * @return false, always
     */
    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    /**
     * Implementing the contains method for Vector2 argument by calling the method for components.
     * @param point Vector2, the point
     * @return boolean, true if shape contains the point
     */
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }
}
