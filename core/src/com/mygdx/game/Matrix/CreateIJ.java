package com.mygdx.game.Matrix;

/**
 * Create object depending on column and row indices
 */

public interface CreateIJ<T> {

    T create(int i, int j);
}
