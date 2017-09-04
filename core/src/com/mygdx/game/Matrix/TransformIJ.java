package com.mygdx.game.Matrix;

/**
 * Transform: Create an object depending on another object, row and column index.
 */

public interface TransformIJ<T,U> {

    T transform(int i, int j, U u);
}
