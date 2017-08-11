package com.mygdx.game.utilities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

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
     * make a byte buffer that contains content of a shortArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(ShortArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*length);
        ShortBuffer buffer=byteBuffer.asShortBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));            // does not change position of the byteBuffer
        }
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains content of a ByteArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(ByteArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(length);
        for (int i=0;i<length;i++) {
            byteBuffer.put(array.get(i));
        }
        byteBuffer.rewind();
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
     * make a byte buffer that contains content of a short[]
     *
     * @param shorts
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(short[] shorts){
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*shorts.length);
        byteBuffer.asShortBuffer().put(shorts);    // does not change position of the byteBuffer
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains content of a byte[]
     *
     * @param bytes
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer make(byte[] bytes){
        ByteBuffer byteBuffer=ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * make a byteBuffer with the data of a Circle object,append on a file
     *
     * @param circle
     * @return
     */
    static public ByteBuffer make(Circle circle){
        ByteBuffer byteBuffer=ByteBuffer.allocate(12);
        byteBuffer.putFloat(circle.x);
        byteBuffer.putFloat(circle.y);
        byteBuffer.putFloat(circle.radius);
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * read incrementally another floatArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void read(FloatArray array, ByteBuffer buffer, int length){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getFloat());                      // position of float buffer advances
        }
    }

    /**
     * read incrementally into existing intArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void read(IntArray array, ByteBuffer buffer, int length){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getInt());
        }
    }

    /**
     * read incrementally into existing shortArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void read(ShortArray array, ByteBuffer buffer, int length){
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getShort());
        }
    }

    /**
     * read incrementally into existing byteArray from a ByteBuffer, size of array is given as parameter
     *
     * @param array
     * @param buffer
     */
    static public void read(ByteArray array, ByteBuffer buffer, int length){
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
    static public void read(float[] floats, ByteBuffer buffer){
        buffer.asFloatBuffer().get(floats);
        ByteBufferU.advance(buffer,4*floats.length);
    }

    /**
     * read incrementally existing int[] from a ByteBuffer
     *
     * @param ints
     * @param buffer
     */
    static public void read(int[] ints, ByteBuffer buffer){
        buffer.asIntBuffer().get(ints);
        ByteBufferU.advance(buffer,4*ints.length);
    }

    /**
     * read incrementally existing short[] from a ByteBuffer
     *
     * @param shorts
     * @param buffer
     */
    static public void read(short[] shorts, ByteBuffer buffer){
        buffer.asShortBuffer().get(shorts);
        ByteBufferU.advance(buffer,2*shorts.length);
    }

    // for bytes use byteBuffer.get(bytes)

    /**
     * create a new circle object with data read from byteBuffer
     *
     * @param buffer
     * @return
     */
    static public Circle circle(ByteBuffer buffer){
        return new Circle(buffer.getFloat(),buffer.getFloat(),buffer.getFloat());
    }
}
