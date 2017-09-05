package com.mygdx.game.Sprite;

import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.utilities.FileU;
import com.mygdx.game.utilities.ReadData;
import com.mygdx.game.utilities.WriteData;

import java.nio.ByteBuffer;

/**
 * Write and read positions, angles and order of sprites.
 * Sprites may not be created or destroyed.
 */

public class SpritePositionOrder extends com.mygdx.game.utilities.Collection<ExtensibleSprite> {
    public SpriteCollection spriteCollection;

    /**
     * create a spritePositionOrder object for a given ExtensibleSprite collection.
     * add all elements of the collection to the this.items array
     *
     * @param spriteCollection SpriteCollection, to watch
     */
    public SpritePositionOrder(SpriteCollection spriteCollection){
        this.spriteCollection=spriteCollection;
        updateList();
    }

    /**
     * update the list of sprites
     * call at construction and when number or types of sprites change
     */
    public void updateList(){
        for (ExtensibleSprite item : spriteCollection.items) {
            add(item);
        }
    }

    /**
     * append the index positions in another TouchableCollection
     * of the items of this collection to a file
     *  @param fileHandle FileHandle, of the output file
     *
     */
    public void appendIndices(FileHandle fileHandle) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * items.size);
        for (ExtensibleSprite item : items) {
            byteBuffer.putInt(spriteCollection.getIndex(item));
        }
        WriteData.appendBuffer(fileHandle, byteBuffer);
    }

    /**
     * append positions and angles to a file
     *
     * @param fileHandle FileHandle, of the output file
     */
    public void appendPositionsAngles(FileHandle fileHandle) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12 * items.size);
        for (ExtensibleSprite item : items) {
            byteBuffer.putFloat(item.getX());
            byteBuffer.putFloat(item.getY());
            byteBuffer.putFloat(item.getAngle());
        }
        WriteData.appendBuffer(fileHandle, byteBuffer);
    }

    /**
     * set indices for the items of this collection from a byteBuffer.
     * put them to the index positions in sprite collection with
     * starting at current position of the byteBuffer
     * Assumes that sprites are not destroyed or created
     *
     * @param byteBuffer ByteBuffer, with input data, nothing happens if null
     */
    public void readIndices(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            int index;
            for (ExtensibleSprite item : items) {
                index = byteBuffer.getInt();
                if (index >= 0) {
                    spriteCollection.set(index, item);
                }
            }
        }
    }

    /**
     * read position and angles of positionable items from a byteBuffer
     * starting at its current position
     * (previously read from a file ...)
     *
     * @param byteBuffer ByteBuffer, with input data, nothing happens if null
     */
    public void readPositionsAngles(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            for (ExtensibleSprite item : items) {
                item.setPositionAngle(byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat());
            }
        }
    }

    /**
     * write all data to a file
     *
     * @param fileHandle FileHandle for the file
     */
    public void write(FileHandle fileHandle){
        fileHandle.delete();
        appendPositionsAngles(fileHandle);
        appendIndices(fileHandle);
    }

    /**
     * write all data to a file on internal storage
     *
     * @param path String, name of file with extension and path
     */
    public void write(java.lang.String path){
        write(FileU.createLocalFileHandle(path));
    }

    /**
     * read all data from a file.
     * Nothing happens if file does not exist.
     *
     * @param fileHandle FileHandle for the file
     */
    public void read(FileHandle fileHandle){
        ByteBuffer buffer= ReadData.byteBuffer(fileHandle);
        readPositionsAngles(buffer);
        readIndices(buffer);
    }

    /**
     * read all data from a file.
     * Nothing happens if file does not exist.
     *
     * @param path String, name of file with extension and path
     */
    public void read(java.lang.String path){
        read(FileU.createLocalFileHandle(path));
    }
}
