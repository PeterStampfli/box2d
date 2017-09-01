package com.mygdx.game.utilities;

import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 8/15/17.
 */

public class Collection<T> {

    public Array<T> items=new Array<T>();


    /**
     * Clear for reuse.
     */
    public void clear(){
        items.clear();
    }

    /**
     * Add one or several elements to the collection. Does nothing if is null.
     *
     * @param ts  objects of type T, t[] and t..., the shapes to add.
     */
    public void add(T... ts) {
        for (T t : ts) {
            if (t != null) {
                this.items.add(t);
            }
        }
    }

    /**
     * Add one or several elements to the collection. Does not add null elements.
     *
     * @param ts Array of objects of type T or subtype, to add.
     */
    public void add(Array<? extends T> ts) {
        for (T t : ts) {
            if (t != null) {
                this.items.add(t);
            }
        }
    }

    /**
     * Add the elements of a collection to the collection. Does not add null elements.
     *
     * @param collection of objects of type T or subtype, to add.
     */
    public void add(Collection<? extends T> collection){
        add(collection.items);
    }

    /**
     * Add one or more touchable objects at the beginning. Be careful if fixed order.
     *
     * @param ts objects of type T, T... or T[]
     */
    public void addFirst(T... ts) {
        for (T t : ts) {
            this.items.insert(0,t);
        }
    }

    /**
     * Remove a given (sub)touchable object, using identity.Removes multiple occurrences.
     * Does not check elements that are inside collections.
     *
     * @param t  object of type T, to remove
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
     * Get first index of an element in the array.
     *
     * @param t object of type T, search for it (using identity)
     * @return int, first index of t or -1 if not found
     */
    public int getIndex(T t){
        return items.indexOf(t,true);
    }

    /**
     * Get element with given index. Simple shortcut.
     *
     * @param i int, index for an element
     * @return object of type T, of index i
     */
    public T get(int i){
        return items.get(i);
    }

    /**
     * Set element with given index. Simple shortcut.
     *
     * @param i int, index of place for item
     * @param item object of type T, to set in the collection
     */
    public void set(int i,T item){
        items.set(i,item);
    }


}
