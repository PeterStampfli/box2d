package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 5/20/17.
 */

public class Address {
    public int i,j;
    public Lattice lattice;

    /**
     * Needs the data of the lattice. Creates address(0,0) vector.
     *
     * @param lattice Lattice object with important data and specific methods
     */
    public Address(Lattice lattice){
        this.lattice=lattice;
    }

    @Override
    public String toString(){
            return "Address ("+i+", "+j+") ";
    }

    /**
     * set components
     * @param i
     * @param j
     * @return this,for chaining
     */
    public Address set(int i,int j){
        this.i=i;
        this.j=j;
        return this;
    }

    /**
     * set Address from position components and given lattice.
     *
     * @param x
     * @param y
     * @return
     */
    public Address fromPosition(float x,float y){
        this.i=lattice.getI(x, y);
        this.j=lattice.getJ(x, y);
        return this;
    }

    /**
     * set address from position vector and given lattice
     *
     * @param vector
     * @return
     */
    public Address fromPosition(Vector2 vector){
        fromPosition(vector.x,vector.y);
        return this;
    }

    /**
     * get x-component of vector to this address
     */
    public float getX(){
        return lattice.getX(this);
    }

    /**
     * get y-component of vector to this address
     */
    public float getY(){
        return lattice.getY(this);
    }

    /**
     * set position vector depending on this address and given lattice
     * @param vector
     * @return
     */
    public Address toPosition(Vector2 vector){
        vector.x=lattice.getX(this);
        vector.y=lattice.getY(this);
        return this;
    }

    /**
     * Check if the address is inside the lattice
     *
     * @return
     */
    public boolean isInside(){
        return lattice.isInside(this.i,this.j);
    }

    /**
     * Make a step upwards in the lattice
     *
     * @return
     */
    public Address stepUp(){
        lattice.stepUp(this);
        return this;
    }

    /**
     * Make a step down in the lattice
     *
     * @return
     */
    public Address stepDown(){
        lattice.stepDown(this);
        return this;
    }

    /**
     * Make a step right in the lattice
     *
     * @return
     */
    public Address stepRight(){
        lattice.stepRight(this);
        return this;
    }

    /**
     * Make a step left in the lattice
     *
     * @return
     */
    public Address stepLeft(){
        lattice.stepLeft(this);
        return this;
    }

    /**
     * Make a step up and right in the lattice
     *
     * @return
     */
    public Address stepUpRight(){
        lattice.stepUpRight(this);
        return this;
    }
}
