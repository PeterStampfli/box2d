package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * simple static routines, mnemonic simple meaningful interfaces to obscure libgdx calls
 */

public class Basic {

    private static int numberOfRenderCalls;

    private static float timeOfLastFrame=10;


    /**
     * switch continuous rendering
     *    input events trigger rendering!
     *
     * @param on true for continuous rendering
     */
    public static void setContinuousRendering(boolean on) {
        Gdx.graphics.setContinuousRendering(on);
    }

    /**
     * demand call to renderFrame
     *   maybe not needed if input event already triggers rendering
     */
    public static void requestRendering() {
        Gdx.graphics.requestRendering();
    }

    /**
     * reset number of renderFrame calls
     */
    public static void resetNumberOfRenderCalls() {
        numberOfRenderCalls = 0;
    }

    /**
     * log number of renderFrame calls to check discontinuous rendering
     */
    public static void logNumberOfRenderCalls() {
        numberOfRenderCalls++;
        L.og("renderFrame calls: " + numberOfRenderCalls);
    }

    /**
     * clear the screen with a solid color
     *
     * @param r 0...1, red component
     * @param g 0...1, green component
     * @param b 0...1, blue component
     */
    public static void clearBackground(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * clear the screen with a solid color
     *
     * @param color libGdx Color, alpha component is ignored
     */
    public static void clearBackground(Color color) {
        clearBackground(color.r, color.g, color.b);
    }

    /**
     * set texture to linear interpolation
     *
     * @param texture for linear interpolation
     */
    public static void linearInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * set texture of a textureregion to linear interpolation
     *
     * @param textureRegion for linear interpolation
     */
    public static void linearInterpolation(TextureRegion textureRegion) {
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * get absolute time
     *
     * @return time in seconds
     */
    public static float getTime() {
        return MathUtils.nanoToSec * TimeUtils.nanoTime();
    }

    /**
     * get time passed since earlier moment
     *
     * @param time (start) in seconds
     * @return time in seconds
     */
    public static float getTimeSince(float time) {
        return MathUtils.nanoToSec * TimeUtils.nanoTime() - time;
    }

    /**
     * get smoothed time since last render call
     *
     * @return time interval in seconds
     */
    public static float getAverageDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    /**
     * get true measured time since last render call
     *
     * @return time interval in seconds
     */
    public static float getTrueDeltaTime() {
        return Gdx.graphics.getRawDeltaTime();
    }

    /**
     * check if time passed since last frame is smaller than given value
     * for debugging, reducing the frame rate
     * @param t
     * @return
     */
    public static boolean timeSinceLastFrameIsSmallerThan(float t){
        timeOfLastFrame+=getTrueDeltaTime();
        if (timeOfLastFrame>=t){
            timeOfLastFrame=0;
            return false;
        }
        return true;
    }

    /**
     * get a float[] array from an Array of Vector2
     * @param vectors
     * @return a float[] with the vectors as float x,y pairs
     */
    static public float[] toFloats(Array<Vector2> vectors){
        float[] result=new float[2*vectors.size];
        int i=0;
        for (Vector2 vector:vectors){
            result[i++]=vector.x;
            result[i++]=vector.y;
        }
        return result;
    }

    /**
     * create a float array that is a scaled copy of another float array
     * @param vertices
     * @param scale
     * @return
     */
    static public float[] scaled(float[] vertices,float scale){
        int length=vertices.length;
        float[] scaledVertices=new float[length];
        for (int i=0;i<length;i++){
            scaledVertices[i]=scale*vertices[i];
        }
        return scaledVertices;
    }
}
