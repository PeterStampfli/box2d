package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Defines LatticeVector methods for lattices together with lattice data
 */

public abstract class Lattice {
    public float left;                              // position of lowest left lattice CENTER
    public float bottom;
    public float cellWidth;
    public float cellHeight;
    public int addressWidth, addressHeight;
    public  Vector2 intermediate=new Vector2();

    /**
     * default constructor ...
     */
    public Lattice() {
    }

    /**
     * Create with given size (square lattice)
     *
     * @param size
     */
    public Lattice(float size) {
        setCellSize(size);
    }

    /**
     * Create with given size (square lattice)
     *
     * @param width
     * @param height
     */
    public Lattice(float width,float height) {
        setCellSize(width, height);
    }

    /**
     * Set size of a lattice unit
     *
     * @param width
     * @param height
     * @return
     */
    public Lattice setCellSize(float width,float height) {
        this.cellWidth = width;
        this.cellHeight=height;
        return  this;
    }

    /**
     * Set size of a lattice unit
     *
     * @param size
     * @return
     */
    public Lattice setCellSize(float size) {
        return setCellSize(size,size);
    }

    /**
     * Set maximum indices.
     *
     * @param iWidth
     * @param iHeight
     * @return
     */
    public Lattice setAddressRange(int iWidth, int iHeight) {
        this.addressWidth = iWidth;
        this.addressHeight = iHeight;
        return this;
    }

    /**
     * Set position of center of lowest cell at left. With chaining.
     *
     * @param left
     * @param bottom
     * @return this, for chaining
     */
    public Lattice setLeftBottomCenter(float left, float bottom) {
        this.left = left;
        this.bottom = bottom;
        return this;
    }

    // manipulate LatticeVector objects

    /**
     * Calculate components of address vector with "integer" components
     * fitting given position
     *
     * @param address Vector2, will be determined from position (x,y)
     * @param x float, x-component
     * @param y float, y-component
     * @return the changed address for chaining
     */
    abstract public void addressOfPosition(Vector2 address,float x,float y);

    /**
     * set address depending on position
     *
     * @param address
     * @param vector
     * @return the changed address for chaining
     */
    public void addressOfPosition(Vector2 address,Vector2 vector){
        addressOfPosition(address,vector.x,vector.y);
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
    abstract public void positionOfAddress(Vector2 vector,float i,float j);

    /**
     * set position depending on address
     *
     * @param vector
     * @param address
     * @return Vector2 position for chaining
     */
    public void positionOfAddress(Vector2 vector,Vector2 address){
        positionOfAddress(vector,address.x,address.y);
    }

    /**
     * Adjusts position to lattice (centers)
     *
     * @param vector Vector2, position vector to adjust
     * @return Vector2, adjusted vector, for chaining
     */
    public Vector2 adjust(Vector2 vector){
        addressOfPosition(intermediate,vector);
        positionOfAddress(vector,intermediate);
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
     * Simple default implementation for rectangular array.
     *
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return boolean
     */
    public boolean addressIsInside(float i, float j) {
        return (i>=0)&&(i< addressWidth)&&(j>=0)&&(j< addressHeight);
    }

    /**
     * Check if address is inside lattice part defined by latticeData.
     *
     * @param address Address, to check
     * @return boolean
     */
    public boolean addressIsInside(Vector2 address){
        return addressIsInside(address.x,address.y);
    }
}
