package com.mygdx.game.Lattice;

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
     * @param x
     * @param y
     * @return
     */
    public Address fromPosition(float x,float y){
        this.i=lattice.getI(x, y);
        this.j=lattice.getJ(x, y);
        return this;
    }


}
