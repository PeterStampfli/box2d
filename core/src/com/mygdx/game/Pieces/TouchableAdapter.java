package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/8/17.
 * A base class with empty methods implementing touchable
 * (use if we do not want TouchableSprite
 */

public class TouchableAdapter implements Touchable {
    @Override
    public void draw(Batch batch) {

    }

    @Override
    public void keepVisible(Camera camera) {

    }

    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    @Override
    public boolean touchBegin(Vector2 position) {
        return false;
    }

    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition) {
        return false;
    }

    @Override
    public boolean touchEnd() {
        return false;
    }

}
