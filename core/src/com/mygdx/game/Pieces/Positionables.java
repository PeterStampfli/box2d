package com.mygdx.game.Pieces;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.mygdx.game.utilities.FileU;

import java.nio.ByteBuffer;

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

    /**
     * set the positions using data of a floatArray
     *
     * @param positionsAngles
     */
    public  void setPositionsAngles(FloatArray positionsAngles){
        int i=0;
        int iLimit=positionsAngles.size;
        for (Positionable item : items) {
            if (i+2<iLimit){
                item.setPositionAngle(positionsAngles.get(i),positionsAngles.get(i+1),positionsAngles.get(i+2));
            }
            i+=3;
        }
    }

    /**
     * get index positions of the items of this collection
     * in another TouchableCollection
     *
     * @param indices will be overwritten
     * @param collection
     */
    public void getIndices(IntArray indices,TouchableCollection collection){
        indices.clear();
        for (Positionable item : items) {
            indices.add(collection.getIndex(item));
        }
    }

    /**
     * append index positions of the items of this collection
     * in another TouchableCollection
     *
     * @param fileHandle
     */
    public void writeIndices(TouchableCollection collection,FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBuffer.allocate(4*items.size);
        for (Positionable item : items) {
            byteBuffer.putInt(collection.getIndex(item));
        }
        FileU.write(byteBuffer,fileHandle);
    }

    /**
     * append positions and angles to a file
     *
     * @param fileHandle
     */
    public void writePositionsAngles(FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBuffer.allocate(12*items.size);
        for (Positionable item : items) {
            byteBuffer.putFloat(item.getX());
            byteBuffer.putFloat(item.getY());
            byteBuffer.putFloat(item.getAngle());
        }
        FileU.write(byteBuffer,fileHandle);
    }


    /**
     * write the items of this collection to Touchable collection with
     * index positions given by IntArray indices
     * Assumes that the touchable collection has enough place, is not mixed, not fixed order
     *
     * @param collection
     * @param indices
     */
    public void setIndices(TouchableCollection collection,IntArray indices){
        int i=0;
        int iLimit=indices.size;
        for (Positionable item : items) {
            if (i<iLimit){
                collection.items.set(indices.get(i),item);
            }
            i++;
        }
    }
}
