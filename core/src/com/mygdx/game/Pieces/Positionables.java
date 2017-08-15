package com.mygdx.game.Pieces;

import java.nio.ByteBuffer;

/**
 * Created by peter on 8/5/17.
 */

public class Positionables extends Collection<Positionable>{



    /**
     * add all Positionable items of a touchable collection to items of this
     * (can't do that in generic superclass because of type erasure)
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

    /**
     * read position and angles of positionable items from a bytebuffer
     * starting at its current position
     * (previously read from a file ...)
     *
     * @param byteBuffer nothing happens if null
     */
    public void positionsAngles(ByteBuffer byteBuffer){
        if (byteBuffer!=null) {
            for (Positionable item : items) {
                item.setPositionAngle(byteBuffer.getFloat(), byteBuffer.getFloat(),byteBuffer.getFloat());
            }
        }
    }

    /**
     * write the index positions of the items of this collection
     * in another TouchableCollection to a bytebuffer
     *
     * @param collection
     * @return
     */
    public ByteBuffer indices(TouchableCollection collection){
        ByteBuffer byteBuffer= ByteBuffer.allocate(4*items.size);
        for (Positionable item : items) {
            byteBuffer.putInt(collection.getIndex(item));
        }
        return byteBuffer;
    }

    /**
     * write positions and angles to a bytebuffer
     *
     * @return byteBuffer
     */
    public ByteBuffer positionsAngles(){
        ByteBuffer byteBuffer= ByteBuffer.allocate(12*items.size);
        for (Positionable item : items) {
            byteBuffer.putFloat(item.getX());
            byteBuffer.putFloat(item.getY());
            byteBuffer.putFloat(item.getAngle());
        }
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * set indices for the items of this collection from a bytebuffer. put them to the index positions in Touchable collection with
     * index positions
     * starting at current position of the bytebuffer
     * Assumes that the touchable collection has enough place, is not mixed with other items, not fixed order
     * uses that both positionables and touchableCollection contain only references
     * 
     * @param collection
     * @param byteBuffer if null nothing happens
     */
    public void indices(TouchableCollection collection, ByteBuffer byteBuffer){
        if (byteBuffer!=null) {
            for (Positionable item : items) {
                collection.items.set(byteBuffer.getInt(), item);
            }
        }
    }
}
