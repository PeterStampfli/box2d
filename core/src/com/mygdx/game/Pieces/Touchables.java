package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
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
     * add one or more touchable objects
     * @param touchables
     */
    public void add(Touchable... touchables){
        for (Touchable touchable:touchables) {
            this.touchables.add(touchable);
        }
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
     * call the draw method, going from last to first, back to front
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
     * check if a touchable contains the given setPosition
     * going from first to last
     * the first touchable that contains the point will be put in front of the drawables array
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x,float y) {
        int length=touchables.size;
        for (int i=0;i<length;i++){
            if (touchables.get(i).contains(x,y)){
                touchables.insert(0,touchables.removeIndex(i));   // put piece to top
                return true;
            }
        }
        return false;
    }

    /**
     * check if a touchable contains the given setPosition
     * going from first to last
     * the first touchable that contains the point will be put in front of the drawables array
     * @param position
     * @return
     */
    @Override
    public boolean contains(Vector2 position) {
        return contains(position.x,position.y);
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
     * @param camera
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera) {
        if (touchables.size>0){
            return touchables.get(0).touchDrag(position,deltaPosition,camera );
        }
        return false;
    }

    /**
     * call the touchEnd method on the first touchable
     * returns false if there is no touchable
     * @return
     * @param position
     */
    @Override
    public boolean touchEnd(Vector2 position) {
        if (touchables.size>0){
            return touchables.get(0).touchEnd(position);
        }
        return false;
    }

    /**
     * call the scroll method on the touchables from front to end
     * until some piece does something and returns true
     * the a piece contains the setPosition, it does something and returns true
     * @param position
     * @param amount
     * @return
     */
    @Override
    public boolean scroll(Vector2 position, int amount){
        int length=touchables.size;
        for (int i=0;i<length;i++){
            if (touchables.get(i).scroll(position, amount)){
                return true;
            }
        }
        return false;
    }

    /**
     * call the keep visible method, going from last to first, back to front
     * @param camera
     */
    @Override
    public boolean keepVisible(Camera camera) {
        boolean somethingChanged=false;
        int length=touchables.size;
        for (int i=length-1;i>=0;i--){
            if (touchables.get(i).keepVisible(camera)){
                somethingChanged=true;
            }
        }
        return somethingChanged;
    }

}
