package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 5/19/17.
 * A simple square lattice
 */

public class SquareLattice extends Lattice {

    /**
     * Create with given size
     *
     * @param size
     */
    public SquareLattice(float size){
        super(size);
    }

    /**
     * Set size of a lattice unit
     *
     * @param size
     * @return this, for chaining
     */
    public SquareLattice setSize(float size) {
        this.size = size;
        return this;
    }

    /**
     * Set maximum indices.
     *
     * @param iWidth
     * @param iHeight
     * @return
     */
    public SquareLattice setWidthHeight(int iWidth, int iHeight) {
        this.iWidth = iWidth;
        this.iHeight = iHeight;
        return this;
    }

    /**
     * Set position of bottom left corner. With chaining.
     *
     * @param left
     * @param bottom
     * @return this, for chaining
     */
    public SquareLattice setLeftBottom(float left, float bottom) {
        this.left = left;
        this.bottom = bottom;
        return this;
    }

    @Override
    public Address addressOfPosition(Address address, float x, float y) {
        address.i=(int)Math.floor((x-left)/size);
        address.j=(int)Math.floor((y-bottom)/size);
        return address;
    }

    @Override
    public Vector2 positionOfAddress(Vector2 vector, int i, int j) {
        vector.x=left+size*(0.5f+address.i);
        vector.y=bottom+size*(0.5f+address.j);
        return vector;
    }

    @Override
    public boolean positionIsInside(float x,float y) {
        return (x>=left)&&(x<=left+size*iWidth)&&(y>=bottom)&&(y<=bottom+size*iHeight);
    }

    @Override
    public boolean addressIsInside(int i, int j) {
        return (i>=0)&&(i<iWidth)&&(j>=0)&&(j<iHeight);
    }

    @Override
    public void stepUp(Address address) {
        address.j++;
    }

    @Override
    public void stepDown(Address address) {
        address.j--;
    }

    @Override
    public void stepRight(Address address) {
        address.i++;
    }

    @Override
    public void stepLeft(Address address) {
        address.i--;
    }

    @Override
    public void stepUpLeft(Address address) {
        address.i--;
        address.j++;
    }

    @Override
    public void stepDownLeft(Address address) {
        address.i--;
        address.j--;
    }

    @Override
    public void stepUpRight(Address address) {
        address.i++;
        address.j++;
    }

    @Override
    public void stepDownRight(Address address) {
        address.i++;
        address.j--;
    }
}
