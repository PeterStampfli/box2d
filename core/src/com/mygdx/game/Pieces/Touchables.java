package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 3/28/17.
 */

public class Touchables implements Touchable {

    public Array<Touchable> touchables;

    /**
     * create
     */
    public Touchables(){
        touchables=new Array<Touchable>();
    }

    /**
     * add a touchable object
     * @param touchable
     */
    public void add(Touchable touchable){
        touchables.add(touchable);
    }

    /**
     * remove a given touchable object, using identity
     * @param touchable
     * @return true if something has been removed
     */
    public boolean remove(Touchable touchable){
        return touchables.removeValue(touchable,true);
    }

    /**
     * call the draw method of the touchables, going from last to first, back to front
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        int length=touchables.size;
        for (int i=length-1;i>=0;i--){
            touchables.get(i).draw(batch);                  // rendering from bottom to top
        }
    }

    /**
     * check if a touchable contains the given position
     * going from first to last
     * the first touchable that contains the ppoint will be put in front of the touchables array
     * @param position
     * @return
     */
    @Override
    public boolean contains(Vector2 position) {
        int length=touchables.size;
        for (int i=0;i<length;i++){
            if (touchables.get(i).contains(position)){
                touchables.insert(0,touchables.removeIndex(i));   // put piece to top
                return true;
            }
        }
        return false;
    }

    /**
     * call the touchBegin method on the first touchable
     * returns false if there is no touchable
     * @param position
     * @return
     */
    @Override
    public boolean touchBegin(Vector2 position) {
        if (touchables.size>0){
            return touchables.get(0).touchBegin(position);
        }
        return false;
    }

    /**
     * call the touchDrag method on the first touchable
     * returns false if there is no touchable
     * @param position
     * @param deltaPosition
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition) {
        if (touchables.size>0){
            return touchables.get(0).touchDrag(position,deltaPosition);
        }
        return false;
    }

    /**
     * call the touchEnd method on the first touchable
     * returns false if there is no touchable
     * @return
     */
    @Override
    public boolean touchEnd() {
        if (touchables.size>0){
            return touchables.get(0).touchEnd();
        }
        return false;
    }
}
