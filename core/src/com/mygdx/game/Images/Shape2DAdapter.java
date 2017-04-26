package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Implements contains method, returning false.
 */

public class Shape2DAdapter implements Shape2D {
    @Override
    public boolean contains(Vector2 point) {
        return false;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }
}
