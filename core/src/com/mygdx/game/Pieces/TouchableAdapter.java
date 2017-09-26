package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Vector2;

/**
 * Implementing all touchable methods by defaults
 */

public class TouchableAdapter implements Touchable {
    @Override
    public void keepVisible() {

    }

    @Override
    public void touchBegin(Vector2 position) {

    }

    @Override
    public void touchDrag(Vector2 position, Vector2 deltaPosition) {

    }

    @Override
    public void touchEnd() {

    }

    @Override
    public void scroll(Vector2 position, int amount) {

    }

    @Override
    public boolean contains(Vector2 point) {
        return false;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }
}
