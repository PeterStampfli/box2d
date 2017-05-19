package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Extension of vector2 to include address vectors of a lattice, superclass of square, triangle and hex lattice
 */

public class LatticeVector extends Vector2 {
    public Lattice lattice;
    public boolean isAddress;

    /**
     * Needs the data of the lattice. Creates position(0,0) vector.
     *
     * @param lattice Lattice object with important data and specific methods
     */
    public LatticeVector(Lattice lattice){
        super();
        this.lattice=lattice;
        this.isAddress=false;
    }

    /**
     * Set components and that it is an address.
     * @param i
     * @param j
     * @return this, for chaining
     */
    public LatticeVector setAddress(int i,int j){
        set(i,j);
        isAddress=true;
        return this;
    }

    /**
     * Set components and that it is a position.
     * @param x
     * @param y
     * @return this, for chaining
     */
    public LatticeVector setPosition(float x, float y){
        set(x,y);
        isAddress=false;
        return this;
    }

    /**
     * Set components and that it is a position.
     * @param vector2
     * @return this, for chaining
     */
    public LatticeVector setPosition(Vector2 vector2){
        set(vector2);
        isAddress=false;
        return this;
    }

    /**
     * Get the components of this vector to set a Vector2.
     *
     * @param vector2 Vector2, will be set to components of this vector
     * @return this, for chaining
     */
    public LatticeVector put(Vector2 vector2){
        vector2.set(x,y);
        return  this;
    }

    /**
     * Make that a position vector becomes an address vector. Address vector remains unchanged.
     *
     * @return this, for chaining
     */
    public LatticeVector toAddress(){
        if (!isAddress){
            lattice.toAddress(this);
        }
        return  this;
    }

    /**
     * Make that an address vector becomes a position vector. Position vector remains unchanged.
     *
     * @return this, for chaining
     */
     public LatticeVector toPosition(){
         if (isAddress){
             lattice.toPosition(this);
         }
         return this;
     }

    /**
     * Check if position or address is inside lattice part defined by latticeData.
     *
     * @return boolean
     */
     public boolean isInside(){
         return lattice.isInside(this);
     }

    /**
     * Get index to array if position/address is inside area. For usual rectangular array.
     * Critical lattice dependent test in isInside() method call.
     *
     * @return int, index to array, or negative if position/address is not inside the area of matrix.
     */
    public int index(){
        if (isInside()){
            toAddress();
            return Math.round(x+lattice.iWidth*y);
        }
        else {
            return -1;
        }
    }

    /**
     * Change address to go up one step. Converts position to address. May depend on lattice type.
     *
     * @return this, for chaining
     */
    public LatticeVector stepUp(){
        toAddress();
        lattice.stepUp(this);
        return this;
    }
}
