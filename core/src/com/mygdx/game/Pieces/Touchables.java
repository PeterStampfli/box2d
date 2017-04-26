package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Collects touchables and implements methods on the collection.
 */

public class Touchables implements Touchable {

    public Array<Touchable> touchables = new Array<Touchable>();

    /**
     * Add one or more touchable objects.
     *
     * @param touchables Touchable... or Touchable[]
     */
    public void add(Touchable... touchables) {
        for (Touchable touchable : touchables) {
            this.touchables.add(touchable);
        }
    }

    /**
     * Remove a given touchable object, using identity. Searches all.
     *
     * @param toRemove Touchable, to remove
     * @return true if something has been removed
     */
    public boolean remove(Touchable toRemove) {
        boolean success = false;
        Iterator<Touchable> touchableIterator = touchables.iterator();
        while (touchableIterator.hasNext()) {
            Touchable touchable = touchableIterator.next();
            if (touchable instanceof Touchables && ((Touchables) touchable).remove(toRemove)) {
                success = true;
            }
        }
        while (touchables.removeValue(toRemove, true)) {
            success = true;
        }
        return success;
    }

    /**
     * Call the draw method, going from last to first object. The painter algorithm: back to front.
     *
     * @param batch  SpriteBatch, of the Device
     * @param camera Camera, current in use. To decide of sprite is visible.
     */
    @Override
    public void draw(Batch batch, Camera camera) {
        int length = touchables.size;
        for (int i = length - 1; i >= 0; i--) {
            touchables.get(i).draw(batch, camera);
        }
    }

    /**
     * Check if a touchable contains the touch position.
     * Goes from first to last object.
     * The first touchable that contains the point will become the first element of the touchables array.
     *
     * @param x float, x-coordinate of (touch) position
     * @param y float, y-coordinate of (touch) position
     * @return true if the collection contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        int length = touchables.size;
        for (int i = 0; i < length; i++) {
            if (touchables.get(i).contains(x, y)) {
                touchables.insert(0, touchables.removeIndex(i));   // put piece to top
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a touchable contains the touch position. Goes from first to last object.
     * The first touchable that contains the point will become the first element of the touchables array.
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
        if (touchables.size > 0) {
            return touchables.get(0).touchBegin(position);
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
     * @param camera        Camera in use. To keep the object visible
     * @return true if something changed
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera) {
        if (touchables.size > 0) {
            return touchables.get(0).touchDrag(position, deltaPosition, camera);
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
        if (touchables.size > 0) {
            return touchables.get(0).touchEnd(position);
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
        Iterator<Touchable> touchableIterator = touchables.iterator();
        while (touchableIterator.hasNext()) {
            if (touchableIterator.next().scroll(position, amount)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Call the keep visible method on all elements.
     *
     * @param camera Camera, in use to see if sprite is visible
     * @return true if something changed
     */
    @Override
    public boolean keepVisible(Camera camera) {
        boolean somethingChanged = false;
        Iterator<Touchable> touchableIterator = touchables.iterator();
        while (touchableIterator.hasNext()) {
            if (touchableIterator.next().keepVisible(camera)) {
                somethingChanged = true;
            }
        }
        return somethingChanged;
    }
}
