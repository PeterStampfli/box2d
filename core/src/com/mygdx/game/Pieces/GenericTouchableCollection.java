package com.mygdx.game.Pieces;

/**
 * Created by peter on 7/2/17.
 *
 * collection of subtypes of Touchabbles, or Touchables themselves (declaration...)
 */

public class GenericTouchableCollection<T extends Touchable> extends Touchables<T> {



    /**
     * Add one or more touchable objects.
     *
     * @param touchables Touchable... or Touchable[]
     */
    public void add(T... touchables) {
        for (T touchable : touchables) {
            this.touchables.add(touchable);
        }
    }

    /**
     * Remove a given touchable object, using identity.Removes multiple occurencies.
     * Does not check elements that are inside collections.
     *
     * @param toRemove Touchable, to remove
     * @return true if something has been removed
     */
    public boolean remove(T toRemove) {
        boolean success = false;
        while (touchables.removeValue(toRemove, true)) {
            success = true;
        }
        return success;
    }

    /**
     * Get index of a touchable in the array.
     * @param touchable
     * @return int, last index of touchable or -1 if not found
     */
    public int getIndex(T touchable){
        return touchables.lastIndexOf(touchable,true);
    }

    /**
     * Get touchable with given index. or null if index is negative.
     *
     * @param i
     * @return Touchable, of index i, or null
     */
    public Touchable get(int i){
        if (i<0){
            return null;
        }
        return touchables.get(i);
    }


}
