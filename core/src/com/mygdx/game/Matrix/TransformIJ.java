package com.mygdx.game.Matrix;

/**
 * Created by peter on 7/7/17.
 */

public interface TransformIJ<T,U> {

    public T transform(int i,int j,U u);
}