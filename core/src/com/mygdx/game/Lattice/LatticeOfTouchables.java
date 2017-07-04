package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.Pieces.Touchables;

/**
 * Created by peter on 7/2/17.
 */

public class LatticeOfTouchables<T extends Touchable> extends Touchables<T> {

    public int width,height;
    public Lattice lattice;
    private Vector2 address=new Vector2();

    /**
     * create for given size
     *
     * @param w
     * @param h
     */
    public LatticeOfTouchables (Lattice lattice,int w,int h){
        setLattice(lattice);
        resize(w, h);
    }

    /**
     * Make that items array has enough space for given 2d width and height, set width and height.
     * Clear items and set size.
     * @param w
     * @param h
     */
    public LatticeOfTouchables resize(int w,int h){
        int newSize=w*h;
        width=w;
        height=h;
        items.setSize(newSize);
        setNull();
        return this;
    }

    /**
     * set the lattice. sets width and height to zero.
     * @param lattice
     */
    public void setLattice(Lattice lattice) {
        this.lattice = lattice;
        resize(0,0);
    }

    // check if indizes are inside, throw exception with message
    	//	if (index >= size) throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + size);
    public void checkIndices(int i,int j){
        if ((i<0)||(i>=width)||(j<0)||(j>=height)) {
            throw new IndexOutOfBoundsException("indices ("+i+","+j+") out of range! Width "+width+", height "+height);
        }
    }

    // how to get or set single elements

    /**
     * set item with indices i,j. Throws exception if not in range.
     * @param i
     * @param j
     * @param t
     */
    public void setAtAddress(int i,int j,T t){
        checkIndices(i, j);
        items.set(j*width+i,t);
    }

    /**
     * Set element with given address vector.
     * @param address
     * @param t
     */
    public void setAtAddress(Vector2 address,T t){
        setAtAddress(Math.round(address.x),Math.round(address.y),t);
    }

    /**
     * Set element with address of given position.
     * @param x
     * @param y
     * @param t
     */
    public void setAtPosition(float x,float y,T t){
        setAtAddress(lattice.addressOfPosition(address,x,y),t);
    }

    /**
     * Set element with address of given position vector.
     * @param position
     * @param t
     */
    public void setAtPosition(Vector2 position,T t){
        setAtAddress(lattice.addressOfPosition(address,position),t);
    }

    /**
     * Get item with indices i,j. Throws exception if not in range.
     * @param i
     * @param j
     */
    public T getAtAddress(int i,int j){
        checkIndices(i, j);
        return items.get(j*width+i);
    }

    /**
     * Get element with given address vector.
     * @param address
     */
    public T getAtAddress(Vector2 address){
        return getAtAddress(Math.round(address.x),Math.round(address.y));
    }

    /**
     * Get element with address of given position.
     * @param x
     * @param y
     */
    public T getAtPosition(float x,float y){
        return getAtAddress(lattice.addressOfPosition(address,x,y));
    }

    /**
     * Get element with address of given position vector.
     * @param position
     */
    public T setAtPosition(Vector2 position){
        return getAtAddress(lattice.addressOfPosition(address,position));
    }

    // iteration

    /**
     * Create all item elements independent of address
     *
     * @param creation with a create method for objects of class T
     */
    public void create(Creation<T> creation) {
        for (int i = items.size - 1; i >= 0; i--) {
            items.set(i, creation.create());
        }
    }

    /**
     * Create all item elements depending on their (i,j) address.
     *
     * @param creationIJ
     */
    public void create(CreationIJ<T> creationIJ){
        int i,j;
        int index=0;
        for (j=0;j<height;j++){
            for (i=0;i<width;i++){
                items.set(index++,creationIJ.create(i,j));
            }
        }
    }

    /**
     * Act/transform all item elements independent of address
     *
     * @param action with an act method for objects of class T
     */
    public void act(Action<T> action) {
        for (T t:items) {
            action.act(t);
        }
    }

    /**
     * Act/transform all item elements depending on their (i,j) address.
     *
     * @param actionIJ
     */
    public void act(ActionIJ<T> actionIJ){
        int i,j;
        int index=0;
        for (j=0;j<height;j++){
            for (i=0;i<width;i++){
                actionIJ.act(items.get(index++),i,j);
            }
        }
    }

    // set elements of one latticeOfTouchables depending on another one

    public void transform(Transformation<T,? extends Touchable> transformation,LatticeOfTouchables<? extends Touchable> latticeOfTouchables){

    }
}
