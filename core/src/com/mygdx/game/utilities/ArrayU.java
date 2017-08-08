package com.mygdx.game.utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by peter on 8/4/17.
 */

public class ArrayU {



    /**
     * Get a new float[] array from an Array of Vector2 objects.
     * For float[]s to be used as shape data
     *
     * @param vectors
     * @return new float[], with the vectors as float x,y pairs.
     */
    static public float[] toFloats(Array<Vector2> vectors) {
        float[] result = new float[2 * vectors.size];
        int i = 0;
        for (Vector2 vector : vectors) {
            result[i++] = vector.x;
            result[i++] = vector.y;
        }
        return result;
    }

    /**
     * Create a float array that is a scaled copy of another float array.
     *
     * @param vertices
     * @param scale
     * @return float[], a scaled copy
     */
    static public float[] scaledCopy(float[] vertices, float scale) {
        int length = vertices.length;
        float[] scaledVertices = new float[length];
        for (int i = 0; i < length; i++) {
            scaledVertices[i] = scale * vertices[i];
        }
        return scaledVertices;
    }

    /**
     * make a bytebuffer of float numbers: first number is size of floatArray,
     * then the float numbers follow
     *
     * @param array
     * @return a byteBuffer with length of array and data, write (append) it on a file
     */
    static public ByteBuffer makeByteBuffer(FloatArray array){
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
    static public ByteBuffer makeByteBuffer(float[] array){
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
    static public ByteBuffer makeByteBuffer(IntArray array){
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
    static public ByteBuffer makeByteBuffer(int[] array){
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
}
