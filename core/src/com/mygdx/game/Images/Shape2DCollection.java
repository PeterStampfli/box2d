package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;

/**
 * Collection of Shape2D shapes. Is itself a Shape2D object.
 */

public class Shape2DCollection extends Shape2DAdapter {
    public Array<Shape2D> shapes2D = new Array<Shape2D>();

    /**
     * check if one of the shapes contains a given point
     *
     * @param x float, x-coordinate of the point
     * @param y float, y-coordinate of the point
     * @return boolean, true if a shape contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        for (Shape2D shape2D : shapes2D) {
            if (shape2D.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add one or several shapes to the collection. Does nothing if a shape is null.
     *
     * @param shapes2D Shape2D... or Shape2D[], the shapes to add.
     */
    public void add(Shape2D... shapes2D) {
        for (Shape2D shape2D : shapes2D) {
            if (shape2D != null) {
                this.shapes2D.add(shape2D);
            }
        }
    }

    /**
     * Add one or several shapes to the collection. Does nothing if a shape is null.
     *
     * @param shapes2D Array<Shape2D>, the shapes to add.
     */
    public void add(Array<Shape2D> shapes2D) {
        for (Shape2D shape2D : shapes2D) {
            if (shape2D != null) {
                this.shapes2D.add(shape2D);
            }
        }
    }

    /**
     * Clear all shapes. For reuse.
     */
    public void clear(){
        shapes2D.clear();
    }
}
