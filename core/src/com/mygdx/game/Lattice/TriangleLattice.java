package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.MathU;

/**
 * A lattice of triangles.
 */

public class TriangleLattice extends Lattice {
    float side;
    float sideRt3;


    /**
     * Create triangle lattice with the length for the side of triangles as basic unit of length
     *
     * @param sideLength float, length of the sides of the triangles
     */
    public TriangleLattice(float sideLength){
        super(sideLength);
        side=sideLength;
        sideRt3= sideLength* MathU.rt3;
    }

    /**
     * Set position of center of lowest cell at left. address (0,0). With chaining.
     *
     * @param left float, x-coordinate of cell at bottom left
     * @param bottom float, y-coordinate of cell at bottom left
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
            if (y<x* MathU.rt3){
                i++;
            }
        }
        else  {
            if (y<-x* MathU.rt3){
                i--;
            }
        }
        address.x=i;
        address.y=j;
        return address;
    }

    @Override
    public Vector2 positionOfAddress(Vector2 position, int i, int j) {
        position.x=left+0.5f*side*i;
        position.y=bottom+0.25f*sideRt3*(1+2*j);
        if (((i+j)&1)==0){
            position.y+=0.083333f*sideRt3;
        }
        else {
            position.y-=0.083333f*sideRt3;
        }
        return position;
    }

    //  walking around
    // left and right is trivial

    /**
     * Make a step right in the address.
     *
     * @param address Vector2, to transform
     */
    @Override
    public void stepRight(Vector2 address) {
        address.x++;
    }

    /**
     * Make a step left in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepLeft(Vector2 address) {
        address.x--;
    }

    // up and down always if close to next

    /**
     * Make a step upwards in the address.
     *
     * @param address Vector2, to transform
     */
    @Override
    public void stepUp(Vector2 address) {
        if ((Math.round(address.x+address.y)&1)==0){
            address.y++;
        }
    }

    /**
     * Make a step downwards in the address.
     *
     * @param address Vector2, to transform
     */
    @Override
    public void stepDown(Vector2 address) {
        if ((Math.round(address.x+address.y)&1)==1){
            address.y--;
        }
    }
}
