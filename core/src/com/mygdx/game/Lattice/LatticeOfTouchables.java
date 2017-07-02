package com.mygdx.game.Lattice;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.Pieces.TouchableCollection;

/**
 * Created by peter on 7/2/17.
 */

public class LatticeOfTouchables<T extends Touchable> extends TouchableCollection {

    public Array<T> items;
    public int width,height;

    /**
     * create for given size
     *
     * @param w
     * @param h
     */
    public LatticeOfTouchables (int w,int h){
        items = new Array<T>();
        resize(w, h);
    }

    /**
     * Make that items array has enough space for given 2d width and height, set width and height.
     * Clear items and set size.
     * @param w
     * @param h
     */
    public void resize(int w,int h){
        int newSize=w*h;
        items.setSize(newSize);
        for (int i=0;i<newSize;i++){
            items.set(i,null);
        }
        width=w;
        height=h;
    }


}
