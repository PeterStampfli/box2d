package com.mygdx.game.Lattice;

/**
 * Created by peter on 7/4/17.
 */

public interface Transformation<T,U> {

    public T transform(U u);
}
