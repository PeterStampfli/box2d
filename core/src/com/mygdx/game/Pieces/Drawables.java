package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 4/2/17.
 */

public class Drawables implements Drawable {


    public Array<Drawable> drawables;

    /**
     * create
     */
    public Drawables(){
        drawables =new Array<Drawable>();
    }

    /**
     * add one or more drawable objects
     * @param drawables
     */
    public void add(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            this.drawables.add(drawable);
        }
    }

    /**
     * remove a given touchable object, using identity
     * @param drawable
     * @return true if something has been removed
     */
    public boolean remove(Drawable drawable){
        return drawables.removeValue(drawable,true);
    }

    /**
     * call the draw method of the drawables, going from last to first, back to front
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        int length= drawables.size;
        for (int i=length-1;i>=0;i--){
            drawables.get(i).draw(batch);                  // rendering from bottom to top
        }
    }

}
