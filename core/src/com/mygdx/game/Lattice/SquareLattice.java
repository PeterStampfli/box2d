package com.mygdx.game.Lattice;

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
    public void toAddress(LatticeVector vector) {
        vector.x = Math.round((vector.x - left) / size);
        vector.y = Math.round((vector.y - bottom) / size);
    }

    @Override
    public void toPosition(LatticeVector vector) {
        vector.x = left + vector.x * size;
        vector.y = bottom + vector.y * size;
    }

    @Override
    public boolean isInside(LatticeVector vector) {
        if (vector.isAddress) {
            return (vector.x >= 0) && (vector.x < iWidth) && (vector.y >= 0) && (vector.y < iHeight);
        }
        return (vector.x >= left) && (vector.x <= left + size * iWidth)
                && (vector.y >= bottom) && (vector.y <= bottom + size * iHeight);
    }

    @Override
    public void stepUp(LatticeVector vector) {
        vector.y++;
    }

    @Override
    public void stepDown(LatticeVector vector) {
        vector.y--;
    }

    @Override
    public void stepRight(LatticeVector vector) {
        vector.x++;
    }

    @Override
    public void stepLeft(LatticeVector vector) {
        vector.x--;
    }

    @Override
    public void stepUpLeft(LatticeVector vector) {
        vector.x--;
        vector.y++;
    }

    @Override
    public void stepDownLeft(LatticeVector vector) {
        vector.x--;
        vector.y--;
    }

    @Override
    public void stepUpRight(LatticeVector vector) {
        vector.x++;
        vector.y++;
    }

    @Override
    public void stepDownRight(LatticeVector vector) {
        vector.x++;
        vector.y--;
    }
}
