package com.mygdx.game.Pieces;

/**
 * Created by peter on 7/2/17.
 *
 * collection of subtypes of Touchabbles, or Touchables themselves
 *
 */

public class TouchableCollection<T extends Touchable> extends Touchables<T> {



    /**
     * Add one or more touchable objects.
     *
     * @param ts T... or T[]
     */
    public void add(T... ts) {
        for (T t : ts) {
            this.items.add(t);
        }
    }

    /**
     * Remove a given (sub)touchable object, using identity.Removes multiple occurrences.
     * Does not check elements that are inside collections.
     *
     * @param t  to remove
     * @return true if something has been removed
     */
    public boolean remove(T t) {
        boolean success = false;
        while (items.removeValue(t, true)) {
            success = true;
        }
        return success;
    }

    /**
     * Get index of an element in the array.
     * @param t
     * @return int, last index of t or -1 if not found
     */
    public int getIndex(T t){
        return items.lastIndexOf(t,true);
    }

    /**
     * Get element with given index. or null if index is negative.
     *
     * @param i
     * @return Touchable, of index i, or null
     */
    public T get(int i){
        if (i<0){
            return null;
        }
        return items.get(i);
    }
}
