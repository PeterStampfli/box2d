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
     * make a bytebuffer of float numbers: first number is size of floatArray,
     * then the float numbers follow
     *
     * @param array
     * @return a byteBuffer with length of array and data, write (append) it on a file
     */
    static public ByteBuffer make(FloatArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*(length+1));
        FloatBuffer buffer=byteBuffer.asFloatBuffer();
        buffer.put(length);
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        return byteBuffer;
    }

    /**
     * make a bytebuffer of float numbers: first number is size of float[],
     * then the float numbers follow
     *
     * @param array
     * @return a byteBuffer with length of array and data, write (append) it on a file
     */
    static public ByteBuffer make(float[] array){
        int length=array.length;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*(length+1));
        FloatBuffer buffer=byteBuffer.asFloatBuffer();
        buffer.put(length);
        for (float element:array) {
            buffer.put(element);
        }
        return byteBuffer;
    }

    /**
     * make a bytebuffer of int numbers: first number is size of floatArray,
     * then the float numbers follow
     *
     * @param array
     * @return a byteBuffer with length of array and data, write (append) it on a file
     */
    static public ByteBuffer make(IntArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*(length+1));
        IntBuffer buffer=byteBuffer.asIntBuffer();
        buffer.put(length);
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        return byteBuffer;
    }

    /**
     * make a bytebuffer of int numbers: first number is size of int[],
     * then the float numbers follow
     *
     * @param array
     * @return a byteBuffer with length of array and data, write (append) it on a file
     */
    static public ByteBuffer make(int[] array){
        int length=array.length;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*(length+1));
        IntBuffer buffer=byteBuffer.asIntBuffer();
        buffer.put(length);
        for (int element:array){
            buffer.put(element);
        }
        return byteBuffer;
    }

    /**
     * read incrementally another floatArray from a floatBuffer, size of array is given as first float
     *
     * @param array
     * @param buffer
     */
    static public void readByteBuffer(FloatArray array,FloatBuffer buffer){
        int length=Math.round(buffer.get());
        array.clear();
        for (int i = 0; i < length; i++) {
            array.add(buffer.get());
        }
    }

    /**
     * read incrementally another intArray from an intBuffer, size of array is given as first float
     *
     * @param array
     * @param buffer
     */
    static public void readByteBuffer(IntArray array,IntBuffer buffer){
        int length=buffer.get();
        array.clear();
        for (int i = 0; i < length; i++) {
            array.add(buffer.get());
        }
    }

    /**
     * read incrementally another float[] from a floatBuffer, size of array is given as first float
     *
     * @param buffer
     * @return the float[] data
     */
    static public float[] floats(FloatBuffer buffer){
        int length=Math.round(buffer.get());
        float[] array=new float[length];
        for (int i = 0; i < length; i++) {
            array[i]=buffer.get();
        }
        return array;
    }

    /**
     * read incrementally another int[] from a IntBuffer, size of array is given as first int
     *
     * @param buffer
     * @return the float[] data
     */
    static public int[] ints(IntBuffer buffer){
        int length=Math.round(buffer.get());
        int[] array=new int[length];
        for (int i = 0; i < length; i++) {
            array[i]=buffer.get();
        }
        return array;
    }
}
