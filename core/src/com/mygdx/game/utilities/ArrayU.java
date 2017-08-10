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
     * scale a float array.
     *
     * @param floats
     * @param scale
     */
    static public void scale(float[] floats, float scale) {
        for (int i = floats.length-1; i >=0; i--) {
            floats[i] *= scale;
        }
    }

    /**
     * Create a float array that is a scaled copy of another float array.
     *
     * @param floats
     * @param scale
     * @return float[], a scaled copy
     */
    static public float[] scaledCopy(float[] floats, float scale) {
        int length = floats.length;
        float[] scaledVertices = new float[length];
        for (int i = 0; i < length; i++) {
            scaledVertices[i] = scale * floats[i];
        }
        return scaledVertices;
    }

}
