package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Collection;

/**
 * Collection of Shape2D shapes. Is itself a Shape2D object.
 */

public class Shape2DCollection extends Collection<Shape2D> implements Shape2D {

    /**
     * check if one of the shapes contains a given point
     *
     * @param x float, x-coordinate of the point
     * @param y float, y-coordinate of the point
     * @return boolean, true if a shape contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        for (Shape2D shape2D : items) {
            if (shape2D.contains(x, y)) {
                return true;
            }
        }
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
