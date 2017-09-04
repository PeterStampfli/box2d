package com.mygdx.game.Matrix;

/**
 * Transform an object: Create an object depending on another one.
 */

public interface Transform<T,U> {

    T transform(U u);
}
