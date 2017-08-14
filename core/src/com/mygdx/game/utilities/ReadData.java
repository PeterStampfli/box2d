package com.mygdx.game.utilities;

import com.badlogic.gdx.files.FileHandle;

import java.nio.ByteBuffer;

/**
 * Read a file on a byte buffer, then read from the bytebuffer elementary data
 */

public class ReadData {



    /**
     * create ByteBuffer and read data from file given by fileHandle
     *
     * @param fileHandle
     * @return a new byteBuffer with the data, or null if file does not exist
     */
    static public ByteBuffer byteBuffer(FileHandle fileHandle){
        if (fileHandle.exists()) {
            byte[] bytes = fileHandle.readBytes();
            return ByteBuffer.wrap(bytes);
        }
        else {
            return null;
        }
    }

    /**
     * read a float from a byte buffer byte, advances buffer by 4
     *
     * @param buffer
     * @return
     */
    public static float aFloat(ByteBuffer buffer){
        return buffer.getFloat();
    }

    /**
     * read a int from a byte buffer byte, advances buffer by 4
     *
     * @param buffer
     * @return
     */
    public static int anInt(ByteBuffer buffer){
        return buffer.getInt();
    }

    /**
     * read a short from a byte buffer byte, advances buffer by 2
     *
     * @param buffer
     * @return
     */
    public static short aShort(ByteBuffer buffer){
        return buffer.getShort();
    }

    /**
     * read a byte from a byte buffer byte, advances buffer by 1
     *
     * @param buffer
     * @return
     */
    public static byte aByte(ByteBuffer buffer){
        return buffer.get();
    }

    /**
     * read a boolean from a byte buffer byte, advances buffer by 1
     *
     * @param buffer
     * @return
     */
    public static boolean aBoolean(ByteBuffer buffer){
        return buffer.get()!=0;
    }


}
