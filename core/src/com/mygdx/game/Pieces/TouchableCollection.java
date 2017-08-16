package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 7/2/17.
 * A touchable collection.
 * Has an array of objects. Makes touchable actions on touchable items.
 *
 * At selection does not change order
 * Or puts selected on top and adds and removes.
 */

public class TouchableCollection<T> extends com.mygdx.game.utilities.Collection<T> implements Touchable {

    boolean fixedOrder;
    int iSelected=-1;

    /**
     * create Touchables with a new item array and choice of ordering
     *
     * @param order
     */
    public TouchableCollection(boolean order){
        super();
        fixedOrder=order;
    }

    /**
     * Create with new item array and no ordering.
     */
    public TouchableCollection(){
        this(false);
    }


    // the Touchable methods

    /**
     * Call the draw method, going from last to first object. The painter algorithm: back to front.
     *
     */
    @Override
    public void draw() {
        Object item;
        for (int i = items.size - 1; i >= 0; i--) {
            item=items.get(i);
            if (item instanceof  Drawable){
                ((Drawable) item).draw();
            }
        }
    }

    /**
     * Check if a touchable contains the touch position.
     * Goes from first to last object.
     * If not fixedOrder then the first touchable that contains the point will become the first element of the items array.
     * iSelected is index to the selected item.
     *
     * @param x float, x-coordinate of (touch) position
     * @param y float, y-coordinate of (touch) position
     * @return true if the collection contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        int length = items.size;
        Object item;
        for (int i = 0; i < length; i++) {
            item=items.get(i);
            if ((item instanceof Shape2D)&&((Shape2D)item).contains(x, y)) {   // we need the index i
                if (fixedOrder){
                    iSelected=i;
                }
                else {
                    items.insert(0, items.removeIndex(i));   // put piece to top, others go down automatically
                    iSelected=0;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a touchable contains the touch position. Goes from first to last object.
     * The first touchable that contains the point will become the first element of the items array.
     *
     * @param position Vector2, the (touch) position
     * @return true if the collection contains the point
     */
    @Override
    public boolean contains(Vector2 position) {
        return contains(position.x, position.y);
    }

    /**
     * Calls the touchBegin method on the first element of the collection.
     * The calling method has to make sure that this makes sense.
     * Returns false if there is no touchable.
     *
     * @param position, Vector2 touch position
     * @return true if something changed
     */
    @Override
    public boolean touchBegin(Vector2 position) {
        if (items.size > 0) {
            Object item=items.get(iSelected);
            if (item instanceof Touchable) {
                return ((Touchable) item).touchBegin(position);
            }
        }
        return false;
    }

    /**
     * Calls the touchDrag method on the first element of the collection.
     * The calling method has to make sure that this makes sense.
     * Returns false if there is no touchable
     *
     * @param position      Vector2 touch position
     * @param deltaPosition Vector2, change in the touch position
     * @return true if something changed
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition) {
        if (items.size > 0) {
            Object item=items.get(iSelected);
            if (item instanceof Touchable) {
                return ((Touchable) item).touchDrag(position, deltaPosition);
            }
        }
        return false;
    }

    /**
     * Calls the touchEnd method on the first element of the collection.
     * The calling method has to make sure that this makes sense.
     * Returns false if there is no touchable.
     *
     * @param position, Vector2 touch position
     * @return true if something changed
     */
    @Override
    public boolean touchEnd(Vector2 position) {
        if (items.size > 0) {
            Object item=items.get(iSelected);
            if (item instanceof Touchable) {
                return ((Touchable) item).touchEnd(position);
            }
        }
        return false;
    }

    /**
     * Calls the scroll method on the collection from first to last until an object returns true.
     * (If an object contains the touch position, it does something and returns true.)
     *
     * @param position, Vector2 touch position
     * @param amount    int, indicates if scrolling up or down
     * @return true if something changed
     */
    @Override
    public boolean scroll(Vector2 position, int amount) {
        for (T item : items) {
            if ((item instanceof  Touchable)&&((Touchable) item).scroll(position, amount)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Call the keep visible method on all elements.
     *
     * @return true if something changed
     */
    @Override
    public boolean keepVisible() {
        boolean somethingChanged = false;
        for (T item : items) {
            if ((item instanceof  Touchable)&&((Touchable) item).keepVisible()) {
                somethingChanged = true;
            }
        }
        return somethingChanged;
    }
}
