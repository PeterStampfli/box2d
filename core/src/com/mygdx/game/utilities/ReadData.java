package com.mygdx.game.utilities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

import java.nio.ByteBuffer;

/**
 * Read a file on a byte buffer, then read from the byte buffer elementary data
 */

public class ReadData {

    /**
     * create ByteBuffer and read data from file given by fileHandle.
     * Return null if file does not exist.
     *
     * @param fileHandle FileHandle
     * @return a new ByteBuffer with the data, or null if file does not exist
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
     * advance the position of the buffer by given amount
     * @param buffer ByteBuffer
     * @param i int i, amount of bytes to advance
     */
    static public void advance(ByteBuffer buffer,int i){
        buffer.position(buffer.position()+i);
    }


    /**
     * read a float from a byte buffer byte, advances buffer by 4
     *
     * @param buffer ByteBuffer
     * @return float
     */
    public static float getFloat(ByteBuffer buffer){
        return buffer.getFloat();
    }

    /**
     * read a int from a byte buffer byte, advances buffer by 4
     *
     * @param buffer ByteBuffer
     * @return int
     */
    public static int getInt(ByteBuffer buffer){
        return buffer.getInt();
    }

    /**
     * read a short from a byte buffer byte, advances buffer by 2
     *
     * @param buffer ByteBuffer
     * @return short
     */
    public static short getShort(ByteBuffer buffer){
        return buffer.getShort();
    }

    /**
     * read a byte from a byte buffer byte, advances buffer by 1
     *
     * @param buffer ByteBuffer
     * @return byte
     */
    public static byte getByte(ByteBuffer buffer){
        return buffer.get();
    }

    /**
     * read a boolean from a byte buffer byte, advances buffer by 1
     *
     * @param buffer ByteBuffer
     * @return boolean
     */
    public static boolean getBoolean(ByteBuffer buffer){
        return buffer.get()!=0;
    }


    /**
     * read incrementally another floatArray from a ByteBuffer
     *
     * @param array FloatArray, will have the data
     * @param length int, number of floats to read
     * @param buffer ByteBuffer
     */
    static public void readFloats(FloatArray array, int length, ByteBuffer buffer){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getFloat());                      // position of float buffer advances
        }
    }

    /**
     * read incrementally into existing intArray from a ByteBuffer
     *
     * @param array IntArray
     * @param length int, number of floats to read
     * @param buffer ByteBuffer
     */
    static public void readInts(IntArray array, int length,ByteBuffer buffer){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getInt());
        }
    }

    /**
     * read incrementally into existing shortArray from a ByteBuffer
     *
     * @param array ShortArray
     * @param length int, number of floats to read
     * @param buffer ByteBuffer
     */
    static public void readShorts(ShortArray array,int length, ByteBuffer buffer){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getShort());
        }
    }

    /**
     * read incrementally into existing byteArray from a ByteBuffer
     *
     * @param array ByteArray, gets data
     * @param length int, number of floats to read
     * @param buffer ByteBuffer
     */
    static public void readBytes(ByteArray array, int length,ByteBuffer buffer){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.get());
        }
    }

    /**
     * read incrementally existing float[] from a ByteBuffer
     *
     * @param floats float[], will be filled with data
     * @param buffer ByteBuffer
     */
    static public void readFloats(float[] floats, ByteBuffer buffer){
        buffer.asFloatBuffer().get(floats);
        advance(buffer,4*floats.length);
    }

    /**
     * read incrementally existing int[] from a ByteBuffer
     *
     * @param ints int[], will be filled with data
     * @param buffer ByteBuffer
     */
    static public void readInts(int[] ints, ByteBuffer buffer){
        buffer.asIntBuffer().get(ints);
        advance(buffer,4*ints.length);
    }

    /**
     * read incrementally existing short[] from a ByteBuffer
     *
     * @param shorts short[], will be filled with data
     * @param buffer ByteBuffer
     */
    static public void readShorts(short[] shorts, ByteBuffer buffer){
        buffer.asShortBuffer().get(shorts);
        advance(buffer,2*shorts.length);
    }

    /**
     * read incrementally existing bytes[] from a ByteBuffer
     *
     * @param bytes byte[], will be filled with data
     * @param buffer ByteBuffer
     */
    static public void readBytes(byte[] bytes, ByteBuffer buffer){
        buffer.get(bytes);
    }

    /**
     * read an integer length from the byteBuffer, create and read a float[] of this length
     * return the float[]
     *
     * @param buffer ByteBuffer
     * @return float[], length and data read from byteBuffer
     */
    static public float[] getFloats(ByteBuffer buffer){
        int length=buffer.getInt();
        float[] fs=new float[length];
        readFloats(fs,buffer);
        return fs;
    }

    /**
     * read an integer length from the byteBuffer, create and read an int[] of this length
     * return the int[]
     *
     * @param buffer ByteBuffer
     * @return int[], length and data read from byteBuffer
     */
    static public int[] getInts(ByteBuffer buffer){
        int length=buffer.getInt();
        int[] is=new int[length];
        readInts(is,buffer);
        return is;
    }

    /**
     * read an integer length from the byteBuffer, create and read an short[] of this length
     * return the short[]
     *
     * @param buffer ByteBuffer
     * @return short[], length and data read from byteBuffer
     */
    static public short[] getShorts(ByteBuffer buffer){
        int length=buffer.getInt();
        short[] is=new short[length];
        readShorts(is,buffer);
        return is;
    }

    /**
     * read an integer length from the byteBuffer, create and read an byte[] of this length
     * return the byte[]
     *
     * @param buffer ByteBuffer
     * @return byte[], length and data read from byteBuffer
     */
    static public byte[] getBytes(ByteBuffer buffer){
        int length=buffer.getInt();
        byte[] is=new byte[length];
        readBytes(is,buffer);
        return is;
    }

}
