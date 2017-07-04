package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 7/2/17.
 * Does collect items or subclass. Makes touchable actions on items.
 */

public class Touchables<T extends Touchable> implements Touchable {

    public Array<T> items = new Array<T>();

    /**
     * set all array elements to null.
     */
    public void setNull(){
        for (int i = items.size - 1; i >= 0; i--) {
            items.set(i,null);
        }
    }

    /**
     * Call the draw method, going from last to first object. The painter algorithm: back to front.
     *
     * @param batch  SpriteBatch, of the Device
     * @param camera Camera, current in use. To decide of sprite is visible.
     */
    @Override
    public void draw(Batch batch, Camera camera) {
        for (int i = items.size - 1; i >= 0; i--) {
            items.get(i).draw(batch, camera);
        }
    }

    /**
     * Check if a touchable contains the touch position.
     * Goes from first to last object.
     * The first touchable that contains the point will become the first element of the items array.
     *
     * @param x float, x-coordinate of (touch) position
     * @param y float, y-coordinate of (touch) position
     * @return true if the collection contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        int length = items.size;
        for (int i = 0; i < length; i++) {
            if (items.get(i).contains(x, y)) {
                items.insert(0, items.removeIndex(i));   // put piece to top
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
            return items.get(0).touchBegin(position);
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
        if (items.size > 0) {
            return items.get(0).touchDrag(position, deltaPosition, camera);
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
            return items.get(0).touchEnd(position);
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
        for (Touchable touchable : items) {
            if (touchable.scroll(position, amount)) {
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
        for (Touchable touchable : items) {
            if (touchable.keepVisible(camera)) {
                somethingChanged = true;
            }
        }
        return somethingChanged;
    }
}
