package com.mygdx.game.Lattice;

/**
 * Defines LatticeVector methods for lattices together with lattice data
 */

public abstract class Lattice {
    public float left;
    public float bottom;
    public float size;
    public int iWidth, iHeight;

    /**
     * default constructor ...
     */
    public Lattice() {
    }

    /**
     * Create with given size
     *
     * @param size
     */
    public Lattice(float size) {
        this.size = size;
    }

    /**
     * Set size of a lattice unit
     *
     * @param size
     * @return this, for chaining
     */
    public Lattice setSize(float size) {
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
    public Lattice setWidthHeight(int iWidth, int iHeight) {
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
    public Lattice setLeftBottom(float left, float bottom) {
        this.left = left;
        this.bottom = bottom;
        return this;
    }

    // manipulate LatticeVector objects

    /**
     * Calculate i-component of address vector fitting given position
     *
     * @param x float, x-component
     * @param y float, y-component
     */
    abstract public int getI(float x,float y);

    /**
     * Calculate j-component of address vector fitting given position
     *
     * @param x float, x-component
     * @param y float, y-component
     */
    abstract public int getJ(float x,float y);

    /**
     * Calculate the x-component of the position fitting the address.
     * The center of the cell.
     *
     * @param address
     */
    abstract public float getX(Address address);

    /**
     * Calculate the y-component of the position fitting the address.
     * The center of the cell.
     *
     * @param address
     */
    abstract public float getY(Address address);

    /**
     * Check if position is inside lattice part defined by latticeData.
     *
     * @param x float, x-component of position
     * @param y float, y-component of position
     * @return boolean
     */
    abstract public boolean isInside(float x,float y);

    /**
     * Check if address is inside lattice part defined by latticeData.
     *
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return boolean
     */
    abstract public boolean isInside(int i, int j);

    /**
     * Make a step upwards in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepUp(Address address);

    /**
     * Make a step downwards in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepDown(Address address);

    /**
     * Make a step right in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepRight(Address address);

    /**
     * Make a step left in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepLeft(Address address);

    /**
     * Make a step upwards and left in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepUpLeft(Address address);

    /**
     * Make a step left and down in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepDownLeft(Address address);

    /**
     * Make a step up and right in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepUpRight(Address address);

    /**
     * Make a step down and right in the address.
     *
     * @param address Address, to transform
     * @return this, for chaining
     */
    abstract public void stepDownRight(Address address);

}
