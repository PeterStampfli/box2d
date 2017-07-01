package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Basic;

/**
 * Created by peter on 6/29/17.
 * A lattice of triangles.
 */

public class TriangleLattice extends Lattice {
    float side;
    float sideRt3;


    /**
     * Create triangle lattice with length for side of triangles
     *
     * @param sideLength
     */
    public TriangleLattice(float sideLength){
        super(sideLength);
        side=sideLength;
        sideRt3=(float) (sideLength* Basic.rt3);
    }

    /**
     * Set position of center of lowest cell at left. With chaining.
     *
     * @param left
     * @param bottom
     * @return this, for chaining
     */
    @Override
    public Lattice setLeftBottomCenter(float left, float bottom) {
        this.left = left;
        this.bottom = bottom-sideRt3/3.0f;
        return this;
    }

    @Override
    public Vector2 addressOfPosition(Vector2 address, float x, float y) {
        x-=left;
        y-=bottom;
        int i=Math.round(x/side);
        int j=Math.round(y/sideRt3);
        x-=i*side;
        y-=j*sideRt3;
        // address of triangle at (0,side/sqrt(3))
        i*=2;
        j*=2;
        // below: correct address j and mirror at x-axis
        if (y<0){
            j--;
            y=-y;
        }
        // at left or right: correct address i
        if (x>0){
            if (y<x*Basic.rt3){
                i++;
            }
        }
        else  {
            if (y<-x*Basic.rt3){
                i--;
            }
        }
        address.x=i;
        address.y=j;
        return address;
    }

    @Override
    public Vector2 positionOfAddress(Vector2 position, float i, float j) {
        position.x=left+0.5f*side*i;
        position.y=bottom+0.25f*sideRt3*(1+2*j);
        if ((Math.round(i+j)&1)==0){
            position.y+=0.083333f*sideRt3;
        }
        else {
            position.y-=0.083333f*sideRt3;
        }
        return position;
    }
}
