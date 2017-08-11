package com.mygdx.game.utilities;

import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by peter on 8/10/17.
 */

public class ByteBufferU {

    /**
     * advance the position of the buffer by given amount
     * @param buffer
     * @param i
     */
    static public void advance(ByteBuffer buffer,int i){
        buffer.position(buffer.position()+i);
    }

    /**
     * make a byte buffer that contains content of a FloatArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(FloatArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length);
        FloatBuffer buffer=byteBuffer.asFloatBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));                  // does not change position of the byteBuffer
        }
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains content of a float[]
     *
     * @param floats
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(float[] floats){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*floats.length);
        byteBuffer.asFloatBuffer().put(floats);
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains content of an IntArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(IntArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length);
        IntBuffer buffer=byteBuffer.asIntBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));            // does not change position of the byteBuffer
        }
        return byteBuffer;
    }


    /**
     * make a byte buffer that contains content of a int[]
     *
     * @param ints
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(int[] ints){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*ints.length);
        byteBuffer.asIntBuffer().put(ints);    // does not change position of the byteBuffer
        return byteBuffer;
    }

    /**
     * read incrementally another floatArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void readByteBuffer(FloatArray array,ByteBuffer buffer,int length){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getFloat());                      // position of float buffer advances
        }
    }

    /**
     * read incrementally another intArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void readByteBuffer(IntArray array,ByteBuffer buffer,int length){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.get());
        }
    }

    /**
     * read incrementally existing floats[] from a ByteBuffer
     *
     * @param floats
     * @param buffer
     */
    static public void readByteBuffer(float[] floats,ByteBuffer buffer){
        buffer.asFloatBuffer().get(floats);
        ByteBufferU.advance(buffer,4*floats.length);
    }

    /**
     * read incrementally existing int[] from a ByteBuffer
     *
     * @param ints
     * @param buffer
     */
    static public void readByteBuffer(int[] ints,ByteBuffer buffer){
        buffer.asIntBuffer().get(ints);
        ByteBufferU.advance(buffer,4*ints.length);
    }
}
