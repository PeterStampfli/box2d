package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/10/17.
 * graphics shape2D that parallels the box2d edgeShape
 */

public class Edge implements Shape2D {

    Vector2 ghostA,a,b,ghostB;

    /**
     * edge without data to create for reuse
     */
    public Edge(){}

    // other creators

    /**
     * null everything for reuse
     */
    public void clear(){
        ghostA=null;
        a=null;
        b=null;
        ghostB=null
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
