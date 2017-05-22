package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Defines LatticeVector methods for lattices together with lattice data
 */

public abstract class Lattice {
    public float left;
    public float bottom;
    public float size;
    public int iWidth, iHeight;
    public  Address address;

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
     * Calculate components of address vector fitting given position
     *
     * @param address Address, will be determined from position (x,y)
     * @param x float, x-component
     * @param y float, y-component
     * @return the changed address for chaining
     */
    abstract public Address addressOfPosition(Address address,float x,float y);

    /**
     * set address depending on position
     *
     * @param address
     * @param vector
     * @return the changed address for chaining
     */
    public Address addressOfPosition(Address address,Vector2 vector){
        return addressOfPosition(address,vector.x,vector.y);
    }

    /**
     * Calculate components of the position fitting the address.
     * The center of the cell.
     *
     * @param vector will be set to position corresponding to address
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return Vector2 position for chaining
     */
    abstract public Vector2 positionOfAddress(Vector2 vector,int i,int j);

    /**
     * set position depending on address
     *
     * @param vector
     * @param address
     * @return Vector2 position for chaining
     */
    public Vector2 positionOfAddress(Vector2 vector,Address address){
        return positionOfAddress(vector,address.i,address.j);
    }

    /**
     * Adjusts position to lattice (centers)
     *
     * @param vector Vector2, position vector to adjust
     * @return Vector2, adjusted vector, for chaining
     */
    public Vector2 adjust(Vector2 vector){
        addressOfPosition(address,vector);
        positionOfAddress(vector,address);
        return vector;
    }

    /**
     * Check if position is inside lattice part defined by latticeData.
     *
     * @param x float, x-component of position
     * @param y float, y-component of position
     * @return boolean
     */
    abstract public boolean positionIsInside(float x,float y);

    /**
     * Check if position is inside lattice part defined by latticeData.
     *
     * @param vector Vector2, to check
     * @return boolean
     */
    public boolean positionIsInside(Vector2 vector){
        return positionIsInside(vector.x,vector.y);
    }

    /**
     * Check if address is inside lattice part defined by latticeData.
     *
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return boolean
     */
    abstract public boolean addressIsInside(int i, int j);

    /**
     * Check if address is inside lattice part defined by latticeData.
     *
     * @param address Address, to check
     * @return boolean
     */
    public boolean addressIsInside(Address address){
        return addressIsInside(address.i,address.j);
    }

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
