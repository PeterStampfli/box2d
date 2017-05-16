package com.mygdx.game.Lattice;

/**
 * Holds data for a lattice (Square, hexagonal or triangular)
 */

public class LatticeData {
    public float left;
    public float bottom;
    public float size;
    public int iWidth, iHeight;

    /**
     * Set size of a lattice unit
     *
     * @param size
     * @return this, for chaining
     */
    public LatticeData setSize(float size) {
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
    public LatticeData setWidthHeight(int iWidth, int iHeight) {
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
    public LatticeData setLeftBottom(float left, float bottom) {
        this.left = left;
        this.bottom = bottom;
        return this;
    }
}
