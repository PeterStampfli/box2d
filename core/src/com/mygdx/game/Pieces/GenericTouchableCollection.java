package com.mygdx.game.Pieces;

/**
 * Created by peter on 7/2/17.
 */

public class GenericTouchableCollection<T extends Touchable> extends Touchables<T> {

    public T dingdong(){
        return touchables.first();
    }


}
