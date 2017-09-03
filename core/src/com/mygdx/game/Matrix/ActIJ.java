package com.mygdx.game.Matrix;

/**
 * Do something on an object, depending on its position indices
 */

public interface ActIJ<T> {

    /**
     * Do something dependent on object and its matrix position. Returns nothing
     *
     * @param i int, column index
     * @param j int, row index
     * @param t object of type T
     */
    void act(int i, int j, T t);
}
