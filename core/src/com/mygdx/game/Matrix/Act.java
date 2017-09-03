package com.mygdx.game.Matrix;

/**
 * Do something with element T independent of index.
 */

public interface Act<T> {

    /**
     * Do something
     *
     * @param t Object of type T
     */
    void act(T t);
}
