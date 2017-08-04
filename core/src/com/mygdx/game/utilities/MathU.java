package com.mygdx.game.utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 8/4/17.
 */

public class MathU {
    public static final float epsilon=0.1f;
    public static final float rt3=(float)Math.sqrt(3);

    /**
     * Get a float[] array from an Array of Vector2 objects.
     *
     * @param vectors
     * @return float[], with the vectors as float x,y pairs.
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
    static public float[] scaled(float[] vertices, float scale) {
        int length = vertices.length;
        float[] scaledVertices = new float[length];
        for (int i = 0; i < length; i++) {
            scaledVertices[i] = scale * vertices[i];
        }
        return scaledVertices;
    }
}
