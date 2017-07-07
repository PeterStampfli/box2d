package com.mygdx.game.Matrix;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.Pieces.TouchableCollection;

/**
 * has objects in matrix. Set and get specific objects, iterate. Create, act and transform.
 * Reference to items can be exported to collection of touchables.
 */

public class Matrix<T> {

    private int width,height;
    public Array<T> items=new Array<T>();

    /**
     * create for given size
     *
     * @param w
     * @param h
     */
    public Matrix(int w, int h){
        resize(w, h);
    }

    /**
     * Wrap a new touchableCollection around the matrix items.
     *
     * @return
     */
    public TouchableCollection<T> createTouchableCollection(){
        return new TouchableCollection<T>(items,true);
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
     * @param w
     * @param h
     */
    public Matrix resize(int w, int h){
        int newSize=w*h;
        width=w;
        height=h;
        items.setSize(newSize);
        setNull();
        return this;
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
    public void set(int i, int j, T t){
        checkIndices(i, j);
        items.set(j*width+i,t);
    }

    /**
     * Set element with given address vector.
     * @param address
     * @param t
     */
    public void set(Vector2 address, T t){
        set(Math.round(address.x),Math.round(address.y),t);
    }

    /**
     * Get item with indices i,j. Throws exception if not in range.
     * @param i
     * @param j
     */
    public T get(int i, int j){
        checkIndices(i, j);
        return items.get(j*width+i);
    }

    /**
     * Get element with given address vector.
     * @param address
     */
    public T get(Vector2 address){
        return get(Math.round(address.x),Math.round(address.y));
    }

    // iteration

    /**
     * Create all item elements independent of address
     *
     * @param creation with a create method for objects of class T
     */
    public void create(com.mygdx.game.Matrix.Creation<T> creation) {
        for (int i = items.size - 1; i >= 0; i--) {
            items.set(i, creation.create());
        }
    }

    /**
     * Create all item elements depending on their (i,j) address.
     *
     * @param creationIJ
     */
    public void create(com.mygdx.game.Matrix.CreationIJ<T> creationIJ){
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
    public void act(com.mygdx.game.Matrix.Action<T> action) {
        for (T t:items) {
            if (t!=null){
                action.act(t);
            }
        }
    }

    /**
     * Act/transform all item elements depending on their (i,j) address.
     *
     * @param actionIJ
     */
    public void act(com.mygdx.game.Matrix.ActionIJ<T> actionIJ){
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
     * set elements of one latticeOfTouchables depending on the elements of another one. independent of address.
     *
     * @param transformation
     * @param matrix
     * @param <U>
     */
    public <U extends Touchable> void transform(Transformation<T,U> transformation,
                                                Matrix<U> matrix){
        U u;
        for (int index=items.size-1;index>=0;index--){
            u= matrix.items.get(index);
            if (u!=null) {
                items.set(index,transformation.transform(u));
            }
        }
    }

    /**
     * set elements of one latticeOfTouchables depending on the elements of another one and of address.
     *
     * @param transformation
     * @param matrix
     * @param <U>
     */
    public <U extends Touchable> void transformIJ(TransformationIJ<T,U> transformation,
                                                Matrix<U> matrix){
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
