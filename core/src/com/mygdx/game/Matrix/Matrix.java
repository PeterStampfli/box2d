package com.mygdx.game.Matrix;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.TouchableCollection;

/**
 * has objects in matrix. Set and get specific objects, iterate. Create, act and transform.
 * Use lattice object to find address of a selected item.
 */

public class Matrix<T> extends TouchableCollection<T> {
    private int width,height;

    /**
     * create for given size
     *
     * @param w int, number of columns
     * @param h int, number of rows
     */
    public Matrix(int w, int h){
        super();
        resize(w, h);
    }

    /**
     * create (default size), resize later
     *
     */
    public Matrix(){
        this(1, 1);
    }

    /**
     * set all array elements to null.
     */
    public void setNull(){
        for (int i = items.size - 1; i >= 0; i--) {
            items.set(i,null);
        }
    }

    /**
     * Make that items array has enough space for given 2d width and height, set width and height.
     * Clear items and set size.
     *
     * @param w int, number of columns
     * @param h int, number of rows
     * @return this Matrix, for chaining
     */
    public Matrix resize(int w, int h){
        int newSize=w*h;
        width=w;
        height=h;
        items.setSize(newSize);
        setNull();
        return this;
    }

    /**
     * check if indices are inside, throw exception with message
     *
     * @param i int, column index
     * @param j int, row index
     */
    public void checkIndices(int i,int j){
        if ((i<0)||(i>=width)||(j<0)||(j>=height)) {
            throw new IndexOutOfBoundsException("indices ("+i+","+j+") out of range! Width "+width+", height "+height);
        }
    }


    /**
     * Compare matrix dimensions with another matrix, throw exception if not equal.
     *
     * @param other Matrix, to compare with
     */
    public void checkDimensions(Matrix other){
        if ((width!=other.width)||(height!=other.height)){
            throw new RuntimeException("matrix dimensions do not match : width, height "+width
                    +", "+height+" other: "+other.width+", "+other.height);
        }
    }

    // how to get or set single elements

    /**
     * set item with indices i,j. Throws exception if not in range.
     *
     * @param i int, column index
     * @param j int, row index
     * @param t object of type T
     */
    public void set(int i, int j, T t){
        checkIndices(i, j);
        items.set(j*width+i,t);
    }

    /**
     * Set element with given address vector.
     *
     * @param address Vector2 or LatticeVector, address
     * @param t Object of type T
     */
    public void set(Vector2 address, T t){
        set(Math.round(address.x),Math.round(address.y),t);
    }

    /**
     * Get item with indices i,j. Throws exception if not in range.
     *
     * @param i int, column index
     * @param j int, row index
     * @return element of type T
     */
    public T get(int i, int j){
        checkIndices(i, j);
        return items.get(j*width+i);
    }

    /**
     * Get element with given address vector.
     *
     * @param address Vector2 or LatticeVector, address
     * @return element of type T
     */
    public T get(Vector2 address){
        return get(Math.round(address.x),Math.round(address.y));
    }

    // iteration

    /**
     * Create all item elements independent of address
     *
     * @param creation object with a create method for objects of class T
     */
    public void create(Create<T> creation) {
        for (int i = items.size - 1; i >= 0; i--) {
            items.set(i, creation.create());
        }
    }

    /**
     * Create all item elements depending on their (i,j) address.
     *
     * @param creationIJ object with a create method for objects of type T, depending on indices
     */
    public void create(CreateIJ<T> creationIJ){
        int i,j;
        int index=0;
        for (j=0;j<height;j++){
            for (i=0;i<width;i++){
                items.set(index++,creationIJ.create(i,j));
            }
        }
    }

    /**
     * Act/transform all item elements independent of address. only non-null elements.
     * Only changes values of object fields.
     *
     * @param action with an act method for objects of class T
     */
    public void act(Act<T> action) {
        for (T t:items) {
            if (t!=null){
                action.act(t);
            }
        }
    }

    /**
     * Act/transform all item elements depending on their (i,j) address.
     *
     * @param actionIJ with an act method for objects of class T, depending on indices
     */
    public void act(ActIJ<T> actionIJ){
        int i,j;
        int index=0;
        T t;
        for (j=0;j<height;j++){
            for (i=0;i<width;i++){
                t=items.get(index++);
                if (t!=null) {
                    actionIJ.act(i,j,t);
                }
            }
        }
    }

    /**
     * set elements of one matrix depending on the elements of another one. independent of address.
     *
     * @param transformation Transform object with U to T transform method
     * @param matrix Matrix of same dimensions with objects of type U
     * @param <U> Type of input objects for the transformation
     */
    public <U> void transform(Transform<T,U> transformation,
                                                Matrix<U> matrix){
        checkDimensions(matrix);
        U u;
        for (int index=items.size-1;index>=0;index--){
            u= matrix.items.get(index);
            if (u!=null) {
                items.set(index,transformation.transform(u));
            }
        }
    }

    /**
     * set elements of one matrix depending on the elements of another one and of address.
     *
     * @param transformation TransformIJ object with U to T transform method depending on indices
     * @param matrix Matrix of same dimensions with objects of type U
     * @param <U> Type of input objects for the transformation
     */
    public <U> void transformIJ(TransformIJ<T,U> transformation,
                                                Matrix<U> matrix){
        checkDimensions(matrix);
        U u;
        int i,j;
        int index=0;
        for (j=0;j<height;j++){
            for (i=0;i<width;i++){
                u= matrix.items.get(index);
                if (u!=null) {
                    items.set(index,transformation.transform(i,j,u));
                }
                index++;
            }
        }
    }
}
