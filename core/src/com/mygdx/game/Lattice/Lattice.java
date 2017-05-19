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
    public Lattice(){}

    /**
     * Create with given size-
     *
     * @param size
     */
    public Lattice(float size){
        this.size=size;
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
     * Make that a position vector becomes an address vector.
     *
     * @param vector LatticeVector to transform
     * @return this, for chaining
     */
    abstract public void toAddress(LatticeVector vector);

    /**
     * Make that an address vector becomes a position vector.
     *
     * @param vector LatticeVector to transform
     * @return this, for chaining
     */
    abstract public void toPosition(LatticeVector vector);

    /**
     * Check if position or address is inside lattice part defined by latticeData.
     * @param vector LatticeVector to check
     * @return boolean
     */
    abstract public boolean isInside(LatticeVector vector);

    /**
     * Make a step upwards in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepUp(LatticeVector vector);

    /**
     * Make a step downwards in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepDown(LatticeVector vector);

    /**
     * Make a step right in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepRight(LatticeVector vector);

    /**
     * Make a step left in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepLeft(LatticeVector vector);

    /**
     * Make a step upwards and left in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepUpLeft(LatticeVector vector);

    /**
     * Make a step left and down in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepDownLeft(LatticeVector vector);

    /**
     * Make a step up and right in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepUpRight(LatticeVector vector);

    /**
     * Make a step down and right in the address. Caller makes that it is an address.
     *
     * @param vector LatticeVector to transform, is an address
     * @return this, for chaining
     */
    abstract public void stepDownRight(LatticeVector vector);

}
