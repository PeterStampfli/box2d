package com.mygdx.game.Pieces;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 8/5/17.
 */

public class Positionables {
    public Array<Positionable> items=new Array<Positionable>();


    // adding, getting and removing items
    /**
     * Add one or more touchable objects at the end. Be careful if fixed order.
     *
     * @param ts T... or T[]
     */
    public void addLast(Positionable... ts) {
        this.items.addAll(ts);
    }

    /**
     * Add one or more touchable objects at the beginning. Be careful if fixed order.
     *
     * @param ts T... or T[]
     */
    public void addFirst(Positionable... ts) {
        for (Positionable t : ts) {
            this.items.insert(0,t);
        }
    }

    /**
     * add all Positionable items of a touchable collection to items of this
     *
     * @param collection
     */
    public void addItems(TouchableCollection collection){
        for (Object item:collection.items){
            if (item instanceof Positionable){
                this.items.add((Positionable) item);
            }
        }
    }


    // set and read positions and angles in a floatarray

    /**
     * get position and angles of positionable items and put in given floatArray
     *
     * @param positionsAngles will be cleared
     */
    public void getPositionsAngles(FloatArray positionsAngles){
        positionsAngles.clear();
        positionsAngles.ensureCapacity(3*items.size);
        for (Positionable item : items) {
            positionsAngles.addAll(item.getX(),item.getY(),item.getAngle());
        }
    }

    public  void setPositionsAngles(FloatArray positionsAngles){
        int i=0;
        int iLimit=positionsAngles.size;
        for (Positionable item : items) {
            L.og(i);
            if (i+2<iLimit){
                item.setPositionAngle(positionsAngles.get(i),positionsAngles.get(i+1),positionsAngles.get(i+2));
            }
            i+=3;
        }




    }

}
