package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Extension of vector2 to include address vectors of a lattice, superclass of square, triangle and hex lattice
 */

public class LatticeVector extends Vector2 {
    public LatticeData latticeData;
    public boolean isAddress;

    /**
     * Needs the data of the lattice. Creates position(0,0) vector.
     *
     * @param latticeData LatticeData object with important data
     */
    public LatticeVector(LatticeData latticeData){
        super();
        this.latticeData=latticeData;
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
    public LatticeVector get(Vector2 vector2){
        vector2.set(x,y);
        return  this;
    }
}
