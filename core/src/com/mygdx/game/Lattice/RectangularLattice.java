package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 5/19/17.
 * A simple rectangular lattice
 */

public class RectangularLattice extends Lattice {

    /**
     * Create lattice with given cell size (square lattice)
     *
     * @param size
     */
    public RectangularLattice(float size){
        super(size);
    }

    /**
     * Create lattice with given cell width and height
     *
     * @param width
     * @param height
     */
    public RectangularLattice(float width,float height){
        super(width, height);
    }

    @Override
    public void addressOfPosition(Vector2 address, float x, float y) {
        address.x=Math.round((x-left)/cellWidth);
        address.y=Math.round((y-bottom)/cellHeight);
    }

    @Override
    public void positionOfAddress(Vector2 vector, float i, float j) {
        vector.x=left+cellWidth*i;
        vector.y=bottom+cellHeight*j;
    }

    @Override
    public boolean positionIsInside(float x,float y) {
        return (x>=left)&&(x<=left+cellWidth* addressWidth)&&(y>=bottom)&&(y<=bottom+cellHeight* addressHeight);
    }

    /**
     * Make a step upwards in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */    public void stepUp(Vector2 address) {
        address.y++;
    }

    /**
     * Make a step downwards in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */    public void stepDown(Vector2 address) {
        address.y--;
    }

    /**
     * Make a step right in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepRight(Vector2 address) {
        address.x++;
    }

    /**
     * Make a step left in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepLeft(Vector2 address) {
        address.x--;
    }

    /**
     * Make a step upwards and left in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepUpLeft(Vector2 address) {
        address.x--;
        address.y++;
    }

    /**
     * Make a step left and down in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepDownLeft(Vector2 address) {
        address.x--;
        address.y--;
    }

    /**
     * Make a step up and right in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepUpRight(Vector2 address) {
        address.x++;
        address.y++;
    }

    /**
     * Make a step down and right in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepDownRight(Vector2 address) {
        address.x++;
        address.y--;
    }
}